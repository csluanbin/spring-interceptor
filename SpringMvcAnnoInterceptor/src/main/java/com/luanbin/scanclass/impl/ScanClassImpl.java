package com.luanbin.scanclass.impl;

import java.io.File;
import java.io.FileFilter;
import java.util.Set;

import com.luanbin.scanclass.ClassFilter;
import com.luanbin.scanclass.ScanClass;

public class ScanClassImpl implements ScanClass{
	
	private String suffix=".class";
	@Override
	public void scan(String packageName, final boolean recursive, Set<Class<?>> classes, ClassFilter filter) {
		String relativelyPath=Thread.currentThread().getContextClassLoader().getResource("").getPath();
		String packagePath=relativelyPath+packageName.replace(".", "\\");
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {  
            return;
        }
		File[] dirfiles = dir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return (recursive && file.isDirectory())||(file.getName().endsWith(suffix));
            }
        });
		for (File file : dirfiles){ 
            if (file.isDirectory()) {
            	scan(packageName + "."+ file.getName(), recursive, classes, filter);
            } 
            else 
            { 
                String className = file.getName().substring(0, file.getName().length()-suffix.length());  
                try
                {
                	Class component=Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className);
                	if(filter.accpet(component))
                	{
                		classes.add(component);
                	}
                	
                } 
                catch (ClassNotFoundException e) 
                {  
                    e.printStackTrace();  
                }  
            }  
        }
	}

}
