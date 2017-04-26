package ${topLevelDomain}.${companyName}.${productName}.model.data;

// CHECKSTYLE:OFF
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "LOOKUP_KEY_VALUE")
@SuppressWarnings("PMD")
@SuppressFBWarnings
public class LookupKeyValue {
    @EmbeddedId
    private LookupKeyValuePK key;
    @Column(name = "LOOKUP_VALUE", nullable = false, length = 2000)
    private String lookupValue;

    @Column(name = "CREATE_TS", nullable = false)
    private Timestamp createTs;

    public LookupKeyValue() {
    }

    public LookupKeyValue(String groupCode, String lookupName, String lookupValue) {
        LookupKeyValuePK key = new LookupKeyValuePK();
        key.setLookupName(lookupName);
        key.setGroupCode(groupCode);
        setKey(key);

        setLookupValue(lookupValue);
    }

    public LookupKeyValuePK getKey() {
        return key;
    }

    public void setKey(LookupKeyValuePK key) {
        this.key = key;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("key", key)
                .append("lookupValue", lookupValue)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LookupKeyValue)) return false;

        LookupKeyValue that = (LookupKeyValue) o;

        return new EqualsBuilder()
                .append(key, that.key)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(key)
                .toHashCode();
    }
}
// CHECKSTYLE:ON
