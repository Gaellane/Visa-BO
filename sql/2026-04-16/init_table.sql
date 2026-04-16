CREATE TABLE visa_type(
   id SERIAL,
   valeur VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE demande_status(
   id INTEGER,
   valeur VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE demande_type(
   id SERIAL,
   valeur VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE piece_justificative(
   id SERIAL,
   nom_piece VARCHAR(50),
   id_type_visa INTEGER,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type_visa) REFERENCES visa_type(id)
);

CREATE TABLE nationalite(
   id SERIAL,
   valeur VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE status_marital(
   id SERIAL,
   valeur VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE genre(
   id SERIAL,
   valeur VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE demandeur(
   id SERIAL,
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50),
   nom_jeune_fille VARCHAR(50),
   adresse VARCHAR(50) NOT NULL,
   mail VARCHAR(50),
   date_naissance DATE NOT NULL,
   tel VARCHAR(50) NOT NULL,
   id_genre INTEGER NOT NULL,
   id_status_marital INTEGER NOT NULL,
   id_nationalite INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_genre) REFERENCES genre(id),
   FOREIGN KEY(id_status_marital) REFERENCES status_marital(id),
   FOREIGN KEY(id_nationalite) REFERENCES nationalite(id)
);

CREATE TABLE passeport(
   id SERIAL,
   numero INTEGER,
   delivrance DATE,
   expiration DATE,
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id)
);

CREATE TABLE visa(
   id SERIAL,
   reference VARCHAR(50),
   date_entree DATE NOT NULL,
   date_expiration DATE NOT NULL,
   lieu_entree VARCHAR(50),
   id_passport INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_passport) REFERENCES passeport(id)
);

CREATE TABLE demande(
   id SERIAL,
   date_demande DATE NOT NULL,
   id_demandeur INTEGER NOT NULL,
   id_type INTEGER,
   id_type_visa INTEGER,
   id_status INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id),
   FOREIGN KEY(id_type) REFERENCES demande_type(id),
   FOREIGN KEY(id_type_visa) REFERENCES visa_type(id),
   FOREIGN KEY(id_status) REFERENCES demande_status(id)
);

CREATE TABLE demande_history(
   id SERIAL,
   motif VARCHAR(50),
   date_changement TIMESTAMP NOT NULL,
   id_demande INTEGER NOT NULL,
   id_status INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demande) REFERENCES demande(id),
   FOREIGN KEY(id_status) REFERENCES demande_status(id)
);

CREATE TABLE demande_piece(
   id SERIAL,
   id_demande INTEGER NOT NULL,
   id_piece INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demande) REFERENCES demande(id),
   FOREIGN KEY(id_piece) REFERENCES piece_justificative(id)
);

CREATE TABLE visa_transformable(
   id SERIAL,
   date_entree DATE,
   lieu VARCHAR(50),
   expiration VARCHAR(50),
   id_demandeur INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demandeur) REFERENCES demandeur(id)
);

CREATE TABLE carte_resident(
   id SERIAL,
   reference VARCHAR(50),
   date_entree DATE NOT NULL,
   date_expiration DATE,
   lieu_entree VARCHAR(50),
   id_demande INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_demande) REFERENCES demande(id)
);
