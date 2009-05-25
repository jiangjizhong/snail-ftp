package snailftp;

import snailftp.queuefile.QueueFile;
import java.util.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author jiangjizhong
 */
public class DefaultFTPModel extends AbstractFTPModel {

    private static final InetAddress localAddress;
    private String username;
    private int port;
    private String password;
    private String host;
    
    private boolean usePasv = false;

    static {
        localAddress = Util.getLocalAddress();
    }
    private String localDirectory = null;
    private String workDirectory = "";
    //注意：以上两个字段在任何时候都以"/"结尾！
    Socket socket = null;
    private Reply reply = null;
    private List<FTPFile> ftpFileList = new ArrayList<FTPFile>();
    private ReplyParser replyParser = null;
    private State state = State.UNCONNECTION;

    public DefaultFTPModel() {
    }

    public synchronized void open(String host, int port, String username, String password)
            throws FTPException {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        try {
            if (this.state != State.UNCONNECTION) {
                this.quit();
            }
            workDirectory = "";
            socket = new Socket(host, port);
            replyParser = new ReplyParser(socket.getInputStream());
            this.getReplyFromSocket();
            if ((this.getReply().getReplyCode() != Reply.SERVICE_READY)) {
                throw new FTPException("连接失败");
            }
            this.sendCommand(DefaultFTPModel.COMMAND_USER, username);
            if (!this.getReply().isPositiveIntermediate()) {
                return;
            }
            //用户名OK
            this.sendCommand(DefaultFTPModel.COMMAND_PASS, password);
            if (!this.getReply().isPositiveCompletion()) {
                return;
            }
        } catch (UnknownHostException uhe) {
            throw new FTPException("未知主机：" + host);
        } catch (IOException exc) {
            throw new FTPException("无法连接" + host + "，端口:" + port);
        }
        //初始化，忽略异常
        try {
            this.getWorkDirectoryFromSocket();
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        this.setState(State.IDLE);
    }
    
    private void getFileListFromSocket() throws IOException, FTPException {
        this.sendCommand(COMMAND_TYPE, "A");
        if (!this.getReply().isPositiveCompletion()) {
            return;
        }
        Socket dataSocket = this.openDataConnection(COMMAND_LIST, null);
        if (dataSocket == null) {
            return;
        }
        FTPFileParser parser = new FTPFileParser(dataSocket.getInputStream(), getFtpFileFilter());
        this.ftpFileList = parser.getFTPFileList();
        dataSocket.close();
        this.getReplyFromSocket();
        for (FTPFileListObserver o : this.ftpFileListObserver) {
            o.update(ftpFileList);
        }
    }

    public List<FTPFile> getFileList() throws IOException, FTPException {
        return this.ftpFileList;
    }

    public String getWorkDirectory() {
        return this.workDirectory;
    }

    public void setWorkDirectory(String dir) throws IOException, FTPException {
        if (dir.equals(this.workDirectory)) {
            return;
        }
        this.sendCommand("CWD", dir);
        if (this.getReply().isPositiveCompletion()) {
            this.getWorkDirectoryFromSocket();
        }
    }

    public void setLocalDirectory(String dir) {
        if (!dir.equals(this.localDirectory)) {
            this.localDirectory = dir;
            for (CurrentDirectoryObserver o : currentDirectoryObserver) {
                o.update(localDirectory, null);
            }
        }
    }

    public void quit() throws IOException, FTPException {
        this.sendCommand(DefaultFTPModel.COMMAND_QUIT);
        if (this.getReply().isPositiveCompletion()) {
            this.ftpFileList.clear();
            this.socket.close();
            this.setState(State.UNCONNECTION);
        }
    }

    public String getLocalDirectory() {
        return this.localDirectory;
    }

    public Reply getReply() {
        return this.reply;
    }

    private void getReplyFromSocket() throws FTPException {
        try {
            this.reply = replyParser.getReply();
        } catch (ConnectionClosedException cce) {
            this.setState(State.UNCONNECTION);
            throw new FTPException("连接已经关闭");
        }
        for (ReplyObserver observer : replyObserver) {
            observer.update(reply);
        }
    }

    private synchronized int sendCommand(String command) throws FTPException {
        try {
            OutputStream os = socket.getOutputStream();
            byte[] bytes = command.getBytes();
            os.write(bytes);
            os.write(new byte[]{'\r', '\n'});
            for (CommandObserver o : this.commandObserver) {
                try {
                    o.update(command);
                } catch (Exception exc) {
                    //empty
                }
            }
            this.getReplyFromSocket();
        } catch (FTPException fexc) {
            throw fexc;
        } catch (Exception exc) {
            exc.printStackTrace();
            this.setState(State.UNCONNECTION);
            try {
                socket.close();
            } catch (IOException ioe) {
                //emtpy
            }
        }
        return this.getReply().getReplyCode();
    }

    private int sendCommand(String command, String arg) throws FTPException {
        StringBuffer sb = new StringBuffer(command);
        sb.append(" ");
        sb.append(arg);
        return this.sendCommand(sb.toString());
    }
    
    private InetSocketAddress parsePasvReply(){
        String replyString = this.getReply().getReplyString();
        int numberOfComma = 0;
        int start = 0;
        int end = -1;
        int highByte = -1;
        int lowByte = -1;
        for(int i = replyString.length(); i > 0; i--){
            char c = replyString.charAt(i - 1);
            if(Character.isDigit(c)){
                if(end < 0)
                    end = i;
            }else if(c == ','){
                numberOfComma++;
                if(numberOfComma == 1){
                    //端口低位
                    lowByte = Integer.parseInt(replyString.substring(i, end));
                    end = i - 1;
                }else if(numberOfComma == 2){
                    highByte = Integer.parseInt(replyString.substring(i, end));
                    end = -1;
                }
            }else if(numberOfComma >= 5){
                start = i;
                break;
            }
        }
        String ip = replyString.substring(start, end);
        ip = ip.replace(',', '.');
        int port = (highByte << 8) + lowByte;
        return new InetSocketAddress(ip, port);
    }
    
    private Socket openPasvDataConnection(String command, String arg)
            throws IOException, FTPException {
        this.sendCommand(FTPModel.COMMAND_PASV);
        Socket dataSocket = null;
        if(this.getReply().isPositiveCompletion()){
            //解析并链接。
            InetSocketAddress address = parsePasvReply();
            dataSocket = new Socket();
            dataSocket.connect(address);
            if(arg == null)
                this.sendCommand(command);
            else
                this.sendCommand(command, arg);
            if(this.getReply().isPositivePreliminary()){
                return dataSocket;
            }
        }
        return null;
    }
    private Socket openPortDataConnection(String command, String arg)
            throws IOException, FTPException {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(null);
        int port = serverSocket.getLocalPort();
        int highByte = port & 0xFF00;
        highByte = highByte >> 8;
        int lowByte = port & 0x00FF;
        String formattedLocalAddress = localAddress.getHostAddress().replace('.', ',');
        this.sendCommand("PORT", formattedLocalAddress + "," + highByte + "," + lowByte);
        if (this.getReply().isPositiveCompletion()) {
            if (arg != null) {
                this.sendCommand(command, arg);
            } else {
                this.sendCommand(command);
            }
            if (this.getReply().isPositivePreliminary()) {
                return serverSocket.accept();
            }
        }
        return null;
    }
    
    private Socket openDataConnection(String command, String arg)
            throws IOException, FTPException {
        //判断使用主动模式还是被动模式
        Socket dataSocket = null;
        if(usePasv){
            dataSocket = this.openPasvDataConnection(command, arg);
            if(dataSocket != null)
                return dataSocket;
        }
        return openPortDataConnection(command, arg);
    }

    /**
     * 获取当前目录并与内存中缓存的目录比较，如果目录变了，会通知目录监听器
     * @return
     * @throws jftp.FTPException
     * @throws java.io.IOException
     */
    private String getWorkDirectoryFromSocket() throws IOException, FTPException {
        this.sendCommand(COMMAND_PWD);
        if (this.getReply().isPositiveCompletion()) {
            int pos = this.getReply().getReplyString().indexOf('"', 1);
            String pwd = this.getReply().getReplyString().substring(1, pos);
            if (!pwd.equals(this.workDirectory)) {
                //先获取目录下的文件，再通知各个目录监听器
                this.getFileListFromSocket();
                this.workDirectory = pwd;
                for (CurrentDirectoryObserver o : currentDirectoryObserver) {
                    o.update(null, workDirectory);
                }
            }
        }
        return this.workDirectory;
    }

    boolean existFileInFTPFileList(String filename, boolean isDirectory) {
        for (FTPFile f : this.ftpFileList) {
            if (f.isDirectory() == isDirectory && f.getName().equals(filename)) {
                return true;
            }
        }
        return false;
    }

    void transmitFile(QueueFile file, boolean upload) throws IOException, FTPException {
        Socket dataSocket = null;
        try {
            this.sendCommand(FTPModel.COMMAND_TYPE, "I");
            if (!this.getReply().isPositiveCompletion()) {
                return;
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            if(upload){
                dataSocket = this.openDataConnection(FTPModel.COMMAND_STOR, file.getName());
                outputStream = dataSocket.getOutputStream();
                inputStream = new FileInputStream(file.getFrom());
            }else{
                dataSocket = this.openDataConnection(FTPModel.COMMAND_RETR, file.getName());
                inputStream = dataSocket.getInputStream();
                outputStream = new FileOutputStream(file.getTo());
            }
            byte[] buffer = new byte[this.getIOBufferSize()];
            long start = System.currentTimeMillis();
            long size = file.getSize();
            long transfer = 0;
            this.setCurrentFilename(file.getName());
            this.setTotalSize(size);
            int readLen = 0;
            while ((readLen = inputStream.read(buffer)) > -1) {
                outputStream.write(buffer, 0, readLen);
                transfer += readLen;
                this.setTransferedSize(transfer);
                this.setTimePassed(System.currentTimeMillis() - start);
            }
            double secondTime = (double)getTimePassed() / 1000;
            this.pulishMessage(Message.INFO, "文件" + file.getName() + "传输完毕，用时" + String.format("%.2f秒", secondTime));
            outputStream.close();
            inputStream.close();
        } catch (NullPointerException npe) {
            this.pulishMessage(Message.ERROR, "无法打开数据连接，跳过文件:" + file.getName());
            npe.printStackTrace();
        } catch (IOException exc) {
            //读写错误，无法恢复，直接跳过
            this.pulishMessage(Message.ERROR, "读写错误，终止本次文件传输:" + exc.getMessage());
            exc.printStackTrace();
        } finally {
            if (dataSocket != null) {
                //有一定的传输
                dataSocket.close();
                this.getReplyFromSocket();
                this.getFileListFromSocket();
            }
        }
    }

    boolean cancel = false;
    OverridePolicy overridePolicy;

    public void transmit(FileQueue queue) throws IOException, FTPException {
        QueueFile queueFile = null;
        overridePolicy = null;
        cancel = false;//每次传输前要初始化cancel
        this.setState(State.TRANSMIT);
        for (FileTransmitObserver o : this.fileTransmitObserver) {
            o.beginTransmit();
        }
        while (!cancel && (queueFile = queue.getFirst()) != null) {
            queueFile.transmit(this, queue);
        }
        for (FileTransmitObserver o : this.fileTransmitObserver) {
            o.endTransmit();
        }
        this.setState(State.IDLE);
    }

    public void list() throws IOException, FTPException {
        this.getFileListFromSocket();
    }

    public void makeDir(String dir) throws IOException, FTPException {
        this.sendCommand(COMMAND_MKD, dir);
    }

    private void pulishMessage(int level, String message) {
        Message m = new Message(level, message);
        for (MessageObserver o : this.messageObserver) {
            o.update(m);
        }
    }

    public int getPort() {
        return this.port;
    }

    public String getHost() {
        return this.host;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void changeWorkDirectoryUp() throws IOException, FTPException {
        this.sendCommand(FTPModel.COMMAND_CDUP);
        this.getWorkDirectoryFromSocket();
    }

    public void removeDirectory(String dir) throws IOException, FTPException {
        //忽略"."和".."为名的文件夹
        if (dir.contains(".") || dir.contains("..")) {
            return;
        //算法，先进入dir，删除里面所有的文件（包括子目录），再删除dir
        //为了避免多次没必要的目录切换，对FTPFileList排序，先删除文件，再删除目录
        }
        this.setWorkDirectory(dir);
        Collections.sort(this.ftpFileList);
        List<FTPFile> fileListCopy = this.ftpFileList;
        for (int i = fileListCopy.size(); i > 0; i--) {
            FTPFile ftpFile = fileListCopy.get(i - 1);
            if (ftpFile.isDirectory()) {
                this.removeDirectory(ftpFile.getName());
            } else {
                this.removeFile(ftpFile.getName());
            }
        }
        //删除dir
        this.changeWorkDirectoryUp();
        this.sendCommand(FTPModel.COMMAND_RMD, dir);
    }

    public void removeFile(String file) throws IOException, FTPException {
        this.sendCommand(FTPModel.COMMAND_DELE, file);
    }

    public void abort() throws IOException, FTPException {
        this.cancel = true;
        this.sendCommand(COMMAND_ABOR);
    }

    public void rename(String originName, String newName) throws IOException, FTPException {
        this.sendCommand(FTPModel.COMMAND_RNFR, originName);
        if (this.getReply().isPositiveIntermediate()) {
            this.sendCommand(FTPModel.COMMAND_RNTO, newName);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        for (ModelStateObserver o : this.modelStateObserver) {
            o.update(state);
        }
    }

    private OverridePolicy getOverridePolicy(QueueFile file) {
        overridePolicy = this.overridePolicyMaker.getOverridePolicy(file);
        switch (overridePolicy) {
            case SKIP:
                overridePolicy = null;
            case SKIP_ALL:
                this.pulishMessage(Message.INFO, "跳过文件：" + file.getName());
                break;
            case OVERRIDE:
                overridePolicy = null;
            case OVERRIDE_ALL:
                break;
            case CANCEL:
                this.pulishMessage(Message.INFO, "已经取消上传");
                this.cancel = true;
        }
        return overridePolicy;
    }

    public void uploadFile(QueueFile file) throws IOException, FTPException {
        //判断文件是否存在
        List<FTPFile> list = this.getFileList();
        boolean exist =  false;
        for(FTPFile f : list){
            if(!f.isDirectory() && f.getName().equals(file.getName())){
                exist = true;
                break;
            }
        }
        if (exist) {
            if (overridePolicy == null) {
                overridePolicy = this.getOverridePolicy(file);
            }
            if((overridePolicy != OverridePolicy.OVERRIDE) && (overridePolicy != OverridePolicy.OVERRIDE_ALL)) {
                return;
            }
        }
        this.transmitFile(file, true);
    }

    public void downloadFile(QueueFile file) throws IOException, FTPException {
        File localFile = new File(file.getTo());
        if(localFile.exists() && localFile.isFile()){
            if (overridePolicy == null) {
                overridePolicy = this.getOverridePolicy(file);
            }
            if((overridePolicy != OverridePolicy.OVERRIDE) && (overridePolicy != OverridePolicy.OVERRIDE_ALL)) {
                return;
            }
        }
        this.transmitFile(file, false);
    }

    public void executeCommand(String command) throws IOException, FTPException {
        this.sendCommand(command);
    }

    public void setPassiveMode(boolean b) {
        this.usePasv = b;
    }
}
