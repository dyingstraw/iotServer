package handler;

import com.alibaba.fastjson.JSON;
import config.MongoUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.RecordDTO;
import model.message.Message;
import org.bson.Document;
import util.ConfigUtil;
import util.RabbitMQUtil;
import util.RedisUtil;

import java.util.Date;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 22:05
 **/
@Slf4j
public class RecordHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (RecordDTO.class.isInstance(msg)){
            RecordDTO record = (RecordDTO) msg;
            // 如果设备在线，则把记录发送到rabbitmq，并更新在线信息
            // 否在，关闭物理连接
            if (RedisUtil.getJedis().exists("dev_"+record.getDevId())) {
                log.info("send message {} to rabbitmq!", record);
                // 发送至消息队列
                RabbitMQUtil.sendAndClose(ConfigUtil.getAppName(), "hello", null, JSON.toJSONString(record).getBytes());
                // 发布到redis话题
                RedisUtil.getJedis().publish(ConfigUtil.get("app.publish"),JSON.toJSONString(record));
                log.info(Thread.currentThread().toString());
                // 更新在线状态
                RedisUtil.getJedis().expire("dev_" + record.getDevId(), 60);
                ctx.writeAndFlush(Message.success());
            }else {
                ctx.writeAndFlush(Message.failed("long time no heart package!"));
                ctx.close();
            }
        }else {
            super.channelRead(ctx, msg);
        }
    }
}
