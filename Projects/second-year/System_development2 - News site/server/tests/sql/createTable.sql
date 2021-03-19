drop table if exists comments;
drop table  if exists article;
drop table if exists categories;

create table article
(
	article_id int auto_increment primary key,
	headline varchar(100) not null,
	post_date timestamp default CURRENT_TIMESTAMP null,
	category varchar(25) null,
	picture longtext null,
	priority int null,
	visible tinyint(1) not null,
	content text null,
	author varchar(45) not null,
	likes int default 0 null
);

create table comments
(
	c_id int auto_increment primary key,
	nickname varchar(25) null,
	text_comment longtext null,
	article_id int null,
	constraint comments_article_article_id_fk foreign key (article_id) references article (article_id)
);

create table categories
(
  id int auto_increment primary key,
  category varchar(25) not null
);



