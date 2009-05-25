
package snailftp.site;

import java.io.Serializable;
/**
 * 代表ftp站点的实体类。实现了
 * @author jiangjizhong
 */
public class Site implements Serializable, Comparable<Site> {
    private static final long serialVersionUID = 100L;
    private String name;
    private String host;
    private int port = 21;
    private String username;
    private String password;
    private String localDirectory;
    private String remoteDirectory;
    private boolean usePassiveMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        if(this.host != null && this.host.isEmpty()){
            this.host = null;
        }
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        if(this.username != null && this.username.isEmpty()){
            this.username = null;
        }
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.getUsername() == null ? null : password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocalDirectory() {
        if(this.localDirectory != null && this.localDirectory.isEmpty()){
            this.localDirectory = null;
        }
        return localDirectory;
    }

    public void setLocalDirectory(String localDirectory) {
        this.localDirectory = localDirectory;
    }

    public String getRemoteDirectory() {
        if(this.remoteDirectory != null && this.remoteDirectory.isEmpty()){
            this.remoteDirectory = null;
        }
        return remoteDirectory;
    }

    public void setRemoteDirectory(String remoteDirectory) {
        if(remoteDirectory.startsWith("/"))
            this.remoteDirectory = remoteDirectory;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == this) return true;
        if(!(obj instanceof Site)) return false;
        Site other = (Site)obj;
        if(this.name == null && other.name == null) return true;
        if(this.name != null && this.name.equals(other)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    public int compareTo(Site site) {
        return this.name.compareTo(site.name);
    }

    public boolean isUsePassiveMode() {
        return usePassiveMode;
    }

    public void setUsePassiveMode(boolean usePassiveMode) {
        this.usePassiveMode = usePassiveMode;
    }
}
