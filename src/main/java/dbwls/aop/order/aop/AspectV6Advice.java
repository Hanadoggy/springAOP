package dbwls.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    @Around("dbwls.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // @Before
            log.info("[start Transaction] {}", joinPoint.getSignature());

            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[end Transaction] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[rollback Transaction] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[release Resources] {}", joinPoint.getSignature());
        }
    }

    @Before("dbwls.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[before] {}", joinPoint.getSignature());
    } // joinPoint.proceed() 까지만 구현하면 이후 호출은 자동으로 실행됨

    // value에 포인트컷을, returning에 코드가 실행된 후 반환된 결과 값을 매핑, 결과는 자동으로 return
    @AfterReturning(value = "dbwls.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result) {
        log.info("[return] {} return={}", joinPoint.getSignature(), result);
    } // return ...;이 불가능하기 때문에 결과를 조작할 수는 있으나 변경할 수는 없음

    // value에 포인트컷을, throwing에 발생한 오류를 매핑, 오류는 자동으로 던짐
    @AfterThrowing(value = "dbwls.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[ex] {} message={}", ex);
    }

    @After("dbwls.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[after] {}", joinPoint.getSignature());
    }
}
