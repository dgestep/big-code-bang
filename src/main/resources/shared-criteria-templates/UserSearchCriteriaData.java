package ${topLevelDomain}.${companyName}.${productName}.model.criteria;

import ${topLevelDomain}.${companyName}.${productName}.model.enumeration.Role;
import ${topLevelDomain}.${companyName}.${productName}.model.util.NameParser;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines the search criteria for users.
 *
 * @author ${codeAuthor}.
 */
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
public class UserSearchCriteriaData {
    private String uuid;
    private List<String> uuids;
    private String activeFlag;
    private String emailAddress;
    private Role role;
    private String name;
    private String firstName;
    private String lastName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getUuids() {
        return uuids;
    }

    public void setUuids(List<String> uuids) {
        this.uuids = uuids;
    }

    public void addUuid(final String uuid) {
        if (uuids == null) {
            uuids = new ArrayList<String>();
        }
        uuids.add(uuid);
    }

    public String getActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(String activeFlag) {
        this.activeFlag = activeFlag;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
        this.parseName();
    }

    public String getName() { return this.name; }

    public void setFirstName(String firstName) { this.firstName =  firstName; }

    public String getFirstName() { return this.firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getLastName() { return this.lastName; }

    /*
     * Parses a name into a first and last name, then assigns the variables.
     */
    private void parseName() {
        NameParser nameParser = new NameParser(this.name);
        this.firstName = nameParser.getFirstName();
        this.lastName = nameParser.getLastName();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("uuid", uuid)
                .append("uuids", uuids)
                .append("activeFlag", activeFlag)
                .append("emailAddress", emailAddress)
                .append("role", role)
                .append("name", name)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserSearchCriteriaData)) return false;

        UserSearchCriteriaData that = (UserSearchCriteriaData) o;

        return new EqualsBuilder()
                .append(uuid, that.uuid)
                .append(uuids, that.uuids)
                .append(activeFlag, that.activeFlag)
                .append(emailAddress, that.emailAddress)
                .append(role, that.role)
                .append(name, that.name)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(uuid)
                .append(uuids)
                .append(activeFlag)
                .append(emailAddress)
                .append(role)
                .append(name)
                .append(firstName)
                .append(lastName)
                .toHashCode();
    }
}
// CHECKSTYLE:ON