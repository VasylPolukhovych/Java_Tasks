package pizza.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class Logging {

    @Pointcut("execution(* pizza.dao.*.*(..))")
    public void selectAllDAOMethods() {

    }

    @Before("selectAllDAOMethods()")
    public void beforeAllDAOMethods(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        System.out.println("Before " + jp.toString() + ", args=[" + args + "]");
    }

    @Pointcut("@annotation(pizza.annotation.CheckUserAnnotation) && args(user,userDB,..)")
    public void callCheckUserAnnotation(String user, String userDB) {
    }

    @Around("callCheckUserAnnotation(user,userDB)")
    public Object aroundCheckUserAnnotation(ProceedingJoinPoint pjp, String user, String userDB) throws Throwable {
        Object retVal = null;
        if (userDB != null) {
            retVal = pjp.proceed();
        } else {
            System.out.println("The User - " + user + " don't have access to see this report");

        }
        return retVal;
    }

}
