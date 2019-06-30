package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import model.dto.HeartDTO;
import util.RedisUtil;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-30 21:47
 **/
public class HeartHandle extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理心跳包
        if (HeartDTO.class.isInstance(msg)){
            // 更新在线状态
            HeartDTO heart = (HeartDTO) msg;
            RedisUtil.getJedis().expire("dev_"+heart.getDevId(),60);
        }else {
            super.channelRead(ctx, msg);
        }
    }
}
