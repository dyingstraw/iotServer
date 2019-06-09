package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import model.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-13 20:46
 **/
@ChannelHandler.Sharable
@Slf4j
public class MessageHandler extends SimpleChannelInboundHandler<String> {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (((Message)msg).getData().equals("hwcao")){
            ctx.writeAndFlush(new Message());
        }

        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        // ByteBuf copy = ((ByteBuf) msg).copy();
        // byte[] buff = new byte[copy.readableBytes()];
        // copy.getBytes(copy.readerIndex(),buff);
        log.info(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

        super.channelReadComplete(ctx);
    }
}
