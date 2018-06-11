-- BANKE
INSERT INTO banka (poreski_idenifikacioni_broj,adresa,oznaka,email,fax,ime,telefon)
VALUES(101626723,"Bulevar oslobođenja 5","08063818","erste@gmail.com",null,"Erste Bank","0800201201");
INSERT INTO banka (poreski_idenifikacioni_broj,adresa,oznaka,email,fax,ime,telefon)
VALUES(100001159,"Milentija Popovića 7b","07759231","intesa@gmail.com","0113108855","Banca Intesa","0113108888");

-- KORISNICI
INSERT INTO korisnik (email,lozinka, bank_id) VALUES ("erste@gmail.com","password", 1);
INSERT INTO korisnik (email,lozinka, bank_id) VALUES ("intesa@gmail.com","password", 2);

-- FIZICKA LICA
INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon) VALUES ("Marko","Markovic","1234567","Novi Sad","aaa","a@a","123");
INSERT INTO fizicka_lica (ime,prezime,jmbg,mesto,adresa,email,telefon) VALUES ("Marko","Jankovic","1234568","Beograd","aab","a@b","124");

-- PRAVNA LICA
INSERT INTO pravna_lica (poreski_identifikacioni_broj,adresa,email,fax,naziv,telefon,mesto,odgovorno_lice) VALUES
('1234354','Marsala Tita 12','dssd@gmail.com','53534','SanRemoBar','1234432','Vrbas','Pera Peric');
INSERT INTO pravna_lica (poreski_identifikacioni_broj,adresa,email,fax,naziv,telefon,mesto,odgovorno_lice) VALUES
('654676876','Sava Kovacevic 25','sk@gmail.com','6554','Capri','897978','Vrbas','Jovan Jovanovic');