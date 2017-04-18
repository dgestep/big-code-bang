package com.${companyName}.${productName}.model.data;

import com.${companyName}.${productName}.model.enumeration.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Converter for the {@link UserRole}.
 *
 * @author ${codeAuthor}.
 */
@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(final Role role) {
        return role.getId();
    }

    @Override
    public Role convertToEntityAttribute(final Integer databaseValue) {
        return Role.toEnum(databaseValue);
    }
}
