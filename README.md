# Kirjanpitosovellus maalausprojekteille

Sovelluksen avulla käyttäjät voivat pitää suojatusti kirjaa, organisoida ja arkistoida maalausprojektejaan. Sovellusta voi käyttää useampi käyttäjä, joiden projektit ovat tallessa henkilökohtaisen salasanan takana.

Sovelluksen toisessa iteraatiossa toiminnassa ovat ominaisuudet:
- Projekti on kasvanut toiminnallisuudellessaan, nyt pystyy erikseen luomaan käyttäjiä, kirjautumaan sisään ja ulos tekstikäyttöliittymällä. Tärkein kehitysaskel on kuitenkin ensimmäisten tietokantatoiminnallisuuksien käyttöönotto.
- Projektin pystyy suorittamaan NetBeansista tai komentokehotteesta komennolla mvn compile exec:java -Dexec.mainClass=maalausprojektikirjanpito.ui.Main
  - **HUOM. projektin Maven-liitännäiset täytyy ensin ladata ennen komennon käyttöä, onnistuu mm NetBeansista right-clickaamalla Dependencies ja valitsemalla "Download Declared Dependencies"**
- Testikattavuusraportin generointi onnistuu, sovelluksen testauksen rivikattavauus on yli 20% ja käytössä on mielekkäitä testejä
- Checkstyle-toiminnallisuus on käytössä ja virheitä on alle 10
- Ohjelman luokkakaavio löytyy dokumentointiosiosta

## Dokumentaatio

[Työaikakirjanpito](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Ohjelmistotekniikan%20harjoitusty%C3%B6n%20ty%C3%B6aikakirjanpito.md)

[Alustava määrittelydokumentti](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Ohjelmistotekniikan%20harjoitusty%C3%B6n%20alustava%20vaatimusm%C3%A4%C3%A4rittely.md)

[Arkkitehtuuri](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

## Viimeisin release

[Release 0.1.0](https://github.com/CleanDry/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

Testit suoritetaan komennolla
```
mvn test
```
Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto /target/site/jacoco/index.html

Checkstyle-raportti luodaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```
Checkstyle-raporttia voi tarkastella avaamalla selaimella tiedosto /target/site/checkstyle.html

Suoritettavan jar-pakkauksen generointi tapahtuu komennolla
```
mvn package
```
Jar-tiedosto löytyy sijainnista /target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar. Tiedoston voi ajaa komennolla
```
java -jar target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar
```
