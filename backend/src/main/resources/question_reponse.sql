-- Script MySQL pour la base istdex
-- 100 questions sur les IST, chacune avec 4 réponses dont 1 correcte.
-- Schéma inspiré du diagramme fourni : question(id, question, id_bonne_reponse) et reponse(id, reponse).
-- Une colonne id_question est ajoutée à reponse pour matérialiser l'association 1 question -> plusieurs réponses.
-- Sources de vérification utilisées : OMS, Santé publique France / questionSexualité, Assurance Maladie (ameli).

CREATE DATABASE IF NOT EXISTS `istdex` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `istdex`;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `question`;
DROP TABLE IF EXISTS `reponse`;
SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE `question` (
  `id` INT NOT NULL,
  `question` VARCHAR(700) NOT NULL,
  `id_bonne_reponse` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `idx_question_bonne_reponse` (`id_bonne_reponse`),
  INDEX `idx_question_id_bonne` (`id`, `id_bonne_reponse`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `reponse` (
  `id` INT NOT NULL,
  `id_question` INT NOT NULL,
  `reponse` VARCHAR(700) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_reponse_question_id` (`id_question`, `id`),
  INDEX `idx_reponse_question` (`id_question`),
  CONSTRAINT `fk_reponse_question` FOREIGN KEY (`id_question`) REFERENCES `question` (`id`) ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

START TRANSACTION;

INSERT INTO `question` (`id`, `question`, `id_bonne_reponse`) VALUES
(1, 'Que signifie le sigle IST ?', NULL),
(2, 'Par quels types de rapports une IST peut-elle se transmettre ?', NULL),
(3, 'Une personne peut-elle avoir une IST sans symptôme ?', NULL),
(4, 'Quel est l''intérêt du dépistage des IST ?', NULL),
(5, 'Quel moyen réduit fortement le risque de nombreuses IST lorsqu''il est bien utilisé ?', NULL),
(6, 'Quel énoncé sur la PrEP VIH est correct ?', NULL),
(7, 'Après une exposition récente au VIH, que faut-il faire pour envisager un TPE ?', NULL),
(8, 'Contre quelles IST existe-t-il des vaccins utilisés en prévention ?', NULL),
(9, 'Quel énoncé sur le VIH est vrai ?', NULL),
(10, 'Que signifie l''idée ''indétectable = intransmissible'' pour le VIH ?', NULL),
(11, 'Quel comportement est recommandé avant d''arrêter le préservatif avec un nouveau partenaire stable ?', NULL),
(12, 'Pourquoi le préservatif ne supprime-t-il pas 100 % du risque pour toutes les IST ?', NULL),
(13, 'Quel lubrifiant est compatible avec un préservatif en latex ?', NULL),
(14, 'Pourquoi faut-il éviter les corps gras avec un préservatif en latex ?', NULL),
(15, 'Quelle bonne pratique concerne l''utilisation du préservatif ?', NULL),
(16, 'Que faut-il vérifier avant d''utiliser un préservatif ?', NULL),
(17, 'Quel énoncé est correct à propos des contraceptifs hormonaux comme la pilule ?', NULL),
(18, 'La contraception d''urgence protège-t-elle des IST après un rapport à risque ?', NULL),
(19, 'Quel est l''intérêt d''une digue dentaire ?', NULL),
(20, 'Les objets sexuels partagés peuvent-ils participer à la transmission d''IST ?', NULL),
(21, 'Que faire pour réduire le risque avec un sextoy partagé ?', NULL),
(22, 'Quelle affirmation sur la chlamydia est juste ?', NULL),
(23, 'Pourquoi traiter une infection à chlamydia est important ?', NULL),
(24, 'Quel énoncé sur la gonorrhée est correct ?', NULL),
(25, 'Pourquoi ne faut-il pas traiter une gonorrhée avec des antibiotiques restants ?', NULL),
(26, 'Quel signe peut évoquer une syphilis au début ?', NULL),
(27, 'La syphilis peut-elle avoir des complications graves si elle n''est pas traitée ?', NULL),
(28, 'Quel énoncé sur l''herpès génital est exact ?', NULL),
(29, 'L''herpès peut-il se transmettre en dehors d''une poussée visible ?', NULL),
(30, 'Quelle infection peut être prévenue par un vaccin et se transmettre sexuellement ou par le sang ?', NULL),
(31, 'Quel organe peut être gravement atteint par l''hépatite B chronique ?', NULL),
(32, 'Quel énoncé sur l''hépatite C est le plus juste ?', NULL),
(33, 'Pourquoi le dépistage après un rapport à risque doit parfois être répété ?', NULL),
(34, 'Pour un test VIH de laboratoire de type ELISA, à quel délai un résultat est généralement considéré fiable après une exposition ?', NULL),
(35, 'Pour un autotest ou un TROD VIH, quel délai est généralement nécessaire après une exposition pour un résultat fiable ?', NULL),
(36, 'En France, que permet le dispositif Mon test IST en laboratoire ?', NULL),
(37, 'Quel lieu peut proposer information, dépistage et diagnostic gratuits des IST en France ?', NULL),
(38, 'Pourquoi prévenir ses partenaires après un diagnostic d''IST est utile ?', NULL),
(39, 'Pourquoi les partenaires doivent-ils parfois être traités en même temps ?', NULL),
(40, 'Pendant le traitement d''une IST, quelle attitude est généralement prudente ?', NULL),
(41, 'Quel type d''IST est traité par antibiotiques lorsqu''un médecin le prescrit ?', NULL),
(42, 'Pourquoi faut-il terminer un traitement antibiotique prescrit contre une IST ?', NULL),
(43, 'Quel énoncé sur le HPV est exact ?', NULL),
(44, 'La vaccination HPV a surtout quel objectif ?', NULL),
(45, 'Quel dépistage reste important pour les personnes concernées même vaccinées contre le HPV ?', NULL),
(46, 'Quel énoncé sur les symptômes d''IST est correct ?', NULL),
(47, 'Une IST de la gorge ou du rectum peut-elle être asymptomatique ?', NULL),
(48, 'Quel rapport peut transmettre certaines IST même sans pénétration vaginale ou anale ?', NULL),
(49, 'Quelle affirmation sur le baiser est la plus prudente ?', NULL),
(50, 'Le VIH se transmet-il par les moustiques ?', NULL),
(51, 'Le VIH se transmet-il par une poignée de main ou une accolade ?', NULL),
(52, 'Quels liquides peuvent transmettre le VIH dans des conditions de risque ?', NULL),
(53, 'Quel geste réduit le risque de VIH, hépatite B et hépatite C chez les personnes qui s''injectent des produits ?', NULL),
(54, 'Pourquoi le nombre de partenaires peut-il influencer le risque d''IST ?', NULL),
(55, 'Quel énoncé est juste sur une relation exclusive ?', NULL),
(56, 'Pourquoi parler de dépistage avec un partenaire est utile ?', NULL),
(57, 'Quel effet l''alcool ou certaines drogues peuvent-ils avoir sur le risque d''IST ?', NULL),
(58, 'Quelle attitude est correcte en cas de rapport non consenti ou de doute après une exposition ?', NULL),
(59, 'Quel énoncé sur les morpions ou la gale est vrai ?', NULL),
(60, 'La trichomonase est causée par quel type d''agent ?', NULL),
(61, 'Quel énoncé sur la grossesse et les IST est correct ?', NULL),
(62, 'Quel symptôme urinaire doit faire penser à consulter après un rapport à risque ?', NULL),
(63, 'Les pertes vaginales inhabituelles après un rapport à risque doivent-elles être prises au sérieux ?', NULL),
(64, 'Quel énoncé sur l''automédication est correct ?', NULL),
(65, 'Pourquoi une consultation est importante en cas de plaie génitale ?', NULL),
(66, 'Quel énoncé sur les tests de dépistage est juste ?', NULL),
(67, 'Pourquoi faut-il parfois faire un prélèvement de gorge ou rectal ?', NULL),
(68, 'Quel message est correct sur les IST et l''apparence d''une personne ?', NULL),
(69, 'Quel est le rôle du préservatif interne ?', NULL),
(70, 'Pourquoi ne faut-il pas mettre un préservatif externe et un préservatif interne en même temps ?', NULL),
(71, 'Quand faut-il mettre un préservatif externe pour qu''il protège correctement ?', NULL),
(72, 'Que faire si un préservatif craque pendant un rapport à risque ?', NULL),
(73, 'Quel énoncé sur le dépistage régulier est pertinent ?', NULL),
(74, 'Quelle affirmation sur la PrEP est correcte ?', NULL),
(75, 'Quel énoncé sur le TPE est correct ?', NULL),
(76, 'Quel est un avantage de combiner plusieurs moyens de prévention ?', NULL),
(77, 'Quel énoncé sur le traitement d''une personne vivant avec le VIH est correct ?', NULL),
(78, 'Quel message est exact sur les IST virales comme HPV, herpès ou VIH ?', NULL),
(79, 'Quel énoncé sur les IST bactériennes est correct ?', NULL),
(80, 'Quel est un bon réflexe après un résultat positif à une IST ?', NULL),
(81, 'Quel énoncé est correct sur les tests en vente ou autotests VIH ?', NULL),
(82, 'Pourquoi le dépistage du VIH peut-il être proposé avec celui d''autres IST ?', NULL),
(83, 'Quelle phrase décrit correctement le risque lors du sexe anal non protégé ?', NULL),
(84, 'Pourquoi utiliser assez de lubrifiant lors d''une pénétration anale peut aider à la prévention ?', NULL),
(85, 'Quel énoncé sur les infections à HPV est prudent ?', NULL),
(86, 'Quel énoncé sur les verrues génitales est exact ?', NULL),
(87, 'Que signifie un dépistage négatif trop tôt après une prise de risque ?', NULL),
(88, 'Quel énoncé sur le consentement est lié à la prévention ?', NULL),
(89, 'Quelle est une limite de l''auto-évaluation des symptômes ?', NULL),
(90, 'Quel énoncé sur les douches vaginales ou intimes après un rapport est correct ?', NULL),
(91, 'Quel comportement protège contre la réinfection après une IST traitée ?', NULL),
(92, 'Quel énoncé sur les rapports oraux est exact ?', NULL),
(93, 'Quel moyen barrière peut être utilisé pour une fellation afin de réduire le risque d''IST ?', NULL),
(94, 'Quel moyen barrière peut être utilisé pour un cunnilingus ou anulingus afin de réduire le risque ?', NULL),
(95, 'Que faire si l''on apprend qu''un partenaire a une IST ?', NULL),
(96, 'Quel énoncé sur la confidentialité du dépistage en France est correct ?', NULL),
(97, 'Quel est un message exact sur les IST et l''hygiène ?', NULL),
(98, 'Pourquoi les informations médicales personnalisées doivent-elles venir d''un professionnel de santé ?', NULL),
(99, 'Quelle combinaison décrit bien une prévention sexuelle complète ?', NULL),
(100, 'Quel objectif principal vise l''éducation sur les IST ?', NULL);

INSERT INTO `reponse` (`id`, `id_question`, `reponse`) VALUES
(11, 1, 'Infection seulement tropicale'),
(12, 1, 'Infection sexuellement transmissible'),
(13, 1, 'Inflammation sanguine temporaire'),
(14, 1, 'Intoxication sexuelle toxique'),
(21, 2, 'Uniquement par pénétration vaginale'),
(22, 2, 'Uniquement par contact avec du sang visible'),
(23, 2, 'Par rapports vaginaux, anaux ou oraux selon l''IST'),
(24, 2, 'Seulement si les deux personnes ont des symptômes'),
(31, 3, 'Oui, certaines IST peuvent être asymptomatiques'),
(32, 3, 'Non, une IST donne toujours de la fièvre'),
(33, 3, 'Non, les symptômes apparaissent toujours en 24 heures'),
(34, 3, 'Oui, mais uniquement chez les hommes'),
(41, 4, 'Remplacer tous les moyens de prévention'),
(42, 4, 'Diagnostiquer plus tôt, traiter et éviter de transmettre'),
(43, 4, 'Savoir si une contraception fonctionne'),
(44, 4, 'Éviter définitivement toutes les IST'),
(51, 5, 'Le préservatif externe ou interne'),
(52, 5, 'La pilule contraceptive'),
(53, 5, 'Le retrait avant l''éjaculation'),
(54, 5, 'La douche après le rapport'),
(61, 6, 'Elle protège contre toutes les IST'),
(62, 6, 'Elle est un antibiotique contre la syphilis'),
(63, 6, 'Elle protège contre la grossesse'),
(64, 6, 'Elle réduit le risque de VIH mais ne protège pas des autres IST'),
(71, 7, 'Attendre les premiers symptômes'),
(72, 7, 'Consulter en urgence, idéalement très vite et au plus tard dans les 48 heures'),
(73, 7, 'Prendre un antibiotique restant à la maison'),
(74, 7, 'Faire uniquement une douche intime'),
(81, 8, 'VIH et chlamydia'),
(82, 8, 'Gonorrhée et syphilis'),
(83, 8, 'Hépatite B et papillomavirus humains (HPV)'),
(84, 8, 'Herpès génital et trichomonase'),
(91, 9, 'Il existe un vaccin disponible contre le VIH'),
(92, 9, 'Il se transmet par une poignée de main'),
(93, 9, 'Il peut être contrôlé par traitement antirétroviral'),
(94, 9, 'Il est guéri par une courte cure d''antibiotiques'),
(101, 10, 'Une personne sous traitement efficace avec charge virale durablement indétectable ne transmet pas le VIH par voie sexuelle'),
(102, 10, 'Toute personne vivant avec le VIH est automatiquement non contagieuse'),
(103, 10, 'Un test rapide négatif rend une personne intransmissible à vie'),
(104, 10, 'Cela concerne aussi toutes les autres IST'),
(111, 11, 'Faire confiance si l''autre personne a l''air en bonne santé'),
(112, 11, 'Faire un dépistage adapté et discuter d''exclusivité ou de risques'),
(113, 11, 'Attendre une semaine sans symptôme'),
(114, 11, 'Utiliser deux préservatifs superposés une fois'),
(121, 12, 'Parce qu''il ne couvre pas toujours toutes les zones de peau pouvant porter des lésions'),
(122, 12, 'Parce qu''il rend les bactéries plus résistantes'),
(123, 12, 'Parce qu''il ne fonctionne que chez les femmes'),
(124, 12, 'Parce qu''il ne protège jamais contre le VIH'),
(131, 13, 'Huile de cuisine'),
(132, 13, 'Vaseline'),
(133, 13, 'Lubrifiant à base d''eau ou de silicone'),
(134, 13, 'Beurre corporel'),
(141, 14, 'Ils peuvent fragiliser le latex et favoriser la rupture'),
(142, 14, 'Ils rendent le test VIH faussement positif'),
(143, 14, 'Ils annulent la vaccination HPV'),
(144, 14, 'Ils provoquent toujours une IST'),
(151, 15, 'Le réutiliser si le rapport reprend'),
(152, 15, 'Le mettre seulement juste avant l''éjaculation'),
(153, 15, 'Utiliser deux préservatifs l''un sur l''autre'),
(154, 15, 'Utiliser un préservatif neuf à chaque rapport ou changement de pratique'),
(161, 16, 'La date de péremption et l''état de l''emballage'),
(162, 16, 'La couleur préférée du partenaire uniquement'),
(163, 16, 'Qu''il a déjà été testé avec de l''eau'),
(164, 16, 'Qu''il est conservé depuis longtemps dans un portefeuille chaud'),
(171, 17, 'Ils protègent contre la grossesse mais pas contre les IST'),
(172, 17, 'Ils protègent contre le VIH'),
(173, 17, 'Ils remplacent le dépistage'),
(174, 17, 'Ils vaccinent contre le HPV'),
(181, 18, 'Oui, contre le VIH uniquement'),
(182, 18, 'Oui, contre la chlamydia uniquement'),
(183, 18, 'Non, elle concerne le risque de grossesse, pas les IST'),
(184, 18, 'Oui, si elle est prise dans les 24 heures'),
(191, 19, 'Réduire le risque d''IST lors de certains rapports bucco-génitaux ou bucco-anaux'),
(192, 19, 'Remplacer un test de dépistage'),
(193, 19, 'Traiter l''herpès génital'),
(194, 19, 'Empêcher une grossesse après un rapport'),
(201, 20, 'Non, jamais'),
(202, 20, 'Oui, surtout s''ils ne sont pas nettoyés ou protégés entre personnes'),
(203, 20, 'Seulement s''ils sont en métal'),
(204, 20, 'Seulement pendant la grossesse'),
(211, 21, 'Le rincer rapidement à l''eau froide uniquement'),
(212, 21, 'Utiliser un préservatif neuf ou le nettoyer correctement entre utilisations'),
(213, 21, 'Le laisser sécher au soleil une minute'),
(214, 21, 'Le partager seulement si personne n''a de fièvre'),
(221, 22, 'Elle ne touche que les personnes de plus de 60 ans'),
(222, 22, 'Elle est toujours visible sur la peau'),
(223, 22, 'Elle peut être fréquente et sans symptôme'),
(224, 22, 'Elle est évitée par la pilule contraceptive'),
(231, 23, 'Pour éviter notamment des complications comme l''infection génitale haute et l''infertilité'),
(232, 23, 'Pour éviter uniquement une grippe'),
(233, 23, 'Parce qu''elle guérit seulement par vaccination'),
(234, 23, 'Parce qu''elle se transmet par moustiques'),
(241, 24, 'C''est une IST bactérienne qui nécessite un diagnostic et un traitement adaptés'),
(242, 24, 'C''est toujours une infection virale incurable'),
(243, 24, 'Elle se transmet par les poignées de porte'),
(244, 24, 'Elle est empêchée par le retrait avant éjaculation'),
(251, 25, 'Parce qu''un traitement inadapté peut échouer et favoriser la résistance'),
(252, 25, 'Parce que la gonorrhée disparaît toujours seule'),
(253, 25, 'Parce que seuls les vaccins la soignent'),
(254, 25, 'Parce que cela rend les préservatifs inutiles'),
(261, 26, 'Un chancre souvent indolore au point d''entrée de l''infection'),
(262, 26, 'Une fracture du bras'),
(263, 26, 'Une perte de cheveux immédiate dans tous les cas'),
(264, 26, 'Une toux exclusivement nocturne'),
(271, 27, 'Oui, elle peut évoluer et atteindre différents organes'),
(272, 27, 'Non, elle est toujours bénigne'),
(273, 27, 'Non, elle ne dure jamais plus de 48 heures'),
(274, 27, 'Oui, mais uniquement chez les enfants'),
(281, 28, 'Il est toujours guéri définitivement par un antibiotique'),
(282, 28, 'Le virus peut rester dans l''organisme et les traitements aident à réduire les poussées ou la transmission'),
(283, 28, 'Il ne se transmet que par les toilettes'),
(284, 28, 'Il est empêché par la contraception d''urgence'),
(291, 29, 'Non, jamais'),
(292, 29, 'Oui, une transmission sans lésion visible est possible'),
(293, 29, 'Oui, mais seulement par moustique'),
(294, 29, 'Non, si la personne se sent en forme'),
(301, 30, 'Hépatite B'),
(302, 30, 'Chlamydia'),
(303, 30, 'Gonorrhée'),
(304, 30, 'Trichomonase'),
(311, 31, 'Le foie'),
(312, 31, 'Les cheveux'),
(313, 31, 'Les dents uniquement'),
(314, 31, 'Les ongles uniquement'),
(321, 32, 'Elle se transmet surtout par exposition au sang, et certains contextes sexuels peuvent augmenter le risque'),
(322, 32, 'Elle se transmet par simple poignée de main'),
(323, 32, 'Elle est empêchée par le vaccin HPV'),
(324, 32, 'Elle est toujours visible immédiatement'),
(331, 33, 'Parce qu''il existe des délais avant qu''une infection soit détectable par certains tests'),
(332, 33, 'Parce que les laboratoires ne savent jamais tester les IST'),
(333, 33, 'Parce qu''un test négatif est toujours faux'),
(334, 33, 'Parce que le préservatif fausse les résultats pendant un an'),
(341, 34, 'Dès la minute qui suit'),
(342, 34, 'Après environ 6 semaines'),
(343, 34, 'Uniquement après 5 ans'),
(344, 34, 'Jamais'),
(351, 35, 'Environ 3 mois'),
(352, 35, '2 heures'),
(353, 35, '6 jours exactement'),
(354, 35, '10 ans'),
(361, 36, 'Un dépistage sans ordonnance ni rendez-vous pour le VIH et certaines IST'),
(362, 36, 'Une vaccination obligatoire contre toutes les IST'),
(363, 36, 'Un traitement antibiotique automatique sans diagnostic'),
(364, 36, 'Un remplacement définitif des CeGidD'),
(371, 37, 'Un CeGidD'),
(372, 37, 'Uniquement une salle de sport'),
(373, 37, 'Uniquement une station-service'),
(374, 37, 'Uniquement un garage automobile'),
(381, 38, 'Pour qu''ils puissent se faire dépister et traiter si nécessaire'),
(382, 38, 'Pour éviter que les tests fonctionnent'),
(383, 38, 'Pour rendre le vaccin immédiatement curatif'),
(384, 38, 'Pour annuler automatiquement l''infection'),
(391, 39, 'Pour éviter les réinfections et interrompre la transmission'),
(392, 39, 'Pour éviter une allergie au préservatif'),
(393, 39, 'Parce que les IST sont toujours imaginaires'),
(394, 39, 'Parce que cela remplace le diagnostic'),
(401, 40, 'Suivre l''avis médical, éviter les rapports non protégés et attendre la fin de la période recommandée'),
(402, 40, 'Arrêter le traitement dès disparition des symptômes'),
(403, 40, 'Partager les comprimés avec des amis'),
(404, 40, 'Avoir plus de partenaires pour vérifier que l''infection a disparu'),
(411, 41, 'Certaines IST bactériennes comme chlamydia, gonorrhée ou syphilis'),
(412, 41, 'Toutes les IST virales définitivement'),
(413, 41, 'Le VIH en une seule prise'),
(414, 41, 'Le HPV par antibiotiques systématiques'),
(421, 42, 'Pour maximiser les chances de guérison et limiter les échecs de traitement'),
(422, 42, 'Pour rendre la contraception plus efficace'),
(423, 42, 'Pour transformer l''infection en virus'),
(424, 42, 'Pour éviter tout dépistage futur à vie'),
(431, 43, 'Certains HPV peuvent causer des cancers et d''autres des verrues génitales'),
(432, 43, 'Le HPV est toujours une bactérie'),
(433, 43, 'Le HPV se transmet uniquement par moustiques'),
(434, 43, 'Le HPV est empêché par la pilule'),
(441, 44, 'Réduire le risque d''infections par certains HPV et de cancers associés'),
(442, 44, 'Guérir instantanément une infection VIH'),
(443, 44, 'Remplacer les préservatifs contre toutes les IST'),
(444, 44, 'Traiter la gonorrhée résistante'),
(451, 45, 'Le dépistage du cancer du col de l''utérus selon les recommandations'),
(452, 45, 'Un test de vue annuel uniquement'),
(453, 45, 'Une radio de la cheville'),
(454, 45, 'Aucun suivi n''est jamais nécessaire'),
(461, 46, 'Brûlures, pertes inhabituelles, douleurs, boutons ou plaies doivent faire consulter'),
(462, 46, 'Seule la fièvre à 40 °C compte'),
(463, 46, 'Un symptôme disparaissant en 24 heures exclut toute IST'),
(464, 46, 'Les IST ne donnent jamais de symptômes locaux'),
(471, 47, 'Oui, certaines infections peuvent être présentes sans symptôme local'),
(472, 47, 'Non, il y a toujours une douleur intense'),
(473, 47, 'Non, ces zones ne peuvent jamais être concernées'),
(474, 47, 'Oui, mais seulement chez les personnes vaccinées'),
(481, 48, 'Le sexe oral'),
(482, 48, 'Regarder la télévision ensemble'),
(483, 48, 'Porter le même manteau'),
(484, 48, 'Se serrer la main'),
(491, 49, 'La plupart des IST ne se transmettent pas par un simple baiser, mais l''herpès peut se transmettre lors de lésions ou contacts à risque'),
(492, 49, 'Toutes les IST se transmettent toujours par un baiser'),
(493, 49, 'Le VIH se transmet par un baiser social'),
(494, 49, 'Le baiser vaccine contre les IST'),
(501, 50, 'Non'),
(502, 50, 'Oui, toujours'),
(503, 50, 'Oui, mais seulement en été'),
(504, 50, 'Seulement si le moustique pique deux personnes en une minute'),
(511, 51, 'Non'),
(512, 51, 'Oui, systématiquement'),
(513, 51, 'Oui, si la personne est sous traitement'),
(514, 51, 'Seulement après un repas'),
(521, 52, 'Sang, sperme, sécrétions vaginales, sécrétions rectales et lait maternel'),
(522, 52, 'Larmes et sueur uniquement'),
(523, 52, 'Salive d''un baiser social uniquement'),
(524, 52, 'Urine sur peau intacte uniquement'),
(531, 53, 'Ne jamais partager aiguilles, seringues ou matériel d''injection'),
(532, 53, 'Rincer la seringue à l''eau froide seulement'),
(533, 53, 'Changer seulement le bouchon de l''aiguille'),
(534, 53, 'Partager le matériel avec une personne connue uniquement'),
(541, 54, 'Plus il y a d''expositions possibles, plus le risque cumulé peut augmenter'),
(542, 54, 'Parce que les IST apparaissent après exactement trois partenaires'),
(543, 54, 'Parce que le risque disparaît avec beaucoup de partenaires'),
(544, 54, 'Parce que le dépistage devient inutile'),
(551, 55, 'Elle réduit le risque seulement si l''exclusivité est réelle et si les partenaires connaissent leur statut par dépistage adapté'),
(552, 55, 'Elle protège automatiquement sans dépistage'),
(553, 55, 'Elle guérit les IST anciennes'),
(554, 55, 'Elle remplace la vaccination'),
(561, 56, 'Pour décider ensemble d''une prévention adaptée'),
(562, 56, 'Pour prouver qu''une IST ne peut jamais arriver'),
(563, 56, 'Pour éviter tout recours au médecin'),
(564, 56, 'Pour remplacer les vaccins'),
(571, 57, 'Ils peuvent diminuer la vigilance et rendre les pratiques de prévention moins constantes'),
(572, 57, 'Ils tuent les bactéries responsables des IST'),
(573, 57, 'Ils rendent les préservatifs plus solides'),
(574, 57, 'Ils remplacent le dépistage'),
(581, 58, 'Chercher rapidement une aide médicale et un accompagnement adapté'),
(582, 58, 'Ne rien dire et attendre plusieurs mois'),
(583, 58, 'Prendre au hasard les médicaments d''une autre personne'),
(584, 58, 'Se fier uniquement à l''absence de symptômes le lendemain'),
(591, 59, 'Ils peuvent se transmettre par contacts rapprochés, y compris sexuels, et nécessitent un traitement adapté'),
(592, 59, 'Ils sont causés par le VIH'),
(593, 59, 'Ils sont empêchés par la PrEP'),
(594, 59, 'Ils prouvent toujours un manque d''hygiène'),
(601, 60, 'Un parasite'),
(602, 60, 'Un champignon de pain'),
(603, 60, 'Un traumatisme osseux'),
(604, 60, 'Une carie dentaire'),
(611, 61, 'Certaines IST peuvent avoir des conséquences pour la grossesse ou le nouveau-né, d''où l''intérêt du dépistage'),
(612, 61, 'Les IST disparaissent toujours pendant la grossesse'),
(613, 61, 'Aucun dépistage n''est possible pendant la grossesse'),
(614, 61, 'La grossesse vaccine contre le VIH'),
(621, 62, 'Brûlures en urinant ou écoulement inhabituel'),
(622, 62, 'Ongle cassé'),
(623, 62, 'Somnolence après un repas'),
(624, 62, 'Douleur au coude uniquement'),
(631, 63, 'Oui, elles peuvent justifier une consultation et un dépistage'),
(632, 63, 'Non, elles excluent une IST'),
(633, 63, 'Oui, mais seulement après un an'),
(634, 63, 'Non, aucun examen n''est jamais utile'),
(641, 64, 'Il vaut mieux éviter les antibiotiques sans diagnostic ni prescription'),
(642, 64, 'Tout antibiotique restant soigne toutes les IST'),
(643, 64, 'L''automédication remplace l''information des partenaires'),
(644, 64, 'Un antidouleur guérit le VIH'),
(651, 65, 'Pour identifier la cause possible, tester si nécessaire et traiter correctement'),
(652, 65, 'Parce qu''une plaie génitale est toujours due à une allergie sans risque'),
(653, 65, 'Parce qu''elle prouve une grossesse'),
(654, 65, 'Parce qu''elle rend le dépistage impossible'),
(661, 66, 'Le choix du test et du site de prélèvement dépend des pratiques et du délai depuis l''exposition'),
(662, 66, 'Un test sanguin détecte toujours toutes les IST partout'),
(663, 66, 'Un test urinaire négatif exclut toujours une infection de la gorge'),
(664, 66, 'Les tests ne servent qu''en présence de fièvre'),
(671, 67, 'Parce que certaines IST peuvent être localisées à ces endroits selon les pratiques sexuelles'),
(672, 67, 'Parce que tous les tests sanguins sont inutiles'),
(673, 67, 'Parce que cela remplace la vaccination'),
(674, 67, 'Parce que les IST ne concernent jamais les organes génitaux'),
(681, 68, 'On ne peut pas savoir avec certitude si quelqu''un a une IST en le regardant'),
(682, 68, 'Une personne sportive ne peut pas avoir d''IST'),
(683, 68, 'Une personne sans symptôme ne peut jamais transmettre'),
(684, 68, 'Les IST changent toujours la couleur des yeux'),
(691, 69, 'C''est une méthode barrière qui peut réduire le risque d''IST lors de la pénétration'),
(692, 69, 'C''est un antibiotique local'),
(693, 69, 'C''est un test de dépistage'),
(694, 69, 'C''est un vaccin contre le HPV'),
(701, 70, 'Le frottement peut augmenter le risque de rupture ou de glissement'),
(702, 70, 'Cela rend les tests VIH positifs'),
(703, 70, 'Cela annule la PrEP'),
(704, 70, 'Cela transforme une IST virale en bactérie'),
(711, 71, 'Avant tout contact sexuel à risque avec les muqueuses ou liquides sexuels'),
(712, 71, 'Uniquement après l''éjaculation'),
(713, 71, 'Seulement après le rapport'),
(714, 71, 'Jamais lors d''une première relation'),
(721, 72, 'Consulter rapidement pour évaluer TPE VIH, dépistage IST et contraception d''urgence si besoin'),
(722, 72, 'Le recoller et continuer'),
(723, 72, 'Attendre toujours trois mois sans rien faire'),
(724, 72, 'Prendre deux préservatifs au rapport suivant suffit'),
(731, 73, 'Il est particulièrement utile en cas de nouveaux partenaires, partenaires multiples ou rapport non protégé'),
(732, 73, 'Il n''est utile qu''après un mariage'),
(733, 73, 'Il remplace les préservatifs à 100 %'),
(734, 73, 'Il ne sert qu''aux personnes ayant des symptômes graves'),
(741, 74, 'Elle nécessite un suivi médical et des dépistages réguliers'),
(742, 74, 'Elle se prend au hasard après chaque rapport sans avis médical'),
(743, 74, 'Elle protège contre le HPV et la syphilis'),
(744, 74, 'Elle remplace tous les vaccins'),
(751, 75, 'C''est un traitement d''urgence après un risque VIH, pas une méthode à prendre régulièrement sans suivi'),
(752, 75, 'C''est un vaccin contre toutes les IST'),
(753, 75, 'Il doit attendre les symptômes pour commencer'),
(754, 75, 'Il sert à traiter la chlamydia'),
(761, 76, 'Réduire plusieurs types de risques : VIH, autres IST, grossesse non prévue selon les outils utilisés'),
(762, 76, 'Garantir qu''aucun dépistage ne sera jamais nécessaire'),
(763, 76, 'Annuler les délais de détection des tests'),
(764, 76, 'Remplacer tous les traitements'),
(771, 77, 'Un traitement pris correctement peut rendre la charge virale indétectable et protéger la santé'),
(772, 77, 'Il guérit le VIH en deux jours'),
(773, 77, 'Il est inutile si la personne se sent bien'),
(774, 77, 'Il est identique à un antibiotique contre la gonorrhée'),
(781, 78, 'Certaines ne se guérissent pas par antibiotiques, mais prévention, suivi et traitements peuvent réduire les risques'),
(782, 78, 'Elles sont toutes guéries par la pénicilline'),
(783, 78, 'Elles ne se transmettent jamais sexuellement'),
(784, 78, 'Elles sont toutes évitées par le retrait'),
(791, 79, 'Elles peuvent souvent être traitées par antibiotiques adaptés après diagnostic'),
(792, 79, 'Elles sont toujours incurables'),
(793, 79, 'Elles sont empêchées par la vaccination HPV'),
(794, 79, 'Elles se transmettent seulement par l''air'),
(801, 80, 'Suivre le traitement, demander quand reprendre les rapports et informer les partenaires concernés'),
(802, 80, 'Ignorer le résultat si les symptômes disparaissent'),
(803, 80, 'Partager son traitement sans avis médical'),
(804, 80, 'Arrêter définitivement tout dépistage'),
(811, 81, 'Un autotest positif doit être confirmé par un test de laboratoire'),
(812, 81, 'Un autotest positif guérit le VIH'),
(813, 81, 'Un autotest négatif juste après l''exposition exclut tout risque'),
(814, 81, 'Un autotest détecte automatiquement toutes les IST'),
(821, 82, 'Parce que des expositions communes peuvent concerner plusieurs infections'),
(822, 82, 'Parce que le VIH est causé par la chlamydia'),
(823, 82, 'Parce qu''un seul vaccin prévient tout'),
(824, 82, 'Parce que les autres IST empêchent le VIH'),
(831, 83, 'Il peut être élevé pour certaines IST, dont le VIH, surtout sans préservatif ou PrEP adaptée'),
(832, 83, 'Il n''existe aucun risque d''IST'),
(833, 83, 'Le risque concerne seulement la grossesse'),
(834, 83, 'Le risque disparaît si le rapport est court'),
(841, 84, 'Cela peut réduire les frottements, lésions et ruptures de préservatif'),
(842, 84, 'Cela remplace le préservatif'),
(843, 84, 'Cela tue le VIH'),
(844, 84, 'Cela rend les tests inutiles'),
(851, 85, 'Elles sont très fréquentes et souvent transitoires, mais certains types peuvent persister et provoquer des lésions'),
(852, 85, 'Elles provoquent toujours un cancer immédiatement'),
(853, 85, 'Elles ne concernent que les personnes âgées'),
(854, 85, 'Elles sont transmises par les moustiques'),
(861, 86, 'Elles peuvent être liées à certains HPV et doivent être évaluées par un professionnel de santé'),
(862, 86, 'Elles prouvent toujours une infection par le VIH'),
(863, 86, 'Elles sont toujours guéries par un antibiotique oral unique'),
(864, 86, 'Elles ne se transmettent jamais'),
(871, 87, 'Il peut être nécessaire de refaire un test au bon délai'),
(872, 87, 'Il prouve une protection à vie'),
(873, 87, 'Il prouve que le partenaire n''a jamais eu d''IST'),
(874, 87, 'Il rend la vaccination inutile'),
(881, 88, 'La prévention se discute mieux dans un cadre consenti, clair et respectueux'),
(882, 88, 'Le consentement remplace les préservatifs'),
(883, 88, 'Le consentement rend les IST impossibles'),
(884, 88, 'Le consentement est un test biologique'),
(891, 89, 'Les symptômes peuvent être absents, discrets ou ressembler à d''autres problèmes'),
(892, 89, 'Les symptômes permettent toujours un diagnostic exact'),
(893, 89, 'Internet remplace toujours un test'),
(894, 89, 'Une douleur exclut une IST'),
(901, 90, 'Elles ne sont pas une méthode fiable de prévention des IST et peuvent irriter'),
(902, 90, 'Elles remplacent le TPE'),
(903, 90, 'Elles guérissent la syphilis'),
(904, 90, 'Elles vaccinent contre l''hépatite B'),
(911, 91, 'S''assurer que les partenaires concernés sont dépistés ou traités selon l''avis médical'),
(912, 91, 'Reprendre les rapports non protégés immédiatement'),
(913, 91, 'Arrêter le traitement dès la première dose'),
(914, 91, 'Ne jamais informer personne'),
(921, 92, 'Ils peuvent transmettre certaines IST comme gonorrhée, syphilis, herpès ou HPV'),
(922, 92, 'Ils ne transmettent aucune IST'),
(923, 92, 'Ils transmettent toujours le VIH avec la même probabilité que le partage de seringue'),
(924, 92, 'Ils sont rendus sans risque par la pilule'),
(931, 93, 'Un préservatif'),
(932, 93, 'Une pilule contraceptive'),
(933, 93, 'Un antibiotique préventif sans ordonnance'),
(934, 93, 'Une boisson énergisante'),
(941, 94, 'Une digue dentaire ou protection adaptée'),
(942, 94, 'Un comprimé de vitamine C'),
(943, 94, 'Un pansement sur le bras'),
(944, 94, 'Une douche après le rapport uniquement'),
(951, 95, 'Demander conseil, se faire dépister au bon délai et éviter les rapports non protégés en attendant'),
(952, 95, 'Considérer que l''on est forcément immunisé'),
(953, 95, 'Prendre son traitement sans diagnostic'),
(954, 95, 'Attendre uniquement que les réseaux sociaux donnent une réponse'),
(961, 96, 'Des lieux comme les CeGidD peuvent proposer un accueil confidentiel et gratuit'),
(962, 96, 'Tout dépistage est automatiquement public'),
(963, 96, 'Un dépistage oblige à informer son employeur'),
(964, 96, 'La confidentialité empêche le traitement'),
(971, 97, 'Une bonne hygiène ne suffit pas à prévenir les IST ; les moyens barrière, vaccins et dépistages restent importants'),
(972, 97, 'Se laver après un rapport remplace un préservatif'),
(973, 97, 'Les IST prouvent toujours une mauvaise hygiène'),
(974, 97, 'Un parfum intime protège du VIH'),
(981, 98, 'Parce que les risques, tests et traitements dépendent de la situation précise'),
(982, 98, 'Parce que les IST sont toujours identiques chez tout le monde'),
(983, 98, 'Parce qu''aucun test n''existe'),
(984, 98, 'Parce que les préservatifs empêchent toute consultation'),
(991, 99, 'Préservatifs, vaccination quand indiquée, dépistage, PrEP/TPE VIH si besoin, traitement et dialogue'),
(992, 99, 'Douche intime, retrait et absence de symptômes'),
(993, 99, 'Antibiotiques au hasard après chaque rapport'),
(994, 99, 'Uniquement la pilule contraceptive'),
(1001, 100, 'Aider à réduire les risques, se faire dépister tôt et traiter correctement'),
(1002, 100, 'Faire peur sans donner de solutions'),
(1003, 100, 'Remplacer tous les professionnels de santé'),
(1004, 100, 'Garantir qu''aucune infection n''existera plus jamais');

UPDATE `question` SET `id_bonne_reponse` = CASE `id`
  WHEN 1 THEN 12
  WHEN 2 THEN 23
  WHEN 3 THEN 31
  WHEN 4 THEN 42
  WHEN 5 THEN 51
  WHEN 6 THEN 64
  WHEN 7 THEN 72
  WHEN 8 THEN 83
  WHEN 9 THEN 93
  WHEN 10 THEN 101
  WHEN 11 THEN 112
  WHEN 12 THEN 121
  WHEN 13 THEN 133
  WHEN 14 THEN 141
  WHEN 15 THEN 154
  WHEN 16 THEN 161
  WHEN 17 THEN 171
  WHEN 18 THEN 183
  WHEN 19 THEN 191
  WHEN 20 THEN 202
  WHEN 21 THEN 212
  WHEN 22 THEN 223
  WHEN 23 THEN 231
  WHEN 24 THEN 241
  WHEN 25 THEN 251
  WHEN 26 THEN 261
  WHEN 27 THEN 271
  WHEN 28 THEN 282
  WHEN 29 THEN 292
  WHEN 30 THEN 301
  WHEN 31 THEN 311
  WHEN 32 THEN 321
  WHEN 33 THEN 331
  WHEN 34 THEN 342
  WHEN 35 THEN 351
  WHEN 36 THEN 361
  WHEN 37 THEN 371
  WHEN 38 THEN 381
  WHEN 39 THEN 391
  WHEN 40 THEN 401
  WHEN 41 THEN 411
  WHEN 42 THEN 421
  WHEN 43 THEN 431
  WHEN 44 THEN 441
  WHEN 45 THEN 451
  WHEN 46 THEN 461
  WHEN 47 THEN 471
  WHEN 48 THEN 481
  WHEN 49 THEN 491
  WHEN 50 THEN 501
  WHEN 51 THEN 511
  WHEN 52 THEN 521
  WHEN 53 THEN 531
  WHEN 54 THEN 541
  WHEN 55 THEN 551
  WHEN 56 THEN 561
  WHEN 57 THEN 571
  WHEN 58 THEN 581
  WHEN 59 THEN 591
  WHEN 60 THEN 601
  WHEN 61 THEN 611
  WHEN 62 THEN 621
  WHEN 63 THEN 631
  WHEN 64 THEN 641
  WHEN 65 THEN 651
  WHEN 66 THEN 661
  WHEN 67 THEN 671
  WHEN 68 THEN 681
  WHEN 69 THEN 691
  WHEN 70 THEN 701
  WHEN 71 THEN 711
  WHEN 72 THEN 721
  WHEN 73 THEN 731
  WHEN 74 THEN 741
  WHEN 75 THEN 751
  WHEN 76 THEN 761
  WHEN 77 THEN 771
  WHEN 78 THEN 781
  WHEN 79 THEN 791
  WHEN 80 THEN 801
  WHEN 81 THEN 811
  WHEN 82 THEN 821
  WHEN 83 THEN 831
  WHEN 84 THEN 841
  WHEN 85 THEN 851
  WHEN 86 THEN 861
  WHEN 87 THEN 871
  WHEN 88 THEN 881
  WHEN 89 THEN 891
  WHEN 90 THEN 901
  WHEN 91 THEN 911
  WHEN 92 THEN 921
  WHEN 93 THEN 931
  WHEN 94 THEN 941
  WHEN 95 THEN 951
  WHEN 96 THEN 961
  WHEN 97 THEN 971
  WHEN 98 THEN 981
  WHEN 99 THEN 991
  WHEN 100 THEN 1001
END
WHERE `id` BETWEEN 1 AND 100;

COMMIT;

ALTER TABLE `question`
  MODIFY `id_bonne_reponse` INT NOT NULL,
  ADD CONSTRAINT `fk_question_bonne_reponse` FOREIGN KEY (`id`, `id_bonne_reponse`) REFERENCES `reponse` (`id_question`, `id`) ON UPDATE CASCADE ON DELETE RESTRICT;

-- Requête de vérification : doit retourner 100 lignes, chaque question avec sa bonne réponse.
-- SELECT q.id, q.question, r.reponse AS bonne_reponse
-- FROM `question` q
-- JOIN `reponse` r ON r.id = q.id_bonne_reponse
-- ORDER BY q.id;

-- Requête de vérification : doit retourner 100 si chaque question a exactement 4 réponses.
-- SELECT COUNT(*) AS questions_avec_4_reponses
-- FROM (
--   SELECT id_question FROM `reponse` GROUP BY id_question HAVING COUNT(*) = 4
-- ) x;