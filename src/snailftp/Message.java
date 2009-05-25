
package snailftp;

/**
 *
 * @author jiangjizhong
 */
public final class Message {
    public static final int ERROR = 1;
    public static final int WARN = 2;
    public static final int INFO = 3;
    public static final int DEBUG = 4;
    public static final int FATAL = 0;
    private static final String ERROR_STRING = "Error";
    private static final String WARN_STRING = "Warn";
    private static final String INFO_STRING = "Info";
    private static final String DEBUG_STRING = "Debug";
    private static final String FATAL_STRING = "Fatal";
    
    private String message;
    private int level;
    public Message(int level, String message){
        this.message = message;
        this.level = level;
    }
    
    public String getMessage(){
        return this.message;
    }
    
    public int getLevel(){
        return this.level;
    }
    
    public String getLevelString(){
        String result = null;
        switch(level){
            case ERROR : result = ERROR_STRING; break;
            case WARN : result = WARN_STRING; break;
            case DEBUG : result = DEBUG_STRING; break;
            case FATAL : result = FATAL_STRING; break;
            case INFO : result = INFO_STRING; break;
        }
        return result;
    }
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getLevelString());
        sb.append(":");
        sb.append(message);
        return sb.toString();
    }
}
