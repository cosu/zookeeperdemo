package ro.cosu;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public interface  NodeReader {
    static void read(ZooKeeper zooKeeper, String name, Watcher watcher) throws KeeperException, InterruptedException {
        zooKeeper.exists(name, watcher);
    }
}
