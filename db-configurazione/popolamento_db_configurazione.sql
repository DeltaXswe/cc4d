insert into macchina (nome) values
('Macchina a vapore'),
('Telaio a vapore');

insert into caratteristica (codice, nome, macchina, limite_min, limite_max, media, adattamento, ampiezza_campione) values
('frequenza_di_oscillazione_pistone', 'Frequenza di oscillazione pistone', 1, 100, 300, 200, true, 0),
('temperatura_dell_acqua', "Temperatura dell'acqua", 1, 100, 300, 200, true, 0),
('frequenza_di_produzione_tessuto', 'Frequenza di produzione tessuto', 2, 1, 10, 5, true, 0);