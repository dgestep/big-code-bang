package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:test-model-spring-context.xml" })
@Transactional
@Rollback
public abstract class ApplicationTestCase extends TestCase {
}
