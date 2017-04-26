package ${topLevelDomain}.${companyName}.${productName}.model.repository;

/**
 * Defines a class which adds, updates, deletes, and retrieves generic information from the database.
 *
 * @param <T> the type of object being persisted.
 * @author ${codeAuthor}.
 */
public interface CrudRepository<T> extends CruRepository<T> {

    /**
     * Deletes a record from the data store.
     *
     * @param clz the class type of the data being deleted.
     * @param key identifies the data to delete.
     */
    void delete(Class<T> clz, Object key);
}
