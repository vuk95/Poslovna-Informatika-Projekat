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
-- INSERT INTO pravna_lica (poreski_identifikacioni_broj,adresa,email,fax,naziv,telefon,mesto,odgovorno_lice) VALUES
-- ('1234354','Marsala Tita 12','dssd@gmail.com','53534','SanRemoBar','1234432','Vrbas','Pera Peric');
-- INSERT INTO pravna_lica (poreski_identifikacioni_broj,adresa,email,fax,naziv,telefon,mesto,odgovorno_lice) VALUES
-- ('654676876','Sava Kovacevic 25','sk@gmail.com','6554','Capri','897978','Vrbas','Jovan Jovanovic');