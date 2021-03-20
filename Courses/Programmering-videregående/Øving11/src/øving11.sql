/* Oppgave 1a */
select * from levinfo natural join ordrehode natural join ordredetalj where levnr = 44

/* Oppgave 1b*/
select navn, levby, delnr from levinfo natural join delinfo where delnr=1

/* Oppgave 1c */
select levnr, navn, pris from levinfo natural join delinfo natural join prisinfo where delnr= 201 and pris= (select min(pris) from levinfo natural join delinfo natural join prisinfo where delnr=201)

/* Oppgave 1d */
select ordrenr, dato, delnr, beskrivelse, kvantum, pris as enhets_pris, (pris*kvantum) as beregnet_belop from ordrehode natural join ordredetalj natural join delinfo natural join prisinfo where ordrenr=16

/* Oppgave 1e */
select delnr, levnr, pris from levinfo natural join delinfo natural join prisinfo
where pris> (select pris from levinfo natural join delinfo natural join prisinfo where katalognr='X7770')

/* Oppgave 1f i */
create table fylketilhørighet(
    levby VARCHAR(40) not null,
    fylke VARCHAR(40) not null,
    primary key (levby),

);

alter table fylketilhørighet (
  add foreign key (levby) references (levinfo2.levby)
  );
insert ignore into fylketilhørighet (fylketilhørighet.levby, fylketilhørighet.fylke) select levinfo.levby, levinfo.fylke from levinfo


CREATE TABLE levinfo2(
                       levnr   INTEGER,
                       navn    VARCHAR(20) NOT NULL,
                       adresse VARCHAR(20) NOT NULL,
                       levby   VARCHAR(20) NOT NULL,
                       postnr  INTEGER NOT NULL,
                       primary key (levnr));

alter table levinfo2
  add foreign key (levby) references fylketilhørighet(levby
    );



insert into levinfo2(levinfo2.levnr, levinfo2.navn, levinfo2.adresse, levinfo2.levby, levinfo2.postnr)
select levinfo.levnr, levinfo.navn, levinfo.adresse, levinfo.levby, levinfo.postnr from levinfo



/* Oppgave 1f ii */
  create view virtuell as select levnr,navn,adresse,levinfo2.levby,postnr,fylke from levinfo2 inner join fylketilhørighet on levinfo2.levby = fylketilhørighet.levby
  -- select * from virtuell ikke del av oppgaven men henter ut tabellen --

/* Oppgave 1g */
select * from levinfo2 left join prisinfo on levinfo2.levnr= prisinfo.levnr where pris is null
    and levby not in (select levby from levinfo2 inner join prisinfo on levinfo2.levnr= prisinfo.levnr)


/* Oppgave 1h */
-- Finner først ut hvilke delnr det er snakk om --
select * from ordredetalj where ordrenr=18
-- Lager view med lev som kan levere delnr 3 og 4 --
create view lev_ordre_18 as select * from prisinfo where delnr=3 or delnr=4

-- Velger ut hvilke leverandører fra view som kan levere alt --
select delnr, levnr, pris from lev_ordre_18 where delnr=3 and levnr=6 or delnr=4 and levnr=6 or delnr=3 and levnr=82 or delnr=4 and levnr=82

-- lager nytt view  ut ifra leverandører som kan levere alt--
create view ordre_18 as select delnr, levnr, pris from lev_ordre_18 where delnr=3 and levnr=6 or delnr=4 and levnr=6 or delnr=3 and levnr=82 or delnr=4 and levnr=82

-- Finner kvantum til delnr --
select delnr, kvantum from ordredetalj where ordrenr=18

-- Lager view til kvantum per delnr --
create view ordre_18_kvantum as select delnr, kvantum from ordredetalj where ordrenr=18

-- Får ut sum per leverandør --
select levnr, sum(subtotal) as total from
(select ordre_18.delnr, levnr, pris, kvantum, pris*kvantum as subtotal from ordre_18 join ordre_18_kvantum on ordre_18.delnr=ordre_18_kvantum.delnr)
  as tabell group by levnr

/* Oppgave 2a */
select * from forlag where telefon>30000000 union (select * from forlag where telefon<30000000) union select * from forlag
select * from forlag where telefon like '%2%' union (select * from forlag where telefon<30000000) union select * from forlag

/* Oppgave 2b */
-- Finner alle det gjelder --
  (select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar, avg(dod_aar-fode_aar) as gj_alder from forfatter where forfatter.dod_aar is not null and forfatter.fode_aar>1900 or forfatter.dod_aar is not null group by forfatter_id)
union (select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar,(year(current_date)-forfatter.fode_aar) as gj_alder from forfatter where forfatter.dod_aar is null and forfatter.fode_aar>1900)

-- Gjennomsittsalder og diverse --
select avg(gj_alder) as gjn_alder from ((select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar, avg(dod_aar-fode_aar) as gj_alder from forfatter where forfatter.dod_aar is not null and forfatter.fode_aar>1900 or forfatter.dod_aar is not null group by forfatter_id)
                                        union (select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar,(year(current_date)-forfatter.fode_aar) as gj_alder from forfatter where forfatter.dod_aar is null and forfatter.fode_aar>1900)) as alder


/* oppgave 2c*/
select count(gjn_alder) as ant,avg(gjn_alder) as gj_alder from (

                                                                 (select  avg(gj_alder) as gjn_alder from ((select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar, avg(dod_aar-fode_aar) as gj_alder from forfatter where forfatter.dod_aar is not null and forfatter.fode_aar>1900 or forfatter.dod_aar is not null group by forfatter_id)
                                                                                                           union (select forfatter.forfatter_id, forfatter.fornavn, forfatter.etternavn, forfatter.fode_aar, forfatter.dod_aar,(year(current_date)-forfatter.fode_aar) as gj_alder from forfatter where forfatter.dod_aar is null and forfatter.fode_aar>1900)) as alder group by forfatter_id)) as subquery
