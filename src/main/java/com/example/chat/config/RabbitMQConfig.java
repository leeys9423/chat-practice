package com.example.chat.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // RabbitMQ 서버와의 연결을 설정
    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");   // RabbitMQ 서버 주소
        factory.setPort(5672);          // AMQP 포트
        factory.setUsername("guest");   // 기본 사용자명
        factory.setPassword("guest");   // 기본 비밀번호
        return factory;
    }

    // RabbitMQ와 메시지를 주고받기 위한 템플릿
    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    // 메시지를 저장할 큐 생성
    @Bean
    public Queue chatQueue() {
        return new Queue("chat.queue", true);  // 두 번째 파라미터는 durable (지속성) 여부
    }

    // 메시지를 라우팅할 교환기(Exchange) 생성
    @Bean
    public TopicExchange chatExchange() {
        return new TopicExchange("chat.exchange"); // 토픽 타입의 교환기 생성
    }

    // 큐와 교환기를 연결(바인딩)
    @Bean
    public Binding chatBinding() {
        return BindingBuilder
                .bind(chatQueue())          // 위에서 만든 큐를
                .to(chatExchange())         // 교환기와 연결하고
                .with("chat.*");  // 라우팅 키 패턴
    }
}