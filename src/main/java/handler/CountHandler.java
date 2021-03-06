package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.StatusService;

import java.io.IOException;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-12 14:58
 **/
@ChannelHandler.Sharable
@Slf4j
public class CountHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        StatusService.INSTANCE.increaseAliveConnection(1);
        StatusService.INSTANCE.increaseConnections(1);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        StatusService.INSTANCE.decreaseAliveConnection(1);
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("CountHandler");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // StatusService.INSTANCE.decreaseAliveConnection(1);
        if (cause instanceof IOException){
            log.info(cause.toString());
        }
        // super.exceptionCaught(ctx, cause);
    }
}
