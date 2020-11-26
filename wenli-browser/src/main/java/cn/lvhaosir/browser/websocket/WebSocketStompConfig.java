package cn.lvhaosir.browser.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import java.util.List;

/**
 * Created by lvhaosir on 2018/9/21.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp端点
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 这里只写 /ws 因为项目 必须要有 /wenli 才能访问，前端就是 /wenli/ws
        // 允许使用socketJs方式访问 即可通过http://IP:PORT/xboot/ws来和服务端websocket连接
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
        // 记得在拦截器忽略该请求

    }

    /**
     * 配置信息代理
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称 user点对点 topic广播即群发
        registry.enableSimpleBroker("/user","/topic");
        // 全局(客户端)使用的消息前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的前缀 无需配置 默认/user
        registry.setUserDestinationPrefix("/user");
    }

}
