
/* oppgave 1 */
select  * from borettslag where borettslag.etabl_aar >= 1975 AND borettslag.etabl_aar<= 1985

/* oppgave 2 */
SELECT andelseier.fornavn, andelseier.etternavn, andelseier.ansiennitet as 'Ansiennitet År'
FROM andelseier ORDER BY ansiennitet DESC

/* oppgave 3 */
select bolag_navn, etabl_aar
from borettslag having etabl_aar = (SELECT MIN(etabl_aar) as 'eldste borettslag' from borettslag)

/* Oppgave 4 */
SELECT bolag_navn, bygn_adr, ant_rom from bygning natural join leilighet having ant_rom>=3

/* Oppgave 5 */
SELECT COUNT(bolag_navn= 'Tertitten') as 'Ant bygg Teritten' FROM borettslag NATURAL JOIN bygning

/* Oppgave 6 */
SELECT borettslag.bolag_navn, count(bygn_id) as ant_bygninger
from borettslag left outer join bygning on borettslag.bolag_navn= bygning.bolag_navn
group by borettslag.bolag_navn order by bygning.bolag_navn

/* Oppgave 7 */
SELECT borettslag.bolag_navn, count(leil_nr) as ant_leil
FROM borettslag left join bygning on borettslag.bolag_navn = bygning.bolag_navn
  left join leilighet on bygning.bygn_id = leilighet.bygn_id group by borettslag.bolag_navn
having  borettslag.bolag_navn = 'Tertitten'

/* Oppgave 8 */
SELECT borettslag.bolag_navn, max(ant_etasjer) as Max_antall_etasjer
FROM borettslag left join bygning on borettslag.bolag_navn = bygning.bolag_navn
  left join leilighet on bygning.bygn_id = leilighet.bygn_id group by borettslag.bolag_navn
having borettslag.bolag_navn= 'Tertitten'

/* Oppgave 9 */
select fornavn, etternavn, telefon, leil_nr from andelseier
  left join leilighet on andelseier.and_eier_nr = leilighet.and_eier_nr where leil_nr is null

/* Oppgave 10 */
SELECT borettslag.bolag_navn, count(and_eier_nr) as ant_andelseiere
FROM borettslag left join andelseier on borettslag.bolag_navn = andelseier.bolag_navn
group by borettslag.bolag_navn order by ant_andelseiere desc

/* Oppgave 11 */
SELECT borettslag.bolag_navn, fornavn, etternavn, leil_nr
from borettslag left join andelseier on borettslag.bolag_navn = andelseier.bolag_navn
  left join leilighet on andelseier.and_eier_nr = leilighet.and_eier_nr where fornavn is not null

/* Oppgave 12 */
SELECT * FROM borettslag left join bygning on borettslag.bolag_navn = bygning.bolag_navn
  left join leilighet on bygning.bygn_id = leilighet.bygn_id having ant_rom=4
/* ingen */


/* Oppgave 13 */
select borettslag.bolag_navn, poststed.postnr, poststed, count(poststed.postnr) as ant_andelseiere
from borettslag join andelseier on borettslag.bolag_navn=andelseier.bolag_navn
  join poststed on borettslag.postnr = poststed.postnr
  left join leilighet on andelseier.and_eier_nr = leilighet.and_eier_nr
where leilighet.and_eier_nr is not null
group by borettslag.bolag_navn
