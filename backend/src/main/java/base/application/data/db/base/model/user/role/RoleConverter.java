package base.application.data.db.base.model.user.role;

import java.util.Arrays;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleConverter implements AttributeConverter<Role, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Role role) {
        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer role) {
        return Arrays.stream(Role.values()).filter(c -> role.equals(c.getValue())).findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}