# Kirjanpitosovellus maalausprojekteille

Sovelluksen avulla käyttäjät voivat pitää suojatusti kirjaa, organisoida ja arkistoida maalausprojektejaan. Sovellusta voi käyttää useampi käyttäjä, joiden projektit ovat tallessa henkilökohtaisen salasanan takana.

Sovelluksen toisessa iteraatiossa toiminnassa ovat ominaisuudet:
- Projekti on kasvanut toiminnallisuudellessaan. Nyt pystyy käyttäjätoiminnallisuuksien lisäksi käyttäjille lisäämään projekteja ja projekteja pystyy selaamaan.
- Projektin pystyy suorittamaan NetBeansista tai komentokehotteesta komennolla mvn compile exec:java -Dexec.mainClass=maalausprojektikirjanpito.ui.Main
  - HUOM. projektin Maven-liitännäiset täytyy ensin ladata ennen komennon käyttöä, onnistuu mm NetBeansista right-clickaamalla Dependencies ja valitsemalla "Download Declared Dependencies"
- Testikattavuusraportin generointi onnistuu, sovelluksen testauksen rivikattavauus on yli 40% ja käytössä on mielekkäitä testejä alla mainitulla komennolla
- Checkstyle-toiminnallisuus on käytössä ja raportin generointi onnistuu alla mainitulla komennolla. Tällä hetkellä virheitä on 10. Tärkeimpinä haasteina on maksimissaan 20 rivin pituus metodille, joka esim tietueen päivitykseen ei riitä, jos käyttää PreparedStatementin parametrinasetusominaisuuksia. Herjaa myös throws SQLExceptionin puuttumisesta kohdissa joissa se on.
- Ohjelmasta on tehty release, mutta jostain syystä Linuxilla sen tietokantatiedosto lukkiutuu. Liittyneekö moniajoon tai vastaavaa? Omalla Windows-koneellani ongelmaa ei ole.
- Ohjelman sekvenssikaavio löytyy arkkitehtuurista, jos kerkeän sen vielä lisäämään. Määrämitta, määrämuoto, määräaika.

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
mvn test jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto /target/site/jacoco/index.html

Checkstyle-raportti luodaan komennolla

```
mvn jxr:jxr checkstyle:checkstyle
```
Checkstyle-raporttia voi tarkastella avaamalla selaimella tiedosto /target/site/checkstyle.html.
Jostain syystä itse en löytänyt ko. tiedostoa hakemistoa selaamalla, jouduin etsimään sen tiedostoselaimen etsintätyökalulla.

Suoritettavan jar-pakkauksen generointi tapahtuu komennolla
```
mvn package
```
Jar-tiedosto löytyy sijainnista /target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar. Tiedoston voi ajaa komennolla
```
java -jar target/Maalausprojektikirjanpito-1.0-SNAPSHOT.jar
```
