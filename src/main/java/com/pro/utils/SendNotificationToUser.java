package com.pro.utils;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.SSLHandshakeException;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import com.pro.entity.MessageEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendNotificationToUser {

	private final static WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
	public static String sendTo ="/topic/pubic";

	public void sendNotification(MessageEntity requestBody) throws ExecutionException, InterruptedException, SSLHandshakeException {
		ListenableFuture<StompSession> f = this.connect();
		StompSession stompSession = f.get();
		this.subscribeGreetings(stompSession);
		this.sendDataToPublic(stompSession, requestBody);
	}

	// connect web socket
	public ListenableFuture<StompSession> connect() throws SSLHandshakeException {
		Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
		List<Transport> transports = Collections.singletonList(webSocketTransport);
		SockJsClient sockJsClient = new SockJsClient(transports);
		sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());
		WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
		String url = "ws://{host}:{port}/ws";
		return stompClient.connect(url, headers, new MyHandler(), "localhost", 1064);
	}

	private class MyHandler extends StompSessionHandlerAdapter {
		public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
			log.info("Now connected");
		}
	}

	public void subscribeGreetings(StompSession stompSession) throws ExecutionException, InterruptedException {
		stompSession.subscribe(sendTo, new StompFrameHandler() {
			public Type getPayloadType(StompHeaders stompHeaders) {
				return byte[].class;
			}
			public void handleFrame(StompHeaders stompHeaders, Object o) {
			}
		});
	}
	
	public void sendDataToPublic(StompSession stompSession,MessageEntity messageEntity) {
        String jsonHello = "{\"statusCode\" : "+messageEntity.getStatusCode()+"}";
        log.info(jsonHello);
        stompSession.send(sendTo, jsonHello.getBytes());
  }
}
