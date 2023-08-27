package dbwls.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@Slf4j
public class AspectV4Pointcut {

    // 외부 포인트컷 호출하여 사용
    @Around("dbwls.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws  Throwable {
        log.info("[log] {}", joinPoint.getSignature()); // join point signature
        return joinPoint.proceed();
    }

    // dbwls.aop.order.* && *Service
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
