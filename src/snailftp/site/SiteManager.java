package snailftp.site;

import java.io.*;
import java.util.*;

/**
 * 站点管理器，负责站点的管理，主要是CRUD
 * @author jiangjizhong
 */
public class SiteManager {

    private static SiteManager siteManager = null;
    private static final String SITE_FILE = "site";
    private Map<String, Site> sites = new HashMap<String, Site>();

    private SiteManager() {
        //读取已经保存的站点
        try {
            this.load();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 重新加载所有站点
     */
    public void load() throws IOException {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(SITE_FILE));
            while (true) {
                Site site = (Site) ois.readObject();
                sites.put(site.getName(), site);
            }
        } catch (EOFException exc) {
            //必然出现这个异常
        } catch (ClassNotFoundException cnfe) {
            //非常严重，但是一般不会出现
            cnfe.printStackTrace();
        } finally {
            if(ois != null){
                ois.close();
            }
        }
    }

    /**
     * 保存当前站点
     */
    public void save() throws IOException {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(SITE_FILE));
            for (Site site : sites.values()) {
                oos.writeObject(site);
            }
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    public static SiteManager getInstance() {
        if (siteManager == null) {
            siteManager = new SiteManager();
        }
        return siteManager;
    }

    public boolean add(Site site) {
        if (this.sites.containsKey(site.getName())) {
            return false;
        }
        this.sites.put(site.getName(), site);
        return true;
    }

    public Site remove(String name) {
        Site site = this.sites.remove(name);
        return site;
    }

    public boolean update(Site site) {
        if (this.sites.containsKey(site.getName())) {
            this.sites.put(site.getName(), site);
            return true;
        }
        return false;
    }

    public Site get(String name) {
        return this.sites.get(name);
    }

    public Collection<Site> sites() {
        return this.sites.values();
    }
}
