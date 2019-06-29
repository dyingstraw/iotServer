package rabbitmqTest;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-23 12:44
 **/
@Slf4j
public class RabbitmqTest {


    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.100");
        factory.setUsername("iot");
        factory.setPassword("iot");
        Connection connection = null;
        Channel channel = null;

        try {
            connection = factory.newConnection();
            channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "Hello World!";
            while (true){
                message = String.valueOf(System.currentTimeMillis());
                System.out.println("chno:"+channel.getChannelNumber());
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
                Thread.sleep(200);
                log.info(message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
            channel.close();
        }
    }


}
