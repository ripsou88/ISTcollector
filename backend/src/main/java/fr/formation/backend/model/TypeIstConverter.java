package fr.formation.backend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeIstConverter implements AttributeConverter<TypeIst, String> {

    @Override
    public String convertToDatabaseColumn(TypeIst typeIst) {
        if (typeIst == null) {
            return null;
        }
        return typeIst.getTypeIst();
    }

    @Override
    public TypeIst convertToEntityAttribute(String typeIst) {
        if (typeIst == null) {
            return null;
        }

        return Stream.of(TypeIst.values())
                .filter(c -> c.getTypeIst().equals(typeIst))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
