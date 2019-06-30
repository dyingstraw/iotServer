package service;

import com.mongodb.client.FindIterable;
import config.Key;
import config.MongoUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import model.message.Message;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import util.CommonUtil;
import util.ConfigUtil;
import util.RedisUtil;
import util.ZookeeperUtil;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-10 10:49
 **/
@Slf4j
public class AuthService {

    private static AuthService instance = new AuthService();


    private AuthService(){
    }

    public static AuthService getInstance(){
        return instance;
    }


    public boolean isExit(AuthDTO authDTO,boolean useRedis){
        if (!useRedis) {
            Document document = new Document();
            document.append("devId", authDTO.getDevId());
            document.append("devKey", authDTO.getDevKey());
            FindIterable<Document> result = MongoUtil.getRecordCollection("auth").find(document);

            if (result != null && result.first() != null && !result.first().isEmpty()) {
                return true;
            }
        }else {
            String key = RedisUtil.getJedis().hget(Key.AUTH.getValue(), authDTO.getDevId().toString());
            log.info("2222:{}",key);
            if (!StringUtils.isEmpty(key)){
                return key.equals(authDTO.getDevKey());
            }
        }
        return false;
    }

    /**
     * 认证服务
     * @param authDTO
     * @param ctx
     * @param handler
     */
    public void auth(AuthDTO authDTO, ChannelHandlerContext ctx, ChannelInboundHandlerAdapter handler){
        // 如果设备服务只有一台，或者当机只剩下1台服务，则开始认证
        if (isExit(authDTO, true)) {
            ctx.pipeline().remove(handler);
            ChannelFuture f = ctx.writeAndFlush(Message.success());
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("认证成功信息发送成功");
                    RedisUtil.getJedis().hset("dev_"+authDTO.getDevId().toString(), "host",ConfigUtil.get("app.name"));
                    // 设置设备超时登陆时间60s
                    RedisUtil.getJedis().expire("dev_"+authDTO.getDevId(),60);
                }
            });
        } else {
            log.info("auth failed");
            ChannelFuture f = ctx.writeAndFlush(Message.failed("authentic failed"));
            f.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("认证失败信息发送成功");
                    ctx.close();
                }
            });
        }
    }
}
