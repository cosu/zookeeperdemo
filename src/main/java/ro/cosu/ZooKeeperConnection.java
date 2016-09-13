package ro.cosu;

import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZooKeeperConnection {
    private static int SESSION_TIMEOUT = 2000;

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    private final ZooKeeper zooKeeper;

    ZooKeeperConnection(String address) throws IOException {
        SessionWatcher sessionWatcher = new SessionWatcher();
        zooKeeper = new ZooKeeper(address, SESSION_TIMEOUT, sessionWatcher);
    }
}
