package handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.extern.slf4j.Slf4j;
import model.dto.RespDto;
import model.message.Message;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-09 16:48
 **/
@Slf4j
public class MyEncoder extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("MyEncoder");
        if (Message.class.isInstance(msg)){
            System.out.println("Message MyEncoder");
            String str = JSONObject.toJSONString((Message<RespDto>) msg);
            ByteBuf byteBuf = ctx.channel().alloc().buffer();
            byteBuf.writeBytes(str.getBytes());
            log.info(Thread.currentThread().toString());
            super.write(ctx,byteBuf,promise);
        }
        // super.write(ctx, msg, promise);
    }
}
