borettslag(bygningsnavn, antall_blokkenheter, etableringsar)

medlemsregister(andelseiere, medlemmer)

leilighet(antall_rom, antall_kvm, leilighetsnr)

bygninger(antall_leiligheter, antall_etasjer, adresse)



CREATE TABLE borettslag
(
    adresse VARCHAR(200) NOT NULL,
    bygningsnavn VARCHAR(20) NOT NULL,
    antall_blokkenheter INT,
    etableringsar INT, 
    CONSTRAINT borettslag_pk PRIMARY KEY(bygningsnavn)
); 

CREATE TABLE bygninger
(
    antall_leiligheter INT,
    antall_etasjer INT,
    adresse VARCHAR(30) NOT NULL,
    CONSTRAINT bygninger_pk PRIMARY KEY
    (adresse)
);

ALTER TABLE bygninger
ADD CONSTRAINT bygninger_fk1 FOREIGN KEY
(adresse)
REFERENCES borettslag;








CREATE TABLE medlemsregister
(
    andelseiere VARCHAR(30) NOT NULL,
    medlemmer VARCHAR(30) NOT NULL,
);

CREATE TABLE leiligheter
(
    antall_rom INTEGER NOT NULL,
    antall_kvm INTEGER NOT NULL,
    leilighetsnr INTEGER NOT NULL,
    CONSTRAINT leiligheter_pk PRIMARY KEY(leilighetsnr)
);

CREATE TABLE bygninger
(
    antall_leiligheter INT,
    antall_etasjer INT,
    adresse VARCHAR(30) NOT NULL,
    CONSTRAINT bygninger_pk PRIMARY KEY(adresse)
);

ALTER TABLE bygninger
ADD CONSTRAINT bygninger_fk1 FOREIGN KEY
(addresse)
REFERENCES borettslag;

ALTER TABLE borettslag
ADD CONSTRAINT borettslag_fk1 FOREIGN KEY
(adresse)
REFERENCES bygninger;

ALTER TABLE bygninger
ADD CONSTRAINT bygninger_fk1 FOREIGN KEY
(leilighetsnr)
REFERENCES leilighet;
