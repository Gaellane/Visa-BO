

CREATE TABLE etat_civil(
   id SERIAL,
   nom VARCHAR(50) NOT NULL,
   prenom VARCHAR(50) NOT NULL,
   nom_jeune_fille VARCHAR(50),
   adresse VARCHAR(50),
   contact VARCHAR(50),
   profession VARCHAR(50),
   nationalite VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE passeport(
   id SERIAL,
   numero INTEGER,
   delivrance DATE,
   expiration DATE,
   id_civil INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_civil) REFERENCES etat_civil(id)
);

CREATE TABLE visa_type(
   id SERIAL,
   valeur VARCHAR(50),
   PRIMARY KEY(id)
);

CREATE TABLE visa(
   id SERIAL,
   reference VARCHAR(50),
   date_entree DATE NOT NULL,
   date_expiration DATE NOT NULL,
   lieu_entree VARCHAR(50),
   id_visa_transformable INTEGER,
   id_type INTEGER NOT NULL,
   id_passport INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_visa_transformable) REFERENCES visa(id),
   FOREIGN KEY(id_type) REFERENCES visa_type(id),
   FOREIGN KEY(id_passport) REFERENCES passeport(id)
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

CREATE TABLE demande(
   id SERIAL,
   date_demande DATE NOT NULL,
   id_type INTEGER,
   id_type_visa INTEGER,
   id_status INTEGER NOT NULL,
   id_visa INTEGER NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(id_type) REFERENCES demande_type(id),
   FOREIGN KEY(id_type_visa) REFERENCES visa_type(id),
   FOREIGN KEY(id_status) REFERENCES demande_status(id),
   FOREIGN KEY(id_visa) REFERENCES visa(id)
);

CREATE TABLE demande_history(
   id SERIAL,
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
