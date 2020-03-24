#Vaatimusmäärittely

## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi organisoida ja pitää kirjaa pienoismalliensa maalausprojekteista ja niissä käytetyistä maali- ja käsittely-yhdistelmistä. 

## Käyttäjät
Sovelluksessa on aluksi vain yksi käyttäjärooli eli normaali käyttäjä. Käyttäjiä on aluksi vain yksi projektien tietojen muokkauksen suojaamiseksi. Myöhemmin sovellukseen saatetaan lisätä suuremmilla oikeuksilla varustettu pääkäyttäjä, ja mahdollisuus useampien käyttäjien lisäämiseen.

## Yksinkertainen käyttöliittymä painikkeilla
Sovelluksessa voi navigoida käyttöliittymän avulla toimivilla otsikoiduilla painikkeilla. Sovellus koostuu ainakin kolmesta näkymästä: Kirjautuminäkymä, käyttäjätunnuksen luomisnäkymä ja sovelluksen projektinäkymä.

## Suunnitellut toiminnallisuudet

### Ennen kirjautumista
    • Käyttäjä voi luoda uuden tunnuksen ja salasanan
        ◦ Käyttäjatunnuksen täytyy olla uniikki ja pituudeltaan vähintään 3 merkkiä
    • Käyttäjä voi kirjautua järjestelmään
        ◦ Kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus ja salasana kirjautumislomakkeelle
        ◦ Jos käyttäjää ei ole olemassa tai salasana on virheellinen, ilmoittaa järjestelmä tästä
### Kirjautumisen jälkeen
    • Käyttäjä voi lisätä ja poistaa tyhjiä projektikategorioita (esim. Warhammer, BFG, Sekalaiset)
    • Kategorioihin voi lisätä ja poistaa tyhjiä projekteja (esim yksikkö, ajoneuvo, maastonkappale). Kategoriat ovat ryhmiteltynä projektiin liittyvän organisaation mukaan (esim Astra Militarum, maasto).
    • Projekteihin voi lisätä ja poistaa alaprojekteja (esim. rivisotilas, kersantti, maastonkappaleen elementti). Projekteja voi merkitä valmistuneiksi ja valmistuneeksi merkityt projektit voi siirtää arkisto-osioon. Poistetut projektit siirtyvät poistetut-osioon, josta projekteja voi siirtää takaisin varsinaisiin tai poistaa kokonaan.
    • Alaprojekteihin voi lisätä ja poistaa maalipintoja (esim. metalliset osat, vaatteet, jalusta).
    • Maalipinnoille voi lisätä ja poistaa maaleja ja käsittelyjä (esim. puuliima, hiekka, pohjamaali, wash, lakkaus). Käsittelyillä ja maaleilla on järjestys jota voi muokata. 
    • Käsittelyillä ja maaleilla on nimi, tyyppi, väritieto, valmistaja ja tunnistenumero. Ne tallentuvat järjestelmään, ja tallennettuja tietoja voi valita muille maalipinnoille.

## Jatkokehitysideoita
    • Käyttöliittymän laajentaminen:
        ◦ Pudotuslistaus
        ◦ Listojen järjestyksen muokkaaminen
        ◦ Maalien väritiedon tarkentaminen ja näyttäminen käyttöliittymässä
        ◦ Maalien tietojen editointi
    • Hakuominaisuus
    • Artikkelien nimien muokkaaminen
    • Maalipintojen kopioiminen projektista toiseen



