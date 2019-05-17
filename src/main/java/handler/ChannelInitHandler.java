package handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @program: netty_study
 * @description: 初始化
 * @author: dyingstraw
 * @create: 2019-05-12 14:56
 **/
public class ChannelInitHandler extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new CountHandler());
        socketChannel.pipeline().addLast(new MessageHandler());
    }
}
