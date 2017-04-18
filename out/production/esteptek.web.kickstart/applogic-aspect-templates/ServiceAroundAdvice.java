package com.${companyName}.${productName}.model.aspect;

import com.${companyName}.${productName}.model.data.MessageData;
import com.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import com.${companyName}.${productName}.model.exception.DataInputException;
import com.${companyName}.${productName}.model.exception.SystemLoggedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Locale;

/**
 * An implementation of {@link AspectAdvice} which proxies a {@link Service} method with exception handling logic.
 *
 * @author ${codeAuthor}.
 */
public class ServiceAroundAdvice implements AspectAdvice {

    @Override
    public Object proxyService(final ProceedingJoinPoint pjp) throws Throwable {
        final Object target = pjp.getTarget();
        if (isNotServiceWrapper(target)) {
            // the target class doesn't contain the annotation which identifies
            // it as a class needing this logic.
            return pjp.proceed();
        }

        final Object[] args = pjp.getArgs();

        try {
            return pjp.proceed();
        }
        catch (final DataInputException | SystemLoggedException ex) {
            // rethrow
            throw ex;
        }
        catch (final PersistenceException pe) {
            final Throwable thr = ExceptionUtils.getRootCause(pe);
            handleSqlIntegrityIssue(thr, args);

            // throw if nothing else hit
            throw new SystemLoggedException(pe);
        }
        catch (final Throwable throwable) {
            // all other exceptions get logged
            throw new SystemLoggedException(throwable);
        }
    }

    /**
     * Attempts to throw a user-friendly exception when an SQL integrity exception has been caught.
     *
     * @param thr  the root throwable.
     * @param args any arguments sent to the called method.
     */
    private void handleSqlIntegrityIssue(final Throwable thr, final Object[] args) {
        if (!(thr instanceof SQLIntegrityConstraintViolationException)) {
            return;
        }

        handleUniqueConstraint(thr, args);
        handleParentKeyViolationConstraint(thr, args);
        handleChildViolationConstraint(thr, args);
    }

    /**
     * Attempts to throw a user-friendly exception when an SQL integrity exception has been caught regarding a duplicate
     * key.
     *
     * @param thr  the root throwable.
     * @param args any arguments sent to the called method.
     */
    private void handleUniqueConstraint(final Throwable thr, final Object[] args) {
        final String msg = thr.toString().toLowerCase(Locale.US);
        final boolean uniqueContraintViolation =
                msg.contains("unique constraint") && msg.contains("violated");
        if (uniqueContraintViolation) {
            String argMsg = getArgumentMessages(args);
            final MessageData messageData = new MessageData(GeneralMessage.G010, argMsg);
            throw new DataInputException(messageData);
        }
    }

    /**
     * Attempts to throw a user-friendly exception when an SQL integrity exception has been caught regarding a parent
     * key violation.
     *
     * @param thr  the root throwable.
     * @param args any arguments sent to the called method.
     */
    private void handleParentKeyViolationConstraint(final Throwable thr, final Object[] args) {
        final String msg = thr.toString().toLowerCase(Locale.US);
        final boolean violation = msg.contains("integrity constraint") && msg.contains("parent key");
        if (violation) {
            String argMsg = getArgumentMessages(args);
            final MessageData messageData = new MessageData(GeneralMessage.G011, argMsg);
            throw new DataInputException(messageData);
        }
    }

    /**
     * Attempts to throw a user-friendly exception when an SQL integrity exception has been caught regarding a parent
     * key violation.
     *
     * @param thr  the root throwable.
     * @param args any arguments sent to the called method.
     */
    private void handleChildViolationConstraint(final Throwable thr, final Object[] args) {
        final String msg = thr.toString().toLowerCase(Locale.US);
        final boolean violation = msg.contains("integrity constraint") && msg.contains("violated - child record found");
        if (violation) {
            String argMsg = getArgumentMessages(args);
            final MessageData messageData = new MessageData(GeneralMessage.G012, argMsg);
            throw new DataInputException(messageData);
        }
    }

    /**
     * Returns a message containing the arguments sent to the called method.
     *
     * @param args any arguments sent to the called method.
     * @return the message.
     */
    @SuppressWarnings("PMD.UseStringBufferForStringAppends")
    private String getArgumentMessages(Object[] args) {
        if (args == null || args.length == 0) {
            return StringUtils.EMPTY;
        }

        final StringBuilder buf = new StringBuilder();
        for (final Object argument : args) {
            buf.append(argument.toString()).append(", ");
        }
        String argMsg = buf.toString();
        final int idx = argMsg.lastIndexOf(", ");
        if (idx >= 0) {
            argMsg = argMsg.substring(0, idx);
        }
        if (argMsg.length() > 0) {
            argMsg = "Arguments: " + argMsg;
        }
        return argMsg;
    }

    /**
     * Returns true if the supplied object is NOT a class that contains the {@link Service} annotation.
     *
     * @param target the class to evaluate.
     * @return true if not a view wrapper class.
     */
    private boolean isNotServiceWrapper(final Object target) {
        final Class<Service> clz = Service.class;
        final Service proxy = target.getClass().getAnnotation(clz);
        return proxy == null;
    }
}
