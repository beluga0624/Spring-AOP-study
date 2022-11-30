package org.fintech.aop;



import java.util.Arrays;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;

@Aspect //해당 클래스는 공통 기능을 모듈화하는 클래스 임을 선언
@Log4j //로그 출력
@Component //개발자가 직접 만든 클래스를 빈 등록하는 어노테이션
//시스템 개발시 미리 개발된 클래스를 빈 등록하기 위해서는 @Bean 을 사용
public class LogAdvice {
	// * org.fintech.service.SampleService*.*(..)
	//공통 기능을 어느 Target에 적용시킬 것인지 선언하는 문법
	//*(접근제한자 관계없이) org.fintech.service(패키지).SampleService*(SampelService로 시작하는 모든 클래스의).*(..)모든 메서드에 대해 적용
	//.. : 모든 매개변수라는 의미
	// * : 반드시 1개의 매개변수는 존재해야 한다
	@Before("execution(* org.fintech.service.SampleService*.*(..))")
	public void logBefore() {
		log.info("===================");
	}
	
	@Before("execution(* org.fintech.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
	public void logBeforeWithParam(String str1, String str2) {
		log.info("str1: " + str1);
		log.info("str2: " + str2);
	}
	
	@AfterThrowing(pointcut = "execution(* org.fintech.service.SampleService*.*(..))", throwing = "exception")
	public void logException(Exception exception) {
		log.info("Exception....!!!!");
		log.info("exception: " + exception);
	}
	
	//공통사항(Advice)이 Target 객체의 메서드를 감싸서 메서드 호출전과 호출후에 공통기능을 수행하는 어노테이션
	@Around("execution(* org.fintech.service.SampleService*.*(..))")
	public Object logTime(ProceedingJoinPoint pjp) {
		long start = System.currentTimeMillis();
		
		//공통기능을 적용하는 Target 즉 객체이름을 리턴
		log.info("Target: " + pjp.getTarget());
		//공통기능을 수행하는 객체의 매개변수 값들을 출력
		log.info("Param: " + Arrays.toString(pjp.getArgs()));
		
		Object result = null;
		
		//pjp.proceed를 기준으로 이전에는 @Before, 이후에는 @After가 실행된다.
		try {
			result = pjp.proceed();
		}catch(Throwable e) {
			e.printStackTrace();
		}
		
		long end = System.currentTimeMillis();
		
		log.info("TIME: " + (end - start));
		
		return result;
	}
}
