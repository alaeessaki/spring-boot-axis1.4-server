package com.exemple.service;

import java.rmi.RemoteException;

import javax.servlet.ServletContext;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.server.ServiceLifecycle;
import javax.xml.rpc.server.ServletEndpointContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;



/**
 * This is the class that is registered in server-config.wsdd
 * 
 * This class is designed to separate the RPC aspects of the SOAP API from your
 * spring based implementation. You could go even further by mapping schema
 * objects to your business domain objects, but we'll keep things simple for now
 * and just the schema class in our implementation.
 * 
 * This class is meant to demonstrate binding Axis created classes with those
 * created by Spring.
 */
public class AxisWebService implements ServiceLifecycle, ServiceTest {

	protected ServletEndpointContext servletEndpointContext;

	protected WebApplicationContext webApplicationContext;

	protected ServiceTestImpl serviceTest;

	@Override
	public void init(Object context) throws ServiceException {
		if (!(context instanceof ServletEndpointContext)) {
			throw new ServiceException("ServletEndpointSupport needs ServletEndpointContext, not [" + context + "]");
		}
		this.servletEndpointContext = (ServletEndpointContext) context;
		ServletContext servletContext = this.servletEndpointContext.getServletContext();
		this.webApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		this.serviceTest = webApplicationContext.getBean(ServiceTestImpl.class);
	}

	@Override
	public void destroy() {
	}
	
	@Override
	public int add(int a, int b) {
		return serviceTest.add(a, b);
	}
	@Override
	public String show(String tmp) {
		return serviceTest.show(tmp);
	}
	
	

}
