# Ključne odluke #

**Paginacija**

Za implementaciju paginacije, umesto ručne implementacije, odabrala sam Paging 3. Ova bibliteka ima dobru podršku za RecyclerView, zbog čega zna kada je potrebno započeti preuzimanje sledeće strane, sama brine o zahtevima i po potrebi dodaje loading indikator.
Svoje stanje čini dostupnim i programerima, tako da je omogućen i prikaz eksternih komponenti kao što su skeleton u slučaju inicijalnog učitavanja ili dialog u slučaju greške.

<br>

**Keširanje**

Podatke skladištim u Room bazi podataka, čime je aplikacija u nekom kapacitetu dostupna i kada nema interneta. S obzirom da već koristim Paging 3, koji pruža keširanje, ova funkcionalnost je zahtevala minimalan dodatni kod.
Osim oflajn prikaza podataka, keširanje je korisno i kada korisnik čeka da pristignu novi, čime on ima utisak da je aplikacija brža. Potencijalni problem ovog rešenja je to što se keširaju i cene proizvoda, koje u realnoj implementaciji moraju biti ažurne.

<br>

**Čuvanje omiljenih proizvoda**

Omiljene proizvode čuvam u zasebnoj tabeli kao cele objekte, a ne samo id. Pošto se tabela sa keširanim proizvoda može obrisati prilikom invalidacije, čuvanje id-ja bi moglo dovesti do situacije da ne postoji objekat sa kojim bi se taj id povezao.
Mana ovog pristupa je to što se podaci dupliraju, zbog čega mogu biti neusklađeni. U realnoj implementaciji čuvala bih samo ID, a u slučaju da objekat nije prisutan ili je zastareo, izvršila bih API poziv.

<br>

**Korisnički interfejs**

Pošto se glavni sadržaj sastoji od dva ekrana, umesto donje navigacije koristila sam tabove u kombinaciji sa ViewPager komponentom. Kako bi se izbeglo slučajno prebacivanje na drugi tab prilikom brzog skrolovanja isključila sam mogućnost promene taba prevlačenjem.
Radi lakšeg prilagođavanja izgleda aplikacije koristila sam Material Design sistem tokena, čime se omogućava lako rebrandiranje. Na početnoj strani sam dodala i AppBar koji prikazuje logo kompanije, ali, pošto zauzima mnogo prostora, korisnik ga može sakriti skrolovanjem.

<br>

**Out of scope**

Kako sam bila ograničena vremenom određene funkcionalnosti sam zamenila prostijim rešenjima. Umesto planiranih skeleton komponenti, koristila sam CircularProgressIndicator. Takođe, isključena je podrška za dark mode.
Neki delovi korisničkog interfejsa, kao što je broj kolona u gridu i ekran sa detaljima nisu optimizovani za tablete. Rešenje ovog problema bi zahtevalo definisanje odvojenih vrednosti ili odvojenih layouta. Na primer, grid bi mogao imati veći broj kolona, dok bi ekran sa detaljima mogao da ima odvojene portrait i landscape layoute.

