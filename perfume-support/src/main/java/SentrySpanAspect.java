import io.sentry.ISpan;
import io.sentry.ITransaction;
import io.sentry.Sentry;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Aspect
@Configuration
@ConditionalOnProperty(value = "sentry.enabled", havingValue = "true")
public class SentrySpanAspect {

  @Pointcut("within(@org.springframework.stereotype.Repository *)")
  public void repositoryBeanMethods() {
  }

  @Pointcut("within(@org.springframework.stereotype.Service *)")
  public void serviceBeanMethods() {
  }

  @Before("repositoryBeanMethods() || serviceBeanMethods()")
  public void beforeMethodExecution(JoinPoint joinPoint) {
    ITransaction span =
        Sentry.startTransaction("RDPF", joinPoint.getSignature().toShortString(), true);
    span.setTag("class", joinPoint.getTarget().getClass().getSimpleName());
    span.setTag("method", joinPoint.getSignature().getName());
  }

  @After("repositoryBeanMethods() || serviceBeanMethods()")
  public void afterMethodExecution(JoinPoint joinPoint) {
    ISpan span = Sentry.getCurrentHub().getSpan();
    if (Objects.isNull(span)) {
      return;
    }
    span.finish();
  }

  @Around("repositoryBeanMethods() || serviceBeanMethods()")
  public Object aroundMethodExecution(ProceedingJoinPoint joinPoint) throws Throwable {
    return joinPoint.proceed();
  }
}
