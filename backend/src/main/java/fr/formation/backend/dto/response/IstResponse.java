package fr.formation.backend.dto.response;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import java.util.List;

public record IstResponse(
    Integer id,
    String nom,
    int gravite,
    Integer incidence,
    String shortDescription,
    String longDescription,
    TypeIst typeIst,
    List<Symptome> symptomes,
    List<Transmission> transmissions,
    List<Prevention> preventions,
    List<Traitement> traitements) {

  public static IstResponse convert(Ist ist) {
    return new IstResponse(
        ist.getId(),
        ist.getNom(),
        ist.getGravite(),
        ist.getIncidence(),
        ist.getShortDescription(),
        ist.getLongDescription(),
        ist.getTypeIst(),
        ist.getSymptomes(),
        ist.getTransmissions(),
        ist.getPreventions(),
        ist.getTraitements());
  }
}
