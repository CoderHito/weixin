/**
 * 
 */
package com.cf.cfsecurity.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.cf.base.BaseSupport;
import com.cf.datalock.DataLock;



/**
 * @author chl_seu
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class HttpSessionMessageListener implements HttpSessionListener {
	
	private static HashMap sessionMap=new HashMap();
	private static final Logger log = Logger.getLogger(HttpSessionMessageListener.class);
	
	public static HashMap getSessionMap() {
		return sessionMap;
	}
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionCreated(HttpSessionEvent event) {
		HttpSession session=event.getSession();
		synchronized (HttpSessionMessageListener.class) {
			sessionMap.put(session.getId(), session);
			log.debug("The current count of session is [" + sessionMap.size()+ "]");
		}

	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session=event.getSession();
		synchronized (HttpSessionMessageListener.class) {
			String username=null;
			try{
				username=BaseSupport.CframeUtil.GetCurrentUserName();
			}catch(Exception e){
				log.info(e.getMessage(),e);
			}
			if(null!=username){
				HashMap<String,String> map=new HashMap<>();
				map.put("LOCKER",username);
				BaseSupport.DataLock.unLockData(map);
			}
			sessionMap.remove(session.getId());
			log.debug("The current count of session is [" + sessionMap.size()
					+ "]");
		}

	}

}
