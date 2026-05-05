package fr.formation.backend.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TransmissionConverter implements AttributeConverter<Transmission, String> {
 
    @Override
    public String convertToDatabaseColumn(Transmission typeTransmission) {
        if (typeTransmission == null) {
            return null;
        }
        return typeTransmission.getTypeTransmission();
    }

    @Override
    public Transmission convertToEntityAttribute(String typeTransmission) {
        if (typeTransmission == null) {
            return null;
        }

        return Stream.of(Transmission.values())
          .filter(c -> c.getTypeTransmission().equals(typeTransmission))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
