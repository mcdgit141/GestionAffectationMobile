-- Table modèle iphone

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (1,'Iphone6');

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (2,'Iphone7');

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (3,'Iphone8');

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (4,'Iphone9');

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (5,'Iphone10');

INSERT INTO modele_iphone_entity(

    modele_id,nom_modele)

   VALUES (6,'Iphone11');

-- Table iphone

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('DISPONIBLE', '010203', 1200.00, 1);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('DISPONIBLE', '010204', 1200.00, 1);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('AFFECTE', '010205', 1200.00, 1);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('DISPONIBLE', '010206', 1300.00, 3);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('DISPONIBLE', '010207', 1400.00, 4);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('DISPONIBLE', '010208', 1500.00, 5);

INSERT INTO iphone_entity(

   etat_iphone, numero_serie, prix_iphone, modele_id)

   VALUES ('PERDU', '010209', 1600.00, 6);

INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (800, 'AFFECTE', '11111111', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (801, 'AFFECTE', '22222222', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (802, 'AFFECTE', '33333333', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (803, 'AFFECTE', '44444444', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (804, 'AFFECTE', '55555555', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (805, 'AFFECTE', '66666666', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (806, 'AFFECTE', '77777777', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (807, 'AFFECTE', '88888888', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (808, 'AFFECTE', '99999999', 1200.00, 1);
INSERT INTO iphone_entity(iphone_id, etat_iphone, numero_serie, prix_iphone, modele_id)
   VALUES (809, 'AFFECTE', '00000000', 1200.00, 1);

-- Table siteExercice

INSERT INTO site_exercice_entity(

   site_id, adresse_postale1, code_postal, code_site, date_cloture, date_creation, nom_site, pays, ville)

   VALUES (1,'41, rue de Valmy', '93100', 'V2', null, '2020-08-04', 'VALMY2', 'France', 'Montreuil');

INSERT INTO site_exercice_entity(

   site_id,adresse_postale1, code_postal, code_site, date_cloture, date_creation, nom_site, pays, ville)

   VALUES (2,'15, rue de la République', '93100', 'V1', null, '2020-08-04', 'VALMY1', 'France', 'Montreuil');

INSERT INTO site_exercice_entity(

   site_id,adresse_postale1, code_postal, code_site, date_cloture, date_creation, nom_site, pays, ville)

   VALUES (3,'16, rue de la Joie', '93100', 'V5', null, '2020-08-04', 'VALMY5', 'France', 'Montreuil');

INSERT INTO site_exercice_entity(

   site_id,adresse_postale1, code_postal, code_site, date_cloture, date_creation, nom_site, pays, ville)

   VALUES (4,'20, rue des Fleurs', '93100', 'V7', null, '2020-08-04', 'VALMY7', 'France', 'Montreuil');

-- Table des Uo

INSERT INTO uo_entity(

   uo_id, code_uo, code_uo_parent, fonction_rattachement, nom_responsable_uo, nom_usage_uo, site_id)

   VALUES (1,'SDI101', 'SDI1', 'BDDF IT', 'Amiral de la Marine', 'SDI DIGITAL', 1);

INSERT INTO uo_entity(

   uo_id,code_uo, code_uo_parent, fonction_rattachement, nom_responsable_uo, nom_usage_uo, site_id)

   VALUES (2,'SDI102', 'SDI2', 'BDDF IT', 'Amiral de la Marine', 'SDI Teradata', 1);

INSERT INTO uo_entity(

   uo_id,code_uo, code_uo_parent, fonction_rattachement, nom_responsable_uo, nom_usage_uo, site_id)

   VALUES (3,'CCR302', 'CCR3', 'BDDF IT', 'Capitaine Haddock', 'Gestion des crédits', 1);

INSERT INTO uo_entity(

   uo_id,code_uo, code_uo_parent, fonction_rattachement, nom_responsable_uo, nom_usage_uo, site_id)

   VALUES (4,'FFF101', 'FFF1', 'BDDF IT', 'Capitaine Tournesol', 'Gestion des paiements', 1);

-- Table des collaborateurs

INSERT INTO collaborateur_entity(

   nom, numero_ligne, prenom, uid, uo_id)

   VALUES ('KAMDEM', '0101010101', 'Léopold', '100200', 3);

INSERT INTO collaborateur_entity(

   nom, numero_ligne, prenom, uid, uo_id)

   VALUES ('VIVIER', '0202020202', 'Damien', '200300', 4);

INSERT INTO collaborateur_entity(

   nom, numero_ligne, prenom, uid, uo_id)

   VALUES ('DUBOIS', '0303030303', 'Marie', '208656', 1);

   INSERT INTO collaborateur_entity(
      nom, numero_ligne, prenom, uid, uo_id)
      VALUES ('DUPOND', '0404040404', 'François', '123456', 1);
   INSERT INTO collaborateur_entity(
      nom, numero_ligne, prenom, uid, uo_id)
      VALUES ('LAGAFF', '0505050505', 'Gaston', '300400', 1);

INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (900, 'nom1', '1234567890', 'prenom1', '111111', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (901, 'nom2', '2345678901', 'prenom2', '222222', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (902, 'nom3', '3456789012', 'prenom3', '333333', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (903, 'nom4', '4567890123', 'prenom4', '444444', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (904, 'nom5', '5678901234', 'prenom5', '555555', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (905, 'nom6', '6789012345', 'prenom6', '666666', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (906, 'nom7', '7890123456', 'prenom7', '777777', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (907, 'nom8', '8901234567', 'prenom8', '888888', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (908, 'nom9', '9012345678', 'prenom9', '999999', 1);
INSERT INTO collaborateur_entity(collaborateur_id, nom, numero_ligne, prenom, uid, uo_id)
   VALUES (909, 'nom0', '0123456789', 'prenom0', '000000', 1);

-- Table Affectation
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire1', '2020-08-01', '2022-08-01', 1, 900, 800);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire2', '2020-08-02', '2022-08-02', 2, 901, 801);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire3', '2020-08-03', '2022-08-03', 3, 902, 802);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire4', '2020-08-04', '2022-08-04', 4, 903, 803);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire5', '2020-08-05', '2022-08-05', 5, 904, 804);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire6', '2020-08-06', '2022-08-06', 6, 905, 805);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire7', '2020-08-07', '2022-08-07', 7, 906, 806);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire8', '2020-08-08', '2022-08-08', 8, 907, 807);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire9', '2020-08-09', '2022-08-09', 9, 908, 808);
INSERT INTO affectation_entity(
	commentaire, date_affectation, date_renouvellement_prevue, numero_affectation, collaborateur_id, iphone_id)
	VALUES ( 'commentaire0', '2020-08-10', '2022-08-10', 10, 909, 809);

--Table Utilisateur

INSERT INTO utilisateur_entity(

   login, password, uid, user_role)

   VALUES ('admin', '$2a$10$ZiJkoU7aFquTyUm5QEF5suvJEn41zQjjPLeILaPzKcFzrsrNqoVea', '208656', 'ROLE_ADMIN');

INSERT INTO utilisateur_entity(

   login, password, uid, user_role)

   VALUES ('user1', '$2a$10$ix2v00b5v0E.Ro3ZM0/Vv.cK704O4N1w/.yQeNq46KIVKmDanaHBi', '100200', 'ROLE_TYPE1');

INSERT INTO utilisateur_entity(

   login, password, uid, user_role)

   VALUES ('user2', '$2a$10$ix2v00b5v0E.Ro3ZM0/Vv.cK704O4N1w/.yQeNq46KIVKmDanaHBi', '200300', 'ROLE_TYPE2');

