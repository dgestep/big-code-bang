package ${topLevelDomain}.${companyName}.${productName}.model;

import org.springframework.jndi.JndiTemplate;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Extension of the Spring JNDITemplate class which contains logic to retrieve an InitialContext
 * implementation suited for a Tomcat server.
 *
 * @author ${codeAuthor}.
 */
public class TomcatJndiTemplate extends JndiTemplate {
    /**
     * Creates an instance of this class.
     */
    public TomcatJndiTemplate() {
        super();
    }

    /**
     * Creates an instance of this class.
     *
     * @param environment the environment properties to apply to the JNDI template.
     */
    public TomcatJndiTemplate(final Properties environment) {
        super(environment);
    }

    @Override
    protected Context createInitialContext() throws NamingException {
        final Context ctx = new InitialContext();
        return (Context) ctx.lookup("java:comp/env");
    }
}
