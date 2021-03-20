

select p.prosj_id, 'timer', kunde,sum(timefaktor*timelønn*ant_timer) as sum_kostnad from prosjekt inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id inner join ansatt a on p.ans_id = a.ans_id group by p.prosj_id
union
select distinct p.prosj_id, tekst, kunde, beløp from prosjekt inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id inner join prosjektkostnader p2 on prosjekt.prosj_id = p2.prosj_id inner join ansatt a on p.ans_id = a.ans_id where p2.faktura_sendt is null


select * from prosjektkostnader


update prosjektkostnader set faktura_sendt = current_date where faktura_sendt is null

update prosjektkostnader set faktura_sendt = '1008-09-30' where faktura_sendt is not null


select p.prosj_id, 'timer', kunde,sum(timefaktor*timelønn*ant_timer) as sum_kostnad from prosjekt
      inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id
      inner join ansatt a on p.ans_id = a.ans_id
      where faktura_sendt is null
      and
      month(dato)=10 group by p.prosj_id
union
select distinct p.prosj_id, tekst, kunde, beløp from prosjekt
     inner join prosjektarbeid p on prosjekt.prosj_id = p.prosj_id
     inner join prosjektkostnader p2 on prosjekt.prosj_id = p2.prosj_id
     inner join ansatt a on p.ans_id = a.ans_id where month(p.dato)=10 and p2.faktura_sendt is null
