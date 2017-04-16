package com.${companyName}.${productName}.model.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Represents a user token.
 *
 * @author ${codeAuthor}.
 */
@Entity
@Table(name = "USER_TOKEN")
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
@SuppressFBWarnings
public class UserToken {
    @Id
    @Column(name = "TOKEN_UUID", nullable = false, length = 36)
    private String tokenUuid;

    @Column(name = "USER_UUID", nullable = false, length = 30)
    private String userUuid;

    @Column(name = "EMAIL_ADDRESS", nullable = false, length = 75)
    private String emailAddress;

    @Column(name = "CREATE_TS", nullable = false)
    private Timestamp createTs;

    @Column(name = "LAST_MODIFIED_TS", nullable = false)
    private Timestamp lastModifiedTs;

    public String getTokenUuid() {
        return tokenUuid;
    }

    public void setTokenUuid(String tokenUuid) {
        this.tokenUuid = tokenUuid;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getLastModifiedTs() {
        return lastModifiedTs;
    }

    public void setLastModifiedTs(Timestamp lastModifiedTs) {
        this.lastModifiedTs = lastModifiedTs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserToken ftlnUserToken = (UserToken) o;

        return new EqualsBuilder().append(tokenUuid, ftlnUserToken.tokenUuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(tokenUuid).toHashCode();
    }
}
// CHECKSTYLE:ON
