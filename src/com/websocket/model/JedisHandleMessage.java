package com.websocket.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisHandleMessage {
	//(sender發送者會員編號:receiver接收者會員編號)

	private static JedisPool pool = JedisPoolUtil.getJedisPool();
	private static Gson gson = new Gson();

	//取得歷史訊息並把未讀改為已讀
	public static List<String> getHistoryMsg(String sender, String receiver) {
		String key = new StringBuilder(sender).append(":").append(receiver).toString();
		Jedis jedis = null;
		jedis = pool.getResource();
		List<String> historyData = jedis.lrange(key, 0, -1);
//		List<String> list = new ArrayList<String>();//將未讀狀態改為已讀
//		for (int i = 0; i < historyData.size(); i++) {
//			ChatMessage chatMessage = gson.fromJson(historyData.get(i), ChatMessage.class);
//			chatMessage.setStatus("1");
//			String json = gson.toJson(chatMessage);
//			jedis.lset(key, i, json);
//			list.add(json);
//		}
		
		//將未讀訊息修改為已讀並刪除暫存的的unread
		 if (jedis.exists("unread"+sender+","+receiver)) {
			 List<String> unread = jedis.lrange("unread"+sender+","+receiver, 0, -1);
			 for(String id:unread) {
				 	String message = jedis.lindex(key,Long.parseLong(id)-1);
					ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
					chatMessage.setStatus("1");
					String json = gson.toJson(chatMessage);
					jedis.lset(key, Long.parseLong(id)-1, json);
			 }
			 jedis.del("unread"+sender+","+receiver);
		 }
		jedis.close();
		return historyData;
	}

	//儲存對話紀錄
	public static void saveChatMessage(String sender, String receiver, String message) {
		// 對雙方來說，都要各存著歷史聊天記錄
		String senderKey = new StringBuilder(sender).append(":").append(receiver).toString();
		String receiverKey = new StringBuilder(receiver).append(":").append(sender).toString();
		Jedis jedis = pool.getResource();
		
		
		//發送者狀態為已讀  接收者則為未讀(前端送來的資料預設為已讀(1))
		ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
		chatMessage.setStatus("0");
		String json = gson.toJson(chatMessage);
		jedis.rpush(senderKey, message);//發送者為已讀
		Long id = jedis.rpush(receiverKey, json);//接收者為未讀(並返回自增主鍵值)
		jedis.rpush("unread"+receiver+","+sender, id.toString());//將未讀的id存到另一組key中
		
		jedis.close();
	}
	
	//取得之前聊天過的會員集合
	public static Set<String> getHistoryFriends(String sender) {
		Jedis jedis = null;
		jedis = pool.getResource();
		Set<String> senderkeys = jedis.keys(sender+":*");
		Set<String> receiverkeys = jedis.keys("*:"+sender);

		Set<String> FriendsSet = new HashSet<String>();
		
		for(String senderkey:senderkeys) {
			String[] split = senderkey.split(":");
			FriendsSet.add(split[1]);
		}
		
		for(String receiverkey:receiverkeys) {
			String[] split = receiverkey.split(":");
			FriendsSet.add(split[0]);
		}
		jedis.close();
		return FriendsSet;
	}
	
	//取得未讀數量
	public static Long getUnReadCount(String sender, String receiver) {
		Jedis jedis = null;
		jedis = pool.getResource();
		Long count = jedis.llen("unread"+sender+","+receiver);		
		jedis.close();
		return count;
	}
	
	//清除未讀數量
	public static void clearUnReadCount(String sender, String receiver) {
		Jedis jedis = null;
		jedis = pool.getResource();
		if (jedis.exists("unread"+sender+","+receiver)) {
			jedis.del("unread"+sender+","+receiver);
			System.out.println("已清除"+sender+":"+receiver);
		}
		jedis.close();
	}
}
