
package snailftp;

import java.io.*;
/**
 *
 * @author jiangjizhong
 */
public class ReplyParser {
    private BufferedReader reader = null;
    private String encoding = null;
    
    public ReplyParser(InputStream inputStream){
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    
    public ReplyParser(InputStream inputStream, String encoding)
            throws UnsupportedEncodingException{
        this.reader = new BufferedReader(new InputStreamReader(inputStream,encoding));
    }
    
    /**
     * 从输入流解析出一个Reply
     * @return
     */
    public Reply getReply() throws ConnectionClosedException {    
        try{
            String line = reader.readLine();
            if(line == null){
                throw new ConnectionClosedException();
            }
            Reply reply = new Reply();
            int code = Integer.parseInt(line.substring(0, 3));
            reply.setReplyCode(code);
            StringBuffer sb = new StringBuffer(line.substring(4));
            char c = line.charAt(3);
            if(c == '-'){
                while(true){
                    String nextLine = reader.readLine();
                    sb.append("\n");
                    if(nextLine.startsWith(code + " ")){
                        sb.append(line.substring(4));
                        break;
                    }else{
                        sb.append(line);
                    }
                }
            }
            reply.setReplyString(sb.toString());
            return reply;
        }catch(Exception exc){
            throw new ConnectionClosedException();
        }
    }
}
