package com.${companyName}.${productName}.model.repository.lookup;

import com.${companyName}.${productName}.model.data.LookupKeyValue;
import com.${companyName}.${productName}.model.repository.CrudRepository;

import java.util.Date;

/**
 * Defines a class which adds, updates, deletes, and retrieves values from the LookupKeyValue store.
 *
 * @author ${codeAuthor}.
 */
public interface LookupKeyValueRepository extends CrudRepository<LookupKeyValue> {
    /**
     * Deletes all entries that are older than the supplied number of days.
     *
     * @param entryDate all entries will be purged that fall before or on this date.
     * @return the number of entries purged.
     */
    int purgeExpiredEntries(Date entryDate);
}
