package ${topLevelDomain}.${companyName}.${productName}.model.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a user in the system.
 *
 * @author ${codeAuthor}.
 */
@SuppressWarnings("PMD")
// CHECKSTYLE:OFF
public class UserData {
    private String userUuid;
    private String emailAddress;
    private String token;
    private String role;
    private String firstName;
    private String lastName;

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userUuid", userUuid).append("role", role).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof UserData)) return false;

        UserData userData = (UserData) o;

        return new EqualsBuilder().append(userUuid, userData.userUuid).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(userUuid).toHashCode();
    }
}
// CHECKSTYLE:ON
