drop table if exists macchina;
drop table if exists caratteristica;

create table macchina (
	ser		serial	primary key,
	nome	name	not null
);

create table caratteristica (
	nome				name	,
	macchina			serial
		references	macchina (ser)
		on delete	cascade
		on update	cascade,
	limite_min			numeric	not null,
	limite_max			numeric not null,
	media				numeric not null,
	adattamento			boolean	not null,
	ampiezza_campione	numeric	,
	
	primary key (nome, macchina),
	constraint chk_intervallo 	check (limite_min <= limite_max),
	constraint chk_ampiezza 	check (ampiezza_campione >= 0)
);

insert into macchina (nome) values
('Macchina a vapore');

insert into caratteristica (nome, macchina, limite_min, limite_max, media, adattamento, ampiezza_campione) values
('Frequenza di oscillazione pistone', 1, 10, 10000, 500, true, 0);