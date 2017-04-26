package ${topLevelDomain}.${companyName}.${productName}.model.repository;

import ${topLevelDomain}.${companyName}.${productName}.model.exception.SystemLoggedException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Adds, updates, and retrieves generic information from the database.
 *
 * @param <T> the type of object being persisted.
 * @author ${codeAuthor}.
 */
@Repository("CruRepository")
public class CruRepositoryImpl<T> implements CruRepository<T> {
    private static final String SEQ_SQL = "SELECT %s.NEXTVAL as NEXTVAL FROM dual";

    @PersistenceContext
    private EntityManager entityManager;

    @Resource(name = "ApplicationRepository")
    private ApplicationRepository sqlRepository;

    /**
     * Returns the {@link ApplicationRepository} allowing for native SQL to be used.
     *
     * @return the SQL repository.
     */
    protected ApplicationRepository nativeSql() {
        if (entityManager.isJoinedToTransaction()) {
            entityManager.flush();
            entityManager.clear();
        }
        return sqlRepository;
    }

    /**
     * Returns the {@link EntityManager}.
     *
     * @return the entity manager.
     */
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void add(T data) {
        if (data != null) {
            entityManager.persist(data);
            entityManager.flush();
        }
    }

    @Override
    public void save(final T data) {
        if (data != null) {
            entityManager.merge(data);
            entityManager.flush();
        }
    }

    @Override
    @SuppressWarnings("PMD.UnnecessaryWrapperObjectCreation")
    public int getNextSequenceValue(final String sequencerName) {
        final String sql = String.format(SEQ_SQL, sequencerName);
        return nativeSql().getJdbcTemplate().query(sql, rs -> {
            final Integer nextVal;
            if (rs.next()) {
                nextVal = Long.valueOf(rs.getLong("NEXTVAL")).intValue();
            } else {
                throw new SystemLoggedException(String.format("Sequencer %s is not found", sequencerName));
            }
            return nextVal;
        });
    }

    @Override
    public T retrieve(final Class<T> clz, final Object key) {
        assertClass(clz);

        final T data;
        if (key == null) {
            data = null;
        } else {
            data = (T) entityManager.find(clz, key);
        }
        return data;
    }

    /**
     * Assets the supplied class is valid.
     *
     * @param clz the class.
     */
    private void assertClass(final Class<T> clz) {
        if (clz == null) {
            throw new SystemLoggedException("The class parameter can not be null", "MISSING_CLASS");
        }
    }
}
