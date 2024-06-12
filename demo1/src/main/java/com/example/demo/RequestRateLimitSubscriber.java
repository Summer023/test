package com.example.demo;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

/**
 * Redis 消息订阅-消息监听器，当收到阅订的消息时，会将消息交给这个类处理
 * <p>
 * 1、可以直接实现 MessageListener 接口，也可以继承它的实现类 MessageListenerAdapter.
 * 2、自动多线程处理，打印日志即可看出，即使手动延迟，也不会影响后面消息的接收。
 *
 */
@Component
public class RequestRateLimitSubscriber {
    @Autowired
    AA aa;

    public MessageListenerAdapter getMessageListenerAdapter(){
        MessageListenerAdapter onMessage = new MessageListenerAdapter(this, "onMessage");
        onMessage.afterPropertiesSet();
        return onMessage;
    }
    /**
     * 监听到的消息必须进行与发送时相同的方式进行反序列
     * 1、订阅端与发布端 Redis 序列化的方式必须相同，否则会乱码。
     *
     * @param message ：消息实体
     * @param pattern ：匹配模式
     */
    public void onMessage(String s) {
        System.out.println(s);
        aa.loadingCache.invalidateAll();
    }
}
