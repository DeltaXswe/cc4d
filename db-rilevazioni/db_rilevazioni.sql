drop table if exists rilevazione;

create table rilevazione (
	creazione_utc	bigint				primary key,
	caratteristica	name				not null,
	macchina		integer				not null,
	valore			double precision	not null,
	anomalo			boolean				not null default false,

	constraint chk_creazione	check (creazione_utc >= 0)
);

select create_hypertable('rilevazione','creazione_utc');

create user backend		password 'backend';
grant select			on all tables in schema public	to backend;

create user api			password 'api';
grant select, insert	on all tables in schema public	to api;