import handler.ChannelInitHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ZookeeperUtil;

import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @program: netty_study
 * @description: netty启动
 * @author: dyingstraw
 * @create: 2019-05-12 14:32
 **/
public class ServerMain {
    private static Logger logger = LoggerFactory.getLogger("Main");
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition STOP = LOCK.newCondition();
    public static void run(String[] args) {
        logger.debug("server init");
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup work = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(work,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitHandler())
            .bind(8765).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            work.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

    public static void main(final String[] args) throws InterruptedException, IOException, KeeperException {
        ZookeeperUtil zookeeperUtil = new ZookeeperUtil(){
            @Override
            public void callback() {
                run(args);

            }
        };
        zookeeperUtil.zkRegist();

        LOCK.lock();
        STOP.await();
    }
}
