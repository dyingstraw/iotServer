package handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import model.dto.RespDto;
import model.message.Message;
import service.AuthService;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 16:28
 **/
@Slf4j
public class AuthHandle extends ChannelInboundHandlerAdapter {

    AuthService authService = AuthService.getInstance();

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandle");
        // 鉴权
        if (AuthDTO.class.isInstance(msg)) {
            if (authService.isExit((AuthDTO) msg, true)) {
                ctx.pipeline().remove(this);
                // Message<RespDto> message = new Message();
                // message.setCmd( 0);
                // message.setVersion(0);
                // message.setData(true);
                ChannelFuture f = ctx.writeAndFlush(Message.success());
                f.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("认证成功");
                    }
                });
            } else {
                log.info("auth failed");
                // Message<RespDto> message = new Message();
                // message.setCmd(0);
                // message.setVersion(0);
                // message.setData(RespDto.FAILED("认证失败"));
                ChannelFuture f = ctx.writeAndFlush(Message.failed());
                f.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("认证失败");
                        ctx.close();
                    }
                });
            }
        } else {
            Message<RespDto> message = new Message();
            message.setCmd((int) 0);
            message.setVersion((int) 0);

            message.setData(RespDto.FAILED("还未认证"));
            ChannelFuture f = ctx.writeAndFlush(message);
            f.addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("还未认证");
                    ctx.close();
                }
            });
        }
    }
}
