package util;


import exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;


import java.io.IOException;
import java.util.List;
import java.util.Vector;
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
    private static ZooKeeper zooKeeper;
    private static List<String> children = new Vector<String>();


    public static List<String> getChildren() throws CustomException {
        if (zooKeeper==null || children==null){
            throw new CustomException("zk还未启动！");
        }
        return children;
    }

    public void callback(){

    }


    public void zkRegist() throws IOException, KeeperException, InterruptedException {
        zooKeeper = new ZooKeeper(ConfigUtil.get("app.zk.addr"), 3000, this);
        countDownLatch.await();
        String appName = ConfigUtil.get("app.name");
        String s = zooKeeper.create("/iot/what/" + appName, "1234".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        List<String> cs = zooKeeper.getChildren("/iot/what",true);

        log.info("zk 服务注册成功,已注册的服务有：\n{}", cs);

    }

    @Override
    public void process(WatchedEvent watchedEvent) {

        if (watchedEvent.getState()==Event.KeeperState.SyncConnected){
            if (Event.EventType.None == watchedEvent.getType() && null==watchedEvent.getPath()) {
                countDownLatch.countDown();
                log.info("已经注册到注册中心，将要启动netty线程！");
                callback();
            /**
             * 有服务注册的时候更新服务列表
             */
            }else if (watchedEvent.getType() == Event.EventType.NodeChildrenChanged){
                log.info("孩子节点变化!");
                try {
                    children = zooKeeper.getChildren("/iot/what" ,true);
                    log.info("当前服务列表：\n{}",children);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }

    }
}
