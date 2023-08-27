package dbwls.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) // 클래스에 사용할 어노테이션이면 TYPE
@Retention(RetentionPolicy.RUNTIME) // 어노테이션이 런타임까지 살아있음
public @interface ClassAop {
}
