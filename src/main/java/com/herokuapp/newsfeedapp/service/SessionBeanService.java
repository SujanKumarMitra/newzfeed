package com.herokuapp.newsfeedapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * This class acts as a session listener. Whenever a session scoped bean of
 * {@link CustomizedNewsService} s created, it adds its reference to
 * {@link SessionBeanService#sessionBeans} list.
 * 
 * This is used the the scheduler service when update is triggered and needs to
 * refresh all custom users article list.
 * 
 * @since 2020-05-19
 * @author Sujan Kumar Mitra
 *
 */
@Service
public class SessionBeanService {

	/**
	 * holds the reference of session scope beans.
	 */
	private List<CustomizedNewsService> sessionBeans = new ArrayList<>();

	/**
	 * adds a bean reference to the list
	 * 
	 * @param bean the bean to add
	 * @since 2020-05-19
	 * @author Sujan Kumar Mitra
	 */
	public void addSessionBean(CustomizedNewsService bean) {
		this.sessionBeans.add(bean);
	}

	/**
	 * removes the reference of session scoped bean
	 * 
	 * @param bean bean to remove
	 * @author Sujan Kumar Mitra
	 */
	public void removeSessionBean(CustomizedNewsService bean) {
		this.sessionBeans.remove(bean);
	}

	public List<CustomizedNewsService> getSessionBeans() {
		return sessionBeans;
	}

	public void setSessionBeans(List<CustomizedNewsService> sessionBeans) {
		this.sessionBeans = sessionBeans;
	}

}
