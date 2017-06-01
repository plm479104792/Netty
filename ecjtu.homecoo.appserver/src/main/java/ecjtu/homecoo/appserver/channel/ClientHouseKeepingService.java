package ecjtu.homecoo.appserver.channel;

import io.netty.channel.Channel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import ecjtu.homecoo.appserver.common.ThreadFactoryImpl;
import ecjtu.homecoo.remoting.ChannelEventListener;

public class ClientHouseKeepingService implements ChannelEventListener {
	private static final Logger log = LoggerFactory
			.getLogger(ClientHouseKeepingService.class);
	private final ChannelManager channelManager;

	public ClientHouseKeepingService(final ChannelManager channelManager) {
		this.channelManager = channelManager;
	}

	private ScheduledExecutorService scheduledExecutorService = Executors
			.newSingleThreadScheduledExecutor(new ThreadFactoryImpl(
					"ClientHousekeepingScheduledThread"));

	public void start() {
		// 定时扫描过期的连接
		this.scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					ClientHouseKeepingService.this.scanExceptionChannel();
				} catch (Exception e) {
					log.error("", e);
				}
			}
		}, 1000 * 10, 1000 * 10, TimeUnit.MICROSECONDS);
	}
	

    public void shutdown() {
        this.scheduledExecutorService.shutdown();
    }


    private void scanExceptionChannel() {
    	this.getChannelManager().scanNotActiveChannel();
        
    }

	
	@Override
	public void onChannelConnect(String remoteAddr, Channel channel) {
	

	}

	@Override
	public void onChannelClose(String remoteAddr, Channel channel) {
		// TODO Auto-generated method stub
		channelManager.onChannelClose(remoteAddr, channel);
	}

	@Override
	public void onChannelException(String remoteAddr, Channel channel) {
		// TODO Auto-generated method stub
		channelManager.onChannelClose(remoteAddr, channel);
	}

	@Override
	public void onChannelIdle(String remoteAddr, Channel channel) {
		channelManager.onChannelIdle(remoteAddr, channel);

	}

	public ChannelManager getChannelManager() {
		return channelManager;
	}

}
