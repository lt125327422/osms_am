package com.amazon.test;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author wanghw
 * @date 2015-4-17
 * @description TODO
 * @version
 */
public class SpringBeanUtils implements ApplicationContextAware {
	private static ApplicationContext context = null;
	private static SpringBeanUtils stools = null;

	public synchronized static SpringBeanUtils init() {
		if (stools == null) {
			stools = new SpringBeanUtils();
		}
		return stools;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public synchronized static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
