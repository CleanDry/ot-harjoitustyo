# Arkkitehtuurikuvaus

## Rakenne

## Käyttöliittymä

Käyttöliittymän sisältää kaksi erilaista näkymää
- kirjautuminen ja uuden käyttäjän luominen
- projektien selaus ja käsittely

Jokainen näistä on toteutettu omana Scene-oliona. Näkymistä yksi kerrallaan on näkyvänä eli sijoitettuna sovelluksen stageen.
Kumpikin elementti sisältää muita elementtejä, esimerkiksi kirjautuminen ja uuden käyttäjän luomisnäkymän välillä vaihto tapahtuu näkymän sisäisiä elementtejä manipuloimalla.

Käyttöliittymä on rakennettu luokassa maalausprojektikirjanpito.ui.UserInterface. Osa käyttöliittymän elementeistä on eriytetty omiksi luokiksiin selkeyden vuoksi. 

Käyttöliittymä on pyritty eristämään sovelluslogiikasta. Käyttöliittymä kutsuu ServiceManager-luokan olion metodeja toimintojen toteuttamiseksi.

Projektien tarkasteluun on käytössä TreeView-olio. Projekteja lisättäessä ja käsiteltäessa käytetään näkymäelementtien refresh-metodeja tietojen päivittämiseen.

## Sovelluslogiikka

Sovelluksen rakentuu seuraavan luokkakaavion mukaisista luokista. Sovelluksen tietojen pysyväistallennus tapahtuu kuvan tietokantakaavion mukaiseen tietokantaan.

![Image of the class diagram](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/Ohjelmistotekniikan%20harjoitysty%C3%B6n%20luokkakaavioluonnos.jpg)

Toiminnallisista kokonaisuuksista vastaa luokan ServiceManager-olio UserInterface-luokassa. Luokka tarjoaa käyttöliittymälle kaikki sen tarvitsemat palvelut, kuten:
- boolean login(String username, String password)
- createUser(String username, String password)
- ArrayList<PaintProject> returnUserProjects()

ManagerService pääsee käsiksi käyttäjiin ja projekteihin tietojen tallenuksesta vastaavien pkkauksessa maalausprojektikirjanpito.dao sijaitsevien luokkien kautta, kuten PaintProjectDao ja SurfaceDao. Kaikki Dao:t toteuttavat geneerisen Dao-rajapinnan.

ManagerServicen ja ohjelman muiden osien suhdetta kuvaava luokka ja pakkauskaavio:

![Image of the package diagram](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/package%20diagram.jpg)

## Tietojen pysyväistallennus

Pakkauksen maalausprojektikirjanpito.dao Dao-luokat vastaavat tietojen tallentamisesta tietokantatiedostoon.

Luokat noudattavat Data Access Object -suunnittelumallia ja toteuttavat geneerisen DAO-rajapinnan, joten ne voidaan tarvittaessa korvata uusilla toteutuksilla.

Käytetyn tietokannan kaavio löytyy aiemmin olleesta luokka- ja tietokantakaavioista.

![Image of the sequence diagram](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/Initial%20sequence%20diagram.jpg)

## Ohjelman rakenteeseen jääneet heikkoudet
Tietokantasyötteet ovat lähes täysin sanitoitu, mutta sanitointisääntöja pitäisi järkevöittää ja keskittää vaikkapa Utilities-luokkaan.
Tietojen muokkaus ja poistotoiminnallisuudet ovat vielä keskeneräisiä, osin GUI:n elemenettien puutteen takia ja muutamilta osin sovelluslogiikan takia.
