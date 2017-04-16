package com.${companyName}.${productName}.model.repository.user;


import com.${companyName}.${productName}.model.criteria.UserSearchCriteriaData;
import com.${companyName}.${productName}.model.data.UserProfile;
import com.${companyName}.${productName}.model.repository.CrudRepository;

import java.util.List;

/**
 * Defines a class which can add, update, delete, and query user information from the database.
 *
 * @author ${codeAuthor}.
 */
public interface UserRepository extends CrudRepository<UserProfile> {
    /**
     * Returns a list of all users in the system.
     *
     * @param criteria the search criteria.
     * @return the users or empty list if no users exist in the system.
     */
    List<UserProfile> search(UserSearchCriteriaData criteria);
}
