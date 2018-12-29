package com.bnym.pr.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.sf.ehcache.CacheManager;

public class PeerReviewServletContextListeners implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		CacheManager.getInstance().shutdown();		
	}

}
