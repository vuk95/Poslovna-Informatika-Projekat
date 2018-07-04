-- BANKE
INSERT INTO banka (poreski_idenifikacioni_broj,adresa,oznaka,email,fax,ime,telefon)
VALUES(101626723,"Bulevar oslobođenja 5","08063818","erste@gmail.com",null,"Erste Bank","0800201201");
INSERT INTO banka (poreski_idenifikacioni_broj,adresa,oznaka,email,fax,ime,telefon)
VALUES(100001159,"Milentija Popovića 7b","07759231","intesa@gmail.com","0113108855","Banca Intesa","0113108888");

-- KORISNICI
INSERT INTO korisnik (email,lozinka, bank_id) VALUES ("erste@gmail.com","password", 1);
INSERT INTO korisnik (email,lozinka, bank_id) VALUES ("intesa@gmail.com","password", 2);

-- FIZICKA LICA
 INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon,tip,bank_id) VALUES ("Marko","Markovic","1234567891234","Novi Sad","Bulevar Oslobodjenja 27","marko@gmail.com","064555333",0, 1);
 INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon,tip,bank_id) VALUES ("Jovan","Jankovic","1234567891234","Beograd","Pariske Komune 11","jovan@gmail.com","069333555",0, 1);
 INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon,tip,bank_id) VALUES ("Sasa","Ilic","0502972683128","Pozarevac","Bulevar Mihajla Pupina 192","sale@gmail.com","0622442244",0, 2);
 INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon,tip,bank_id) VALUES ("Momcilo","Vukotic","0384928652186","Beograd","Cirpanova 18","moca@gmail.com","068045342",0, 2);
 INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon,tip,bank_id) VALUES ("Dragan","Mance","9834285926718","Zemun","Maksima Gorkog 65","dragan@gmail.com","061343545",0, 2);

-- PRAVNA LICA
INSERT INTO pravna_lica (adresa, tip, email, fax, naziv, telefon, poreski_identifikacioni_broj, mesto, odgovorno_lice, bank_id)
VALUES ("Marsala Tita 12",1,"sanremo@gmail.com","02195432","San Remo","0218403", "0834581274", "Vrbas", "Pera Peric", 1);
INSERT INTO pravna_lica (adresa, tip, email, fax, naziv, telefon, poreski_identifikacioni_broj, mesto, odgovorno_lice, bank_id)
VALUES ("Mihajla Pupina 32",1,"galija@gmail.com","02353432","Picerija Galija","0235430", "0367281924", "Zrenjanin", "Leandre Tawamba", 1);
INSERT INTO pravna_lica (adresa, tip, email, fax, naziv, telefon, poreski_identifikacioni_broj, mesto, odgovorno_lice, bank_id)
VALUES ("Humska 1",1,"kkpartizan@gmail.com","0113690395","KK Partizan","0113690664", "0705102643", "Beograd", "Predrag Danilovic", 2);
INSERT INTO pravna_lica (adresa, tip, email, fax, naziv, telefon, poreski_identifikacioni_broj, mesto, odgovorno_lice, bank_id)
VALUES ("Humska 1",1,"partizan@gmail.com","0113690395","Fudbalski Klub Partizan","0113690664", "100268842", "Beograd", "Milos Vazura", 2);

--RACUNI
INSERT INTO racun(broj_racuna, datum_otvaranja,vazeci,raspoloziva_sredstva,bank_id,individual_individual_id) values("123456","2018-07-04",true,"1000",2, 3);
INSERT INTO racun(broj_racuna, datum_otvaranja,vazeci,raspoloziva_sredstva,bank_id,individual_individual_id) values("123457","2018-07-04",true,"100",2, 4);

--DRZAVE
INSERT INTO drzava (oznaka,naziv) values ('SRB','Srbija');
INSERT INTO drzava (oznaka,naziv) values ('EU','Evropska unija');
INSERT INTO drzava (oznaka,naziv) values ('USA','Sjedinjene Americke Drzave');
INSERT INTO drzava (oznaka,naziv) values ('SWI','Svajcarska');
INSERT INTO drzava (oznaka,naziv) values ('AUS','Australija');
INSERT INTO drzava (oznaka,naziv) values ('CAN','Kanada');
INSERT INTO drzava (oznaka,naziv) values ('CRO','Hrvatska');
INSERT INTO drzava (oznaka,naziv) values ('DEN','Danska');
INSERT INTO drzava (oznaka,naziv) values ('HUN','Madjarska');
INSERT INTO drzava (oznaka,naziv) values ('NOR','Norveska');
INSERT INTO drzava (oznaka,naziv) values ('SWE','Svedska');
INSERT INTO drzava (oznaka,naziv) values ('GBR','Velika Britanija');
INSERT INTO drzava (oznaka,naziv) values ('BIH','Bosna i Hercegovina');
INSERT INTO drzava (oznaka,naziv) values ('RUS','Rusija');

--VALUTE
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Evro','EUR','2');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Americki Dolar','USD','3');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Svajcarski Franak','CHF','4');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Australijski Dolar','AUD','5');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Kanadski Dolar','CAD','6');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Hrvatska Kuna','HRK','7');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Danska Kruna','DKK','8');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Madjarska Forinta','HUF','9');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Norveska Kruna','NOK','10');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Svedska Kruna','SEK','11');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Funta Sterlinga','GBP','12');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Konvertibilna Marka','BAM','13');
INSERT INTO valuta (domicilna,naziv,zvanicna_sifra,country_id) values 
(true,'Ruska Rublja','RUB','14');

--KURSNE LISTE
INSERT INTO kursna_lista (primenjuje_se_od,datum,broj_kursne_liste) values
('2018-06-21','2018-06-22','1');

--KURSEVI U VALUTI
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('117.5000','118.1475','118.9000','1','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('117.5000','100.0000','102.1014','2','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('99.7000','102.3897','103.1500','3','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('71.4500','75.1909','76.3500','4','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('73.0000','76.7241','77.9500','5','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('14.7200','15.9964','16.0500','6','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('15.0600','15.8453','15.9000','7','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('0.3355','0.3642','0.3660','8','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('11.8600','12.4783','12.5000','9','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('10.9200','11.4869','11.5000','10','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('130.3500','134.3501','137.7000','11','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('56.8000','60.4079','60.8000','12','1');
INSERT INTO kurs_u_valuti (kupovni,srednji,prodajni,currency_id,exchange_rate_list_id) values
('1.4065','1.6063','1.7300','13','1');

--DRZAVA-VALUTA
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('2','1');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('3','2');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('4','3');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('5','4');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('6','5');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('7','6');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('8','7');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('9','8');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('10','9');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('11','10');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('12','11');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('13','12');
INSERT INTO drzava_currencies (drzava_id,currencies_id) values ('14','13');

--VALUTA - KURS U VALUTI
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('1','1');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('2','2');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('3','3');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('4','4');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('5','5');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('6','6');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('7','7');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('8','8');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('9','9');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('10','10');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('11','11');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('12','12');
INSERT INTO valuta_currency_rates (valuta_id,currency_rates_id) values ('13','13');

--KURSNA LISTA - KURS U VALUTI
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','1');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','2');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','3');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','4');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','5');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','6');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','7');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','8');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','9');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','10');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','11');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','12');
INSERT INTO kursna_lista_currency_rates (kursna_lista_id,currency_rates_id) values ('1','13');
