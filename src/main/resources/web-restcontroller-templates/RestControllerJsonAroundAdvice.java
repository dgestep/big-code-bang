package ${topLevelDomain}.${companyName}.${productName}.restcontroller;

import ${topLevelDomain}.${companyName}.${productName}.model.JsonResponseData;
import ${topLevelDomain}.${companyName}.${productName}.model.aspect.AspectAdvice;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.SecurityMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.SystemLoggedException;
import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * An implementation of {@link AspectAdvice} which proxies a {@link RestController} implementation where the method
 * being invoked returns JSON.
 *
 * @author ${codeAuthor}.
 */
public class RestControllerJsonAroundAdvice implements AspectAdvice {
    @Override
    public Object proxyService(final ProceedingJoinPoint pjp) throws Throwable {
        if (isNotJsonService(pjp)) {
            // target class is not a Rest Controller or method is not returning JSON
            return pjp.proceed();
        }

        JsonResponseData json;
        try {
            return pjp.proceed();
        }
        catch (final AccessDeniedException ade) {
            final MessageData messageData = new MessageData(SecurityMessage.SC004);
            json = new JsonResponseData(HttpServletResponse.SC_CONFLICT, messageData);
        }
        catch (final DataInputException die) {
            json = new JsonResponseData(HttpServletResponse.SC_CONFLICT, die.getMessageData());
        }
        catch (final Throwable throwable) {
            if (!(throwable instanceof SystemLoggedException)) {
                final Logger logger = LogFactory.getLogger();
                logger.error(throwable.getMessage(), throwable);
            }
            json = new JsonResponseData(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, GeneralMessage.G004);
        }
        return new ResponseEntity<>(json, HttpStatus.OK);
    }

    /**
     * Returns true if the method being proxied is NOT a {@link RequestMapping} method that returns JSON.
     *
     * @param pjp the {@link ProceedingJoinPoint} instance.
     * @return true if not a JSON method.
     */
    private boolean isNotJsonService(final ProceedingJoinPoint pjp) {
        boolean notJsonMethod = true;

        if (isNotRestControllerClass(pjp.getTarget())) {
            return notJsonMethod;
        }

        final Signature signature = pjp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            return notJsonMethod;
        }

        final MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        final Method method = methodSignature.getMethod();
        final RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        if (mapping == null) {
            return notJsonMethod;
        }

        final String[] produces = mapping.produces();
        if (produces == null || produces.length == 0) {
            return notJsonMethod;
        }

        for (String type : produces) {
            if (type.contains("json") || type.contains("JSON")) {
                notJsonMethod = false;
                break;
            }
        }

        return notJsonMethod;
    }

    /**
     * Returns true if the supplied object is NOT a class that contains the {@link RestController} annotation.
     *
     * @param target the class to evaluate.
     * @return true if not a RestController class.
     */
    private boolean isNotRestControllerClass(final Object target) {
        final Class<RestController> clz = RestController.class;
        final RestController proxy = target.getClass().getAnnotation(clz);
        return proxy == null;
    }
}
