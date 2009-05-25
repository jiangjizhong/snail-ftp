
package snailftp;

import java.io.*;
import java.util.*;
/**
 *
 * @author jiangjizhong
 */
public class FTPFileParser {
    private BufferedReader reader = null;
    private String encoding = null;
    private FTPFileFilter filter;
    public FTPFileParser(InputStream inputStream, FTPFileFilter filter){
        reader = new BufferedReader(new InputStreamReader(inputStream));
        this.filter = filter;
    }
    
    /**
     * 解析并返回一个长度为9的字符串数组
     * 注意！最后的文件名不包含前导空格和后导空格
     * @param line
     * @return
     */
    private String[] parse(String line){
        final int PARTS_NUM = 8;
        boolean blank = true;
        int wordCount = 0;
        int start = 0;
        String[] parts = new String[9];
        int i = 0;
        for(; i < line.length() && wordCount < PARTS_NUM; i++){
            char c = line.charAt(i);
            boolean isWhiteSpace = Character.isWhitespace(c);
            if(isWhiteSpace && !blank){
                //一个新单词完成了
                parts[wordCount++] = line.substring(start, i);
                blank = true;
            }
            if(!isWhiteSpace && blank){
                //一个新单词开始了
                start = i;
                blank = false;
            }
        }
        //解析名字，把剩下的一股脑全放进去，从i到最后
        parts[8] = line.substring(i);
        return parts;
    }
    
    public List<FTPFile> getFTPFileList() throws IOException, FTPException{
        List<FTPFile> list = new ArrayList<FTPFile>();
        String line = null;
        while((line = reader.readLine()) != null){
            FTPFile file = new FTPFile();
            String[] parts = this.parse(line);
            file.setAttribute(parts[0]);
            file.setOwner(parts[2]);
            file.setGroup(parts[3]);
            file.setSize(Long.parseLong(parts[4]));
            Calendar calendar = Calendar.getInstance();
            if(parts[7].contains(":")){
                //是时间，不是年
                String[] time = parts[7].split(":", 2);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            }else{
                calendar.set(Calendar.YEAR, Integer.parseInt(parts[7]));
            }
            file.setCreateAt(calendar.getTime());
            file.setName(parts[8]);
            if(this.filter.accept(file)){
                list.add(file);
            }
        }
        return list;
    }
    
    private int getMonth(String str){
        if(str.equalsIgnoreCase("jan")){
            return Calendar.JANUARY;
        }else if(str.equalsIgnoreCase("feb")){
            return Calendar.FEBRUARY;
        }else if(str.equalsIgnoreCase("mar")){
            return Calendar.MARCH;
        }else if(str.equalsIgnoreCase("apr")){
            return Calendar.APRIL;
        }else if(str.equalsIgnoreCase("may")){
            return Calendar.MAY;
        }else if(str.equalsIgnoreCase("jun")){
            return Calendar.JULY;
        }else if(str.equalsIgnoreCase("jul")){
            return Calendar.JULY;
        }else if(str.equalsIgnoreCase("aug")){
            return Calendar.AUGUST;
        }else if(str.equalsIgnoreCase("sep")){
            return Calendar.SEPTEMBER;
        }else if(str.equalsIgnoreCase("oct")){
            return Calendar.OCTOBER;
        }else if(str.equalsIgnoreCase("nov")){
            return Calendar.NOVEMBER;
        }else
            return Calendar.DECEMBER;
    }
}
