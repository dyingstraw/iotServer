package util;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

import static org.springframework.amqp.rabbit.core.RabbitAdmin.QUEUE_NAME;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-23 15:59
 **/
public class RabbitMQUtil {

    private static Connection connection = null;
    private static String host = "192.168.1.100";
    private static final String QUEUE_NAME = "hello";
    private static CachingConnectionFactory cachingConnectionFactory;

    static {
        cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CHANNEL);
        cachingConnectionFactory.setHost(host);
        cachingConnectionFactory.setUsername("iot");
        cachingConnectionFactory.setPassword("iot");
    }
    public static Channel getChannel() {
        return cachingConnectionFactory.createConnection().createChannel(false);
    }
    public static void sendAndClose(String exchange, String routingKey, AMQP.BasicProperties props, byte[] body) throws IOException, TimeoutException {
        Channel ch = getChannel();
        ch.basicPublish(exchange, routingKey, props, body);
        ch.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException, TimeoutException {
        while (true) {
            String message = String.valueOf(System.currentTimeMillis());
            // Channel channel = getChannel();
            // System.out.println(" [√] " + channel.getChannelNumber());
            // channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            // System.out.println(" [x] Sent '" + message + "'");
            // Thread.sleep(1000);
            // channel.close();
            sendAndClose(ConfigUtil.getAppName(),QUEUE_NAME,null,message.getBytes());
            Thread.sleep(20);
        }
    }
}
