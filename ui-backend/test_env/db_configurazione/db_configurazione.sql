drop table if exists macchina;
drop table if exists caratteristica;

create table macchina (
	id		serial	primary key,
	nome	name	not null
);

create table caratteristica (
	codice				name	,
	nome				name	not null unique,
	macchina			integer
		references	macchina (id)
		on delete	cascade
		on update	cascade,
	limite_min			numeric	not null,
	limite_max			numeric not null,
	media				numeric not null,
	adattamento			boolean	not null,
	ampiezza_campione	numeric	,
	
	primary key (codice, macchina),
	constraint chk_intervallo 	check (limite_min <= limite_max),
	constraint chk_ampiezza 	check (ampiezza_campione >= 0)
);

create user backend	password 'backend';
grant select, insert, update, delete	on all tables 		in schema public	to backend;
grant usage, select						on all sequences 	in schema public 	to backend;

create user api		password 'api';
grant select							on all tables 		in schema public	to api;