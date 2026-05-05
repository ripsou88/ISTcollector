package fr.formation.backend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypePreventionConverter implements AttributeConverter<TypePrevention, String> {
 
    @Override
    public String convertToDatabaseColumn(TypePrevention typePrevention) {
        if (typePrevention == null) {
            return null;
        }
        return typePrevention.getTypePrevention();
    }

    @Override
    public TypePrevention convertToEntityAttribute(String typePrevention) {
        if (typePrevention == null) {
            return null;
        }

        return Stream.of(TypePrevention.values())
          .filter(c -> c.getTypePrevention().equals(typePrevention))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
