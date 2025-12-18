-- CREATE SCHEMA ADRESSEBOK;
SET search_path TO ADRESSEBOK; -- Apparently creates the schema as well

-- CREATE USER AB_USER WITH PASSWORD 'user123'; -- Should be used by application

CREATE TABLE ADRESSEBOK(
  adressebok_id UUID        PRIMARY KEY,
  opprettet_tid TIMESTAMPTZ  DEFAULT NOW() NOT NULL,
  oppdatert_tid TIMESTAMPTZ  DEFAULT NOW() NOT NULL
);

CREATE TABLE KONTAKT(
  kontakt_id    UUID        PRIMARY KEY,
  opprettet_tid TIMESTAMPTZ  DEFAULT NOW() NOT NULL,
  oppdatert_tid TIMESTAMPTZ  DEFAULT NOW() NOT NULL,
  fornavn       TEXT        NOT NULL,
  etternavn     TEXT        NOT NULL,
  adressebok_id	UUID 		NOT NULL REFERENCES ADRESSEBOK(adressebok_id)
);

CREATE TABLE ADRESSE_TYPE(
  kode          TEXT        PRIMARY KEY,
  beskrivelse   TEXT
);

INSERT INTO ADRESSE_TYPE (kode, beskrivelse) VALUES 
('PRIVAT', 'Privatadresse'),
('JOBB', 'Jobbadresse');

CREATE TABLE ADRESSE(
  adresse_type  TEXT        NOT NULL REFERENCES ADRESSE_TYPE(kode),
  gatenavn      TEXT        NOT NULL,
  gatenummer    TEXT        NOT NULL,
  postnummer    TEXT        NOT NULL,
  sted          TEXT        NOT NULL,
  land          TEXT        NOT NULL,
  kontakt_id    UUID        NOT NULL REFERENCES KONTAKT(kontakt_id)
);

CREATE TABLE EPOST(
  epostadresse  TEXT        NOT NULL,
  kontakt_id    UUID        NOT NULL REFERENCES KONTAKT(kontakt_id)
);

CREATE TABLE TELEFON_TYPE(
  kode          TEXT        PRIMARY KEY,
  beskrivelse   TEXT
);

INSERT INTO TELEFON_TYPE (kode, beskrivelse) VALUES 
('PRIVAT', 'Privat telefon'),
('JOBB', 'Jobb telefon'),
('ANNET', 'Annen type av telefon');

CREATE TABLE TELEFON(
  telefon_type   TEXT        NOT NULL REFERENCES TELEFON_TYPE(kode),
  landskode     TEXT        NOT NULL,  
  telefonnummer TEXT        NOT NULL,
  kontakt_id    UUID        NOT NULL REFERENCES KONTAKT(kontakt_id)
);
