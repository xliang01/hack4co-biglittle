package org.bbbs.sportsbuddies.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class BaseService {

	protected ApplicationContext context;
	
	public BaseService()
	{
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}