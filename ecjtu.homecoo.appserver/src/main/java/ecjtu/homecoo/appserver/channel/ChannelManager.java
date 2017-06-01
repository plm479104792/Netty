package ecjtu.homecoo.appserver.channel;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ecjtu.homecoo.appserver.client.ClientChannelInfo;
import ecjtu.homecoo.remoting.common.RemotingUtil;
import ecjtu.homecoo.remoting.protocol.Message;

/**
 * 连接管理
 */
public class ChannelManager {

	private final ConcurrentHashMap<Channel, ClientChannelInfo> channelInfoTable = new ConcurrentHashMap<Channel, ClientChannelInfo>(
			16);
	private static final Logger log = LoggerFactory
			.getLogger(ChannelManager.class.getName());

	private static final ChannelManager instance = new ChannelManager();

	public static final ChannelManager getInstance() {
		return instance;
	}

	public ClientChannelInfo getChannel(String gateWayNo) {
		Iterator<Entry<Channel, ClientChannelInfo>> it = this.channelInfoTable
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<Channel, ClientChannelInfo> next = it.next();
			if (next.getValue().getGateWayno().equals(gateWayNo)) {
				return next.getValue();
			}
		}

		return null;
	}

	public List<Channel> getAllChannel() {
		List<Channel> result = new ArrayList<Channel>();

		result.addAll(this.channelInfoTable.keySet());

		return result;
	}

	public void unregisterChannel(final ClientChannelInfo clientChannelInfo) {
		ClientChannelInfo old = this.channelInfoTable.remove(clientChannelInfo
				.getChannel());
		if (old != null) {
			log.info("unregister a consumer[{}] from consumerGroupInfo {}",
					old.toString());
		}
	}

	public ClientChannelInfo registorChannel(
			final ClientChannelInfo clientChannelInfo) {
		
		this.channelInfoTable.put(clientChannelInfo.getChannel(),
				clientChannelInfo);
		
		return clientChannelInfo;
	}

	public void onChannelConnect(String remoteAddr, Channel channel) {

	}

	public void onChannelClose(String remoteAddr, Channel channel) {
		this.channelInfoTable.remove(channel);
	}

	public void onChannelException(String remoteAddr, Channel channel) {
		this.channelInfoTable.remove(channel);
	}

	public void onChannelIdle(String remoteAddr, Channel channel) {
		this.channelInfoTable.remove(channel);
	}

	public void scanNotActiveChannel() {
		for(Channel channel: channelInfoTable.keySet()){
			final ClientChannelInfo clientChannelInfo = channelInfoTable.get(channel);

             long diff = System.currentTimeMillis() - clientChannelInfo.getLastHeartTime();
             //设置时间接收到的心跳时间间隔 ms
             if (diff > 10000) {
            	 channelInfoTable.remove(channel);
                 RemotingUtil.closeChannel(channel);
             }
		}
	}
	

	public void sendMessage(String gateWayno , Message message){
		ClientChannelInfo clientChannelInfo  = getChannel(gateWayno);
		if(clientChannelInfo != null){
			clientChannelInfo.getChannel().writeAndFlush(message);
		}
	}

}
