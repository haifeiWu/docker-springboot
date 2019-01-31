package com.whforever.dockerspringboot;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author wuhf
 * @Date 2019/1/21 16:05
 **/
public class Producer {

    public void mqProducer() {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
        producer.setNamesrvAddr("test.whforever.cn:9876");
        /**
         *默认情况下，一台服务器只能启动一个Producer或Consumer实例，所以如果需要在一台服务器启
         *动多个实例，需要设置实例的名称
         */
        producer.setInstanceName("Producer");
        producer.setSendMsgTimeout(3000);//发送消息超时
        producer.setRetryTimesWhenSendFailed(2);//发送失败后，重试几次
        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        try {
            producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        /**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
        for (int i = 0; i < 100; i++) {
            try {
                {
                    Message msg = new Message("TopicTest1",// topic
                            "TagA",// tag
                            "OrderID001",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }
                {
                    Message msg = new Message("TopicTest2",// topic
                            "TagB",// tag
                            "OrderID0034",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }
                {
                    Message msg = new Message("TopicTest3",// topic
                            "TagC",// tag
                            "OrderID061",// key
                            ("Hello MetaQ").getBytes());// body
                    SendResult sendResult = producer.send(msg);
                    System.out.println(sendResult);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }

    public void testConsumer() {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ConsumerGroupName需要由应用来保证唯一。
         *不同consumer group里的consumer即便是消费同一个topic下的同一个queue，
         *那消费进度也是分开存储的。也就是说，不同的consumer group内的consumer的消费
         *完全隔离，彼此不受影响。
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
                "ConsumerGroupName");
        consumer.setNamesrvAddr("192.168.0.123:9876");
        consumer.setInstanceName("Consumber");
        //广播消费是指一个consumer只要订阅了某个topic的消息，那它就会收到该topic下的所有queue里的消息，
        //而不管这个consumer的group是什么。所以对于广播消费来说，consumer group没什么实际意义。consumer可以在实例化时，我们可以指定是集群消费还是广播消费。
        //consumer.setMessageModel(MessageModel.BROADCASTING);
        /**
         * 订阅指定topic下tags分别等于TagA或TagC或TagD
         */
        try {
            consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         */
        try {
            consumer.subscribe("TopicTest2", "*");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            /**
             * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(
                    List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName()
                        + " Receive New Messages: " + msgs.size());
                MessageExt msg = msgs.get(0);
                if (msg.getTopic().equals("TopicTest1")) {
                    // 执行TopicTest1的消费逻辑
                    if (msg.getTags() != null && msg.getTags().equals("TagA")) {
                        // 执行TagA的消费
                        System.out.println(new String(msg.getBody()));
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagC")) {
                        // 执行TagC的消费
                    } else if (msg.getTags() != null
                            && msg.getTags().equals("TagD")) {
                        // 执行TagD的消费
                    }
                } else if (msg.getTopic().equals("TopicTest2")) {
                    System.out.println(new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        try {
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer Started.");
    }

    @Test
    public void test01() {
        mqProducer();
    }

    @Test
    public void test02() {
        testConsumer();
    }

}
