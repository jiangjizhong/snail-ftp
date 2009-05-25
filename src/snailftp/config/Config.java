
package snailftp.config;

import java.util.*;
import java.io.*;
/**
 * App配置
 * 单例
 * Config不关心有多少配置项。它只负责加载和保存配置项，提供一些简单的转换方法。
 * @author jiangjizhong
 */
public class Config {

    public static String ANTIIDLE_ENABLE_KEY = "antiIdle.enable";
    public static String ANTIIDLE_WAIT_KEY = "antiIdle.wait";
    public static String ANTIIDLE_COMMAND_KEY = "antiIdle.command";
    
    private static Config config = null;
    private static final String CONFIG_FILENAME = "snailftp.conf";
    private Properties properties = new Properties();
    
    
    private Config(){
        this.load();
    }
    
    public void load(){
        try{
            properties.load(new FileInputStream(Config.CONFIG_FILENAME));
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    
    public void save(){
        try{
            properties.store(new FileOutputStream(Config.CONFIG_FILENAME), "SnailFTP配置文件");
        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
    
    public static Config getInstance(){
        //使用double-check lock
        if(config == null){
            synchronized(Config.class){
                if(config == null){
                    config = new Config();
                }
            }
        }
        return config;
    }
    
    private String getValue(String key){
        return this.properties.getProperty(key);
    }
    
    public boolean getBoolean(String key, boolean defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : Boolean.parseBoolean(value);
    }
    
    public long getLong(String key, long defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : Long.parseLong(value);
    }
    
    public int getInteger(String key, int defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : Integer.parseInt(value);
    }
    
    public List<String> getList(String key, String splitRegex){
        List<String> list = new ArrayList<String>();
        String value = this.getValue(key);
        if(value == null){
            return list;
        }
        String[] parts = value.split(splitRegex);
        for(String s : parts){
            list.add(s);
        }
        return list;
    }
    
    public float getFloat(String key, float defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : Float.parseFloat(value);
    }
    
    public double getDouble(String key, double defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : Double.parseDouble(value);
    }
    
    public String getString(String key, String defaultValue){
        String value = this.getValue(key);
        return value == null ? defaultValue : value;
    }
    /**
     * 默认返回false
     * @param key
     * @return
     */
    public boolean getBoolean(String key){
        return this.getBoolean(key, false);
    }
    
    /**
     * 默认返回0
     * @param key
     * @return
     */
    public long getLong(String key){
        return this.getLong(key, 0L);
    }
    
    /**
     * 默认返回0
     * @param key
     * @return
     */
    public int getInteger(String key){
        return this.getInteger(key, 0);
    }
    /**
     * 默认返回0
     * @param key
     * @return
     */
    public float getFloat(String key){
        return this.getFloat(key, 0.0f);
    }
    
    /**
     * 默认返回0
     * @param key
     * @return
     */
    public double getDouble(String key){
        return this.getDouble(key, 0.0);
    }
    
    /**
     * 默认返回null
     * @param key
     * @return
     */
    public String getString(String key){
        return this.properties.getProperty(key);
    }
    
    public void set(String key, Object value){
        this.properties.setProperty(key, String.valueOf(value));
    }
}
