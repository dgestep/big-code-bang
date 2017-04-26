package ${topLevelDomain}.${companyName}.${productName}.model.service.lookup;

import ${topLevelDomain}.${companyName}.${productName}.model.ConfigConstant;
import ${topLevelDomain}.${companyName}.${productName}.model.data.LookupKeyValue;
import ${topLevelDomain}.${companyName}.${productName}.model.data.MessageData;
import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.message.GeneralMessage;
import ${topLevelDomain}.${companyName}.${productName}.model.exception.DataInputException;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.lookup.LookupKeyValueRepository;
import ${topLevelDomain}.${companyName}.${productName}.model.service.EntityAssertion;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Stores and lookup key-value pair entries.
 *
 * @author ${codeAuthor}.
 */
@Service("LookupKeyValueService")
public class LookupKeyValueServiceImpl implements LookupKeyValueService {
    @Resource(name = "LookupKeyValueRepository")
    private LookupKeyValueRepository lookupKeyValueRepository;

    @Override
    public void add(LookupKeyValue data) {
        if (data == null) {
            final MessageData message = new MessageData(GeneralMessage.G002, "lookup key value data");
            throw new DataInputException(message);
        }

        if (data.getCreateTs() == null) {
            data.setCreateTs(new Timestamp(new Date().getTime()));
        }

        final EntityAssertion entity = new EntityAssertion();
        entity.evaluate(data);

        lookupKeyValueRepository.add(data);
    }

    @Override
    public void save(LookupKeyValue data) {
        final EntityAssertion entity = new EntityAssertion();
        entity.evaluate(data);

        lookupKeyValueRepository.save(data);
    }

    @Override
    public LookupKeyValue retrieve(Object key) {
        return lookupKeyValueRepository.retrieve(LookupKeyValue.class, key);
    }

    @Override
    public void delete(LookupKeyValue data) {
        lookupKeyValueRepository.delete(LookupKeyValue.class, data.getKey());
    }

    @Override
    public int purgeExpiredEntries() {
        final int maxDays = ConfigConstant.RESET_PASSWORD_MAX_DAYS;
        final Date purgeDate = DateUtils.addDays(new Date(), -maxDays);
        return lookupKeyValueRepository.purgeExpiredEntries(purgeDate);
    }
}
