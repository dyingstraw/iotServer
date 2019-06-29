package rabbitmqTest;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @program: netty_study
 * @description:
 * @author: dyingstraw
 * @create: 2019-06-23 13:44
 **/
public class Receive  {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.100");
        factory.setUsername("iot");
        factory.setPassword("iot");
        final Channel ch = factory.newConnection().createChannel();
        ch.basicQos(1);
        ch.queueDeclare("hello", false, false, false, null);
        Consumer consumer = new DefaultConsumer(ch){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");

                System.out.println(" [x] Received '" + message + "'");
                try {
                    Thread.sleep(0);
                    ch.basicAck(envelope.getDeliveryTag(),false);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        ch.basicConsume("hello",false,consumer);
    }
}
