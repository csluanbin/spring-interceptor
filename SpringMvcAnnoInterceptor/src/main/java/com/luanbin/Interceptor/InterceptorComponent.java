package com.luanbin.Interceptor;

import java.util.Arrays;

public class InterceptorComponent implements Comparable{

	private int order;
	private String[] pathPatterns;
	private Class component;
	
	public InterceptorComponent(int order, String[] pathPatterns, Class component) {
		super();
		this.order = order;
		this.pathPatterns = pathPatterns;
		this.component=component;
	}

	public int getOrder() {
		return order;
	}


	public void setOrder(int order) {
		this.order = order;
	}


	public String[] getPathPatterns() {
		return pathPatterns;
	}


	public void setPathPatterns(String[] pathPatterns) {
		this.pathPatterns = pathPatterns;
	}

	public Class getComponent() {
		return component;
	}

	public void setComponent(Class component) {
		this.component = component;
	}
	@Override
	public int compareTo(Object o) {
		InterceptorComponent interceptor=(InterceptorComponent)o;
		if(this.order>interceptor.getOrder())
		{
			return 1;
		}
		else
		{
			if(this.order<interceptor.getOrder())
			{
				return -1;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "InterceptorComponent [order=" + order + ", pathPatterns="
				+ Arrays.toString(pathPatterns) + ", component=" + component
				+ "]";
	}
}
