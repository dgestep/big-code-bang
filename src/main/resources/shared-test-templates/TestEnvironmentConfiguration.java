package ${topLevelDomain}.${companyName}.${productName}.model;

import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Region;
import junit.framework.TestCase;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class TestEnvironmentConfiguration extends TestCase {
    private static final String RUNTIME_SYSTEM_PROPERTY = "runtime.environment";

    @Test
    public void testRegionInitialization() {
        // initiates region
        System.setProperty(RUNTIME_SYSTEM_PROPERTY, "PRODUCTION");
        Region region = EnvironmentConfiguration.getRegion();
        Assert.assertEquals(Region.PRODUCTION, region);

        // returns initiated region
        Assert.assertEquals(Region.PRODUCTION, EnvironmentConfiguration.getRegion());
    }
}
