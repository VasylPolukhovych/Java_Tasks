package pizza.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import pizza.service.CheckUser;

import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
public class LogAndChecks {

    @Pointcut("execution(* pizza.dao.*.*(..))")
    public void selectAllDAOMethods() {

    }

    @After("selectAllDAOMethods()")
    public void afterAllDAOMethods(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(a -> a.toString())
                .collect(Collectors.joining(","));
        System.out.println("After " + jp.toString() + ", args=[" + args + "]");
    }

    @Pointcut("@annotation(pizza.annotation.CheckUserAnnotation) && args(checkUser,..)")
    public void callCheckUserAnnotation(CheckUser checkUser) {
    }

    @Around("callCheckUserAnnotation(checkUser)")
    public Object aroundCheckUserAnnotation(ProceedingJoinPoint pjp, CheckUser checkUser) throws Throwable {
        Object retVal = null;
        String user = checkUser.getCurrentUser();
        String userDB = checkUser.getUseFromDB(user);
        if (userDB != null) {
            retVal = pjp.proceed();
        } else {
            System.out.println("The User - " + user + " don't have access to see this report");

        }
        return retVal;
    }
}
