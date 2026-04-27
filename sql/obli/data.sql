-- Types de visa
INSERT INTO visa_type (valeur)
SELECT 'Investisseur'
WHERE NOT EXISTS (
	SELECT 1 FROM visa_type WHERE valeur = 'Investisseur'
);

INSERT INTO visa_type (valeur)
SELECT 'Travailleur'
WHERE NOT EXISTS (
	SELECT 1 FROM visa_type WHERE valeur = 'Travailleur'
);

-- Pieces communes (id_type_visa vide)
INSERT INTO piece_justificative (nom_piece, id_type_visa)
VALUES
	('02 photos d''identite', NULL),
	('Notice de renseignement', NULL),
	('Demande adressee au Ministere de l''Interieur et de la Decentralisation avec e-mail et numero de telephone portable', NULL),
	('Photocopie certifiee du visa en cours de validite', NULL),
	('Photocopie certifiee de la premiere page du passeport', NULL),
	('Photocopie certifiee de la carte resident en cours de validite', NULL),
	('Certificat de residence a Madagascar', NULL),
	('Extrait de casier judiciaire de moins de 3 mois', NULL);

-- Pieces pour Investisseur
INSERT INTO piece_justificative (nom_piece, id_type_visa)
VALUES
	(
		'Statut de la societe',
		(SELECT id FROM visa_type WHERE valeur = 'Investisseur' ORDER BY id LIMIT 1)
	),
	(
		'Extrait d''inscription au registre de commerce',
		(SELECT id FROM visa_type WHERE valeur = 'Investisseur' ORDER BY id LIMIT 1)
	),
	(
		'Carte fiscale',
		(SELECT id FROM visa_type WHERE valeur = 'Investisseur' ORDER BY id LIMIT 1)
	);

-- Pieces pour Travailleur
INSERT INTO piece_justificative (nom_piece, id_type_visa)
VALUES
	(
		'Autorisation d''emploi delivree a Madagascar par le Ministere de la Fonction publique',
		(SELECT id FROM visa_type WHERE valeur = 'Travailleur' ORDER BY id LIMIT 1)
	),
	(
		'Attestation d''emploi delivree par l''employeur (Original)',
		(SELECT id FROM visa_type WHERE valeur = 'Travailleur' ORDER BY id LIMIT 1)
	);

-- Genres
INSERT INTO genre (valeur)
SELECT 'Homme'
WHERE NOT EXISTS (
	SELECT 1 FROM genre WHERE valeur = 'Homme'
);

INSERT INTO genre (valeur)
SELECT 'Femme'
WHERE NOT EXISTS (
	SELECT 1 FROM genre WHERE valeur = 'Femme'
);

-- Status marital
INSERT INTO status_marital (valeur)
SELECT 'Celibataire'
WHERE NOT EXISTS (
	SELECT 1 FROM status_marital WHERE valeur = 'Celibataire'
);

INSERT INTO status_marital (valeur)
SELECT 'Marie(e)'
WHERE NOT EXISTS (
	SELECT 1 FROM status_marital WHERE valeur = 'Marie(e)'
);

INSERT INTO status_marital (valeur)
SELECT 'Divorce(e)'
WHERE NOT EXISTS (
	SELECT 1 FROM status_marital WHERE valeur = 'Divorce(e)'
);

INSERT INTO status_marital (valeur)
SELECT 'Veuf(ve)'
WHERE NOT EXISTS (
	SELECT 1 FROM status_marital WHERE valeur = 'Veuf(ve)'
);

-- Nationalites
INSERT INTO nationalite (valeur)
SELECT 'Malagasy'
WHERE NOT EXISTS (
	SELECT 1 FROM nationalite WHERE valeur = 'Malagasy'
);

INSERT INTO nationalite (valeur)
SELECT 'Francaise'
WHERE NOT EXISTS (
	SELECT 1 FROM nationalite WHERE valeur = 'Francaise'
);

INSERT INTO nationalite (valeur)
SELECT 'Comorienne'
WHERE NOT EXISTS (
	SELECT 1 FROM nationalite WHERE valeur = 'Comorienne'
);

INSERT INTO nationalite (valeur)
SELECT 'Mauricienne'
WHERE NOT EXISTS (
	SELECT 1 FROM nationalite WHERE valeur = 'Mauricienne'
);

-- Types de demande
INSERT INTO demande_type (valeur)
SELECT 'Nouvelle demande'
WHERE NOT EXISTS (
	SELECT 1 FROM demande_type WHERE valeur = 'Nouvelle demande'
);

INSERT INTO demande_type (valeur)
SELECT 'Duplicata'
WHERE NOT EXISTS (
    SELECT 1 FROM demande_type WHERE valeur = 'Duplicata'
);

INSERT INTO demande_type (valeur)
SELECT 'Transfert'
WHERE NOT EXISTS (
    SELECT 1 FROM demande_type WHERE valeur = 'Transfert'
);


-- Status de demande
INSERT INTO demande_status (id, valeur)
SELECT 1, 'Dossier cree'
WHERE NOT EXISTS (
	SELECT 1 FROM demande_status WHERE id = 1
);

INSERT INTO demande_status (id, valeur)
SELECT 2, 'En cours d''instruction'
WHERE NOT EXISTS (
	SELECT 1 FROM demande_status WHERE id = 2
);

INSERT INTO demande_status (id, valeur)
SELECT 3, 'Validee'
WHERE NOT EXISTS (
	SELECT 1 FROM demande_status WHERE id = 3
);

INSERT INTO demande_status (id, valeur)
SELECT 4, 'Rejetee'
WHERE NOT EXISTS (
	SELECT 1 FROM demande_status WHERE id = 4
);

