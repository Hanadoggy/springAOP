package dbwls.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    /**
     * 어드바이스의 순서는 랜덤으로 스프링이 순서를 보장해주지 않음
     * 어드바이스의 순서를 설정하기 위해선 aspect 단위로 @Order 어노테이션 사용
     * 단 메서드 단위로는 적요되지 않기에 내부 클래스를 선언하여 따로따로 aspect로 분리
     * AspectV5Order class는 aspect를 보관하기 위한 껍데기로 @Aspect 어노테이션 사용 X
     */

    @Aspect
    @Order(2) // 숫자가 낮을수록 우선순위가 높음
    public static class LogAspect {
        @Around("dbwls.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable {
            log.info("[log] {}", joinPoint.getSignature()); // join point signature
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(1)
    public static class TxAspect {
        @Around("dbwls.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

            try {
                log.info("[start Transaction] {}", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[end Transaction] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                log.info("[rollback Transaction] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[release Resources] {}", joinPoint.getSignature());
            }
        }
    }
}
