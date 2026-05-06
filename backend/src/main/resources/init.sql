-- Script MySQL pour la base istdex
-- 100 questions sur les IST, chacune avec 4 réponses dont 1 correcte.
-- Nouveau schéma : question(id, question) et reponse(id, id_question, reponse, correct).
-- La colonne reponse.correct remplace l'ancien champ question.id_bonne_reponse.
-- id_question matérialise l'association 1 question -> plusieurs réponses dans MySQL.

-- Script de peuplement pour la base `istedex`
-- Compatible MySQL 8.x / phpMyAdmin
-- Respecte les contraintes varchar courtes du schema fourni.
-- Remarques importantes :
-- 1) `ist.transmission` ne permet qu'une seule valeur ; le script choisit la transmission principale.
-- 2) `prevention.type_prevention` ne contient pas `vaccin` ni `depistage`, donc Vaccination et Depistage sont classes en `medical`.
-- 3) `incidence` vaut NULL lorsqu'il n'existe pas de chiffre annuel francais fiable ou comparable.
-- 4) Les traitements sont des categories informatives : ils ne remplacent jamais une prescription medicale.


USE `istedex`;

SET FOREIGN_KEY_CHECKS = 0;
SET FOREIGN_KEY_CHECKS = 1;


START TRANSACTION;


-- Comptes de demonstration : remplacer les mots de passe par des hashes si Spring Security/BCrypt est utilise.
INSERT INTO `account` (`type_compte`, `username`, `password`, `level`) VALUES
  ('adm', 'admin', '$2a$10$.uJZ1O1pk8YT1Vj6fuuBZuhmcA1k0jG8We9H.jtV2wXOwrl2Q8s9G', 0),
  ('usr', 'demo', '$2a$10$.uJZ1O1pk8YT1Vj6fuuBZuhmcA1k0jG8We9H.jtV2wXOwrl2Q8s9G', 0)
ON DUPLICATE KEY UPDATE `type_compte` = VALUES(`type_compte`), `password` = VALUES(`password`), `level` = VALUES(`level`);

-- Preventions
INSERT INTO `prevention` (`nom`, `type_prevention`) VALUES
  ('Preservatif', 'barriere'),
  ('Digue dentaire', 'barriere'),
  ('Depistage', 'medical'),
  ('Vaccination', 'medical'),
  ('PrEP VIH', 'medical'),
  ('TPE VIH', 'medical'),
  ('Materiel sterile', 'comportement'),
  ('Eviter poussees', 'comportement'),
  ('Partenaire traite', 'comportement'),
  ('Pas partage objet', 'comportement'),
  ('Hygiene linge', 'comportement'),
  ('Examen prenatal', 'medical'),
  ('Limiter partenaires', 'comportement'),
  ('Informer partenaire', 'comportement')
ON DUPLICATE KEY UPDATE `type_prevention` = VALUES(`type_prevention`);

-- Symptomes
INSERT INTO `symptome` (`nom`) VALUES
  ('Asymptomatique'),
  ('Brulures urine'),
  ('Ecoulements'),
  ('Douleurs pelvis'),
  ('Chancre'),
  ('Eruption cutanee'),
  ('Vesicules'),
  ('Verrues genitales'),
  ('Fatigue'),
  ('Ictere'),
  ('Fievre'),
  ('Douleur gorge'),
  ('Pertes vaginales'),
  ('Demangeaisons'),
  ('Ulcere genital'),
  ('Douleur testicule'),
  ('Douleur rapports'),
  ('Saignements'),
  ('Adenopathies'),
  ('Diarrhee rectale'),
  ('Lesions cutanees'),
  ('Prurit pubien'),
  ('Papules')
ON DUPLICATE KEY UPDATE `nom` = VALUES(`nom`);

-- Traitements
INSERT INTO `traitement` (`duree`, `nom`, `prise`, `type_ist`) VALUES
  (-1, 'Antiretroviraux', 'Cachet', 'viral'),
  (7, 'Doxycycline', 'Cachet', 'bacterien'),
  (1, 'Ceftriaxone', 'Injection', 'bacterien'),
  (1, 'Penicilline G', 'Injection', 'bacterien'),
  (10, 'Valaciclovir', 'Cachet', 'viral'),
  (NULL, 'Lesions HPV', 'Medicale', 'viral'),
  (-1, 'Tenofovir', 'Cachet', 'viral'),
  (84, 'Antiviraux VHC', 'Cachet', 'viral'),
  (7, 'Metronidazole', 'Cachet', 'parasite'),
  (7, 'Moxifloxacine', 'Cachet', 'bacterien'),
  (1, 'Azithromycine', 'Cachet', 'bacterien'),
  (21, 'Doxycycline 21j', 'Cachet', 'bacterien'),
  (21, 'Azithromycine 21j', 'Cachet', 'bacterien'),
  (1, 'Permethrine', 'Topique', 'parasite'),
  (14, 'Ivermectine', 'Cachet', 'parasite'),
  (1, 'Antiparasitaire', 'Topique', 'parasite')
ON DUPLICATE KEY UPDATE `duree` = VALUES(`duree`), `prise` = VALUES(`prise`), `type_ist` = VALUES(`type_ist`);

