package fr.formation.backend.config;

import fr.formation.backend.model.Ist;
import fr.formation.backend.model.Prevention;
import fr.formation.backend.model.Symptome;
import fr.formation.backend.model.Traitement;
import fr.formation.backend.model.Transmission;
import fr.formation.backend.model.TypeIst;
import fr.formation.backend.model.TypePrevention;
import fr.formation.backend.repo.IstRepository;
import fr.formation.backend.repo.PreventionRepository;
import fr.formation.backend.repo.SymptomeRepository;
import fr.formation.backend.repo.TraitementRepository;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IstInitializer {

  @Bean
  CommandLineRunner initIst(
      IstRepository istRepository,
      SymptomeRepository symptomeRepository,
      TraitementRepository traitementRepository,
      PreventionRepository preventionRepository) {

    return args -> {
      if (istRepository.count() == 0) {

        // Preventions
        Prevention preservatif =
            preventionRepository.save(new Prevention("Preservatif", TypePrevention.barriere));
        Prevention digueDentaire =
            preventionRepository.save(new Prevention("Digue dentaire", TypePrevention.barriere));
        Prevention depistage =
            preventionRepository.save(new Prevention("Depistage", TypePrevention.medical));
        Prevention vaccination =
            preventionRepository.save(new Prevention("Vaccination", TypePrevention.medical));
        Prevention prepVih =
            preventionRepository.save(new Prevention("PrEP VIH", TypePrevention.medical));
        Prevention tpeVih =
            preventionRepository.save(new Prevention("TPE VIH", TypePrevention.medical));
        Prevention materielSterile =
            preventionRepository.save(
                new Prevention("Materiel sterile", TypePrevention.comportement));
        Prevention eviterPoussees =
            preventionRepository.save(
                new Prevention("Eviter poussees", TypePrevention.comportement));
        Prevention partenaireTraite =
            preventionRepository.save(
                new Prevention("Partenaire traite", TypePrevention.comportement));
        Prevention pasPartageObjet =
            preventionRepository.save(
                new Prevention("Pas partage objet", TypePrevention.comportement));
        Prevention hygieneLinge =
            preventionRepository.save(new Prevention("Hygiene linge", TypePrevention.comportement));
        Prevention examenPrenatal =
            preventionRepository.save(new Prevention("Examen prenatal", TypePrevention.medical));
        Prevention limiterPartenaires =
            preventionRepository.save(
                new Prevention("Limiter partenaires", TypePrevention.comportement));
        Prevention informerPartenaire =
            preventionRepository.save(
                new Prevention("Informer partenaire", TypePrevention.comportement));

        // Symptomes
        Symptome asymptomatique = symptomeRepository.save(new Symptome("Asymptomatique"));
        Symptome bruluresUrine = symptomeRepository.save(new Symptome("Brulures urine"));
        Symptome ecoulements = symptomeRepository.save(new Symptome("Ecoulements"));
        Symptome douleursPelvis = symptomeRepository.save(new Symptome("Douleurs pelvis"));
        Symptome chancre = symptomeRepository.save(new Symptome("Chancre"));
        Symptome eruptionCutanee = symptomeRepository.save(new Symptome("Eruption cutanee"));
        Symptome vesicules = symptomeRepository.save(new Symptome("Vesicules"));
        Symptome verruesGenitales = symptomeRepository.save(new Symptome("Verrues genitales"));
        Symptome fatigue = symptomeRepository.save(new Symptome("Fatigue"));
        Symptome ictere = symptomeRepository.save(new Symptome("Ictere"));
        Symptome fievre = symptomeRepository.save(new Symptome("Fievre"));
        Symptome douleurGorge = symptomeRepository.save(new Symptome("Douleur gorge"));
        Symptome pertesVaginales = symptomeRepository.save(new Symptome("Pertes vaginales"));
        Symptome demangeaisons = symptomeRepository.save(new Symptome("Demangeaisons"));
        Symptome ulcereGenital = symptomeRepository.save(new Symptome("Ulcere genital"));
        Symptome douleurTesticule = symptomeRepository.save(new Symptome("Douleur testicule"));
        Symptome douleurRapports = symptomeRepository.save(new Symptome("Douleur rapports"));
        Symptome saignements = symptomeRepository.save(new Symptome("Saignements"));
        Symptome adenopathies = symptomeRepository.save(new Symptome("Adenopathies"));
        Symptome diarrheeRectale = symptomeRepository.save(new Symptome("Diarrhee rectale"));
        Symptome lesionsCutanees = symptomeRepository.save(new Symptome("Lesions cutanees"));
        Symptome pruritPubien = symptomeRepository.save(new Symptome("Prurit pubien"));
        Symptome papules = symptomeRepository.save(new Symptome("Papules"));

        // Traitements
        Traitement antiretroviraux =
            traitementRepository.save(new Traitement("Antiretroviraux", "Cachet", -1));
        Traitement doxycycline =
            traitementRepository.save(new Traitement("Doxycycline", "Cachet", 7));
        Traitement ceftriaxone =
            traitementRepository.save(new Traitement("Ceftriaxone", "Injection", 1));
        Traitement penicillineG =
            traitementRepository.save(new Traitement("Penicilline G", "Injection", 1));
        Traitement valaciclovir =
            traitementRepository.save(new Traitement("Valaciclovir", "Cachet", 10));
        Traitement tenofovir = traitementRepository.save(new Traitement("Tenofovir", "Cachet", -1));
        Traitement antivirauxVhc =
            traitementRepository.save(new Traitement("Antiviraux VHC", "Cachet", 84));
        Traitement metronidazole =
            traitementRepository.save(new Traitement("Metronidazole", "Cachet", 7));
        Traitement moxifloxacine =
            traitementRepository.save(new Traitement("Moxifloxacine", "Cachet", 7));
        Traitement azithromycine =
            traitementRepository.save(new Traitement("Azithromycine", "Cachet", 1));
        Traitement doxycycline21j =
            traitementRepository.save(new Traitement("Doxycycline 21j", "Cachet", 21));
        Traitement azithromycine21j =
            traitementRepository.save(new Traitement("Azithromycine 21j", "Cachet", 21));
        Traitement permethrine =
            traitementRepository.save(new Traitement("Permethrine", "Topique", 1));
        Traitement ivermectine =
            traitementRepository.save(new Traitement("Ivermectine", "Cachet", 14));
        Traitement antiparasitaire =
            traitementRepository.save(new Traitement("Antiparasitaire", "Topique", 1));

        // ISTs
        Ist vih = new Ist();
        vih.setNom("vih");
        vih.setGravite(5);
        vih.setIncidence(5100);
        vih.setShortDescription("Evolue vers sida");
        vih.setLongDescription(
            "Le VIH est un retrovirus qui attaque le systeme immunitaire, surtout les lymphocytes T"
                + " CD4. Sans traitement, l'infection peut evoluer vers le sida. Le traitement"
                + " antiretroviral ne guerit pas mais controle durablement le virus, protege la"
                + " sante et reduit fortement le risque de transmission lorsque la charge virale"
                + " reste indetectable.");
        vih.setTypeIst(TypeIst.viral);
        vih.setTransmissions(
            List.of(Transmission.sexuelle, Transmission.sang, Transmission.materno));
        vih.setSymptomes(List.of(asymptomatique, fievre, fatigue, adenopathies));
        vih.setTraitements(List.of(antiretroviraux));
        vih.setPreventions(
            List.of(preservatif, depistage, prepVih, tpeVih, materielSterile, informerPartenaire));

        Ist chlamydia = new Ist();
        chlamydia.setNom("chlamydia");
        chlamydia.setGravite(3);
        chlamydia.setIncidence(61100);
        chlamydia.setShortDescription("Souvent invisible");
        chlamydia.setLongDescription(
            "La chlamydiose est une IST bacterienne due a Chlamydia trachomatis. Elle touche le"
                + " col, l'uretre, le rectum ou la gorge. Elle est tres souvent asymptomatique, ce"
                + " qui facilite sa transmission. Non traitee, elle peut provoquer une infection"
                + " genitale haute, des douleurs pelviennes et des problemes de fertilite, surtout"
                + " chez la femme.");
        chlamydia.setTypeIst(TypeIst.bacterien);
        chlamydia.setTransmissions(List.of(Transmission.sexuelle));
        chlamydia.setSymptomes(
            List.of(
                asymptomatique,
                bruluresUrine,
                ecoulements,
                douleursPelvis,
                douleurRapports,
                saignements));
        chlamydia.setTraitements(List.of(doxycycline, azithromycine));
        chlamydia.setPreventions(
            List.of(preservatif, depistage, partenaireTraite, informerPartenaire));

        Ist gonorrhee = new Ist();
        gonorrhee.setNom("gonorrhee");
        gonorrhee.setGravite(3);
        gonorrhee.setIncidence(25800);
        gonorrhee.setShortDescription("Brulures/ecoulement");
        gonorrhee.setLongDescription(
            "La gonorrhee, ou infection a gonocoque, est une IST bacterienne due a Neisseria"
                + " gonorrhoeae. Elle peut causer des brulures en urinant, des ecoulements"
                + " genitaux, des douleurs pelviennes, une infection rectale ou de la gorge, mais"
                + " peut aussi passer inapercue. Non traitee, elle peut entrainer des complications"
                + " genitales et favoriser la transmission du VIH.");
        gonorrhee.setTypeIst(TypeIst.bacterien);
        gonorrhee.setTransmissions(List.of(Transmission.sexuelle));
        gonorrhee.setSymptomes(
            List.of(
                asymptomatique,
                bruluresUrine,
                ecoulements,
                douleursPelvis,
                douleurGorge,
                diarrheeRectale));
        gonorrhee.setTraitements(List.of(ceftriaxone, azithromycine));
        gonorrhee.setPreventions(
            List.of(preservatif, depistage, partenaireTraite, informerPartenaire));

        Ist syphilis = new Ist();
        syphilis.setNom("syphilis");
        syphilis.setGravite(4);
        syphilis.setIncidence(6500);
        syphilis.setShortDescription("Chancre puis grave");
        syphilis.setLongDescription(
            "La syphilis est une IST bacterienne due a Treponema pallidum. Elle evolue par stades."
                + " Elle debute souvent par une plaie indolore appelee chancre, puis peut provoquer"
                + " des signes cutanes ou generaux. Sans traitement, elle peut atteindre le systeme"
                + " nerveux, le coeur ou d'autres organes plusieurs annees apres l'infection.");
        syphilis.setTypeIst(TypeIst.bacterien);
        syphilis.setTransmissions(List.of(Transmission.sexuelle, Transmission.materno));
        syphilis.setSymptomes(List.of(chancre, eruptionCutanee, fievre, adenopathies));
        syphilis.setTraitements(List.of(penicillineG));
        syphilis.setPreventions(
            List.of(preservatif, depistage, examenPrenatal, informerPartenaire));

        Ist herpes = new Ist();
        herpes.setNom("herpes_genital");
        herpes.setGravite(3);
        herpes.setIncidence(36000);
        herpes.setShortDescription("Poussees douloureus");
        herpes.setLongDescription(
            "L'herpes genital est une infection virale chronique due aux virus herpes simplex HSV-1"
                + " ou HSV-2. Il peut provoquer des vesicules ou ulcerations douloureuses au niveau"
                + " genital, anal ou perineal. Le virus reste dans l'organisme et peut se"
                + " reactiver. La transmission est possible pendant les poussees et parfois en"
                + " dehors des symptomes.");
        herpes.setTypeIst(TypeIst.viral);
        herpes.setTransmissions(List.of(Transmission.direct, Transmission.sexuelle));
        herpes.setSymptomes(List.of(vesicules, ulcereGenital, demangeaisons, fievre));
        herpes.setTraitements(List.of(valaciclovir));
        herpes.setPreventions(
            List.of(preservatif, digueDentaire, eviterPoussees, informerPartenaire));

        Ist hpv = new Ist();
        hpv.setNom("papillomavirus");
        hpv.setGravite(4);
        hpv.setShortDescription(
            "Une IST virale très fréquente, souvent invisible, mais liée à certains cancers.");
        hpv.setLongDescription(
            "Les papillomavirus humains sont des virus tres frequents transmis surtout par contact"
                + " sexuel peau a peau ou muqueuse a muqueuse. La plupart des infections"
                + " disparaissent seules. Certains types peuvent causer des verrues genitales,"
                + " d'autres des lesions precancereuses et des cancers du col de l'uterus, de"
                + " l'anus, du penis ou de la gorge.");
        hpv.setTypeIst(TypeIst.viral);
        hpv.setTransmissions(List.of(Transmission.sexuelle, Transmission.direct));
        hpv.setSymptomes(List.of(asymptomatique, verruesGenitales, lesionsCutanees));
        hpv.setTraitements(List.of());
        hpv.setPreventions(List.of(vaccination, preservatif, depistage));

        Ist hepatiteB = new Ist();
        hepatiteB.setNom("hepatite_b");
        hepatiteB.setGravite(4);
        hepatiteB.setShortDescription("Atteinte du foie");
        hepatiteB.setLongDescription(
            "L'hepatite B est une infection virale du foie due au VHB. Elle se transmet par le"
                + " sang, les rapports sexuels et de la mere a l'enfant. Elle peut etre aigue ou"
                + " devenir chronique, avec risque de cirrhose et de cancer du foie. La vaccination"
                + " est la prevention principale et protege aussi contre l'hepatite Delta chez les"
                + " personnes non infectees par le VHB.");
        hepatiteB.setTypeIst(TypeIst.viral);
        hepatiteB.setTransmissions(
            List.of(Transmission.sexuelle, Transmission.sang, Transmission.materno));
        hepatiteB.setSymptomes(List.of(asymptomatique, fatigue, ictere, fievre));
        hepatiteB.setTraitements(List.of(tenofovir));
        hepatiteB.setPreventions(
            List.of(vaccination, preservatif, depistage, materielSterile, examenPrenatal));

        Ist hepatiteC = new Ist();
        hepatiteC.setNom("hepatite_c");
        hepatiteC.setGravite(4);
        hepatiteC.setShortDescription("Virus du sang");
        hepatiteC.setLongDescription(
            "L'hepatite C est une infection virale du foie due au VHC. La transmission est surtout"
                + " sanguine, notamment par partage de materiel d'injection, mais une transmission"
                + " sexuelle peut exister dans certains contextes. L'infection devient souvent"
                + " chronique si elle n'est pas traitee. Les antiviraux a action directe guerissent"
                + " la majorite des infections.");
        hepatiteC.setTypeIst(TypeIst.viral);
        hepatiteC.setTransmissions(List.of(Transmission.sang, Transmission.sexuelle));
        hepatiteC.setSymptomes(List.of(asymptomatique, fatigue, ictere));
        hepatiteC.setTraitements(List.of(antivirauxVhc));
        hepatiteC.setPreventions(List.of(materielSterile, preservatif, depistage));

        Ist trichomonase = new Ist();
        trichomonase.setNom("trichomonase");
        trichomonase.setGravite(2);
        trichomonase.setShortDescription("Parasite genital");
        trichomonase.setLongDescription(
            "La trichomonase est une IST due au parasite Trichomonas vaginalis. Elle peut provoquer"
                + " des pertes vaginales, une irritation, des demangeaisons ou des brulures"
                + " urinaires, mais elle est parfois asymptomatique. Chez l'homme, elle peut causer"
                + " une uretrite discrete. Le traitement repose sur des medicaments de la famille"
                + " des nitro-imidazoles.");
        trichomonase.setTypeIst(TypeIst.parasite);
        trichomonase.setTransmissions(List.of(Transmission.sexuelle));
        trichomonase.setSymptomes(
            List.of(asymptomatique, pertesVaginales, demangeaisons, bruluresUrine));
        trichomonase.setTraitements(List.of(metronidazole));
        trichomonase.setPreventions(List.of(preservatif, depistage, partenaireTraite));

        Ist mycoplasma = new Ist();
        mycoplasma.setNom("mycoplasma");
        mycoplasma.setGravite(3);
        mycoplasma.setShortDescription("IST discrete");
        mycoplasma.setLongDescription(
            "Mycoplasma genitalium est une bacterie sexuellement transmissible pouvant provoquer"
                + " une uretrite, une cervicite, une infection rectale ou des douleurs pelviennes."
                + " Elle peut aussi etre peu symptomatique. Sa prise en charge doit tenir compte du"
                + " risque de resistance aux macrolides et se fait selon prescription medicale.");
        mycoplasma.setTypeIst(TypeIst.bacterien);
        mycoplasma.setTransmissions(List.of(Transmission.sexuelle));
        mycoplasma.setSymptomes(
            List.of(asymptomatique, bruluresUrine, douleursPelvis, ecoulements));
        mycoplasma.setTraitements(List.of(moxifloxacine, azithromycine));
        mycoplasma.setPreventions(List.of(preservatif, depistage, partenaireTraite));

        Ist lgv = new Ist();
        lgv.setNom("lgv");
        lgv.setGravite(3);
        lgv.setShortDescription("Chlamydia invasive");
        lgv.setLongDescription(
            "La lymphogranulomatose venerienne est une IST due a certains types invasifs de"
                + " Chlamydia trachomatis. Elle peut debuter par une petite lesion passee"
                + " inapercue, puis provoquer des ganglions douloureux ou une rectite avec"
                + " douleurs, saignements et ecoulements. Elle est traitee plus longtemps que la"
                + " chlamydiose genitale commune.");
        lgv.setTypeIst(TypeIst.bacterien);
        lgv.setTransmissions(List.of(Transmission.sexuelle));
        lgv.setSymptomes(List.of(ulcereGenital, adenopathies, diarrheeRectale, saignements));
        lgv.setTraitements(List.of(doxycycline21j));
        lgv.setPreventions(List.of(preservatif, depistage, informerPartenaire));

        Ist chancreMou = new Ist();
        chancreMou.setNom("chancre_mou");
        chancreMou.setGravite(3);
        chancreMou.setShortDescription("Ulceres douloureux");
        chancreMou.setLongDescription(
            "Le chancre mou est une IST bacterienne due a Haemophilus ducreyi. Elle provoque des"
                + " ulceres genitaux douloureux et parfois des ganglions inflammatoires dans"
                + " l'aine. Elle est rare en France metropolitaine et plus presente dans certaines"
                + " zones tropicales. Le traitement repose sur des antibiotiques adaptes.");
        chancreMou.setTypeIst(TypeIst.bacterien);
        chancreMou.setTransmissions(List.of(Transmission.sexuelle));
        chancreMou.setSymptomes(List.of(ulcereGenital, adenopathies));
        chancreMou.setTraitements(List.of(azithromycine));
        chancreMou.setPreventions(List.of(preservatif, depistage, informerPartenaire));

        Ist donovanose = new Ist();
        donovanose.setNom("donovanose");
        donovanose.setGravite(3);
        donovanose.setShortDescription("Ulceres chroniques");
        donovanose.setLongDescription(
            "La donovanose, ou granulome inguinal, est une IST bacterienne rare due a Klebsiella"
                + " granulomatis. Elle provoque des ulcerations genitales chroniques, souvent peu"
                + " douloureuses mais destructrices si elles ne sont pas traitees. Elle concerne"
                + " surtout certaines zones tropicales et necessite un traitement antibiotique"
                + " prolonge.");
        donovanose.setTypeIst(TypeIst.bacterien);
        donovanose.setTransmissions(List.of(Transmission.sexuelle));
        donovanose.setSymptomes(List.of(ulcereGenital, lesionsCutanees));
        donovanose.setTraitements(List.of(azithromycine21j));
        donovanose.setPreventions(List.of(preservatif, depistage, informerPartenaire));

        Ist gale = new Ist();
        gale.setNom("gale");
        gale.setGravite(2);
        gale.setShortDescription("Prurit nocturne");
        gale.setLongDescription(
            "La gale est une parasitose de la peau due a Sarcoptes scabiei. Elle n'est pas"
                + " uniquement sexuelle, mais peut se transmettre lors de contacts corporels"
                + " rapproches et prolonges, y compris sexuels. Elle provoque des demangeaisons"
                + " intenses, souvent nocturnes. Le traitement doit souvent concerner les contacts"
                + " proches et le linge.");
        gale.setTypeIst(TypeIst.parasite);
        gale.setTransmissions(List.of(Transmission.direct));
        gale.setSymptomes(List.of(demangeaisons, lesionsCutanees));
        gale.setTraitements(List.of(permethrine, ivermectine));
        gale.setPreventions(List.of(hygieneLinge, informerPartenaire, pasPartageObjet));

        Ist pouxPubiens = new Ist();
        pouxPubiens.setNom("poux_pubiens");
        pouxPubiens.setGravite(1);
        pouxPubiens.setShortDescription("Prurit pubien");
        pouxPubiens.setLongDescription(
            "Les poux pubiens, ou morpions, sont des parasites transmis le plus souvent par contact"
                + " intime direct. Ils provoquent des demangeaisons du pubis et parfois de petites"
                + " lesions de grattage. La prise en charge repose sur un traitement"
                + " antiparasitaire, le lavage du linge et l'information des partenaires recents.");
        pouxPubiens.setTypeIst(TypeIst.parasite);
        pouxPubiens.setTransmissions(List.of(Transmission.direct));
        pouxPubiens.setSymptomes(List.of(pruritPubien, demangeaisons));
        pouxPubiens.setTraitements(List.of(antiparasitaire));
        pouxPubiens.setPreventions(List.of(hygieneLinge, pasPartageObjet, informerPartenaire));

        Ist molluscum = new Ist();
        molluscum.setNom("molluscum");
        molluscum.setGravite(1);
        molluscum.setShortDescription("Petites papules");
        molluscum.setLongDescription(
            "Le molluscum contagiosum est une infection virale de la peau qui peut se transmettre"
                + " par contact cutane direct, y compris lors de contacts sexuels chez l'adulte. Il"
                + " provoque de petites papules arrondies, parfois situees dans la region genitale."
                + " Les lesions peuvent disparaitre seules ou etre traitees localement si"
                + " necessaire.");
        molluscum.setTypeIst(TypeIst.viral);
        molluscum.setTransmissions(List.of(Transmission.direct));
        molluscum.setSymptomes(List.of(papules, lesionsCutanees));
        molluscum.setTraitements(List.of());
        molluscum.setPreventions(List.of(pasPartageObjet, informerPartenaire));

        istRepository.saveAll(
            List.of(
                vih,
                chlamydia,
                gonorrhee,
                syphilis,
                herpes,
                hpv,
                hepatiteB,
                hepatiteC,
                trichomonase,
                mycoplasma,
                lgv,
                chancreMou,
                donovanose,
                gale,
                pouxPubiens,
                molluscum));
      }
    };
  }
}
