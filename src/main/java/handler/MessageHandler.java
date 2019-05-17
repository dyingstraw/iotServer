package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-13 20:46
 **/
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<String> {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // ByteBuf copy = ((ByteBuf) msg).copy();
        // byte[] buff = new byte[copy.readableBytes()];
        // copy.getBytes(copy.readerIndex(),buff);
        logger.info(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        super.channelReadComplete(ctx);
    }
}
