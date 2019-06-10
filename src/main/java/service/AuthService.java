package service;

import com.mongodb.client.FindIterable;
import config.MongoUtil;
import lombok.extern.slf4j.Slf4j;
import model.dto.AuthDTO;
import org.bson.BsonDocument;
import org.bson.Document;

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


    public boolean isExit(AuthDTO authDTO){
        Document document = new Document();
        document.append("devId",authDTO.getDevId());
        document.append("devKey",authDTO.getDevKey());

        FindIterable<Document> result = MongoUtil.getRecordCollection("auth").find(document);

        if (result!=null && result.first()!=null && !result.first().isEmpty()){
            return true;
        }
        return false;
    }

    // public static void main(String[] args) {
    //     AuthDTO authDTO = new AuthDTO();
    //     authDTO.setDevId(1L);
    //     authDTO.setDevKey("1234567");
    //     System.out.println(new AuthService().isExit(authDTO));
    //
    //
    //
    // }
}
