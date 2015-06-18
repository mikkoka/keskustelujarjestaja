## Testausdokumentti

* Tiedostonluvun ja tallennuksen testaaminen automaattisesti tuntui varsin haastavalta, ja jäi melko vähälle. Esimerkiksi tiedostot, joita sovellukseni kirjoittaa, ovat rakenteeltaan lähellä, mutta kuitenkin erilaisia, kuin tiedostot, joita se alun alkuaan lukee. (Elanilla tallennetut tekstitiedostot.) Ei ole helppoa testata tiedostonkirjoituksen toimivuutta vertaamalla sen tuotosta tiedostoon, joka sisältää samat tiedot, mutta mahdollisesti eri järjestyksessä, esim.

* Olen jonkin verran kokeillut, mitä tapahtuu, jos luen "väärän tyyppisiä" tekstitiedostoja, tai suorastaan esim. videoitiedostoja, tekstitiedostonlukijalla, ja prosessissa torjunut joitakin mahdollisia syntyviä ongelmatilanteita. Kaikkia portteja en väitä saaneeni kiinni.

* Projektin alkuvaiheessa etenkin minulla oli tapana pitää jokaisessa luokassa omaa Main -metodia, jossa oli koodia, jolla testasin, toimiiko luokka odotusteni mukaisesti. Ehkäpä tästä syystä en heti ymmärtänyt käyttää yksikkötestauksen tarjoamia mahdollsuuksia hyväkseni.

* Käyttöliittymän ongelmatilanteita olen kokeillut niin paljon kuin olen ehtinyt. Esimerkiksi tiedostonhallinnan mahdollisia ongelmalähteitä on mahdollista sulkea pois jo käyttöliittymässä. Tällä tavalla *tavallaan* tämä "epävirallinen" testaamiseni on korvannut yksikkötestausta. 