-- IST principales et infections pouvant etre transmises sexuellement
INSERT INTO `ist` (`gravite`, `incidence`, `long_description`, `nom`, `short_description`, `transmission`, `type_ist`) VALUES
  (5, 5100, 'Le VIH est un retrovirus qui attaque le systeme immunitaire, surtout les lymphocytes T CD4. Sans traitement, l''infection peut evoluer vers le sida. Le traitement antiretroviral ne guerit pas mais controle durablement le virus, protege la sante et reduit fortement le risque de transmission lorsque la charge virale reste indetectable.', 'VIH', 'Evolue vers sida', 'sexuelle', 'viral'),
  (3, 61100, 'La chlamydiose est une IST bacterienne due a Chlamydia trachomatis. Elle touche le col, l''uretre, le rectum ou la gorge. Elle est tres souvent asymptomatique, ce qui facilite sa transmission. Non traitee, elle peut provoquer une infection genitale haute, des douleurs pelviennes et des problemes de fertilite, surtout chez la femme.', 'Chlamydiose', 'Souvent invisible', 'sexuelle', 'bacterien'),
  (3, 25800, 'La gonorrhee, ou infection a gonocoque, est une IST bacterienne due a Neisseria gonorrhoeae. Elle peut causer des brulures en urinant, des ecoulements genitaux, des douleurs pelviennes, une infection rectale ou de la gorge, mais peut aussi passer inapercue. Non traitee, elle peut entrainer des complications genitales et favoriser la transmission du VIH.', 'Gonorrhee', 'Brulures/ecoulem.', 'sexuelle', 'bacterien'),
  (4, 6500, 'La syphilis est une IST bacterienne due a Treponema pallidum. Elle evolue par stades. Elle debute souvent par une plaie indolore appelee chancre, puis peut provoquer des signes cutanes ou generaux. Sans traitement, elle peut atteindre le systeme nerveux, le coeur ou d''autres organes plusieurs annees apres l''infection.', 'Syphilis', 'Chancre puis grave', 'sexuelle', 'bacterien'),
  (3, 36000, 'L''herpes genital est une infection virale chronique due aux virus herpes simplex HSV-1 ou HSV-2. Il peut provoquer des vesicules ou ulcerations douloureuses au niveau genital, anal ou perineal. Le virus reste dans l''organisme et peut se reactiver. La transmission est possible pendant les poussees et parfois en dehors des symptomes.', 'Herpes genital', 'Poussees douloureus', 'direct', 'viral'),
  (4, NULL, 'Les papillomavirus humains sont des virus tres frequents transmis surtout par contact sexuel peau a peau ou muqueuse a muqueuse. La plupart des infections disparaissent seules. Certains types peuvent causer des verrues genitales, d''autres des lesions precancereuses et des cancers du col de l''uterus, de l''anus, du penis ou de la gorge.', 'HPV', 'Virus tres frequent', 'sexuelle', 'viral'),
  (4, NULL, 'L''hepatite B est une infection virale du foie due au VHB. Elle se transmet par le sang, les rapports sexuels et de la mere a l''enfant. Elle peut etre aigue ou devenir chronique, avec risque de cirrhose et de cancer du foie. La vaccination est la prevention principale et protege aussi contre l''hepatite Delta chez les personnes non infectees par le VHB.', 'Hepatite B', 'Atteinte du foie', 'sexuelle', 'viral'),
  (4, NULL, 'L''hepatite C est une infection virale du foie due au VHC. La transmission est surtout sanguine, notamment par partage de materiel d''injection, mais une transmission sexuelle peut exister dans certains contextes. L''infection devient souvent chronique si elle n''est pas traitee. Les antiviraux a action directe guerissent la majorite des infections.', 'Hepatite C', 'Virus du sang', 'sang', 'viral'),
  (2, NULL, 'La trichomonase est une IST due au parasite Trichomonas vaginalis. Elle peut provoquer des pertes vaginales, une irritation, des demangeaisons ou des brulures urinaires, mais elle est parfois asymptomatique. Chez l''homme, elle peut causer une uretrite discrete. Le traitement repose sur des medicaments de la famille des nitro-imidazoles.', 'Trichomonase', 'Parasite genital', 'sexuelle', 'parasite'),
  (3, NULL, 'Mycoplasma genitalium est une bacterie sexuellement transmissible pouvant provoquer une uretrite, une cervicite, une infection rectale ou des douleurs pelviennes. Elle peut aussi etre peu symptomatique. Sa prise en charge doit tenir compte du risque de resistance aux macrolides et se fait selon prescription medicale.', 'Mycoplasma', 'IST discrete', 'sexuelle', 'bacterien'),
  (3, NULL, 'La lymphogranulomatose venerienne est une IST due a certains types invasifs de Chlamydia trachomatis. Elle peut debuter par une petite lesion passee inapercue, puis provoquer des ganglions douloureux ou une rectite avec douleurs, saignements et ecoulements. Elle est traitee plus longtemps que la chlamydiose genitale commune.', 'LGV', 'Chlamydia invasive', 'sexuelle', 'bacterien'),
  (3, NULL, 'Le chancre mou est une IST bacterienne due a Haemophilus ducreyi. Elle provoque des ulceres genitaux douloureux et parfois des ganglions inflammatoires dans l''aine. Elle est rare en France metropolitaine et plus presente dans certaines zones tropicales. Le traitement repose sur des antibiotiques adaptes.', 'Chancre mou', 'Ulceres douloureux', 'sexuelle', 'bacterien'),
  (3, NULL, 'La donovanose, ou granulome inguinal, est une IST bacterienne rare due a Klebsiella granulomatis. Elle provoque des ulcerations genitales chroniques, souvent peu douloureuses mais destructrices si elles ne sont pas traitees. Elle concerne surtout certaines zones tropicales et necessite un traitement antibiotique prolonge.', 'Donovanose', 'Ulceres chroniques', 'sexuelle', 'bacterien'),
  (2, NULL, 'La gale est une parasitose de la peau due a Sarcoptes scabiei. Elle n''est pas uniquement sexuelle, mais peut se transmettre lors de contacts corporels rapproches et prolonges, y compris sexuels. Elle provoque des demangeaisons intenses, souvent nocturnes. Le traitement doit souvent concerner les contacts proches et le linge.', 'Gale', 'Prurit nocturne', 'direct', 'parasite'),
  (1, NULL, 'Les poux pubiens, ou morpions, sont des parasites transmis le plus souvent par contact intime direct. Ils provoquent des demangeaisons du pubis et parfois de petites lesions de grattage. La prise en charge repose sur un traitement antiparasitaire, le lavage du linge et l''information des partenaires recents.', 'Poux pubiens', 'Prurit pubien', 'direct', 'parasite'),
  (1, NULL, 'Le molluscum contagiosum est une infection virale de la peau qui peut se transmettre par contact cutane direct, y compris lors de contacts sexuels chez l''adulte. Il provoque de petites papules arrondies, parfois situees dans la region genitale. Les lesions peuvent disparaitre seules ou etre traitees localement si necessaire.', 'Molluscum', 'Petites papules', 'direct', 'viral')
ON DUPLICATE KEY UPDATE
  `gravite` = VALUES(`gravite`),
  `incidence` = VALUES(`incidence`),
  `long_description` = VALUES(`long_description`),
  `short_description` = VALUES(`short_description`),
  `transmission` = VALUES(`transmission`),
  `type_ist` = VALUES(`type_ist`);

-- Nettoyage des relations pour rendre le script rejouable
DELETE ip FROM `ist-prevention` ip JOIN `ist` i ON i.id = ip.ist_id WHERE i.nom IN ('VIH', 'Chlamydiose', 'Gonorrhee', 'Syphilis', 'Herpes genital', 'HPV', 'Hepatite B', 'Hepatite C', 'Trichomonase', 'Mycoplasma', 'LGV', 'Chancre mou', 'Donovanose', 'Gale', 'Poux pubiens', 'Molluscum');
DELETE iss FROM `ist-symptome` iss JOIN `ist` i ON i.id = iss.ist_id WHERE i.nom IN ('VIH', 'Chlamydiose', 'Gonorrhee', 'Syphilis', 'Herpes genital', 'HPV', 'Hepatite B', 'Hepatite C', 'Trichomonase', 'Mycoplasma', 'LGV', 'Chancre mou', 'Donovanose', 'Gale', 'Poux pubiens', 'Molluscum');
DELETE it FROM `ist-traitement` it JOIN `ist` i ON i.id = it.ist_id WHERE i.nom IN ('VIH', 'Chlamydiose', 'Gonorrhee', 'Syphilis', 'Herpes genital', 'HPV', 'Hepatite B', 'Hepatite C', 'Trichomonase', 'Mycoplasma', 'LGV', 'Chancre mou', 'Donovanose', 'Gale', 'Poux pubiens', 'Molluscum');

-- Relations IST -> symptomes
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'VIH' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'VIH' AND s.nom = 'Fievre';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'VIH' AND s.nom = 'Fatigue';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'VIH' AND s.nom = 'Adenopathies';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chlamydiose' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chlamydiose' AND s.nom = 'Brulures urine';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chlamydiose' AND s.nom = 'Ecoulements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chlamydiose' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chlamydiose' AND s.nom = 'Douleur rapports';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Brulures urine';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Ecoulements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Douleur gorge';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Douleur testicule';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gonorrhee' AND s.nom = 'Diarrhee rectale';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Syphilis' AND s.nom = 'Chancre';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Syphilis' AND s.nom = 'Eruption cutanee';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Syphilis' AND s.nom = 'Fievre';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Syphilis' AND s.nom = 'Adenopathies';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Syphilis' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Herpes genital' AND s.nom = 'Vesicules';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Herpes genital' AND s.nom = 'Ulcere genital';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Herpes genital' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Herpes genital' AND s.nom = 'Fievre';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Herpes genital' AND s.nom = 'Adenopathies';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'HPV' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'HPV' AND s.nom = 'Verrues genitales';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'HPV' AND s.nom = 'Saignements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'HPV' AND s.nom = 'Lesions cutanees';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite B' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite B' AND s.nom = 'Fatigue';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite B' AND s.nom = 'Ictere';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite B' AND s.nom = 'Fievre';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite B' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite C' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite C' AND s.nom = 'Fatigue';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Hepatite C' AND s.nom = 'Ictere';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Trichomonase' AND s.nom = 'Pertes vaginales';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Trichomonase' AND s.nom = 'Demangeaisons';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Trichomonase' AND s.nom = 'Brulures urine';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Trichomonase' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Mycoplasma' AND s.nom = 'Brulures urine';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Mycoplasma' AND s.nom = 'Ecoulements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Mycoplasma' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Mycoplasma' AND s.nom = 'Saignements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Mycoplasma' AND s.nom = 'Asymptomatique';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'LGV' AND s.nom = 'Ulcere genital';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'LGV' AND s.nom = 'Adenopathies';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'LGV' AND s.nom = 'Diarrhee rectale';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'LGV' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'LGV' AND s.nom = 'Saignements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chancre mou' AND s.nom = 'Ulcere genital';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chancre mou' AND s.nom = 'Adenopathies';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Chancre mou' AND s.nom = 'Douleurs pelvis';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Donovanose' AND s.nom = 'Ulcere genital';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Donovanose' AND s.nom = 'Lesions cutanees';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Donovanose' AND s.nom = 'Saignements';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gale' AND s.nom = 'Demangeaisons';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gale' AND s.nom = 'Lesions cutanees';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Gale' AND s.nom = 'Prurit pubien';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Poux pubiens' AND s.nom = 'Prurit pubien';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Poux pubiens' AND s.nom = 'Demangeaisons';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Poux pubiens' AND s.nom = 'Lesions cutanees';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Molluscum' AND s.nom = 'Papules';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Molluscum' AND s.nom = 'Lesions cutanees';
INSERT INTO `ist-symptome` (`ist_id`, `symptome_id`) SELECT i.id, s.id FROM `ist` i JOIN `symptome` s WHERE i.nom = 'Molluscum' AND s.nom = 'Asymptomatique';

