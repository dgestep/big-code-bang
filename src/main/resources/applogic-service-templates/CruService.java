package com.${companyName}.${productName}.model.service;

/**
 * Represents a service which saves and retrieves information from the system.
 *
 * @param <T>
 * @author ${codeAuthor}.
 */
public interface CruService<T> {

    /**
     * Adds the supplied data to the system.
     *
     * @param data the data to saveDocumentAssociation.
     */
    void add(T data);

    /**
     * Updates the supplied data in the system.
     *
     * @param data the data to saveDocumentAssociation.
     */
    void save(T data);

    /**
     * Returns the data associated with the supplied ID.
     *
     * @param key identifies the data to retrieve.
     * @return the data or null if not found.
     */
    T retrieve(Object key);
}
