# Kirjanpitosovellus maalausprojekteille

Sovelluksen avulla käyttäjät voivat pitää suojatusti kirjaa, organisoida ja arkistoida maalausprojektejaan. Sovellusta voi käyttää useampi käyttäjä, joiden projektit ovat tallessa henkilökohtaisen salasanan takana.

Sovelluksen ensimmäisessä iteraatiossa toiminnassa ovat harjoitustyölle viikolla 3 vaadittavat ominaisuudet:
- Repositorion juuresta löytyy Maven-projekti
- Projektin pystyy suorittamaan NetBeansista tai komentokehotteesta komennolla mvn compile exec:java -Dexec.mainClass=maalausprojektikirjanpito.ui.Main
  - **HUOM. projektin Maven-liitännäiset täytyy ensin ladata ennen komennon käyttöä, onnistuu mm NetBeansista right-clickaamalla Dependencies ja valitsemalla "Download Declared Dependencies"**
- Toiminnassa on käyttäjätunnuksen luominen, sisäänkirjautuminen ja uloskirjautuminen
- Toiminnassa on kourallinen testejä jotka menevät läpi
- Testikattavuusraportin generointi onnistuu
- Sovelluksen toimivuus on tarkastettu laitoksen järjestelmällä etäyhteyden välityksellä, sekä komentokehotteesta että NetBeansilla.

## Dokumentaatio

[Työaikakirjanpito](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Ohjelmistotekniikan%20harjoitusty%C3%B6n%20ty%C3%B6aikakirjanpito.md)

[Alustava määrittelydokumentti](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Ohjelmistotekniikan%20harjoitusty%C3%B6n%20alustava%20vaatimusm%C3%A4%C3%A4rittely.md)

## Komentorivitoiminnot

Testit suoritetaan komennolla
```
mvn test
```
Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_
