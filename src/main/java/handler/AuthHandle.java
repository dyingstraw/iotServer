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
import service.BlanceService;
import util.ZookeeperUtil;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 16:28
 **/
@Slf4j
public class AuthHandle extends ChannelInboundHandlerAdapter {

    AuthService authService = AuthService.getInstance();
    BlanceService blanceService = BlanceService.getInstance();

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandle");
        // 鉴权
        if (AuthDTO.class.isInstance(msg)) {

            /*先判断负载是否均衡均衡，如果本机服务可以直接接受设备连接，则认证成功，
            * 如果本机不接受此设备，则下发给设备重新认证的命令
            *
            * */
            log.info("{}",ZookeeperUtil.getChildren());

            if (ZookeeperUtil.getChildren()!=null && ZookeeperUtil.getChildren().size()>1){
                log.info("负载均衡：{}",ZookeeperUtil.getChildren());




            }



            if (authService.isExit((AuthDTO) msg, true)) {
                ctx.pipeline().remove(this);
                ChannelFuture f = ctx.writeAndFlush(Message.success());
                f.addListener(new ChannelFutureListener() {
                    @Override
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
                    @Override
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
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("还未认证");
                    ctx.close();
                }
            });
        }
    }
}
