drop table if exists rilevazione;

create table rilevazione (
	creazione_utc	bigint				primary key,
	caratteristica	name				not null,
	macchina		integer				not null,
	valore			double precision	not null,

	constraint chk_creazione	check (creazione_utc >= 0)
);