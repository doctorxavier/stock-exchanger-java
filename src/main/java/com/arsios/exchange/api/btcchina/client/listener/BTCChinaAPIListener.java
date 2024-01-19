package com.arsios.exchange.api.btcchina.client.listener;

import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.arsios.exchange.api.btcchina.client.listener.callback.BTCChinaAPICallback;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

@Controller
public class BTCChinaAPIListener implements Runnable {
	
	private static final Logger		LOGGER	= LoggerFactory.getLogger(BTCChinaAPIListener.class);
	
	private Socket socket;
	
	@Resource
	private BTCChinaAPICallback btcChinaAPICallback;
	
	@PostConstruct
	private void init() throws URISyntaxException {
		IO.Options opt = new IO.Options();
		opt.reconnection = true;
		socket = IO.socket("https://websocket.btcchina.com", opt);
	}

	@Override
	public void run() {
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				LOGGER.info("Connected!");
				socket.emit("subscribe", "marketdata_cnybtc");
			}
		})
		.on("ticker", btcChinaAPICallback)
		.on(Socket.EVENT_CONNECT_ERROR , new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				LOGGER.info("Connect error!");
			}
		})
		.on(Socket.EVENT_CONNECT_TIMEOUT , new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				LOGGER.info("Connection timeout!");
			}
		})
		.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				LOGGER.info("Disconnected!");
			}
		});
		socket.connect();
		
		// CHECKSTYLE:OFF
		while (!Thread.currentThread().isInterrupted()) { }
		// CHECKSTYLE:ON

		this.socket.close();
		socket.off();
	}
	
}
