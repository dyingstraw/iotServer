package util;

import com.sun.corba.se.impl.activation.ServerMain;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-29 20:37
 **/
@Slf4j
public class ZookeeperUtil implements Watcher {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void callback(){

    }


    public void zkRegist() throws IOException, KeeperException, InterruptedException {
        ZooKeeper zooKeeper = new ZooKeeper(ConfigUtil.get("app.zk.addr"), 3000, this);
        countDownLatch.await();
        String appName = ConfigUtil.get("app.name");
        String s = zooKeeper.create("/iot/what/" + appName, "1234".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        log.info("1111111111{}", s);

    }

    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
            countDownLatch.countDown();
            log.info("已经注册到注册中心，将要启动netty线程！");
            callback();
        }

    }

    // public static void main(String[] args) throws InterruptedException, IOException, KeeperException {
    //     new ZookeeperUtil().zkRegist();
    // }
}
