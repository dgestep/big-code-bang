package ${topLevelDomain}.${companyName}.${productName}.model.data;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Represents a user credential.
 *
 * @author ${codeAuthor}.
 */
@Entity
@Table(name = "USER_CREDENTIAL")
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
@SuppressFBWarnings
public class UserCredential {
    @Id
    @Column(name = "UUID", nullable = false, length = 36)
    private String uuid;

    @Column(name = "PASSWORD", nullable = false, length = 200)
    private String password;

    @Column(name = "LAST_MODIFIED_TS", nullable = false)
    private Timestamp lastModifiedTs;

    public UserCredential() {
    }

    public UserCredential(final String uuid) {
        setUuid(uuid);
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastModifiedTs() {
        return lastModifiedTs;
    }

    public void setLastModifiedTs(Timestamp lastModifiedTs) {
        this.lastModifiedTs = lastModifiedTs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("uuid", uuid).append("lastModifiedTs", lastModifiedTs).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserCredential that = (UserCredential) o;

        return new EqualsBuilder().append(uuid, that.uuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(uuid).toHashCode();
    }
}
// CHECKSTYLE:ON