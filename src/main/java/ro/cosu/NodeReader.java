package ro.cosu;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public interface  NodeReader {
    static Logger LOGGER = LoggerFactory.getLogger(NodeReader.class);
    static void exists(ZooKeeper zooKeeper, String name, Watcher watcher) throws KeeperException, InterruptedException {
        zooKeeper.exists(name, watcher);
    }

    static String read(ZooKeeper zooKeeper, String node) throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData(node, false, stat);
        LOGGER.info("Stat: {}", stat);
        return new String(data, Charset.forName("UTF-8"));
    }
}
