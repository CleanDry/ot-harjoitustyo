# Kirjanpitosovellus maalausprojekteille

Sovelluksen avulla käyttäjät voivat pitää suojatusti kirjaa, organisoida ja arkistoida maalausprojektejaan. Sovellusta voi käyttää useampi käyttäjä, joiden projektit ovat tallessa henkilökohtaisen salasanan takana.

Sovelluksen viimeisimmässä iteraatiossa toiminnassa ovat ominaisuudet:
- Projektin on toiminnallisuuksiltaan pysynyt lähes samana, nyt sovelluksessa on graafinen käyttöliittymä. Kaikkia toiminnallisuuksia ei ole vielä yhdistetty graafiseen käyttöliittymään. Tällä hetkellä pystyy luomaan uuden käyttäjän ja kirjautumaan sisään. Projektinäkymä on rakenteilla.
- Projektin pystyy suorittamaan NetBeansista tai komentokehotteesta komennolla mvn compile exec:java -Dexec.mainClass=maalausprojektikirjanpito.ui.Main
  - HUOM. projektin Maven-liitännäiset täytyy ensin ladata ennen komennon käyttöä, onnistuu mm NetBeansista right-clickaamalla Dependencies ja valitsemalla "Download Declared Dependencies"
- Testikattavuusraportin generointi onnistuu, sovelluksen testauksen rivikattavauus on yli 60% ja käytössä on mielekkäitä testejä alla mainitulla komennolla.
- Checkstyle-toiminnallisuus on käytössä ja raportin generointi onnistuu alla mainitulla komennolla.
- Ohjelmasta on tehty toinen release, mutta jostain syystä Linuxilla sen tietokantatiedosto lukkiutuu. Liittyneekö moniajoon tai vastaavaa? Omalla Windows-koneellani ongelmaa ei ole.
- Ohjelman arkkitehtuurikuvaus ja käyttöohje löytyvät löytyy dokumentoinnista.

## Dokumentaatio

[Työaikakirjanpito](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/ty%C3%B6aikakirjanpito.md)

[Alustava määrittelydokumentti](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/vaatimusm%C3%A4%C3%A4rittely.md)

[Arkkitehtuuri](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/arkkitehtuuri.md)

[Käyttöohje](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/k%C3%A4ytt%C3%B6ohje.md)

## Viimeisin release

[Release 0.2.0](https://github.com/CleanDry/ot-harjoitustyo/releases/tag/viikko6)

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
