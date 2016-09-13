package ro.cosu;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public interface NodeWriter {
    static void write (ZooKeeper zooKeeper, String name) throws IOException, KeeperException, InterruptedException {
        zooKeeper.create(name, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }
}
