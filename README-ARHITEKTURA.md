# Arhitektura #

Aplikacije koristi kombinaciju Clean Architecture i MVVM principa.
Clean Architecture obezbeđuje razdvajanje koda na nezavisne slojeve, čija se implementacija može lako menjati bez uticaja na ostatak projekta.
MVVM obezbeđuje razdvajanje korisničkog interfejsa od biznis logika. Na ovaj način, UI postaje potpuno nezavisan od izvora podataka i njihove obrade.

**Slojevi aplikacije**
* UI sloj (Presentation) - ViewModel komunicira sa data slojem preko repository i izlaže stanje preko StateFlow-a. Activity i fragmenti osluškuju promene i prikazuju sadržaj na osnovu dobijenog stanja čime postaju odgovorni samo za prikaz, ali ne i čuvanje podataka.
* Data sloj - Ovaj sloj upravlja podacima iz različitih izvora sa repository kao posdrednikom. Za komunikaciju sa web API se koristi Retrofit, dok se za lokalno skladištenje koristi Room baza. Podaci preuzeti sa APi-ja se prvo skladište u bazi, odakle se dalje emituju ka UI sloju. Ovim pristupom baza postaje jedini izvor istine.

# Instrukcije za instalaciju #

**Opcija 1: APK**
1. Iz release sekcije preuzmi priloženi apk fajl
2. Instaliraj apk fajl

**Opcija 2: Ručno buildovanje**
1. Kloniraj repo
2. Otvori projekat u Android Studiju
3. Sačekaj da se izvrši gradle sync
4. Ako želiš, promeni flavor (Trenutno su dostupni greenBrand i blueBrand)
5. Pokreni aplikaciju

# Screenshotovi #
<p align="left">
  <img src="https://github.com/user-attachments/assets/baedae3d-bd8c-46d4-9a6b-09449dd4a97d" width="20%" />
  <img src="https://github.com/user-attachments/assets/4a8f7938-f16e-4268-bc2b-d56861b74b1f" width="20%" /> 
  <img src="https://github.com/user-attachments/assets/1fa843f3-cb27-4762-852f-8b7023fc01d8" width="20%" />
</p>

Pritisak na karticu sa proizvodom otvara ekran sa detaljima o proizvodu. Pritisak na strelicu zatvara ekran, dok pritisak na srce dodaje proizvod u Favorites.
