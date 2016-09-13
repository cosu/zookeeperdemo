package ro.cosu;


import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.Charset;

public interface NodeWriter {
    static void write (ZooKeeper zooKeeper, String node, String data) throws IOException, KeeperException, InterruptedException {
        zooKeeper.create(node, data.getBytes(Charset.forName("UTF-8")), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
    }
}
