package snailftp;

import snailftp.queuefile.QueueFile;
import snailftp.view.FTPView;
import java.io.*;
import java.util.List;
import snailftp.site.Site;
/**
 *
 * @author jiangjizhong
 */
public class DefaultFTPController implements FTPController {

    private FTPView view;
    private FTPModel model;
    private FileQueue queue;

    public DefaultFTPController(FTPModel ftpModel) {
        this.model = ftpModel;
        this.queue = new DefaultFileQueue();
        view = new FTPView(this, model, queue);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        });
        //设置初始目录
        model.setLocalDirectory(Util.getPresentWorkDirectory());
    }

    public void login(Site site)
            throws IOException, FTPException {
        int port = site.getPort();
        if (port == 0) {
            port = FTPModel.DEFAULT_PORT;
        }

        String username = site.getUsername();
        if (username == null) {
            username = FTPModel.ANONYMOUS_USERNAME;
        }

        String password = site.getPassword();
        if (password == null) {
            password = FTPModel.ANONYMOUS_PASSWORD;
        }

        String host = site.getHost();
        this.model.setPassiveMode(site.isUsePassiveMode());
        this.model.open(host, port, username, password);
        if(model.getState() != FTPModel.State.UNCONNECTION){
            //连接成功
            if(site.getLocalDirectory() != null)
                model.setLocalDirectory(site.getLocalDirectory());
            if(site.getRemoteDirectory() != null)
                model.setWorkDirectory(site.getRemoteDirectory());
        }
    }

    public void transmit() throws IOException, FTPException {
        model.transmit(queue);
    }

    public void quit() throws IOException, FTPException {
        model.quit();
    }

    public void addQueueFile(List<QueueFile> list) {
        for (QueueFile file : list) {
            this.addQueueFile(file);
        }
    }

    public void setLocalDirectory(String localDirectory) {
        model.setLocalDirectory(localDirectory);
    }

    public void addQueueFile(QueueFile file) {
        queue.add(file);
    }

    public void changeWorkDirectory(String workDirectory) throws IOException, FTPException {
        model.setWorkDirectory(workDirectory);
    }

    public void clearQueue() {
        queue.clear();
    }

    public void moveToHead(QueueFile file) {
        queue.moveToHead(file);
    }

    public void moveToTail(QueueFile file) {
        queue.moveToTail(file);
    }

    public void remove(QueueFile file) {
        queue.remove(file);
    }

    public void list() throws IOException, FTPException {
        model.list();
    }

    public void makeDir(String dir) throws IOException, FTPException {
        model.makeDir(dir);
        model.list();
    }

    public void changeWorkDirectoryUp() throws IOException, FTPException {
        model.changeWorkDirectoryUp();
        model.list();
    }

    public void removeDirectory(String dir) throws IOException, FTPException {
        model.removeDirectory(dir);
        model.list();
    }

    public void removeFile(String file) throws IOException, FTPException {
        model.removeFile(file);
        model.list();
    }

    public void abort() throws IOException, FTPException {
        model.abort();
    }

    public void rename(String originName, String newName) throws IOException, FTPException {
        model.rename(originName, newName);
        model.list();
    }

    public void setIOBufferSize(int ioBufferSize) {
        model.setIOBufferSize(ioBufferSize);
    }

    public void executeCommand(String command) throws IOException, FTPException {
        model.executeCommand(command);
    }
}
