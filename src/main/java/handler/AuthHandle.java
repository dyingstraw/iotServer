package handler;

import com.sun.deploy.config.Config;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.RunTimeStatus;
import model.dto.AuthDTO;
import model.dto.RespDto;
import model.message.Message;
import model.message.MessageType;
import service.AuthService;
import service.BlanceService;
import service.RunTimeStatusService;
import service.impl.RunTimeStatusServiceImpl;
import util.CommonUtil;
import util.ConfigUtil;
import util.RedisUtil;
import util.ZookeeperUtil;

import java.util.ArrayList;
import java.util.Map;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 16:28
 **/
@Slf4j
public class AuthHandle extends ChannelInboundHandlerAdapter {

    AuthService authService = AuthService.getInstance();
    private static RunTimeStatusService runTimeStatusService = RunTimeStatusServiceImpl.getINSTANCE();

    BlanceService blanceService = BlanceService.getInstance();

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("AuthHandle");
        // 鉴权
        if (AuthDTO.class.isInstance(msg)) {

            /*先判断负载是否均衡，如果本机服务可以直接接受设备连接，则认证成功，
            * 如果本机不接受此设备，则下发给设备重新认证的命令
            *
            * */
            log.info("{}",ZookeeperUtil.getChildren());
            if (ZookeeperUtil.getChildren()!=null && ZookeeperUtil.getChildren().size()>1){
                log.info("负载均衡：{}",ZookeeperUtil.getChildren());
                /** 选取最适合的服务的地址，下发给设备，让设备重新认证**/
                String best = runTimeStatusService.judgeBestServer();
                // TODOne: 2019/6/30 如果得出结论，最佳负载服务就是本地，不做处理
                if (best.equals(ConfigUtil.get("app.name"))){
                    // 直接本地认证
                    authService.auth((AuthDTO) msg,ctx,this);
                }
                ctx.writeAndFlush(new Message<String>(MessageType.REDIRECT.getKey(),best)).addListener(
                        new ChannelFutureListener() {
                            @Override
                            public void operationComplete(ChannelFuture future) throws Exception {
                                ctx.close();
                            }
                        }
                );
                return;
            }
            // 单继状态下，直接认证
            authService.auth((AuthDTO) msg,ctx,this);
            // // 如果设备服务只有一台，或者当机只剩下1台服务，则开始认证
            // if (authService.isExit((AuthDTO) msg, true)) {
            //     ctx.pipeline().remove(this);
            //     ChannelFuture f = ctx.writeAndFlush(Message.success());
            //     f.addListener(new ChannelFutureListener() {
            //         @Override
            //         public void operationComplete(ChannelFuture future) throws Exception {
            //             log.info("认证成功");
            //         }
            //     });
            // } else {
            //     log.info("auth failed");
            //     ChannelFuture f = ctx.writeAndFlush(Message.failed());
            //     f.addListener(new ChannelFutureListener() {
            //         @Override
            //         public void operationComplete(ChannelFuture future) throws Exception {
            //             log.info("认证失败");
            //             ctx.close();
            //         }
            //     });
            // }
        } else {
            Message<RespDto> message = new Message();
            message.setCmd((int) 0);
            message.setVersion((int) 0);

            message.setData(RespDto.FAILED("还未认证"));
            ChannelFuture f = ctx.writeAndFlush(Message.failed("unAuth"));
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
