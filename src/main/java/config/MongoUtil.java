package config;

import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import exception.CustomException;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 21:53
 **/
public class MongoUtil {
    private static MongoClient instance = null;
    private static MongoCollection recordCollection;




    private MongoUtil(){}

    public static MongoClient getInstance(){
        if (instance!=null) {
            return instance;
        }
        System.out.println("init mongo");
        ServerAddress serverAddress = new ServerAddress("139.199.201.66",27017);
        MongoCredential credential = MongoCredential.createScramSha1Credential("root","admin","root".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(serverAddress,credentials);
        return mongoClient;
    }
    public static void closeMongoClient() throws CustomException {
        if (instance==null){
            throw new CustomException();
        }
        instance.close();

    }
    public static MongoCollection<Document> getRecordCollection(){
        if (recordCollection!=null){
            return recordCollection;
        }
        recordCollection = getInstance().getDatabase("iot").getCollection("record");

        return recordCollection;
    }
    public static MongoCollection<Document> getRecordCollection(String name){
        if (recordCollection!=null){
            return recordCollection;
        }
        recordCollection = getInstance().getDatabase("iot").getCollection(name);

        return recordCollection;
    }
    public static Document objectToDocument(Object o){
        String str = JSON.toJSONString(o);
        return Document.parse(str);
    }



}
