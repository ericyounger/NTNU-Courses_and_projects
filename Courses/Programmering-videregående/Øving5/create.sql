
--Lage tabeller med primærnøkkel
CREATE TABLE borettslag
(
    borettslag_id INTEGER AUTO_INCREMENT,
    navn VARCHAR(50) NOT NULL,
    adresse VARCHAR(50) NOT NULL,
    antall_enheter INTEGER,
    etabaar INTEGER NOT NULL,
    CONSTRAINT borettslag_pk PRIMARY KEY(borettslag_id)
);

CREATE TABLE bygninger
(
    ant_leilig INTEGER NOT NULL,
    ant_etasjer INTEGER NOT NULL,
    borettslag_id INTEGER NOT NULL,
    bygnings_id INTEGER AUTO_INCREMENT,
    CONSTRAINT bygninger_pk PRIMARY KEY(bygnings_id)
);

CREATE TABLE leiligheter
(
    leilig_id INTEGER AUTO_INCREMENT,
    ant_rom INTEGER NOT NULL,
    ant_kvm INTEGER NOT NULL,
    etasje INTEGER NOT NULL,
    leilig_nr INTEGER NOT NULL,
    bygnings_id INTEGER NOT NULL,
    CONSTRAINT leiligheter_pk PRIMARY KEY(leilig_id)
);

CREATE TABLE andelseiere
(
    navn VARCHAR(50),
    leilig_id INTEGER NOT NULL,
    eier_id INTEGER AUTO_INCREMENT,
    CONSTRAINT andelseiere_pk PRIMARY KEY(eier_id)    
);


--Legg til fremmednøkkel
ALTER TABLE bygninger
ADD CONSTRAINT bygninger_fk FOREIGN KEY(borettslag_id) REFERENCES borettslag(borettslag_id);

ALTER TABLE leiligheter
ADD CONSTRAINT leiligheter_fk FOREIGN KEY(bygnings_id) REFERENCES bygninger(bygnings_id);

ALTER TABLE andelseiere
ADD CONSTRAINT andelseiere_fk FOREIGN KEY(leilig_id) REFERENCES leiligheter(leilig_id);



-- INSERT SETNINGER

--Borettslag
INSERT INTO borettslag VALUES(null, 'Akrinn', 'Sjøveien 12', 10, 1985);
INSERT INTO borettslag VALUES(null, 'Gløshaugen', 'Vegmesterstien 8a', 12, 1960);
INSERT INTO borettslag VALUES(null, 'Dragvoll', 'Randabergveien 1', 7, 1987);
INSERT INTO borettslag VALUES(null, 'Øya', 'Testveien 98', 15, 1990);

--Bygningner
INSERT INTO bygninger VALUES(8, 2, 1, null);
INSERT INTO bygninger VALUES(10, 3, 2, null);
INSERT INTO bygninger VALUES(12, 3, 2,null);

--Leiligheter
INSERT INTO leiligheter VALUES(null, 3, 80, 1, 1, 1);
INSERT INTO leiligheter VALUES(null, 2, 50, 2, 7, 2);
INSERT INTO leiligheter VALUES(null, 3, 60, 1, 3, 1);

--//Andelseiere
INSERT INTO andelseiere VALUES('Eric Younger', 2, null);
INSERT INTO andelseiere VALUES('Rebekka Oma Gimnes', 3, null);
INSERT INTO andelseiere VALUES('Henning Hansen', 4, null);

--INSERT setninger med brudd på referanse og entitets integriteten
INSERT INTO andelseiere VALUES('Test', null, null);
--MySQL said:
--#1048 - Column 'leilig_id' cannot be null
--ENTITETS INTEGRITETSFEIL


INSERT INTO andelseiere VALUES('Test2', 9, null);
--MySQL said:
--#1452 - Cannot add or update a child row: a foreign key constraint fails 
--(`ericy`.`andelseiere`, CONSTRAINT `andelseiere_fk` FOREIGN KEY
--(`leilig_id`) REFERENCES `leiligheter`(`leilig_id`))
--REFERANSE INTEGRITETSFEIL