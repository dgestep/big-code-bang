package ${topLevelDomain}.${companyName}.${productName}.model.aspect;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Defines a class which contains aspect advice.
 *
 * @author ${codeAuthor}.
 */
public interface AspectAdvice {

    /**
     * Proxies a view method using the supplied {@link ProceedingJoinPoint} to control where in the method to inject the
     * advice.
     *
     * @param proceedingJoinPoint controls where in the method to inject the advice.
     * @return the return value from the proxied method.
     * @throws Throwable thrown for all exceptions.
     */
    Object proxyService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;
}
