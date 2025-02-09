package com.example.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // RabbitMQ를 외부 메시지 브로커로 사용
        registry.enableStompBrokerRelay("/topic", "/queue") // topic: 일대다 메시징, queue: 일대일 메시징
                .setRelayHost("localhost")      // RabbitMQ 호스트 설정
                .setRelayPort(61613)            // STOMP 프로토콜을 위한 RabbitMQ 포트
                .setClientLogin("guest")        // RabbitMQ 접속 계정
                .setClientPasscode("guest");    // RabbitMQ 접속 비밀번호

        // 메시지 발행 prefix
        // 클라이언트가 /pub로 시작하는 주소로 메시지를 보내면 @MessageMapping으로 받을 수 있음
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 연결 엔드포인트 설정
        registry.addEndpoint("/ws-chat")       // 웹소켓 연결 주소
                .setAllowedOriginPatterns("*")        // CORS 설정 (모든 도메인 허용)
                //.setAllowedOrigins("http://localhost:3000", "http://localhost:8080") // 프로덕션 레벨에서는 이렇게
                .withSockJS();                 // SockJS 지원 활성화 (웹소켓을 지원하지 않는 브라우저를 위한 fallback)
    }
}
