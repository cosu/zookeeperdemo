package ro.cosu;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ZookeeperRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(ZookeeperRunner.class);


    private static void cleanupDirAtExit(Path path) {
        Runtime.getRuntime().addShutdownHook( new Thread(() ->{
            try {
                Files.walk(path, FileVisitOption.FOLLOW_LINKS)
                        .sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .peek(f -> LOGGER.info("deleting {}", f))
                        .forEach(File::delete);
            } catch (IOException e) {
                LOGGER.error("Error during deletion",e);
            }
        }));
    }


    private static ServerConfig getZookeeperProperties(Path path) throws IOException, QuorumPeerConfig.ConfigException {
        Properties props = new Properties();
        props.setProperty("dataDir", path.toAbsolutePath().toString());
        props.setProperty("clientPort", "6666");
        QuorumPeerConfig quorumPeerConfig = new QuorumPeerConfig();

        quorumPeerConfig.parseProperties(props);
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.readFrom(quorumPeerConfig);
        return serverConfig;
    }

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        Path path = Files.createTempDirectory(null);

        cleanupDirAtExit(path);

        Executor executor = Executors.newSingleThreadExecutor();

        final ZooKeeperServerMain zooKeeperServerMain = new ZooKeeperServerMain();

        executor.execute(() -> {
            try {
                zooKeeperServerMain.runFromConfig(getZookeeperProperties(path));
            } catch (IOException | QuorumPeerConfig.ConfigException e) {
                LOGGER.error("{}", e);
            }
        });
        ZooKeeperConnection zooKeeperConnection = new ZooKeeperConnection("localhost:6666");
        SessionWatcher sessionWatcher = new SessionWatcher();


        NodeReader.read(zooKeeperConnection.getZooKeeper(), "/foo", sessionWatcher);
        NodeWriter.write(zooKeeperConnection.getZooKeeper(), "/foo");

    }
}
