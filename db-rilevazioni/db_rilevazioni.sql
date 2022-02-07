drop table if exists rilevazione;

create table rilevazione (
	creazione_utc	bigint				,
	caratteristica	name				,
	macchina		integer				,
	valore			double precision	not null,
	anomalo			boolean				not null default false,
	
	primary key		(creazione_utc, caratteristica, macchina),
	constraint 		chk_creazione	check (creazione_utc >= 0)
);

select create_hypertable('rilevazione', 'creazione_utc', chunk_time_interval => 100000);

create user backend		password 'backend';
grant select			on all tables in schema public	to backend;

create user api			password 'api';
grant select, insert	on all tables in schema public	to api;