UPDATE demande_status SET valeur = 'Scan termine' WHERE id=2;

UPDATE piece_justificative SET code = 'PHOTO_ID' WHERE nom_piece = '02 photos d''identite';
UPDATE piece_justificative SET code = 'NOTICE_INFO' WHERE nom_piece = 'Notice de renseignement';
UPDATE piece_justificative SET code = 'DEMANDE_MIN' WHERE nom_piece = 'Demande adressee au Ministere de l''Interieur et de la Decentralisation avec e-mail et numero de telephone portable';
UPDATE piece_justificative SET code = 'VISA_VALIDE' WHERE nom_piece = 'Photocopie certifiee du visa en cours de validite';
UPDATE piece_justificative SET code = 'PASSEPORT_PAGE1' WHERE nom_piece = 'Photocopie certifiee de la premiere page du passeport';
UPDATE piece_justificative SET code = 'CARTE_RESIDENT' WHERE nom_piece = 'Photocopie certifiee de la carte resident en cours de validite';
UPDATE piece_justificative SET code = 'CERT_RESIDENCE' WHERE nom_piece = 'Certificat de residence a Madagascar';
UPDATE piece_justificative SET code = 'CASIER_JUD' WHERE nom_piece = 'Extrait de casier judiciaire de moins de 3 mois';
UPDATE piece_justificative SET code = 'STATUT_SOCIETE' WHERE nom_piece = 'Statut de la societe';
UPDATE piece_justificative SET code = 'REG_COMMERCE' WHERE nom_piece = 'Extrait d''inscription au registre de commerce';
UPDATE piece_justificative SET code = 'CARTE_FISCALE' WHERE nom_piece = 'Carte fiscale';
UPDATE piece_justificative SET code = 'AUTORISATION_EMPLOI' WHERE nom_piece = 'Autorisation d''emploi delivree a Madagascar par le Ministere de la Fonction publique';
UPDATE piece_justificative SET code = 'ATTESTATION_EMPLOI' WHERE nom_piece = 'Attestation d''emploi delivree par l''employeur (Original)';