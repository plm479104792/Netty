package ecjtu.homecoo.appserver.processor;


import ecjtu.homecoo.appserver.channel.ChannelManager;
import ecjtu.homecoo.remoting.netty.NettyRequestProcessor;

/*
 * 所有app上行的请求都已经继承这个类
 */
public abstract class AbstractUpdatProcesser implements NettyRequestProcessor {
	protected ChannelManager connectionManager;
	public AbstractUpdatProcesser() {
		// TODO Auto-generated constructor stub
	}
	public AbstractUpdatProcesser(ChannelManager connectionManager2){
		this.connectionManager = connectionManager2;
	}
}
