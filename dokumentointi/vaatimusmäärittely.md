# Vaatimusmäärittely

## Sovelluksen tarkoitus
Sovelluksen avulla käyttäjä voi organisoida ja pitää kirjaa pienoismalliensa maalausprojekteista ja niissä käytetyistä maali- ja käsittely-yhdistelmistä. 

## Käyttäjät
Sovelluksessa on aluksi vain yksi käyttäjärooli eli normaali käyttäjä. Sovelluksessa on mahdollisuus useampien käyttäjien lisäämiseen.

## Automaattisesti päivittyvä graafinen käyttöliittymä painikkeilla
Sovelluksessa voi navigoida käyttöliittymän avulla toimivilla otsikoiduilla painikkeilla. 
Sovellus koostuu kolmesta näkymästä: Kirjautuminäkymä, käyttäjätunnuksen luomisnäkymä ja sovelluksen työskentelynäkymä.

## Toiminnallisuudet

### Ennen kirjautumista
    • Käyttäjä voi luoda uuden tunnuksen ja salasanan
        ◦ Käyttäjatunnuksen täytyy olla uniikki, pituudeltaan 3-20 merkkiä ja sisältää vain kirjaimia 
          tai numeroja.
        ◦ Salasanan täytyy olla pituudeltaan 8-20 merkkiä ja sisältää vain kirjaimia tai numeroja.
        ◦ Salasana syötetään kahdesti oikeellisuuden varmentamiseksi.
    • Käyttäjä voi kirjautua järjestelmään
        ◦ Kirjautuminen onnistuu syötettäessä olemassaoleva käyttäjätunnus ja salasana 
          kirjautumislomakkeelle
        ◦ Jos käyttäjää ei ole olemassa tai salasana on virheellinen, ilmoittaa järjestelmä tästä
### Kirjautumisen jälkeen
    • Käyttäjä voi tarkastella ja muokata omia projektejaan, alaprojekteja, pintoja jne., 
      sekä kirjautua ulos.
      Tiedot haetaan tietokannasta, jossa ne ovat pitkäaikaistalletuksessa.
    • Käyttäjä voi lisätä uusia projekteja. Projekteille annetaan kategoria (esim. Warhammer 40k), 
      faktio (esim. Imperial Guard) ja nimi.
    • Projekteihin voi lisätä alaprojekteja (esim. rivisotilas, kersantti, maastonkappaleen 
      elementti). 
    • Alaprojekteihin voi lisätä maalipintoja (esim. metalliset osat, vaatteet, jalusta).
    • Maalipinnoille voi lisätä maaleja ja käsittelyjä (esim. puuliima, hiekka, pohjamaali, 
      wash, lakkaus). 
    • Käsittelyillä ja maaleilla on nimi, tyyppi, väritieto ja valmistaja. Ne tallentuvat 
      järjestelmään, ja tallennettuja tietoja voi valita muille maalipinnoille.

## Jatkokehitysideoita
    • Käyttöliittymän laajentaminen:
        ◦ Tietojen editointimahdollisuuksien laajetaminen
        ◦ tietojen poistamisen GUI-toteutus
        ◦ Tietojen muokkauksen GUI-toteutus
        ◦ Listojen järjestyksen muokkaaminen
    • Automaattisen testauksen viimeistely
