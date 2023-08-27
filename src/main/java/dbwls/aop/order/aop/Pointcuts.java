package dbwls.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    // 외부에서 참조할 수 있도록 포인트컷만 모아서 선언, public 접근지정자 사용

    // include dbwls.aop.order package and sub packages
    @Pointcut("execution(* dbwls.aop.order..*(..))")
    public void allOrder() {
    } // pointcut signature, 재사용 가능

    // class name pattern = *Service
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {
    }

    @Pointcut("allOrder() && allService()")
    public void orderAndService(){}
}
