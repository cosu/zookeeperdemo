package ro.cosu;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionWatcher implements Watcher{
    static Logger LOGGER = LoggerFactory.getLogger(SessionWatcher.class);

    public void process(WatchedEvent watchedEvent) {
        LOGGER.info(watchedEvent.toString());
    }
}
