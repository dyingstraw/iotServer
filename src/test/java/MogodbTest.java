import com.alibaba.fastjson.JSON;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-08 20:02
 **/
public class MogodbTest {
    class User {
        private String name;
        private Integer age;
        private Integer random;

        public User(String name, Integer age, Integer random) {
            this.name = name;
            this.age = age;
            this.random = random;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Integer getRandom() {
            return random;
        }

        public void setRandom(Integer random) {
            this.random = random;
        }
    }
    @Test
    public void test(){
        //
        ServerAddress serverAddress = new ServerAddress("139.199.201.66",27017);
        MongoCredential credential = MongoCredential.createScramSha1Credential("root","admin","root".toCharArray());
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(credential);
        MongoClient mongoClient = new MongoClient(serverAddress,credentials);
        MongoIterable<String> names = mongoClient.listDatabaseNames();
        System.out.println("---");

        MongoCursor<String> it = names.iterator();
        while (it.hasNext()){
            System.out.println(it.next());
        }

        MongoDatabase iotdb = mongoClient.getDatabase("iot");
        MongoCollection<Document> c1 = iotdb.getCollection("test1");
        for (int i = 0; i < 10; i++) {
            String jo = JSON.toJSONString(new User("xiao" + i, 20, (int) (Math.random() * 100)));
            c1.insertOne(Document.parse(jo));
        }
        mongoClient.close();



    }
}