-- Relations IST -> preventions
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'PrEP VIH';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'TPE VIH';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'Materiel sterile';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'VIH' AND p.nom = 'Examen prenatal';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chlamydiose' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chlamydiose' AND p.nom = 'Digue dentaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chlamydiose' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chlamydiose' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chlamydiose' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gonorrhee' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gonorrhee' AND p.nom = 'Digue dentaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gonorrhee' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gonorrhee' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gonorrhee' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Syphilis' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Syphilis' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Syphilis' AND p.nom = 'Examen prenatal';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Syphilis' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Syphilis' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Herpes genital' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Herpes genital' AND p.nom = 'Eviter poussees';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Herpes genital' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Herpes genital' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'HPV' AND p.nom = 'Vaccination';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'HPV' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'HPV' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite B' AND p.nom = 'Vaccination';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite B' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite B' AND p.nom = 'Materiel sterile';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite B' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite B' AND p.nom = 'Examen prenatal';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite C' AND p.nom = 'Materiel sterile';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite C' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite C' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Hepatite C' AND p.nom = 'Pas partage objet';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Trichomonase' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Trichomonase' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Trichomonase' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Trichomonase' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Mycoplasma' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Mycoplasma' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Mycoplasma' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Mycoplasma' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'LGV' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'LGV' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'LGV' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'LGV' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chancre mou' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chancre mou' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chancre mou' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Chancre mou' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Donovanose' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Donovanose' AND p.nom = 'Depistage';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Donovanose' AND p.nom = 'Partenaire traite';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Donovanose' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gale' AND p.nom = 'Eviter poussees';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gale' AND p.nom = 'Hygiene linge';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Gale' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Poux pubiens' AND p.nom = 'Hygiene linge';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Poux pubiens' AND p.nom = 'Informer partenaire';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Poux pubiens' AND p.nom = 'Pas partage objet';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Molluscum' AND p.nom = 'Preservatif';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Molluscum' AND p.nom = 'Pas partage objet';
INSERT INTO `ist-prevention` (`ist_id`, `prevention_id`) SELECT i.id, p.id FROM `ist` i JOIN `prevention` p WHERE i.nom = 'Molluscum' AND p.nom = 'Informer partenaire';

-- Relations IST -> traitements
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'VIH' AND t.nom = 'Antiretroviraux';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Chlamydiose' AND t.nom = 'Doxycycline';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Gonorrhee' AND t.nom = 'Ceftriaxone';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Syphilis' AND t.nom = 'Penicilline G';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Herpes genital' AND t.nom = 'Valaciclovir';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'HPV' AND t.nom = 'Lesions HPV';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Hepatite B' AND t.nom = 'Tenofovir';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Hepatite C' AND t.nom = 'Antiviraux VHC';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Trichomonase' AND t.nom = 'Metronidazole';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Mycoplasma' AND t.nom = 'Moxifloxacine';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'LGV' AND t.nom = 'Doxycycline 21j';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Chancre mou' AND t.nom = 'Azithromycine';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Donovanose' AND t.nom = 'Azithromycine 21j';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Gale' AND t.nom = 'Permethrine';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Gale' AND t.nom = 'Ivermectine';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Poux pubiens' AND t.nom = 'Antiparasitaire';
INSERT INTO `ist-traitement` (`ist_id`, `traitement_id`) SELECT i.id, t.id FROM `ist` i JOIN `traitement` t WHERE i.nom = 'Molluscum' AND t.nom = 'Lesions HPV';

-- Cartes de demo pour l'utilisateur demo
DELETE uc FROM `user-card` uc JOIN `account` a ON a.id = uc.user_id WHERE a.username = 'demo';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'VIH';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'Chlamydiose';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'Gonorrhee';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'Syphilis';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'Herpes genital';
INSERT INTO `user-card` (`user_id`, `ist_id`) SELECT a.id, i.id FROM `account` a JOIN `ist` i WHERE a.username = 'demo' AND i.nom = 'HPV';



