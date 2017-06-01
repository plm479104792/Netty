package ecjtu.homecoo.appserver;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Log4JLoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.logging.log4j2.Log4j2LoggerFactory;

import ecjtu.homecoo.appserver.channel.ChannelManager;
import ecjtu.homecoo.appserver.channel.ClientHouseKeepingService;
import ecjtu.homecoo.appserver.processor.DefaultProcessor;
import ecjtu.homecoo.appserver.processor.app.AppProcesser;
import ecjtu.homecoo.remoting.netty.NettyRemotingServer;
import ecjtu.homecoo.remoting.netty.NettyServerConfig;

public class AppServerController {
	 // 服务端网络请求处理线程池
  
	public static void main(String[] args) {
		initSpring();
//		InternalLoggerFactory.setDefaultFactory(new Log4JLoggerFactory());
		ChannelManager channelManager = ChannelManager.getInstance();
		ClientHouseKeepingService clientHousekeepingService = new  ClientHouseKeepingService(channelManager);
		NettyServerConfig nettyServerConfig = new NettyServerConfig();
		clientHousekeepingService.start();
		NettyRemotingServer nettyRemotingServer = new NettyRemotingServer(nettyServerConfig,clientHousekeepingService);
		
		ExecutorService remotingExecutor = Executors.newFixedThreadPool(100);
		
		//手机这边的报文，每次有新的报文，都要在这注册一下
		nettyRemotingServer.registerProcessor((short)2, new AppProcesser(channelManager), remotingExecutor);
		nettyRemotingServer.registerDefaultProcessor(new DefaultProcessor(channelManager),remotingExecutor);
		nettyRemotingServer.start();
	     
	}
	
	private static void initSpring(){
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
			context.start();
	}
}