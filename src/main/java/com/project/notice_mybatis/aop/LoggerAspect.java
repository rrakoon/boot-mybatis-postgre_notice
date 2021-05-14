package com.project.notice_mybatis.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//Bean등록 어노테이션.
@Component
/*
    @Bean : 외부 라이브러리를 Bean등록 할때 (개발자 제어 x)
    @Component : 개발자가 직접 정의한 클래스를 Bean등록.
 */
@Aspect //AOP 기능을하는 클래스의 클래스 레벨에 지정하는 어노테이션.
public class LoggerAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    //Advice의 종류 中 하나. 메서드의 호출 자체를 제어 가능하다.
    /*
       Before Advice (@Before) - Target 메서드 호출 이전에 적용할 어드바이스 정의.
       After returning (@AfterReturning) - Target 메서드가 성공적으로 실행되고 결과값을 반환한뒤 적용.
       After throwing (@AfterThrowing) - Target 메서드에서 예외 발생 이후 적용 (try catch 의 catch격)
       After (@After) - Target 메서드에서 예외 발생 관계없이 적용 (try catch 의 finally 격)
       Around (@Around) - Target 메서드의 호출 이전과 이후 모두 적용.
    */

    /*
        execution - Point Cut 지정 문법
        접근제어자, 리턴타입, 타입패턴, 메서드, 파라미터타입, 예외 타입 등 조합해서 포인트컷 만들수 있음.
    */
    @Around("execution(* com.project.notice_mybatis..controller.*Controller.*(..)) ||" +
            "execution(* com.project.notice_mybatis..service.*Impl.*(..)) || " +
            "execution(* com.project.notice_mybatis..mapper.*Mapper.*(..))")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        /*
            ProceedingJoinPoint
                Object[] getArgs() - 전달되는 모든 파라미터를 Object타입의 배열로 가져옴.
                String getKind() - 해당 Advice타입을 가져옴.
                Signature getSignature() - 실행되는 대상의 객체의 메서드에 대한 정보를 가져옴.
                Object getTarget() - Target 객체를 가져옴.
                Object getThis() - 어드바이스를 행하는 객체를 가져옴.
        */
        String type = "";
        String name = joinPoint.getSignature().getDeclaringTypeName();

        if (name.contains("Controller") == true) {
            type = "* Controller ===> ";

        } else if (name.contains("Service") == true) {
            type = "* ServiceImpl ===> ";

        } else if (name.contains("Mapper") == true) {
            type = "* Mapper ===> ";
        }

        logger.debug(type + name + "." + joinPoint.getSignature().getName() + "()");

        return joinPoint.proceed();
    }


}
