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
        socketChannel.pipeline().addFirst(new RecordHandler());
        socketChannel.pipeline().addFirst(new AuthHandle());
        socketChannel.pipeline().addFirst(new BeforeHandler());
        socketChannel.pipeline().addFirst(new MyDecode());
        socketChannel.pipeline().addFirst(new MyEncoder());
    }
}
