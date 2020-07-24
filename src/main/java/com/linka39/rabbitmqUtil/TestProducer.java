package com.linka39.rabbitmqUtil;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TestProducer {
    public final static String EXCHANGE_NAME="topics_exchange";
//    public final static String EXCHANGE_NAME="fanout_exchange";
    public final static String QUEUE_NAME="direct_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQUtil.checkServer();
//        fandout();
//        direct();
        topic();
    }
    public static void topic()throws IOException, TimeoutException{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String[] routing_keys = new String[]{ "usa.news", "usa.weather",
                "europe.news", "europe.weather" };
        String[] messages = new String[] { "美国新闻", "美国天气",
                "欧洲新闻", "欧洲天气" };
        for(int i = 0; i<routing_keys.length;i++){
            String routingKey = routing_keys[i];
            String message = messages[i];
            channel.basicPublish(EXCHANGE_NAME,routingKey,null,message.getBytes());
            System.out.printf("发送消息到路由：%s，内容是: %s %n",routingKey,message);
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    public static void fandout()throws IOException, TimeoutException{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        for (int i = 0; i < 100; i++) {
            String message = "direct 消息 " +i;
            //发送消息到队列中
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println("发送消息： " + message);

        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }

    public static void direct()throws IOException, TimeoutException{
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("localhost");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();


        for (int i = 0; i < 100; i++) {
            String message = "direct 消息 " +i;
            //发送消息到队列中
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println("发送消息： " + message);

        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
