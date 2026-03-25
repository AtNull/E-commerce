# Arhitektura #

Aplikacije koristi kombinaciju kombinaciju Clean Architecture i MVVM principa.
Clean Architecture obezbeđuje razdvajanje koda na nezavisne slojeve, čija se implementacija može lako menjati bez uticaja na ostatak projekta.
MVVM obezbeđuje razdvanaje korisničkog interfejsa od biznis logika. Na ovaj način UI postaje potpuno nezavisan od izvora podataka i njihove obrade.

**Slojevi aplikacije**
* UI sloj (Presentation) - ViewModel komunicira sa data slojem preko repository i izlaže stanje preko StateFlow-a. Activity i fragmenti osluškuju promene i prikazuju sadržaj na osnovu dobijenog stanja čime postaju odgovorni samo za prikaz, ali ne i čuvanje podataka.
* Data sloj - Ovaj sloj upravlja podacima iz različitih izvora sa repository kao posdrednikom. Za komunikaciju sa web API koristi se Retrofit, dok se za lokalno skladištenje koristi Room baza. Podaci preuzeti sa APi-ja se prvo skladište u bazu, odakle se dalje emituju ka UI sloju. Ovim pristupom baza postaje jedini izvor istine.

# Instrukcije za instalaciju #

**Opcija 1: APK**
1. Preuzmi priloženi apk fajl
2. Instaliraj apk fajl

**Opcija 2: Ručno buildovanje**
1. Kloniraj repo
2. Otvori projekat u Android Studiju
3. Sačekaj da se izvrši gradle sync
4. Ako želiš, promeni flavor (Trenutno su dostupni greenBrand i blueBrand)
5. Pokreni aplikaciju
