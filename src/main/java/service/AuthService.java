package service;

import com.mongodb.client.FindIterable;
import config.Key;
import config.MongoUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import model.message.Message;
import org.bson.BsonDocument;
import org.bson.Document;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
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

    public void auth(AuthDTO authDTO){

    }
}
