package fr.formation.backend.config;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import fr.formation.backend.model.TypePrevention;
import fr.formation.backend.repo.IstRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IstInitializer {
  @Bean
  CommandLineRunner initIst(IstRepository istRepository) {
    return args -> {
      if (istRepository.count() == 0) {
        Ist vih = new Ist();
        vih.setNom("vih");
        vih.setGravite(5);
        vih.setIncidence(5000);
        vih.setSymptomes(List.of(new Symptome("Generique")));
        vih.setShortDescription("Virus evoluant vers le sida");
        vih.setLongDescription(
            "Le VIH est un retrovirus qui va s’attaquer au systeme immunitaire et plus"
                + " specifiquement aux lymphocyte T CD4, qui au stade final d’infection est connu"
                + " sous le nom de sida.");
        vih.setTypeIst(TypeIst.viral);
        vih.setTraitements(List.of(new Traitement("Antiretroviral", "Cachet", -1)));
        vih.setPreventions(List.of(new Prevention("Preservatif", TypePrevention.barriere)));
        vih.setTransmissions(
            List.of(Transmission.sang, Transmission.materno, Transmission.sexuelle));
      }
    };
  }
}
