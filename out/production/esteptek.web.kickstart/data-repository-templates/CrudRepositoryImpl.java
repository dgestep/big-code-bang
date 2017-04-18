package com.${companyName}.${productName}.model.repository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * Adds, updates, deletes, and retrieves generic information from the database.
 *
 * @param <T> the type of object being persisted.
 * @author ${codeAuthor}.
 */
@Repository("CrudRepository")
public class CrudRepositoryImpl<T> extends CruRepositoryImpl<T> implements CrudRepository<T> {
    @Override
    public void delete(final Class<T> clz, final Object key) {
        if (key == null) {
            return;
        }

        final T data = retrieve(clz, key);
        if (data != null) {
            final EntityManager em = getEntityManager();
            em.remove(data);
            em.flush();
        }
    }
}
