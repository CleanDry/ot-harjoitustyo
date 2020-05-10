# Arkkitehtuurikuvaus

## Rakenne

## Käyttöliittymä

Käyttöliittymän sisältää neljä erilaista näkymää
- kirjautuminen
- uuden käyttäjän luominen
- projektien selaus ja arkistointi
- yksittäisen projektin tarkastelu ja aliprojektien käsittely

Jokainen näistä on toteutettu omana Scene-oliona. Näkymistä yksi kerrallaan on näkyvänä eli sijoitettuna sovelluksen stageen.
Käyttöliittymä on rakennettu luokassa maalausprojektikirjanpito.ui.UserInterface. TreeView-komponentin apuluokka TreeViewHelper on totetettu omana luokkanaan ui-pakkauksessa.

Käyttöliittymä on pyritty eristämään sovelluslogiikasta. Käyttöliittymä kutsuu ServiceManager-luokan olion metodeja toimintojen toteuttamiseksi.

Projektien tarkasteluun on käytössä TreeView-olio ja sillä apuna TreeViewHelper-luokka, joka toteuttaa erilaisia toiminnallisuuksia TreeView-puuhun. Projekteja lisättäessä ja käsiteltäessa käytetään TreeView:n tapahtumia puun päivittämiseen.

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

## Päätoiminnallisuudet

### Käyttäjän kirjautuminen

### Uuden käyttäjän luominen

### Uuden projektin luominen

![Image of the sequence diagram](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/Initial%20sequence%20diagram.jpg)

## Ohjelman rakenteeseen jääneet heikkoudet
