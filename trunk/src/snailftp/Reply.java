
package snailftp;

/**
 *
 * @author jiangjizhong
 */
public class Reply {
    
    public static final int RESTART_MARKER = 110;
    public static final int SERVICE_NOT_READY = 120;
    public static final int DATA_CONNECTION_ALREADY_OPEN = 125;
    public static final int FILE_STATUS_OK = 150;
    public static final int COMMAND_OK = 200;
    public static final int COMMAND_IS_SUPERFLUOUS = 202;
    public static final int SYSTEM_STATUS = 211;
    public static final int DIRECTORY_STATUS = 212;
    public static final int FILE_STATUS = 213;
    public static final int HELP_MESSAGE = 214;
    public static final int NAME_SYSTEM_TYPE = 215;
    public static final int SERVICE_READY = 220;
    public static final int SERVICE_CLOSING_CONTROL_CONNECTION = 221;
    public static final int DATA_CONNECTION_OPEN = 225;
    public static final int CLOSING_DATA_CONNECTION = 226;
    public static final int ENTERING_PASSIVE_MODE = 227;
    public static final int USER_LOGGED_IN = 230;
    public static final int FILE_ACTION_OK = 250;
    public static final int PATHNAME_CREATED = 257;
    public static final int NEED_PASSWORD = 331;
    public static final int NEED_ACCOUNT = 332;
    public static final int FILE_ACTION_PENDING = 350;
    public static final int SERVICE_NOT_AVAILABLE = 421;
    public static final int CANNOT_OPEN_DATA_CONNECTION = 425;
    public static final int TRANSFER_ABORTED = 426;
    public static final int FILE_ACTION_NOT_TAKEN = 450;
    public static final int ACTION_ABORTED = 451;
    public static final int INSUFFICIENT_STORAGE = 452;
    public static final int UNRECOGNIZED_COMMAND = 500;
    public static final int SYNTAX_ERROR_IN_ARGUMENTS = 501;
    public static final int COMMAND_NOT_IMPLEMENTED = 502;
    public static final int BAD_COMMAND_SEQUENCE = 503;
    public static final int COMMAND_NOT_IMPLEMENTED_FOR_PARAMETER = 504;
    public static final int NOT_LOGGED_IN = 530;
    public static final int NEED_ACCOUNT_FOR_STORING_FILES = 532;
    public static final int FILE_UNAVAILABLE = 550;
    public static final int PAGE_TYPE_UNKNOWN = 551;
    public static final int STORAGE_ALLOCATION_EXCEEDED = 552;
    public static final int FILE_NAME_NOT_ALLOWED = 553;
    
    private int replyCode;
    private String replyString;

    public int getReplyCode() {
        return replyCode;
    }

    public void setReplyCode(int replyCode) {
        this.replyCode = replyCode;
    }

    public String getReplyString() {
        return replyString;
    }

    public void setReplyString(String replyString) {
        this.replyString = replyString;
    }
    
    public boolean isPositivePreliminary(){
        return (replyCode >= 100 && replyCode < 200);
    }
    
    public boolean isPositiveIntermediate(){
        return (replyCode >= 300 && replyCode < 400);
    }
    
    public boolean isPositiveCompletion(){
        return (replyCode >= 200 && replyCode < 300);
    }
    
    public boolean isNegativeTransient(){
        return (replyCode >= 400 && replyCode < 500);
    }
    
    public boolean isNegativePermanent(){
        return (replyCode >= 500 && replyCode < 600);
    }
    
    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(this.getReplyCode());
        sb.append(" ");
        sb.append(this.getReplyString());
        return sb.toString();
    }
}
