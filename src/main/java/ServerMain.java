import handler.ChannelInitHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: netty_study
 * @description: netty启动
 * @author: dyingstraw
 * @create: 2019-05-12 14:32
 **/
public class ServerMain {
    static Logger logger = LoggerFactory.getLogger("Main");
    public static void main(String[] args) {
        logger.debug("server init");
        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup work = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            bootstrap.group(work,childGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitHandler())
            .bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            work.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