INSERT INTO `question` (`id`, `question`) VALUES
(1, 'Que signifie le sigle IST ?'),
(2, 'Par quels types de rapports une IST peut-elle se transmettre ?'),
(3, 'Une personne peut-elle avoir une IST sans symptôme ?'),
(4, 'Quel est l''intérêt du dépistage des IST ?'),
(5, 'Quel moyen réduit fortement le risque de nombreuses IST lorsqu''il est bien utilisé ?'),
(6, 'Quel énoncé sur la PrEP VIH est correct ?'),
(7, 'Après une exposition récente au VIH, que faut-il faire pour envisager un TPE ?'),
(8, 'Contre quelles IST existe-t-il des vaccins utilisés en prévention ?'),
(9, 'Quel énoncé sur le VIH est vrai ?'),
(10, 'Que signifie l''idée ''indétectable = intransmissible'' pour le VIH ?'),
(11, 'Quel comportement est recommandé avant d''arrêter le préservatif avec un nouveau partenaire stable ?'),
(12, 'Pourquoi le préservatif ne supprime-t-il pas 100 % du risque pour toutes les IST ?'),
(13, 'Quel lubrifiant est compatible avec un préservatif en latex ?'),
(14, 'Pourquoi faut-il éviter les corps gras avec un préservatif en latex ?'),
(15, 'Quelle bonne pratique concerne l''utilisation du préservatif ?'),
(16, 'Que faut-il vérifier avant d''utiliser un préservatif ?'),
(17, 'Quel énoncé est correct à propos des contraceptifs hormonaux comme la pilule ?'),
(18, 'La contraception d''urgence protège-t-elle des IST après un rapport à risque ?'),
(19, 'Quel est l''intérêt d''une digue dentaire ?'),
(20, 'Les objets sexuels partagés peuvent-ils participer à la transmission d''IST ?'),
(21, 'Que faire pour réduire le risque avec un sextoy partagé ?'),
(22, 'Quelle affirmation sur la chlamydia est juste ?'),
(23, 'Pourquoi traiter une infection à chlamydia est important ?'),
(24, 'Quel énoncé sur la gonorrhée est correct ?'),
(25, 'Pourquoi ne faut-il pas traiter une gonorrhée avec des antibiotiques restants ?'),
(26, 'Quel signe peut évoquer une syphilis au début ?'),
(27, 'La syphilis peut-elle avoir des complications graves si elle n''est pas traitée ?'),
(28, 'Quel énoncé sur l''herpès génital est exact ?'),
(29, 'L''herpès peut-il se transmettre en dehors d''une poussée visible ?'),
(30, 'Quelle infection peut être prévenue par un vaccin et se transmettre sexuellement ou par le sang ?'),
(31, 'Quel organe peut être gravement atteint par l''hépatite B chronique ?'),
(32, 'Quel énoncé sur l''hépatite C est le plus juste ?'),
(33, 'Pourquoi le dépistage après un rapport à risque doit parfois être répété ?'),
(34, 'Pour un test VIH de laboratoire de type ELISA, à quel délai un résultat est généralement considéré fiable après une exposition ?'),
(35, 'Pour un autotest ou un TROD VIH, quel délai est généralement nécessaire après une exposition pour un résultat fiable ?'),
(36, 'En France, que permet le dispositif Mon test IST en laboratoire ?'),
(37, 'Quel lieu peut proposer information, dépistage et diagnostic gratuits des IST en France ?'),
(38, 'Pourquoi prévenir ses partenaires après un diagnostic d''IST est utile ?'),
(39, 'Pourquoi les partenaires doivent-ils parfois être traités en même temps ?'),
(40, 'Pendant le traitement d''une IST, quelle attitude est généralement prudente ?'),
(41, 'Quel type d''IST est traité par antibiotiques lorsqu''un médecin le prescrit ?'),
(42, 'Pourquoi faut-il terminer un traitement antibiotique prescrit contre une IST ?'),
(43, 'Quel énoncé sur le HPV est exact ?'),
(44, 'La vaccination HPV a surtout quel objectif ?'),
(45, 'Quel dépistage reste important pour les personnes concernées même vaccinées contre le HPV ?'),
(46, 'Quel énoncé sur les symptômes d''IST est correct ?'),
(47, 'Une IST de la gorge ou du rectum peut-elle être asymptomatique ?'),
(48, 'Quel rapport peut transmettre certaines IST même sans pénétration vaginale ou anale ?'),
(49, 'Quelle affirmation sur le baiser est la plus prudente ?'),
(50, 'Le VIH se transmet-il par les moustiques ?'),
(51, 'Le VIH se transmet-il par une poignée de main ou une accolade ?'),
(52, 'Quels liquides peuvent transmettre le VIH dans des conditions de risque ?'),
(53, 'Quel geste réduit le risque de VIH, hépatite B et hépatite C chez les personnes qui s''injectent des produits ?'),
(54, 'Pourquoi le nombre de partenaires peut-il influencer le risque d''IST ?'),
(55, 'Quel énoncé est juste sur une relation exclusive ?'),
(56, 'Pourquoi parler de dépistage avec un partenaire est utile ?'),
(57, 'Quel effet l''alcool ou certaines drogues peuvent-ils avoir sur le risque d''IST ?'),
(58, 'Quelle attitude est correcte en cas de rapport non consenti ou de doute après une exposition ?'),
(59, 'Quel énoncé sur les morpions ou la gale est vrai ?'),
(60, 'La trichomonase est causée par quel type d''agent ?'),
(61, 'Quel énoncé sur la grossesse et les IST est correct ?'),
(62, 'Quel symptôme urinaire doit faire penser à consulter après un rapport à risque ?'),
(63, 'Les pertes vaginales inhabituelles après un rapport à risque doivent-elles être prises au sérieux ?'),
(64, 'Quel énoncé sur l''automédication est correct ?'),
(65, 'Pourquoi une consultation est importante en cas de plaie génitale ?'),
(66, 'Quel énoncé sur les tests de dépistage est juste ?'),
(67, 'Pourquoi faut-il parfois faire un prélèvement de gorge ou rectal ?'),
(68, 'Quel message est correct sur les IST et l''apparence d''une personne ?'),
(69, 'Quel est le rôle du préservatif interne ?'),
(70, 'Pourquoi ne faut-il pas mettre un préservatif externe et un préservatif interne en même temps ?'),
(71, 'Quand faut-il mettre un préservatif externe pour qu''il protège correctement ?'),
(72, 'Que faire si un préservatif craque pendant un rapport à risque ?'),
(73, 'Quel énoncé sur le dépistage régulier est pertinent ?'),
(74, 'Quelle affirmation sur la PrEP est correcte ?'),
(75, 'Quel énoncé sur le TPE est correct ?'),
(76, 'Quel est un avantage de combiner plusieurs moyens de prévention ?'),
(77, 'Quel énoncé sur le traitement d''une personne vivant avec le VIH est correct ?'),
(78, 'Quel message est exact sur les IST virales comme HPV, herpès ou VIH ?'),
(79, 'Quel énoncé sur les IST bactériennes est correct ?'),
(80, 'Quel est un bon réflexe après un résultat positif à une IST ?'),
(81, 'Quel énoncé est correct sur les tests en vente ou autotests VIH ?'),
(82, 'Pourquoi le dépistage du VIH peut-il être proposé avec celui d''autres IST ?'),
(83, 'Quelle phrase décrit correctement le risque lors du sexe anal non protégé ?'),
(84, 'Pourquoi utiliser assez de lubrifiant lors d''une pénétration anale peut aider à la prévention ?'),
(85, 'Quel énoncé sur les infections à HPV est prudent ?'),
(86, 'Quel énoncé sur les verrues génitales est exact ?'),
(87, 'Que signifie un dépistage négatif trop tôt après une prise de risque ?'),
(88, 'Quel énoncé sur le consentement est lié à la prévention ?'),
(89, 'Quelle est une limite de l''auto-évaluation des symptômes ?'),
(90, 'Quel énoncé sur les douches vaginales ou intimes après un rapport est correct ?'),
(91, 'Quel comportement protège contre la réinfection après une IST traitée ?'),
(92, 'Quel énoncé sur les rapports oraux est exact ?'),
(93, 'Quel moyen barrière peut être utilisé pour une fellation afin de réduire le risque d''IST ?'),
(94, 'Quel moyen barrière peut être utilisé pour un cunnilingus ou anulingus afin de réduire le risque ?'),
(95, 'Que faire si l''on apprend qu''un partenaire a une IST ?'),
(96, 'Quel énoncé sur la confidentialité du dépistage en France est correct ?'),
(97, 'Quel est un message exact sur les IST et l''hygiène ?'),
(98, 'Pourquoi les informations médicales personnalisées doivent-elles venir d''un professionnel de santé ?'),
(99, 'Quelle combinaison décrit bien une prévention sexuelle complète ?'),
(100, 'Quel objectif principal vise l''éducation sur les IST ?');

