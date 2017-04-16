package com.${companyName}.${productName}.model.data;

import com.${companyName}.${productName}.model.enumeration.Role;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;

/**
 * Represents a user profile.
 *
 * @author ${codeAuthor}.
 */
@Entity
@Table(name = "USER_PROFILE")
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
@SuppressFBWarnings
public class UserProfile {
    @Id
    @Column(name = "UUID", nullable = false, length = 36)
    private String uuid;

    @Column(name = "EMAIL_ADDRESS", nullable = false, length = 75)
    private String emailAddress;

    @Column(name = "FIRST_NAME", nullable = false, length = 75)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false, length = 75)
    private String lastName;

    @Column(name = "CREATE_TS", nullable = false)
    private Timestamp createTs;

    @Column(name = "LAST_MODIFIED_TS", nullable = true)
    private Timestamp lastModifiedTs;

    @Column(name = "LAST_LOGGED_TS", nullable = true)
    private Timestamp lastLoggedTs;

    @Column(name = "ACTIVE_FLAG", nullable = false, length = 1)
    private String activeFlag;

    @Convert(converter = RoleConverter.class)
    @Column(name = "ROLE_ID", nullable = false, precision = 0)
    private Role role;

    @Transient
    private UserCredential userCredential;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Timestamp getLastLoggedTs() {
        return lastLoggedTs;
    }

    public void setLastLoggedTs(Timestamp lastLoggedTs) {
        this.lastLoggedTs = lastLoggedTs;
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserProfile ftlnUserToken = (UserProfile) o;

        return new EqualsBuilder().append(uuid, ftlnUserToken.uuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(uuid).toHashCode();
    }
}
// CHECKSTYLE:ON
