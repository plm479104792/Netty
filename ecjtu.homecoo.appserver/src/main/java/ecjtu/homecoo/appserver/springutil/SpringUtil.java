package ecjtu.homecoo.appserver.springutil;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class SpringUtil  implements ApplicationContextAware,InitializingBean{
	private static ApplicationContext applicationContext;
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		
		this.applicationContext = applicationContext;
	}
	
	public static <T> T  getObject(String name , Class<T> clsType){
		return applicationContext.getBean(name, clsType);
	}
	
	public static <T> T  getObject(Class<T> clsType){
		return applicationContext.getBean(clsType);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
	}

}
