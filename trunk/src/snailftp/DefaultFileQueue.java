
package snailftp;

import snailftp.queuefile.QueueFile;
import java.util.*;
/**
 *
 * @author jiangjizhong
 */
public class DefaultFileQueue implements FileQueue{
    LinkedList<QueueFile> list = new LinkedList<QueueFile>();
    List<FileQueueObserver> observerList = new ArrayList<FileQueueObserver>();
    
    public void add(QueueFile file) {
        if(list.contains(file)){
            //不允许重复
            return;
        }
        list.add(file);
        notifyObservers();
    }

    private void notifyObservers(){
        for(FileQueueObserver observer : observerList){
            observer.update(this);
        }
    }
    
    public void remove(QueueFile file) {
        list.remove(file);
        notifyObservers();
    }
    
    public void moveToHead(QueueFile file) {
        list.remove(file);
        list.addFirst(file);
        notifyObservers();
    }

    public void moveToTail(QueueFile file) {
        list.remove(file);
        list.add(file);
        notifyObservers();
    }

    public void clear() {
        list.clear();
        notifyObservers();
    }

    public void addObserver(FileQueueObserver observer) {
        this.observerList.add(observer);
    }

    public void removeObserver(FileQueueObserver observer) {
        this.observerList.remove(observer);
    }

    public Iterator<QueueFile> iterator() {
        return this.list.iterator();
    }
    
    public QueueFile getFirst(){
        QueueFile file = list.poll();
        notifyObservers();
        return file;
    }
    
    public void addFirst(QueueFile queueFile){
        list.addFirst(queueFile);
        notifyObservers();
    }
}
