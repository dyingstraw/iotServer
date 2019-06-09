package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.json.JsonObjectDecoder;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-05-17 20:36
 **/
// @ChannelHandler.Sharable
@Slf4j
public class MyDecode extends JsonObjectDecoder {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("MyDecode");
        super.channelRead(ctx, msg);
    }
}
