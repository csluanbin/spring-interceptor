package com.luanbin.Interceptor;

import java.util.Comparator;

public class InterceptorComparator  implements Comparator<InterceptorComponent>{

	@Override
	public int compare(InterceptorComponent component1, InterceptorComponent component2) {
		return component1.getOrder()-component2.getOrder();
		/*if(component1.getOrder()>component2.getOrder())
		{
			return 1;
		}
		else
		{
			if(component1.getOrder()<component2.getOrder())
			{
				return -1;
			}
		}
		return 0;*/
	}

}
