package com.example.demo;

import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 自定义 RedisTemplate 序列化方式
 * 配置主题订阅 - Redis 消息监听器绑定监听指定通道
 */
@Configuration
public class RedisConfig {
    // 自定义的消息订阅监听器，当收到阅订的消息时，会将消息交给这个类处理
    @Resource
    private RequestRateLimitSubscriber requestRateLimitSubscriber;
 
    //  自定义 RedisTemplate 序列化方式   
    @Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(RedisSerializer.string());// key 序列化规则
		redisTemplate.setHashKeySerializer(RedisSerializer.string());// hash key 序列化规则
		redisTemplate.setValueSerializer(RedisSerializer.java());// value 序列化规则
		redisTemplate.setHashValueSerializer(RedisSerializer.java()); // hash value 序列化规则
		redisTemplate.setConnectionFactory(factory); //绑定 RedisConnectionFactory
		return redisTemplate; //返回设置好的 RedisTemplate
	}
    /**
     * 配置主题订阅
     * RedisMessageListenerContainer - Redis 消息监听器绑定监听指定通道
     * 1、可以添加多个监听器，监听多个通道，只需要将消息监听器与订阅的通道/主题绑定即可。
     * 2、订阅的通道可以配置在全局配置文件中，也可以配置在数据库中，
     * <p>
     * addMessageListener(MessageListener listener, Collection<? extends Topic> topics)：将消息监听器与多个订阅的通道/主题绑定
     * addMessageListener(MessageListener listener, Topic topic)：将消息监听器与订阅的通道/主题绑定
     *
     * @param connectionFactory
     * @return
     */
	@Bean
	public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory factory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		// 设置连接工厂，RedisConnectionFactory 可以直接从容器中取，也可以从 RedisTemplate 中取
		container.setConnectionFactory(factory);
		// 订阅名称叫 select_rate_limit_channel 的通道, 类似 Redis 中的 subscribe 命令
		container.addMessageListener(requestRateLimitSubscriber.getMessageListenerAdapter(), new PatternTopic("test"));
		// 订阅名称以 'basic-' 开头的全部通道, 类似 Redis 的 pSubscribe 命令
		return container;

	}
}
