package com.${companyName}.${productName}.model.data;

// CHECKSTYLE:OFF
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@SuppressWarnings("PMD")
public class LookupKeyValuePK implements Serializable {
    private static final long serialVersionUID = 1061640693807555508L;
    @Column(name = "GROUP_CODE", nullable = false, length = 100)
    private String groupCode;
    @Column(name = "LOOKUP_NAME", nullable = false, length = 200)
    private String lookupName;

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getLookupName() {
        return lookupName;
    }

    public void setLookupName(String lookupName) {
        this.lookupName = lookupName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("groupCode", groupCode)
                .append("lookupName", lookupName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof LookupKeyValuePK)) return false;

        LookupKeyValuePK that = (LookupKeyValuePK) o;

        return new EqualsBuilder()
                .append(groupCode, that.groupCode)
                .append(lookupName, that.lookupName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(groupCode)
                .append(lookupName)
                .toHashCode();
    }
}
// CHECKSTYLE:ON
