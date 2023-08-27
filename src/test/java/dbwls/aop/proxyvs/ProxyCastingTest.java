package dbwls.aop.proxyvs;

import dbwls.aop.member.MemberService;
import dbwls.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {

        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // use JDK 동적 프록시

        // proxy to interface casting success
        MemberService memberServiceProxy =(MemberService) proxyFactory.getProxy();

        // proxy to impl class casting false, ClassCastException
        Assertions.assertThrows(ClassCastException.class, () -> {
            MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;
        });
    }

    @Test
    void cglibProxy() {

        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // use CGLIB proxy

        // proxy to interface casting success
        MemberService memberServiceProxy =(MemberService) proxyFactory.getProxy();

        // proxy to impl class casting success
        MemberServiceImpl castingMemberService = (MemberServiceImpl) memberServiceProxy;

    }
}
