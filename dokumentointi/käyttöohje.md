# Käyttöohje

Lataa tiedosto PaintProjectManager-vx.x.x.jar, missä x.x.x on viimeisin versio.

### Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla
```
java -jar PaintProjectManager-vx.x.x.jar
```

### Kirjautuminen

Sovellus käynnistyy kirjautumisnäkymään:

![Image of login screen](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/login%20screen.png)

Kirjautuminen onnistuu kirjoittamalla olemassaoleva käyttäjätunnus ja salasana syötekenttään ja painamalla _Log in_.

### Uuden käyttäjän luominen

Kirjautumisnäkymästä on mahdollista siirtyä uuden käyttäjän luomisnäkymään painikkeella _Create a new user_.

![Image of new user creation screen](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/new%20user%20creation%20screen.png)

Uusi käyttäjä luodaan syöttämällä haluttu käyttäjänimi ja salasana syötekenttiin. Salasana tulee syöttää uudestaan täsmälleen samanlaisena kolmanteen kenttään.

_Käyttäjätunnuksen tulee olla 3-20 merkkiä pitkiä, eikä saa sisältää muita merkkejä kuin kirjaimia ja/tai numeroita._
_Salasanan tulee olla 8-20 merkkiä pitkiä, eikä saa sisältää muita merkkejä kuin kirjaimia ja/tai numeroita._

Uusi käyttäjä luodaan painikkeella _Create_.

Jos käyttäjän luominen onnistuu, palauttaa sovellus sinut takaisin kirjautumisnäkymään.
Mikäli kenttien tiedot ovat virheellisiä, ilmoittaa sovellus tästä virhetekstillä.

![Image of new user creation screen with error msgs](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/new%20user%20creation%20screen%20error%20msg.png)

### Kirjautumisen jälkeen
Onnistuneen kirjautumisen jälkeen aukeaa sovelluksen päänäkymä:

![Image of the app main view](https://github.com/CleanDry/ot-harjoitustyo/blob/master/dokumentointi/Attachments/ppm%20app%20main%20view%20scrshot.png)

#### Kirjautuminen ulos
Järjestelmästä ulos kirjautuminen tapahtuu päänäkymän oikeasta yläkulmasta _Log out_ painikkeesta.

#### Uuden projektin luominen
Uuden projektin luominen onnistuu näkymän vasemmassa alakulmassa olevasta _Create a new Project_ painikkeesta.
Painiketta painamalla aukeaa syötekentät, joihin projektin tiedot tulee syöttää. Projektille täytyy syöttää nimi, faktio ja kategoria. Projektin luominen viimeistellään _Create_ painikkeella. Muutosten peruminen tapahtuu _Cancel_ painikkeella.

_Projektin nimen, faktion ja kategorian tulee olla 3-40 merkkiä ja sisältää vain kirjaimia, numeroita ja/tai välejä. Irtonaiset välimerkit karsitaan pois alusta ja lopusta._

#### Projektin tietojen muuttaminen
Projektin tietojen muokkaaminen onnistuu näytön yläosassa olevassa, projektin tiedot sisältävässä näkymässä. Näkymässä ei ole aluksi tietoja. Tiedot saa näkymiin valitsemalla jokin projekti projektilistauksesta nimen vieressä olevaa ">" painiketta painamalla.

Projektin tietoja pääsee muokkaamaan painamalla yläreunan _Edit_ painiketta. Painikkeen painaminen avaa syötekentät tietojen muokkaamista varten. Tiedot tallennetaan painamalla _Save_ painiketta. Muutokset perutaan painamalla _Cancel_ painiketta.

_Projektin nimen, faktion ja kategorian tulee olla 3-40 merkkiä ja sisältää vain kirjaimia, numeroita ja/tai välejä. Irtonaiset välimerkit karsitaan pois alusta ja lopusta._

#### Aliprojektien ja pintojen lisäys
Aliprojektien ja pintojen lisäys onnistuu näkymän alareunan _Create a new Subproject_ ja _Create a new Surface_ painikkeilla. Painneita painamalla avautuu syöttökentät tietojen syöttämistä varten. Aliprojektille tulee syöttää nimi. Pinnalle tulee syöttää nimi ja tieto siitä, että mihin aliprojektiin pinta kuuluu. Uuden aliprojektin nimen syöttäminen luo automaattisesti uuden aliprojektin ja lisää pinnan siihen.

_Projektin nimen, faktion ja kategorian tulee olla 3-40 merkkiä ja sisältää vain kirjaimia, numeroita ja/tai välejä. Irtonaiset välimerkit karsitaan pois alusta ja lopusta._

#### Pintakäsittely-yhdistelmien (esim. maalisekoitusten) ja pintakäsittelyjen (esim. maali) lisäys
Pintanäkymän aktivoiminen onnistuu painamalla aliprojektilistauksessa pinnan nimen vieressä olevaa ">" painiketta.

Nyt pinnoille voi alareunan painikkeista lisätä pintakäsittelyjä _Create a new treatment_ painikkeella ja pintakäsittelyjen yhdistelmiä _Create a new layer_ painikkeella.

Pintakäsittelylle tulee syöttää nimi, tyyppi (esim. maali, liima), valmistaja ja väritieto. Väritieto syötetään valikon avulla. Käsittelyjen yhdistelmälle tulee syöttää nimi, 0-4 pintakäsittelyä sekä valinnainen lisähuomio.
_Pintakäsittelyn nimi, tyyppi ja valmistajan tulee olla 3-40 merkkiä ja sisältää vain kirjaimia, numeroita ja/tai välejä._
_Pintakäsittely-yhdistelmän nimen tulee olla 3-40 merkkiä ja sisältää vain kirjaimia, numeroita ja/tai välejä._
_Pintakäsittely-yhdistelmän lisähuomion on valinnainen, ja voi olla 0-60 merkkiä. Se saa sisältää vain kirjaimia, numeroita ja/tai välejä._