INSERT INTO `reponse` (`id`, `id_question`, `reponse`, `correct`) VALUES
(11, 1, 'Infection seulement tropicale', FALSE),
(12, 1, 'Infection sexuellement transmissible', TRUE),
(13, 1, 'Inflammation sanguine temporaire', FALSE),
(14, 1, 'Intoxication sexuelle toxique', FALSE),
(21, 2, 'Uniquement par pénétration vaginale', FALSE),
(22, 2, 'Uniquement par contact avec du sang visible', FALSE),
(23, 2, 'Par rapports vaginaux, anaux ou oraux selon l''IST', TRUE),
(24, 2, 'Seulement si les deux personnes ont des symptômes', FALSE),
(31, 3, 'Oui, certaines IST peuvent être asymptomatiques', TRUE),
(32, 3, 'Non, une IST donne toujours de la fièvre', FALSE),
(33, 3, 'Non, les symptômes apparaissent toujours en 24 heures', FALSE),
(34, 3, 'Oui, mais uniquement chez les hommes', FALSE),
(41, 4, 'Remplacer tous les moyens de prévention', FALSE),
(42, 4, 'Diagnostiquer plus tôt, traiter et éviter de transmettre', TRUE),
(43, 4, 'Savoir si une contraception fonctionne', FALSE),
(44, 4, 'Éviter définitivement toutes les IST', FALSE),
(51, 5, 'Le préservatif externe ou interne', TRUE),
(52, 5, 'La pilule contraceptive', FALSE),
(53, 5, 'Le retrait avant l''éjaculation', FALSE),
(54, 5, 'La douche après le rapport', FALSE),
(61, 6, 'Elle protège contre toutes les IST', FALSE),
(62, 6, 'Elle est un antibiotique contre la syphilis', FALSE),
(63, 6, 'Elle protège contre la grossesse', FALSE),
(64, 6, 'Elle réduit le risque de VIH mais ne protège pas des autres IST', TRUE),
(71, 7, 'Attendre les premiers symptômes', FALSE),
(72, 7, 'Consulter en urgence, idéalement très vite et au plus tard dans les 48 heures', TRUE),
(73, 7, 'Prendre un antibiotique restant à la maison', FALSE),
(74, 7, 'Faire uniquement une douche intime', FALSE),
(81, 8, 'VIH et chlamydia', FALSE),
(82, 8, 'Gonorrhée et syphilis', FALSE),
(83, 8, 'Hépatite B et papillomavirus humains (HPV)', TRUE),
(84, 8, 'Herpès génital et trichomonase', FALSE),
(91, 9, 'Il existe un vaccin disponible contre le VIH', FALSE),
(92, 9, 'Il se transmet par une poignée de main', FALSE),
(93, 9, 'Il peut être contrôlé par traitement antirétroviral', TRUE),
(94, 9, 'Il est guéri par une courte cure d''antibiotiques', FALSE),
(101, 10, 'Une personne sous traitement efficace avec charge virale durablement indétectable ne transmet pas le VIH par voie sexuelle', TRUE),
(102, 10, 'Toute personne vivant avec le VIH est automatiquement non contagieuse', FALSE),
(103, 10, 'Un test rapide négatif rend une personne intransmissible à vie', FALSE),
(104, 10, 'Cela concerne aussi toutes les autres IST', FALSE),
(111, 11, 'Faire confiance si l''autre personne a l''air en bonne santé', FALSE),
(112, 11, 'Faire un dépistage adapté et discuter d''exclusivité ou de risques', TRUE),
(113, 11, 'Attendre une semaine sans symptôme', FALSE),
(114, 11, 'Utiliser deux préservatifs superposés une fois', FALSE),
(121, 12, 'Parce qu''il ne couvre pas toujours toutes les zones de peau pouvant porter des lésions', TRUE),
(122, 12, 'Parce qu''il rend les bactéries plus résistantes', FALSE),
(123, 12, 'Parce qu''il ne fonctionne que chez les femmes', FALSE),
(124, 12, 'Parce qu''il ne protège jamais contre le VIH', FALSE),
(131, 13, 'Huile de cuisine', FALSE),
(132, 13, 'Vaseline', FALSE),
(133, 13, 'Lubrifiant à base d''eau ou de silicone', TRUE),
(134, 13, 'Beurre corporel', FALSE),
(141, 14, 'Ils peuvent fragiliser le latex et favoriser la rupture', TRUE),
(142, 14, 'Ils rendent le test VIH faussement positif', FALSE),
(143, 14, 'Ils annulent la vaccination HPV', FALSE),
(144, 14, 'Ils provoquent toujours une IST', FALSE),
(151, 15, 'Le réutiliser si le rapport reprend', FALSE),
(152, 15, 'Le mettre seulement juste avant l''éjaculation', FALSE),
(153, 15, 'Utiliser deux préservatifs l''un sur l''autre', FALSE),
(154, 15, 'Utiliser un préservatif neuf à chaque rapport ou changement de pratique', TRUE),
(161, 16, 'La date de péremption et l''état de l''emballage', TRUE),
(162, 16, 'La couleur préférée du partenaire uniquement', FALSE),
(163, 16, 'Qu''il a déjà été testé avec de l''eau', FALSE),
(164, 16, 'Qu''il est conservé depuis longtemps dans un portefeuille chaud', FALSE),
(171, 17, 'Ils protègent contre la grossesse mais pas contre les IST', TRUE),
(172, 17, 'Ils protègent contre le VIH', FALSE),
(173, 17, 'Ils remplacent le dépistage', FALSE),
(174, 17, 'Ils vaccinent contre le HPV', FALSE),
(181, 18, 'Oui, contre le VIH uniquement', FALSE),
(182, 18, 'Oui, contre la chlamydia uniquement', FALSE),
(183, 18, 'Non, elle concerne le risque de grossesse, pas les IST', TRUE),
(184, 18, 'Oui, si elle est prise dans les 24 heures', FALSE),
(191, 19, 'Réduire le risque d''IST lors de certains rapports bucco-génitaux ou bucco-anaux', TRUE),
(192, 19, 'Remplacer un test de dépistage', FALSE),
(193, 19, 'Traiter l''herpès génital', FALSE),
(194, 19, 'Empêcher une grossesse après un rapport', FALSE),
(201, 20, 'Non, jamais', FALSE),
(202, 20, 'Oui, surtout s''ils ne sont pas nettoyés ou protégés entre personnes', TRUE),
(203, 20, 'Seulement s''ils sont en métal', FALSE),
(204, 20, 'Seulement pendant la grossesse', FALSE),
(211, 21, 'Le rincer rapidement à l''eau froide uniquement', FALSE),
(212, 21, 'Utiliser un préservatif neuf ou le nettoyer correctement entre utilisations', TRUE),
(213, 21, 'Le laisser sécher au soleil une minute', FALSE),
(214, 21, 'Le partager seulement si personne n''a de fièvre', FALSE),
(221, 22, 'Elle ne touche que les personnes de plus de 60 ans', FALSE),
(222, 22, 'Elle est toujours visible sur la peau', FALSE),
(223, 22, 'Elle peut être fréquente et sans symptôme', TRUE),
(224, 22, 'Elle est évitée par la pilule contraceptive', FALSE),
(231, 23, 'Pour éviter notamment des complications comme l''infection génitale haute et l''infertilité', TRUE),
(232, 23, 'Pour éviter uniquement une grippe', FALSE),
(233, 23, 'Parce qu''elle guérit seulement par vaccination', FALSE),
(234, 23, 'Parce qu''elle se transmet par moustiques', FALSE),
(241, 24, 'C''est une IST bactérienne qui nécessite un diagnostic et un traitement adaptés', TRUE),
(242, 24, 'C''est toujours une infection virale incurable', FALSE),
(243, 24, 'Elle se transmet par les poignées de porte', FALSE),
(244, 24, 'Elle est empêchée par le retrait avant éjaculation', FALSE),
(251, 25, 'Parce qu''un traitement inadapté peut échouer et favoriser la résistance', TRUE),
(252, 25, 'Parce que la gonorrhée disparaît toujours seule', FALSE),
(253, 25, 'Parce que seuls les vaccins la soignent', FALSE),
(254, 25, 'Parce que cela rend les préservatifs inutiles', FALSE),
(261, 26, 'Un chancre souvent indolore au point d''entrée de l''infection', TRUE),
(262, 26, 'Une fracture du bras', FALSE),
(263, 26, 'Une perte de cheveux immédiate dans tous les cas', FALSE),
(264, 26, 'Une toux exclusivement nocturne', FALSE),
(271, 27, 'Oui, elle peut évoluer et atteindre différents organes', TRUE),
(272, 27, 'Non, elle est toujours bénigne', FALSE),
(273, 27, 'Non, elle ne dure jamais plus de 48 heures', FALSE),
(274, 27, 'Oui, mais uniquement chez les enfants', FALSE),
(281, 28, 'Il est toujours guéri définitivement par un antibiotique', FALSE),
(282, 28, 'Le virus peut rester dans l''organisme et les traitements aident à réduire les poussées ou la transmission', TRUE),
(283, 28, 'Il ne se transmet que par les toilettes', FALSE),
(284, 28, 'Il est empêché par la contraception d''urgence', FALSE),
(291, 29, 'Non, jamais', FALSE),
(292, 29, 'Oui, une transmission sans lésion visible est possible', TRUE),
(293, 29, 'Oui, mais seulement par moustique', FALSE),
(294, 29, 'Non, si la personne se sent en forme', FALSE),
(301, 30, 'Hépatite B', TRUE),
(302, 30, 'Chlamydia', FALSE),
(303, 30, 'Gonorrhée', FALSE),
(304, 30, 'Trichomonase', FALSE),
(311, 31, 'Le foie', TRUE),
(312, 31, 'Les cheveux', FALSE),
(313, 31, 'Les dents uniquement', FALSE),
(314, 31, 'Les ongles uniquement', FALSE),
(321, 32, 'Elle se transmet surtout par exposition au sang, et certains contextes sexuels peuvent augmenter le risque', TRUE),
(322, 32, 'Elle se transmet par simple poignée de main', FALSE),
(323, 32, 'Elle est empêchée par le vaccin HPV', FALSE),
(324, 32, 'Elle est toujours visible immédiatement', FALSE),
(331, 33, 'Parce qu''il existe des délais avant qu''une infection soit détectable par certains tests', TRUE),
(332, 33, 'Parce que les laboratoires ne savent jamais tester les IST', FALSE),
(333, 33, 'Parce qu''un test négatif est toujours faux', FALSE),
(334, 33, 'Parce que le préservatif fausse les résultats pendant un an', FALSE),
(341, 34, 'Dès la minute qui suit', FALSE),
(342, 34, 'Après environ 6 semaines', TRUE),
(343, 34, 'Uniquement après 5 ans', FALSE),
(344, 34, 'Jamais', FALSE),
(351, 35, 'Environ 3 mois', TRUE),
(352, 35, '2 heures', FALSE),
(353, 35, '6 jours exactement', FALSE),
(354, 35, '10 ans', FALSE),
(361, 36, 'Un dépistage sans ordonnance ni rendez-vous pour le VIH et certaines IST', TRUE),
(362, 36, 'Une vaccination obligatoire contre toutes les IST', FALSE),
(363, 36, 'Un traitement antibiotique automatique sans diagnostic', FALSE),
(364, 36, 'Un remplacement définitif des CeGidD', FALSE),
(371, 37, 'Un CeGidD', TRUE),
(372, 37, 'Uniquement une salle de sport', FALSE),
(373, 37, 'Uniquement une station-service', FALSE),
(374, 37, 'Uniquement un garage automobile', FALSE),
(381, 38, 'Pour qu''ils puissent se faire dépister et traiter si nécessaire', TRUE),
(382, 38, 'Pour éviter que les tests fonctionnent', FALSE),
(383, 38, 'Pour rendre le vaccin immédiatement curatif', FALSE),
(384, 38, 'Pour annuler automatiquement l''infection', FALSE),
(391, 39, 'Pour éviter les réinfections et interrompre la transmission', TRUE),
(392, 39, 'Pour éviter une allergie au préservatif', FALSE),
(393, 39, 'Parce que les IST sont toujours imaginaires', FALSE),
(394, 39, 'Parce que cela remplace le diagnostic', FALSE),
(401, 40, 'Suivre l''avis médical, éviter les rapports non protégés et attendre la fin de la période recommandée', TRUE),
(402, 40, 'Arrêter le traitement dès disparition des symptômes', FALSE),
(403, 40, 'Partager les comprimés avec des amis', FALSE),
(404, 40, 'Avoir plus de partenaires pour vérifier que l''infection a disparu', FALSE),
(411, 41, 'Certaines IST bactériennes comme chlamydia, gonorrhée ou syphilis', TRUE),
(412, 41, 'Toutes les IST virales définitivement', FALSE),
(413, 41, 'Le VIH en une seule prise', FALSE),
(414, 41, 'Le HPV par antibiotiques systématiques', FALSE),
(421, 42, 'Pour maximiser les chances de guérison et limiter les échecs de traitement', TRUE),
(422, 42, 'Pour rendre la contraception plus efficace', FALSE),
(423, 42, 'Pour transformer l''infection en virus', FALSE),
(424, 42, 'Pour éviter tout dépistage futur à vie', FALSE),
(431, 43, 'Certains HPV peuvent causer des cancers et d''autres des verrues génitales', TRUE),
(432, 43, 'Le HPV est toujours une bactérie', FALSE),
(433, 43, 'Le HPV se transmet uniquement par moustiques', FALSE),
(434, 43, 'Le HPV est empêché par la pilule', FALSE),
(441, 44, 'Réduire le risque d''infections par certains HPV et de cancers associés', TRUE),
(442, 44, 'Guérir instantanément une infection VIH', FALSE),
(443, 44, 'Remplacer les préservatifs contre toutes les IST', FALSE),
(444, 44, 'Traiter la gonorrhée résistante', FALSE),
(451, 45, 'Le dépistage du cancer du col de l''utérus selon les recommandations', TRUE),
(452, 45, 'Un test de vue annuel uniquement', FALSE),
(453, 45, 'Une radio de la cheville', FALSE),
(454, 45, 'Aucun suivi n''est jamais nécessaire', FALSE),
(461, 46, 'Brûlures, pertes inhabituelles, douleurs, boutons ou plaies doivent faire consulter', TRUE),
(462, 46, 'Seule la fièvre à 40 °C compte', FALSE),
(463, 46, 'Un symptôme disparaissant en 24 heures exclut toute IST', FALSE),
(464, 46, 'Les IST ne donnent jamais de symptômes locaux', FALSE),
(471, 47, 'Oui, certaines infections peuvent être présentes sans symptôme local', TRUE),
(472, 47, 'Non, il y a toujours une douleur intense', FALSE),
(473, 47, 'Non, ces zones ne peuvent jamais être concernées', FALSE),
(474, 47, 'Oui, mais seulement chez les personnes vaccinées', FALSE),
(481, 48, 'Le sexe oral', TRUE),
(482, 48, 'Regarder la télévision ensemble', FALSE),
(483, 48, 'Porter le même manteau', FALSE),
(484, 48, 'Se serrer la main', FALSE),
(491, 49, 'La plupart des IST ne se transmettent pas par un simple baiser, mais l''herpès peut se transmettre lors de lésions ou contacts à risque', TRUE),
(492, 49, 'Toutes les IST se transmettent toujours par un baiser', FALSE),
(493, 49, 'Le VIH se transmet par un baiser social', FALSE),
(494, 49, 'Le baiser vaccine contre les IST', FALSE),
(501, 50, 'Non', TRUE),
(502, 50, 'Oui, toujours', FALSE),
(503, 50, 'Oui, mais seulement en été', FALSE),
(504, 50, 'Seulement si le moustique pique deux personnes en une minute', FALSE),
(511, 51, 'Non', TRUE),
(512, 51, 'Oui, systématiquement', FALSE),
(513, 51, 'Oui, si la personne est sous traitement', FALSE),
(514, 51, 'Seulement après un repas', FALSE),
(521, 52, 'Sang, sperme, sécrétions vaginales, sécrétions rectales et lait maternel', TRUE),
(522, 52, 'Larmes et sueur uniquement', FALSE),
(523, 52, 'Salive d''un baiser social uniquement', FALSE),
(524, 52, 'Urine sur peau intacte uniquement', FALSE),
(531, 53, 'Ne jamais partager aiguilles, seringues ou matériel d''injection', TRUE),
(532, 53, 'Rincer la seringue à l''eau froide seulement', FALSE),
(533, 53, 'Changer seulement le bouchon de l''aiguille', FALSE),
(534, 53, 'Partager le matériel avec une personne connue uniquement', FALSE),
(541, 54, 'Plus il y a d''expositions possibles, plus le risque cumulé peut augmenter', TRUE),
(542, 54, 'Parce que les IST apparaissent après exactement trois partenaires', FALSE),
(543, 54, 'Parce que le risque disparaît avec beaucoup de partenaires', FALSE),
(544, 54, 'Parce que le dépistage devient inutile', FALSE),
(551, 55, 'Elle réduit le risque seulement si l''exclusivité est réelle et si les partenaires connaissent leur statut par dépistage adapté', TRUE),
(552, 55, 'Elle protège automatiquement sans dépistage', FALSE),
(553, 55, 'Elle guérit les IST anciennes', FALSE),
(554, 55, 'Elle remplace la vaccination', FALSE),
(561, 56, 'Pour décider ensemble d''une prévention adaptée', TRUE),
(562, 56, 'Pour prouver qu''une IST ne peut jamais arriver', FALSE),
(563, 56, 'Pour éviter tout recours au médecin', FALSE),
(564, 56, 'Pour remplacer les vaccins', FALSE),
(571, 57, 'Ils peuvent diminuer la vigilance et rendre les pratiques de prévention moins constantes', TRUE),
(572, 57, 'Ils tuent les bactéries responsables des IST', FALSE),
(573, 57, 'Ils rendent les préservatifs plus solides', FALSE),
(574, 57, 'Ils remplacent le dépistage', FALSE),
(581, 58, 'Chercher rapidement une aide médicale et un accompagnement adapté', TRUE),
(582, 58, 'Ne rien dire et attendre plusieurs mois', FALSE),
(583, 58, 'Prendre au hasard les médicaments d''une autre personne', FALSE),
(584, 58, 'Se fier uniquement à l''absence de symptômes le lendemain', FALSE),
(591, 59, 'Ils peuvent se transmettre par contacts rapprochés, y compris sexuels, et nécessitent un traitement adapté', TRUE),
(592, 59, 'Ils sont causés par le VIH', FALSE),
(593, 59, 'Ils sont empêchés par la PrEP', FALSE),
(594, 59, 'Ils prouvent toujours un manque d''hygiène', FALSE),
(601, 60, 'Un parasite', TRUE),
(602, 60, 'Un champignon de pain', FALSE),
(603, 60, 'Un traumatisme osseux', FALSE),
(604, 60, 'Une carie dentaire', FALSE),
(611, 61, 'Certaines IST peuvent avoir des conséquences pour la grossesse ou le nouveau-né, d''où l''intérêt du dépistage', TRUE),
(612, 61, 'Les IST disparaissent toujours pendant la grossesse', FALSE),
(613, 61, 'Aucun dépistage n''est possible pendant la grossesse', FALSE),
(614, 61, 'La grossesse vaccine contre le VIH', FALSE),
(621, 62, 'Brûlures en urinant ou écoulement inhabituel', TRUE),
(622, 62, 'Ongle cassé', FALSE),
(623, 62, 'Somnolence après un repas', FALSE),
(624, 62, 'Douleur au coude uniquement', FALSE),
(631, 63, 'Oui, elles peuvent justifier une consultation et un dépistage', TRUE),
(632, 63, 'Non, elles excluent une IST', FALSE),
(633, 63, 'Oui, mais seulement après un an', FALSE),
(634, 63, 'Non, aucun examen n''est jamais utile', FALSE),
(641, 64, 'Il vaut mieux éviter les antibiotiques sans diagnostic ni prescription', TRUE),
(642, 64, 'Tout antibiotique restant soigne toutes les IST', FALSE),
(643, 64, 'L''automédication remplace l''information des partenaires', FALSE),
(644, 64, 'Un antidouleur guérit le VIH', FALSE),
(651, 65, 'Pour identifier la cause possible, tester si nécessaire et traiter correctement', TRUE),
(652, 65, 'Parce qu''une plaie génitale est toujours due à une allergie sans risque', FALSE),
(653, 65, 'Parce qu''elle prouve une grossesse', FALSE),
(654, 65, 'Parce qu''elle rend le dépistage impossible', FALSE),
(661, 66, 'Le choix du test et du site de prélèvement dépend des pratiques et du délai depuis l''exposition', TRUE),
(662, 66, 'Un test sanguin détecte toujours toutes les IST partout', FALSE),
(663, 66, 'Un test urinaire négatif exclut toujours une infection de la gorge', FALSE),
(664, 66, 'Les tests ne servent qu''en présence de fièvre', FALSE),
(671, 67, 'Parce que certaines IST peuvent être localisées à ces endroits selon les pratiques sexuelles', TRUE),
(672, 67, 'Parce que tous les tests sanguins sont inutiles', FALSE),
(673, 67, 'Parce que cela remplace la vaccination', FALSE),
(674, 67, 'Parce que les IST ne concernent jamais les organes génitaux', FALSE),
(681, 68, 'On ne peut pas savoir avec certitude si quelqu''un a une IST en le regardant', TRUE),
(682, 68, 'Une personne sportive ne peut pas avoir d''IST', FALSE),
(683, 68, 'Une personne sans symptôme ne peut jamais transmettre', FALSE),
(684, 68, 'Les IST changent toujours la couleur des yeux', FALSE),
(691, 69, 'C''est une méthode barrière qui peut réduire le risque d''IST lors de la pénétration', TRUE),
(692, 69, 'C''est un antibiotique local', FALSE),
(693, 69, 'C''est un test de dépistage', FALSE),
(694, 69, 'C''est un vaccin contre le HPV', FALSE),
(701, 70, 'Le frottement peut augmenter le risque de rupture ou de glissement', TRUE),
(702, 70, 'Cela rend les tests VIH positifs', FALSE),
(703, 70, 'Cela annule la PrEP', FALSE),
(704, 70, 'Cela transforme une IST virale en bactérie', FALSE),
(711, 71, 'Avant tout contact sexuel à risque avec les muqueuses ou liquides sexuels', TRUE),
(712, 71, 'Uniquement après l''éjaculation', FALSE),
(713, 71, 'Seulement après le rapport', FALSE),
(714, 71, 'Jamais lors d''une première relation', FALSE),
(721, 72, 'Consulter rapidement pour évaluer TPE VIH, dépistage IST et contraception d''urgence si besoin', TRUE),
(722, 72, 'Le recoller et continuer', FALSE),
(723, 72, 'Attendre toujours trois mois sans rien faire', FALSE),
(724, 72, 'Prendre deux préservatifs au rapport suivant suffit', FALSE),
(731, 73, 'Il est particulièrement utile en cas de nouveaux partenaires, partenaires multiples ou rapport non protégé', TRUE),
(732, 73, 'Il n''est utile qu''après un mariage', FALSE),
(733, 73, 'Il remplace les préservatifs à 100 %', FALSE),
(734, 73, 'Il ne sert qu''aux personnes ayant des symptômes graves', FALSE),
(741, 74, 'Elle nécessite un suivi médical et des dépistages réguliers', TRUE),
(742, 74, 'Elle se prend au hasard après chaque rapport sans avis médical', FALSE),
(743, 74, 'Elle protège contre le HPV et la syphilis', FALSE),
(744, 74, 'Elle remplace tous les vaccins', FALSE),
(751, 75, 'C''est un traitement d''urgence après un risque VIH, pas une méthode à prendre régulièrement sans suivi', TRUE),
(752, 75, 'C''est un vaccin contre toutes les IST', FALSE),
(753, 75, 'Il doit attendre les symptômes pour commencer', FALSE),
(754, 75, 'Il sert à traiter la chlamydia', FALSE),
(761, 76, 'Réduire plusieurs types de risques : VIH, autres IST, grossesse non prévue selon les outils utilisés', TRUE),
(762, 76, 'Garantir qu''aucun dépistage ne sera jamais nécessaire', FALSE),
(763, 76, 'Annuler les délais de détection des tests', FALSE),
(764, 76, 'Remplacer tous les traitements', FALSE),
(771, 77, 'Un traitement pris correctement peut rendre la charge virale indétectable et protéger la santé', TRUE),
(772, 77, 'Il guérit le VIH en deux jours', FALSE),
(773, 77, 'Il est inutile si la personne se sent bien', FALSE),
(774, 77, 'Il est identique à un antibiotique contre la gonorrhée', FALSE),
(781, 78, 'Certaines ne se guérissent pas par antibiotiques, mais prévention, suivi et traitements peuvent réduire les risques', TRUE),
(782, 78, 'Elles sont toutes guéries par la pénicilline', FALSE),
(783, 78, 'Elles ne se transmettent jamais sexuellement', FALSE),
(784, 78, 'Elles sont toutes évitées par le retrait', FALSE),
(791, 79, 'Elles peuvent souvent être traitées par antibiotiques adaptés après diagnostic', TRUE),
(792, 79, 'Elles sont toujours incurables', FALSE),
(793, 79, 'Elles sont empêchées par la vaccination HPV', FALSE),
(794, 79, 'Elles se transmettent seulement par l''air', FALSE),
(801, 80, 'Suivre le traitement, demander quand reprendre les rapports et informer les partenaires concernés', TRUE),
(802, 80, 'Ignorer le résultat si les symptômes disparaissent', FALSE),
(803, 80, 'Partager son traitement sans avis médical', FALSE),
(804, 80, 'Arrêter définitivement tout dépistage', FALSE),
(811, 81, 'Un autotest positif doit être confirmé par un test de laboratoire', TRUE),
(812, 81, 'Un autotest positif guérit le VIH', FALSE),
(813, 81, 'Un autotest négatif juste après l''exposition exclut tout risque', FALSE),
(814, 81, 'Un autotest détecte automatiquement toutes les IST', FALSE),
(821, 82, 'Parce que des expositions communes peuvent concerner plusieurs infections', TRUE),
(822, 82, 'Parce que le VIH est causé par la chlamydia', FALSE),
(823, 82, 'Parce qu''un seul vaccin prévient tout', FALSE),
(824, 82, 'Parce que les autres IST empêchent le VIH', FALSE),
(831, 83, 'Il peut être élevé pour certaines IST, dont le VIH, surtout sans préservatif ou PrEP adaptée', TRUE),
(832, 83, 'Il n''existe aucun risque d''IST', FALSE),
(833, 83, 'Le risque concerne seulement la grossesse', FALSE),
(834, 83, 'Le risque disparaît si le rapport est court', FALSE),
(841, 84, 'Cela peut réduire les frottements, lésions et ruptures de préservatif', TRUE),
(842, 84, 'Cela remplace le préservatif', FALSE),
(843, 84, 'Cela tue le VIH', FALSE),
(844, 84, 'Cela rend les tests inutiles', FALSE),
(851, 85, 'Elles sont très fréquentes et souvent transitoires, mais certains types peuvent persister et provoquer des lésions', TRUE),
(852, 85, 'Elles provoquent toujours un cancer immédiatement', FALSE),
(853, 85, 'Elles ne concernent que les personnes âgées', FALSE),
(854, 85, 'Elles sont transmises par les moustiques', FALSE),
(861, 86, 'Elles peuvent être liées à certains HPV et doivent être évaluées par un professionnel de santé', TRUE),
(862, 86, 'Elles prouvent toujours une infection par le VIH', FALSE),
(863, 86, 'Elles sont toujours guéries par un antibiotique oral unique', FALSE),
(864, 86, 'Elles ne se transmettent jamais', FALSE),
(871, 87, 'Il peut être nécessaire de refaire un test au bon délai', TRUE),
(872, 87, 'Il prouve une protection à vie', FALSE),
(873, 87, 'Il prouve que le partenaire n''a jamais eu d''IST', FALSE),
(874, 87, 'Il rend la vaccination inutile', FALSE),
(881, 88, 'La prévention se discute mieux dans un cadre consenti, clair et respectueux', TRUE),
(882, 88, 'Le consentement remplace les préservatifs', FALSE),
(883, 88, 'Le consentement rend les IST impossibles', FALSE),
(884, 88, 'Le consentement est un test biologique', FALSE),
(891, 89, 'Les symptômes peuvent être absents, discrets ou ressembler à d''autres problèmes', TRUE),
(892, 89, 'Les symptômes permettent toujours un diagnostic exact', FALSE),
(893, 89, 'Internet remplace toujours un test', FALSE),
(894, 89, 'Une douleur exclut une IST', FALSE),
(901, 90, 'Elles ne sont pas une méthode fiable de prévention des IST et peuvent irriter', TRUE),
(902, 90, 'Elles remplacent le TPE', FALSE),
(903, 90, 'Elles guérissent la syphilis', FALSE),
(904, 90, 'Elles vaccinent contre l''hépatite B', FALSE),
(911, 91, 'S''assurer que les partenaires concernés sont dépistés ou traités selon l''avis médical', TRUE),
(912, 91, 'Reprendre les rapports non protégés immédiatement', FALSE),
(913, 91, 'Arrêter le traitement dès la première dose', FALSE),
(914, 91, 'Ne jamais informer personne', FALSE),
(921, 92, 'Ils peuvent transmettre certaines IST comme gonorrhée, syphilis, herpès ou HPV', TRUE),
(922, 92, 'Ils ne transmettent aucune IST', FALSE),
(923, 92, 'Ils transmettent toujours le VIH avec la même probabilité que le partage de seringue', FALSE),
(924, 92, 'Ils sont rendus sans risque par la pilule', FALSE),
(931, 93, 'Un préservatif', TRUE),
(932, 93, 'Une pilule contraceptive', FALSE),
(933, 93, 'Un antibiotique préventif sans ordonnance', FALSE),
(934, 93, 'Une boisson énergisante', FALSE),
(941, 94, 'Une digue dentaire ou protection adaptée', TRUE),
(942, 94, 'Un comprimé de vitamine C', FALSE),
(943, 94, 'Un pansement sur le bras', FALSE),
(944, 94, 'Une douche après le rapport uniquement', FALSE),
(951, 95, 'Demander conseil, se faire dépister au bon délai et éviter les rapports non protégés en attendant', TRUE),
(952, 95, 'Considérer que l''on est forcément immunisé', FALSE),
(953, 95, 'Prendre son traitement sans diagnostic', FALSE),
(954, 95, 'Attendre uniquement que les réseaux sociaux donnent une réponse', FALSE),
(961, 96, 'Des lieux comme les CeGidD peuvent proposer un accueil confidentiel et gratuit', TRUE),
(962, 96, 'Tout dépistage est automatiquement public', FALSE),
(963, 96, 'Un dépistage oblige à informer son employeur', FALSE),
(964, 96, 'La confidentialité empêche le traitement', FALSE),
(971, 97, 'Une bonne hygiène ne suffit pas à prévenir les IST ; les moyens barrière, vaccins et dépistages restent importants', TRUE),
(972, 97, 'Se laver après un rapport remplace un préservatif', FALSE),
(973, 97, 'Les IST prouvent toujours une mauvaise hygiène', FALSE),
(974, 97, 'Un parfum intime protège du VIH', FALSE),
(981, 98, 'Parce que les risques, tests et traitements dépendent de la situation précise', TRUE),
(982, 98, 'Parce que les IST sont toujours identiques chez tout le monde', FALSE),
(983, 98, 'Parce qu''aucun test n''existe', FALSE),
(984, 98, 'Parce que les préservatifs empêchent toute consultation', FALSE),
(991, 99, 'Préservatifs, vaccination quand indiquée, dépistage, PrEP/TPE VIH si besoin, traitement et dialogue', TRUE),
(992, 99, 'Douche intime, retrait et absence de symptômes', FALSE),
(993, 99, 'Antibiotiques au hasard après chaque rapport', FALSE),
(994, 99, 'Uniquement la pilule contraceptive', FALSE),
(1001, 100, 'Aider à réduire les risques, se faire dépister tôt et traiter correctement', TRUE),
(1002, 100, 'Faire peur sans donner de solutions', FALSE),
(1003, 100, 'Remplacer tous les professionnels de santé', FALSE),
(1004, 100, 'Garantir qu''aucune infection n''existera plus jamais', FALSE);

COMMIT;

