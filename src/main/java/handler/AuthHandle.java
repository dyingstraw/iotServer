package handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import model.dto.RespDto;
import model.message.Message;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 16:28
 **/
@Slf4j
public class AuthHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandle");
        if (AuthDTO.class.isInstance(msg)){
            if (((AuthDTO)(msg)).getDevId()==1 && ((AuthDTO)(msg)).getDevKey().equals("123456")){
                ctx.pipeline().remove(this);
                Message<RespDto>message = new Message();
                message.setCmd((byte) 0);
                message.setVersion((byte) 0);
                message.setData(RespDto.SUCCESS());
                ChannelFuture f = ctx.writeAndFlush(message);
                f.addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("认证成功");
                    }
                });
            }else {
                log.info("auth failed");
                Message<RespDto>message = new Message();
                message.setCmd((byte) 0);
                message.setVersion((byte) 0);
                message.setData(RespDto.FAILED("认证失败"));
                ChannelFuture f = ctx.writeAndFlush(message);
                f.addListener(new ChannelFutureListener(){
                    public void operationComplete(ChannelFuture future) throws Exception {
                        log.info("认证失败");
                        ctx.close();
                    }
                });
            }
        }else {
            Message<RespDto>message = new Message();
            message.setCmd((byte) 0);
            message.setVersion((byte) 0);

            message.setData(RespDto.FAILED("还未认证"));
            ChannelFuture f = ctx.writeAndFlush(message);
            f.addListener(new ChannelFutureListener(){
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("还未认证");
                    ctx.close();
                }
            });
        }
    }
}
