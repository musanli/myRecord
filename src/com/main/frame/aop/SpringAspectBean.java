package com.main.frame.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 环绕通知切面类,基于注解
 * @author li_bin
 *
 */
@Component
@Aspect
public class SpringAspectBean {
	
	@Pointcut("execution(* com..*.*(..))")
	private void aspectPointCut(){
	}
	
//	@Before(value="aspectPointCut()")
	public void aspectBefore(JoinPoint joinPoint){
		System.out.println("前置通知 ： " + joinPoint.getSignature().getName());
	}
	
//	@AfterReturning(value="aspectPointCut()" ,returning="ret")
	public void aspectAfterReturning(JoinPoint joinPoint,Object ret){
		System.out.println("后置通知 ： " + joinPoint.getSignature().getName() + " , -->" + ret);
	}

	/** 
	 * @Description: Spring环绕通知
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around(value = "aspectPointCut()")
	public Object aspectAround(ProceedingJoinPoint pjp) throws Throwable{
		String className = pjp.getTarget().getClass().getName();
		String methodName = pjp.getSignature().getName();
		String fullPath = className+"."+methodName;
		System.out.println("开始执行方法["+methodName+"];全路径["+fullPath+"]");
		long time = System.currentTimeMillis();
		Object retVal = pjp.proceed();
		time = System.currentTimeMillis() - time;
		System.out.println("结束执行方法["+methodName+"];耗时["+time+"]全路径["+fullPath+"]");
		return retVal;
	}

	/** 
	 * @Description: Spring异常通知
	 * @author li_bin
	 * @Date: 2017年12月7日
	 * @throws 
	 * @param joinPoint
	 * @param throwable
	 */
	@AfterThrowing(value="aspectPointCut()" ,throwing="throwable")
	public void aspectAfterThrowing(JoinPoint joinPoint,Throwable throwable){
		System.out.println("抛出异常通知 ： " + throwable.getMessage());
	}

}
