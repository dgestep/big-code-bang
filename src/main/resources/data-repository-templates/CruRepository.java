package com.${companyName}.${productName}.model.repository;

/**
 * Defines a class which adds, updates, and retrieves generic information from the database.
 *
 * @param <T> the type of object being persisted.
 * @author ${codeAuthor}.
 */
public interface CruRepository<T> {

    /**
     * Adds a record to the data store.
     *
     * @param data the data to add.
     */
    void add(T data);

    /**
     * Updates a record in the data store.
     *
     * @param data the data to save.
     */
    void save(T data);

    /**
     * Returns the next sequencer value associated with the supplied sequencer name.
     *
     * @param sequencerName the sequencer name.
     * @return the next value.
     */
    int getNextSequenceValue(String sequencerName);

    /**
     * Returns the data associated with the supplied ID.
     *
     * @param clz the class type of the data being retrieved.
     * @param key identifies the data to retrieve.
     * @return the data or null if not found.
     */
    T retrieve(Class<T> clz, Object key);
}
