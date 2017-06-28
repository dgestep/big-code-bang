package ${topLevelDomain}.${companyName}.${productName}.model.repository.user;

import ${topLevelDomain}.${companyName}.${productName}.model.data.UserToken;
import ${topLevelDomain}.${companyName}.${productName}.model.repository.CrudRepository;

/**
 * Defines a class which can add, update, delete, and query user tokens from the database.
 *
 * @author ${codeAuthor}.
 */
public interface UserTokenRepository extends CrudRepository<UserToken> {

    /**
     * Updates all tokens, replacing the email address with the supplied value.
     *
     * @param fromEmailAddress the email address to replace.
     * @param toEmailAddress   the email address to set.
     * @return returns the number of rows affected.
     */
    int updateByEmail(String fromEmailAddress, String toEmailAddress);
}
