-- 1b --

kandidat(k_id, fnavn, enavn, tlf, epost)
bedrift(org_nr, bed_navn, bed_tlf, bed_epost)
oppdrag(Opp_nr, org_nr, kvalif_id, st_dato, sl_dato, k_id, ant_timer)
kvalifikasjon(kvalif_id, kvalif_type)
kandidat_kval(k_id, kvalif_id)
vikartjenesten(org_nr, opp_nr, bed_navn, kvalif_id, f_st_dato, f_sl_dato)


-- 1c --
create table kandidater(
  k_id INTEGER auto_increment not null,
  fnavn varchar(50) not null,
  enavn varchar(50) not null,
  tlf integer (15) not null,
  epost varchar(50) not null,
  primary key (k_id)
);

create table bedrifter(
  org_nr integer(25) not null,
  bed_navn varchar(50) not null,
  tlf integer (15) not null,
  epost varchar(50) not null,
  primary key (org_nr)
);

create table kvalifikasjoner(
  kvalif_id integer auto_increment not null,
  kvalif_type varchar(30) not null,
  primary key (kvalif_id)
);

create table oppdrag(
  opp_nr INTEGER auto_increment not null,
  org_nr Integer not null,
  kvalif_id Integer not null,
  st_dato date default null,
  sl_dato date default null,
  f_st_dato date not null,
  f_sl_dato date not null,
  primary key (opp_nr),
  foreign key(org_nr) references bedrifter(org_nr),
  foreign key(kvalif_id) references kvalifikasjoner(kvalif_id)
);

create table kandidat_kval(
  k_id integer not null,
  kvalif_id integer not null,
  primary key (k_id, kvalif_id),
  foreign key(k_id) references kandidater(k_id),
  foreign key (kvalif_id) references kvalifikasjoner(kvalif_id)
);



-- 1c --

insert into kandidater values(default, "Eric", "Younger",97885945, "fotografyounger@gmail.com");
insert into kandidater values(default, "Rebekka Oma", "Gimnes",93533445, "asafa@gmail.com");
insert into kandidater values(default, "Henning", "Hansen",99532415, "henning@gmail.com");
insert into kandidater values(default, "Mats", "Hansen",95252415, "mats@gmail.com");

insert into bedrifter values(9050100, "Aker Solutions", 51525131, "aker@solutions.com");
insert into bedrifter values(9050603, "NTNU", 51426221, "learn@ntnu.no");
insert into bedrifter values(9050323, "Bouvet", 51476312, "code@bouvet.no");


insert into kvalifikasjoner values (default, "Bueskyting");
insert into kvalifikasjoner values (default, "Langrenn");
insert into kvalifikasjoner values (default, "Programmering");

insert into oppdrag values (default, 9050100, 1, default, default, '2019-04-12', '2020-04-12');
insert into oppdrag values (default, 9050323, 2, default, default, '2019-02-11', '2030-02-11');
insert into oppdrag values (default, 9050603, 3, default, default, '2019-01-01', '2021-01-01');

insert into kandidat_kval values(1, 2);
insert into kandidat_kval values(2,3);
insert into kandidat_kval values (3,1);



-- 1d --

-- 1 --
select bed_navn, tlf, epost from bedrifter;
-- 2 --
select opp_nr, bed_navn, tlf from oppdrag inner join bedrifter b on oppdrag.org_nr = b.org_nr;
-- 3 --
select k.k_id, fnavn, enavn, k2.kvalif_id, kvalif_type from kandidat_kval
inner join kandidater k on kandidat_kval.k_id = k.k_id inner join kvalifikasjoner k2 on kandidat_kval.kvalif_id = k2.kvalif_id;
-- 4 --
select kandidater.k_id, fnavn, enavn, k.kvalif_id, kvalif_type from kandidater left outer join kandidat_kval kk on kandidater.k_id = kk.k_id left join kvalifikasjoner k on kk.kvalif_id = k.kvalif_id;
-- 5 --
select fnavn, enavn, st_dato, sl_dato, opp_nr, bed_navn from oppdrag inner join kandidat_kval on kandidat_kval.kvalif_id=oppdrag.kvalif_id inner join kandidater k on kandidat_kval.k_id = k.k_id inner join bedrifter b on oppdrag.org_nr = b.org_nr where k.k_id=1;