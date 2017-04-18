package com.${companyName}.${productName}.model.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.persistence.annotations.IdValidation;
import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents the roles assignable to a user.
 *
 * @author ${codeAuthor}.
 */
@Entity
@Table(name = "USER_ROLE")
@PrimaryKey(validation = IdValidation.NONE)
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
public class UserRole {
    @Id
    @Column(name = "ID", nullable = false, precision = 0)
    private int id;

    @Column(name = "ROLE", nullable = false, length = 255)
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE).append("id", id)
                .append("role", role).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRole lnssState = (UserRole) o;

        return new EqualsBuilder().append(id, lnssState.id).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(id).toHashCode();
    }
}
//CHECKSTYLE:ON