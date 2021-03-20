--1a

--// Seleksjon //--
SELECT * FROM `bok` WHERE utgitt_aar=1995
--//Projeksjon //--
SELECT tittel, utgitt_aar FROM `bok`

--1b
SELECT *FROM bok, forlag
--//Danner et skjema med alle mulige kombinasjoner, kunne også brukt JOIN som ville gitt samme resultat. Danner det kartesiske produkt. //--

--1c
--//Likhetsforening (equjoin) slår sammen på likhet men har to like attributter//--
SELECT *FROM bok, forlag WHERE bok.forlag_id=forlag.forlag_id

--//Natural join slår sammen og fjerner duplikat attributt.//--
SELECT * FROM bok NATURAL JOIN forlag

--1d
--Union legger sammen resultat fra to tabeller og fjerner duplikatverdier.

--2a
SELECT forlag_navn FROM forlag
--brukte projeksjon.

--2b
SELECT * FROM forlag LEFT OUTER JOIN bok ON bok.forlag_id=forlag.forlag_id WHERE bok.tittel IS NULL;
--LEFT OUTER JOIN på forlag_id  med seleksjon hvor boktittel er null.


--2c
SELECT fornavn, etternavn, fode_aar FROM `forfatter` WHERE fode_aar= 1948
--Brukte projekson for å velge ut fornavn, etternavn og fodeaar og seleksjon med where=fode_aar =1948


--2d
SELECT forlag_navn, adresse, bok.tittel FROM forlag NATURAL JOIN bok HAVING tittel='Generation X'
--Projeksjon for å få ut navn, adresse og tittel, naturlig forening og Har (Having) tittel = Generation X

--2e
SELECT forfatter.etternavn, bok.tittel, bok.utgitt_aar FROM forfatter NATURAL JOIN bok_forfatter NATURAL JOIN bok HAVING forfatter.etternavn='Hamsun'
-- projeksjon på valg av attributter, natural join mellom forfatter, bok_forfatter og bok og seleksjon på forfatter.etternavn = 'Hamsun'

--2f
SELECT bok.tittel, bok.utgitt_aar, forlag.forlag_navn, forlag.adresse, forlag.telefon FROM forlag LEFT JOIN bok ON forlag.forlag_id=bok.forlag_id;
/* Har brukt projeksjon for å finne attributtene som jeg vil ha listet opp, og brukt LEFT OUTER JOIN på bok på verdien forlag_id. */
