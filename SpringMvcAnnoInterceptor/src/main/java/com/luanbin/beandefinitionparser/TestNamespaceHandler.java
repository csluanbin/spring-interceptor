package com.luanbin.beandefinitionparser;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;  

public class TestNamespaceHandler extends NamespaceHandlerSupport {  
  
    @Override
	public void init() {  
    	//System.out.println("TestNamespaceHandler init");
        registerBeanDefinitionParser("interceptor-scan", new InterceptorScanBeanDefinitionParser());  
    }  
}
