package com.taotao.activemq.queue;

import com.taotao.activemq.spring.MyMessageListener;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息的消费者
 *
 * @author eliefly
 * @date 18/1/13d
 */
public class Customer {

    public static void main(String[] args) throws Exception {
        // 1. 创建ActiveMQConnectionFactory连接工厂，需要ActiveMQ的服务地址，使用的是tcp协议
        String brokerURL = "tcp://192.168.56.101:61616";
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerURL);

        // 2. 使用连接工厂创建连接
        Connection connection = factory.createConnection();

        // 3. 使用连接对象开启连接，使用start方法
        connection.start();

        // 4. 从连接对象里获取session
        // 第一个参数的作用是，是否使用JTA分布式事务，设置为false不开启
        // 第二个参数是设置应答方式，如果第一个参数是true，那么第二个参数就失效了，这里设置的应答方式是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 5. 从session获取消息类型Destination，获取queue，（也可以获取topic，topic是订阅模式）
        // 参数就是设置队列名称
        Queue queue = session.createQueue("test-queue");

        // 6. 从session中获取消息的消费者
        MessageConsumer consumer = session.createConsumer(queue);

        // 7.接受消息
        // 方式一: 直接获取消息
//        while (true) {
//            // 参数表示接受消息等待的时间，单位是毫秒
//            Message message = consumer.receive(200000l);
//
//            // 如果没有消息则跳出循环
//            if (message == null) {
//                break;
//            }
//
//            // 判断消息类型是TextMessage
//            if (message instanceof TextMessage) {
//                // 如果是，则进行强转
//                TextMessage textMessage = (TextMessage) message;
//
//                // 8. 消费消息，打印消息内容
//                System.out.println(textMessage.getText());
//            }
//        }

        // 使用监听器接收消息
        // 使用监听的方式接收消息，其实是创建了一个新的线程来处理消息的接收
        consumer.setMessageListener(new MyMessageListener());

        // 让主线程等待一会，监听器能够有时间执行
        Thread.sleep(10000);

        // 9. 释放资源
        consumer.close();
        session.close();
        connection.close();
    }

}
