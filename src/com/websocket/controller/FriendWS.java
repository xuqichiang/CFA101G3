package com.websocket.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.websocket.model.ChatMessage;
import com.websocket.model.JedisHandleMessage;
import com.websocket.model.State;



@ServerEndpoint("/FriendWS/{userName}")
public class FriendWS {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("userName") String userName, Session userSession) throws IOException {
		/* 將新的連線存進map集合 */
		sessionsMap.put(userName, userSession);
		
		/* 取得之前聊天過的會員 */
		Set<String> userNames = JedisHandleMessage.getHistoryFriends(userName);
		
		/* 會員對應未讀數量 */
		Map<String,Long> unreadMap = new HashMap<String,Long>();
		
		/*將對應的會員及未讀數量加到unreadMap*/
		for(String receiver:userNames) {
			Long count = JedisHandleMessage.getUnReadCount(userName, receiver);
			unreadMap.put(receiver, count);
		}
		State stateMessage = new State("open", userName,userNames,unreadMap);
		String stateMessageJson = gson.toJson(stateMessage);
		
		/*傳給建立連線的會員*/
		userSession.getAsyncRemote().sendText(stateMessageJson);
		
		
		/*傳給所有線上會員*/
//		Collection<Session> sessions = sessionsMap.values();
//		for (Session session : sessions) {
//			if (session.isOpen()) {
//				session.getAsyncRemote().sendText(stateMessageJson);
//			}
//		}


		System.out.println(sessionsMap);
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		String sender = chatMessage.getSender();
		String receiver = chatMessage.getReceiver();
		
		//取得歷史訊息
		if ("history".equals(chatMessage.getType())) {
			List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
			String historyMsg = gson.toJson(historyData);
			ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				System.out.println("history = " + gson.toJson(cmHistory));
				return;
			}
		}
		//與新會員聊天
		else if("newChat".equals(chatMessage.getType())) {
			Set<String> historyFriendsData = JedisHandleMessage.getHistoryFriends(sender);
			historyFriendsData.add(receiver);
			String historyFriends = gson.toJson(historyFriendsData);
			ChatMessage cmHistory = new ChatMessage("newChat", sender, receiver, historyFriends);
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				return;
			}
		}
		//新訊息
		else if("newMsg".equals(chatMessage.getType())) {
			ChatMessage cmHistory = new ChatMessage("newMsg", sender, receiver, "");
			if (userSession != null && userSession.isOpen()) {
				userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
				return;
			}
		}
		
		//清除未讀數量
		else if("clearUnRead".equals(chatMessage.getType())) {
			JedisHandleMessage.clearUnReadCount(sender, receiver);
				return;
		}

		
		Session receiverSession = sessionsMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
			receiverSession.getAsyncRemote().sendText(message);
			userSession.getAsyncRemote().sendText(message);
		}else {
			userSession.getAsyncRemote().sendText(message);
			JedisHandleMessage.saveChatMessage(sender, receiver, message);
		}
		
		
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String userNameClose = null;
		Set<String> userNames = sessionsMap.keySet();
		for (String userName : userNames) {
			if (sessionsMap.get(userName).equals(userSession)) {
				userNameClose = userName;
				sessionsMap.remove(userName);
				break;
			}
		}

//		if (userNameClose != null) {
//			State stateMessage = new State("close", userNameClose, userNames);
//			String stateMessageJson = gson.toJson(stateMessage);
//			Collection<Session> sessions = sessionsMap.values();
//			for (Session session : sessions) {
//				session.getAsyncRemote().sendText(stateMessageJson);
//			}
//		}
//
//		String text = String.format("session ID = %s, disconnected; close code = %d%nusers: %s", userSession.getId(),
//				reason.getCloseCode().getCode(), userNames);
//		System.out.println(text);
	}
}
