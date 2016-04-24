package com.luanbin.scanclass;

import java.util.Set;


public interface ScanClass {
	public void scan(String packageName, final boolean recursive, Set<Class<?>> classes, ClassFilter filter);
}
