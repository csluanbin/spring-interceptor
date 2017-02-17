package com.luanbin.beandefinitionparser;
 

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;  
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;  
import org.springframework.beans.factory.xml.BeanDefinitionParser;  
import org.springframework.beans.factory.xml.ParserContext;  
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.w3c.dom.Element;  

import com.luanbin.Interceptor.Interceptor;
import com.luanbin.Interceptor.InterceptorComparator;
import com.luanbin.Interceptor.InterceptorComponent;
import com.luanbin.scanclass.ClassFilter;
import com.luanbin.scanclass.ScanClass;
import com.luanbin.scanclass.impl.ScanClassImpl;
  
public class InterceptorScanBeanDefinitionParser implements BeanDefinitionParser {  
  
    @Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {  
  
        String packageName = element.getAttribute("scan-package");
        
        ScanClass scanner=new ScanClassImpl();
        Set<Class<?>> classes=new HashSet<Class<?>>();
        scanner.scan(packageName, true, classes, new ClassFilter(){
        	public boolean accpet(Class component){
        		if((component.isAnnotationPresent(Interceptor.class))&&(HandlerInterceptor.class.isAssignableFrom(component)))
        		{
        			return true;
        		}
        		/*if(!component.isAnnotationPresent(Interceptor.class))
    			{
        			Annotation[] ans=component.getAnnotations();
        			System.out.println("component: "+component.getName());
    				//System.out.println("annotation");
    			}
    			if(!HandlerInterceptor.class.isAssignableFrom(component))
    			{
    				System.out.println("AssignableFrom");
    			}*/
        		return false;
        	}
        });
        InterceptorComponent[] list_interceptor=new InterceptorComponent[classes.size()];
        int count=0;
        for(Class component:classes){
        	Interceptor annotation=(Interceptor) component.getAnnotation(Interceptor.class);
        	int order=annotation.order();
        	String[] pathPatterns=annotation.path().split(";");
        	list_interceptor[count]=new InterceptorComponent(order, pathPatterns, component);
        	count++;
        }
        Arrays.sort(list_interceptor, new InterceptorComparator());
        for(InterceptorComponent component: list_interceptor){
        	BeanComponentDefinition beandefinition=assemble(component, parserContext);
        	parserContext.registerComponent(beandefinition);
        }
        return null;
    }
    
    private BeanComponentDefinition assemble(InterceptorComponent component, ParserContext parserContext) {
    	RootBeanDefinition beanDefinition = new RootBeanDefinition(MappedInterceptor.class);
        beanDefinition.setSource(null);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        String[] pathPatterns=component.getPathPatterns();
        
        String[] aliases=new String[1];
        aliases[0]=component.getComponent().getName();//"com.tan.interceptor.LegelInterceptor";
        String beanName=component.getComponent().getSimpleName();//"LegelInterceptor";
        BeanDefinition beanDefinition1=new GenericBeanDefinition();
        beanDefinition1.setBeanClassName(component.getComponent().getName());
        
        BeanDefinitionHolder interceptorBean=new BeanDefinitionHolder(beanDefinition1, beanName, aliases);
        
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, pathPatterns);
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(1, interceptorBean);

        String beanName1 = parserContext.getReaderContext().registerWithGeneratedName(beanDefinition);
		return new BeanComponentDefinition(beanDefinition, beanName1);
    }
}