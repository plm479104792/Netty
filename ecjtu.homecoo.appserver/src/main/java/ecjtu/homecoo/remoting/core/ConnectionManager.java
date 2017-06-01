package ecjtu.homecoo.remoting.core;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import ecjtu.homecoo.remoting.protocol.Message;


public class ConnectionManager {
	private AtomicInteger idMaker = new AtomicInteger(0);
	private Map<String, Connection> connectionManager = new ConcurrentHashMap<String, Connection>();
	
	public void addConnection(String gateWayNo,ChannelHandlerContext context){
		if(!connectionManager.containsKey(gateWayNo)){
			Connection connection = new Connection(context);
			connection.setConnectId(idMaker.getAndIncrement());
			connectionManager.put(gateWayNo, connection);
		}
	}
	
	public Connection getConnection(String gateWayNo){
		return connectionManager.get(gateWayNo);
	}
	
	public void sendMessage(String gateWayno , Message message){
		Connection connection = getConnection(gateWayno);
		if(connection != null){
			connection.sendMessage(message);
		}
	}

}
