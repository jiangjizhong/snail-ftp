
package snailftp;

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.net.*;
/**
 * 
 * @author jiangjizhong
 */
public final class Util {
    private Util(){
        //empty
    }
    /**
     * 判断两个对象是否相等，注意，两个null对象是相等的
     * @param obj1
     * @param obj2
     */
    public static boolean equals(Object obj1, Object obj2){
        if (obj1 == null && obj2 == null) return true;
        if (obj1 != null && obj1.equals(obj2)) return true;
        return false;
    }
    
    /**
     * 文件的最初目录(根目录)
     * 在windows中，是"c:\","d:\"等
     * 在linux中，是"/"
     * @param file
     * @return
     */
    public static File getDriver(File file){
        File absoluteFile = file.getAbsoluteFile();
        while(absoluteFile.getParent() != null){
            absoluteFile = absoluteFile.getParentFile();
        }
        return absoluteFile;
    }
    
    /**
     * 判断文件是否为根目录
     * 在Linux下，根目录是"/"
     * 在Windows下，根目录是磁盘盘符(不是桌面)
     * @param file
     * @return
     */
    public static boolean isRoot(File file){
        return file.equals(getDriver(file));
    }
    
    /**
     * 获取当前目录
     * @return
     */
    public static String getPresentWorkDirectory(){
        try{
            File f = new File(".");
            return f.getCanonicalPath();
        }catch(IOException exc){
            return null;
        }
    }
    /**
     * 获取一个图标
     * @param url
     * @return
     */
    public static Icon getIcon(String url){
        Icon icon = null;
        URL imageURL = Util.class.getResource(url);
        return imageURL == null ? new ImageIcon() : new ImageIcon(imageURL);
    }
    
    /**
     * 格式化文件大小
     * @param size
     * @return
     */
    public static String formatFileSize(long size){
        assert size >= 0;
        if(size == 0){
            return 0 + "";
        }
        final long B = 1;
        final long K = 1024 * B;
        final long M = 1024 * K;
        double newSize = 0.0;
        String unit = null;
        if(size > M){
            newSize = (double)size / M;
            unit = "M";
        }else if(size > K){
            newSize = (double)size / K;
            unit = "KB";
        }else{
            newSize = size;
            unit = "Byte";
        }
        return String.format("%,.2f%s", newSize, unit);
    }
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    
    public static String formatDate(Date date, String format){
        simpleDateFormat.applyPattern(format);
        return simpleDateFormat.format(date);
    }
    
    public static String formatTime(int second){
        final int SECOND_PER_HOUR = 3600;
        final int SECOND_PER_MINUTE = 60;
        StringBuffer sb = new StringBuffer();
        if(second > SECOND_PER_HOUR){
            sb.append(second / SECOND_PER_HOUR);
            sb.append("小时");
            second %= SECOND_PER_HOUR;
        }
        if(second > SECOND_PER_MINUTE){
            sb.append(second / SECOND_PER_MINUTE);
            sb.append("分");
            second %= SECOND_PER_MINUTE;
        }
        if(second > 0){
            sb.append(second);
            sb.append("秒");
        }
        return sb.toString();
    }
    
    public static InetAddress getLocalAddress(){
            try{
	         return InetAddress.getLocalHost();
            }catch(Exception exc){
                return null;
            }
    }

    public static void main(String[] args){
         System.out.println(Util.getLocalAddress());
    }
}
