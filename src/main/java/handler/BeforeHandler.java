package handler;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import model.dto.BaseDTO;
import model.dto.RecordDTO;
import model.message.Message;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 14:11
 **/
@ChannelHandler.Sharable
@Slf4j
public class BeforeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("BeforeHandler");
        String jsonStr = new String(ByteBufUtil.getBytes((ByteBuf) msg));
        log.info("jsonstr:"+jsonStr);
        Message<String> jo = JSONObject.parseObject(jsonStr,new TypeReference<Message<String>>(){});
        if (jo.getCmd()==0){
            AuthDTO authDTO = JSONObject.parseObject(jo.getData(),new TypeReference<AuthDTO>(){});
            super.channelRead(ctx,authDTO);
        }else if (jo.getCmd()==1){
            RecordDTO recordDTO = JSONObject.parseObject(jo.getData(),new TypeReference<RecordDTO>(){});
            super.channelRead(ctx, recordDTO);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
