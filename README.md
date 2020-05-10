# Kirjanpitosovellus maalausprojekteille

Sovelluksen avulla käyttäjät voivat pitää suojatusti kirjaa, organisoida ja arkistoida maalausprojektejaan. Sovellusta voi käyttää useampi käyttäjä, joiden projektit ovat tallessa henkilökohtaisen salasanan takana.

Sovelluksen loppupalautuksessa toiminnassa ovat ominaisuudet:
- Projektin graafinen käyttöliittymä on peruselementeiltään olemassa ja toiminnallisuudet käytössä. Loppupalautukseen mennessä sain toimimaan lähinnä elementtien lisäyksen. Tietojen muokkaus ja poistaminen vaatii GUI:n elementtien rakennuksen ja logiikan viimeistelyn.
- Projektin pystyy suorittamaan NetBeansista tai komentokehotteesta komennolla mvn compile exec:java -Dexec.mainClass=maalausprojektikirjanpito.ui.Main
  - HUOM. projektin Maven-liitännäiset täytyy ensin ladata ennen komennon käyttöä, onnistuu mm NetBeansista right-clickaamalla Dependencies ja valitsemalla "Download Declared Dependencies"
- Testikattavuusraportin generointi onnistuu alla olevalla komennolla. Testikattavuuksien laajuus supistui osuudeltaan viimeiseen palautukseen, koska sovellus laajentui merkittävän nopeasti kurssin loppupuolella.
- Checkstyle-toiminnallisuus on käytössä ja raportin generointi onnistuu alla mainitulla komennolla.
- Ohjelmasta on tehty lopullinen release kurssia varten. Sovellusta on kuitenkin kehitetty käyttöä varten, joten mikäli aika vain riittää jatkan sen kehitystä vielä kurssin jälkeen. Tämän seurauksena sovelluksessa on joitain toiminnallisuuksia, jotka eivät vielä ole käytettävissä käyttöliittymän puitteissa.
- Ohjelman dokumentaatio löytyy ko. kansiosta. Sovelluksen kehitys vei valtaosan työajasta, joten varsinkin arkkitehtuurikuvaus on vaatimaton. Käyttöohje, työaikakirjanpito, vaatimusmäärittely ja JavaDoc ovat halutulla tasolla.

## Dokumentaatio

[Työaikakirjanpito](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/ty%C3%B6aikakirjanpito.md)

[Määrittelydokumentti](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/vaatimusm%C3%A4%C3%A4rittely.md)

[Arkkitehtuuri](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md)

## Viimeisin release

[Loppupalautus (0.5.0)](https://github.com/CleanDry/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot

Testit suoritetaan komennolla
```
mvn test
```
Testikattavuusraportti luodaan komennolla
```
mvn test jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto /target/site/jacoco/index.html

Checkstyle-raportti luodaan komennolla
```
mvn jxr:jxr checkstyle:checkstyle
```
Checkstyle-raporttia voi tarkastella avaamalla selaimella tiedosto /target/site/checkstyle.html.
Jostain syystä itse en löytänyt ko. tiedostoa hakemistoa selaamalla, jouduin etsimään sen tiedostoselaimen etsintätyökalulla.

JavaDoc-raportin luominen tapahtuu komennolla
```
mvn javadoc:javadoc
```
Generoitu JavaDoc löytyy hakemistosta target/site/apidocs/index.html
Huom. Mikäli järjestelmän JAVA_HOME-muuttujaa ei ole asetettu, täytyy se asettaa ensin. 
Ohjeita esim: https://askubuntu.com/questions/686292/problem-in-setting-java-home-variable.

Suoritettavan jar-pakkauksen generointi tapahtuu komennolla
```
mvn package
```
Jar-tiedosto löytyy sijainnista /target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar. Tiedoston voi ajaa komennolla
```
java -jar target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar
```
