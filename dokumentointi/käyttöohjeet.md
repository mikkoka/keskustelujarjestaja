## Keskustelujärjestäjä

Keskusteluntutkijat tekevät annotaatioita keskusteluista tehtyihin nauhotteisiin. Annotaatiot koskevat jotakin aikaväliä jollakin tietyllä nauhoitteella. Kutsuttakoon tällaista aikaväliä keskustelu*otteeksi*. 

Annotaatioitten tekoon käytettyistä ohjelmista esimerkiksi käy Elan (https://tla.mpi.nl/tools/tla-tools/elan/). Annotaatioita voi Elanin kaltaisista ohjelmista viedä tekstitiedostoihin.

*Keskustelujärjestäjä* -sovellus lukee otteita Elanilla tallennetuista UTF-8 -muotoisista tekstitiedostoista (tallennusohjeita toisaalla), ja tarjoaa välineitä niitten "annotointiin". Sovelluksen käynnistyttyä käyttäjän eteen ilmaantuu ikkuna, jossa on kaksi pudotusvalikkoa ("Projekti" ja "Otteet") sekä kaksi (tyhjää) välilehteä. 

**Otteiden tuominen  Elanissa tallennetusta tekstitiedostosta**

* Valitse `Projekti > Tuo > Elanista`, ja etsi sitten aukeavista tiedostonhakuvalikoista ensin Elan -annotaatioita sisältävä tekstitiedosto, sitten ko. tekstitiedostoon liittyvä mediatiedosto. Otteet sekä tekstitiedostossa olleet havaintokategoriat aukeavat eteesi projekti hakupaneeliin. 

**Aiemmin luotujen ja tallennettujen otteiden ja havaintojen lataaminen**

* Valitse `Projekti > Lataa` ja sitten aukeavasta  tiedostonhakuvalikosta aiemmin tallennetut otteet ja havainnot sisältävä tekstitiedosto. 
* Koska *Keskustelujärjestäjä* ei tule hyvin toimeen "vääränlaiseen" formaattiin muotoiltujen tekstitiedostojen kanssa, on erittäin suositeltavaa, että ladatessasi ja tallentaessasi otteita ja havaintoja käytät tiedostoissa päätettä "keskjarj". 
* Valittuasi tiedoston, sen sisältämät otteet ja havainnot aukeavat eteesi, ja aiemmin mahd. esillä olleet otteet ja havainnot ovat kadonneet.

**Uuden havaintokategorian luominen**

* Valitse `Projekti > Luo havaintokategoria > Otetta koskeva` ja syötä aukeavaan ruutuun nimi, jonka haluaisit kategorialle antaa. Mikäli nimi on mahdollinen (se ei esim. ole aiemmin käytössä), uusi kategoria ilmaantuu aakkosjärjestyksen mukaiseen sarakkeeseen hakupaneeliin.

**Havaintokategorian liittäminen otteeseen hakupaneelissa**

* Valitse haluamasi ote (rivi), ja haluamasi havaintokategoria (sarake), ja klikkaa ruutua taulukossa, niin ote liitetään kategoriaan (tai poistetaan siitä).

**Otteeseen liittyvän nauhoitteen tarkastelu hakupaneelissa**

* Valitse haluamasi rivi (ote) taulukosta, ja valitse valikosta `Otteet > Toista valittu ote VLC:llä` (tai vaihtoehtoisesti Alt+v). VLC toistaa otteen.

**Otteiden järjestely järjestelypaneelissa**

* Valitse otteita hakupaneelista, ja sitten `Otteet > Järjestele valittuja otteita`.
* Valitse aukeavasta listasta havaintokategoria, johonka liittyviä havaintoja haluat järjestelypaneelissa tehdä. 
* Halutessasi valitse toinenkin havaintokategoria, johonka liittyviä havaintoja teet.
* Mikäli valintasi olivat ongelmattomat (ei ole olemassa otteita, jotka kuuluvat mahd. molempiin valitsemiisi kategorioihin), otteet ilmaantuvat nyt *järjestelypaneeliin*, asettuen siinä ikkunan eri osiin sen mukaan, kuuluvatko ne jompaan kumpaan valitsemistasi havaintokategorioista. 
* Ikkunan vasen ja oikea puoli edustavat eri havaintokategorioita, ja voit vetää otteita edustavia suorakaiteita hiirellä jompaan kumpaan laitaan, tai ikkunan keskiosaan, jossa olevat otteet eivät kuulu kumpaankaan kategoriaan. 

**Otteeseen liittyvän nauhoitteen tarkastelu järjestelypaneelissa**

* Valitse haluamasi ote hiirellä, ja klikkaa sitä AltGR- näppäimen ollessa painettuna (tai vaihtoehtoisesti valitse valikosta `Otteet > Toista valittu ote VLC:llä`). VLC toistaa nyt otteen.

**Järjestelyn lopettaminen ja sen tulosten tuominen hakupaneelin taulukkoon**

* Valitse valikosta `Otteet > Lopeta järjestely ja tuo otteet taulukkoon`. Otteet saavat nyt taulukossa sijaintiensa mukaiset kategoriat (sen perusteella, missä otetta edustavan suorakateen keskikohta sijaitsee).

**Otteiden ja havaintojen tallentaminen**

* Valitse valikosta `Projekti > Tallenna`, ja kirjoita aukeavan tiedostonvalintavalikon kenttään tiedoston nimi, johon haluat tallentaa. 
* Koska *Keskustelujärjestäjä* ei tule hyvin toimeen "vääränlaiseen" formaattiin muotoiltujen tekstitiedostojen kanssa, on erittäin suositeltavaa, että tallentaessasi otteita ja havaintoja käytät tiedostoissa päätettä "keskjarj". 
* Otteet ja havainnot tallentuvat tiedostoon

**Lopettaminen**
* Valitse valikosta `Projekti > Lopeta`. Sovellus kysyy tallentamisesta, ja sulkeutuu.
* Sovellusta ei voi sulkea nurkan ruksista, kun sen koodaaja ei osannut liittää tapahtumakuuntelijaa moiseen klikkaukseen, eikä toisaalta halunnut sen voivan sulkeutua vahingossa.


