package com.${companyName}.${productName}.model.service;

/**
 * Represents a service which saves, retrieves, and deletes information from the system.
 *
 * @param <T>
 * @author ${codeAuthor}.
 */
public interface CrudService<T> extends CruService<T> {
    /**
     * Deletes a record from the system.
     *
     * @param data the data to delete.
     */
    void delete(T data);
}
