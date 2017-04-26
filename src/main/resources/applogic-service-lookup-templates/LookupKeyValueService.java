package ${topLevelDomain}.${companyName}.${productName}.model.service.lookup;

import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValue;
import ${topLevelDomain}.${companyName}.${productName}.model.service.CrudService;

/**
 * Defines a class which can store and lookup key-value pair entries.
 *
 * @author ${codeAuthor}.
 */
public interface LookupKeyValueService extends CrudService<LookupKeyValue> {
    /**
     * Deletes all entries that are older than the configured retention period.
     *
     * @return returns the number of entries that were purged.
     */
    int purgeExpiredEntries();
}
