Proiektuaren Dokumentazioa:
BIDEOA:

Sarrera Viajes Android-erako garatutako aplikazio mugikor natibo bat da, erabiltzaileei hiri desberdinetan hotelak bilatzeko, haien kokapen zehatza mapa interaktibo batean ikusteko eta erreserbak kudeatzeko aukera ematen diena.

Erabilitako Teknologiak eta Liburutegiak

Proiektuak Android-eko garapen modernoaren praktika onenak jarraitzen ditu, Java hizkuntza nagusi gisa erabiliz.

Arkitektura: Android Architecture Components-en oinarritua (ViewBinding, Adapters, Room).

Sare-konexioa (APIak): Retrofit 2 + Gson web zerbitzuen kontsumorako.

Tokiko Datu-basea: Room Persistence Library erreserben biltegiratzerako.

Mapak: OSMDroid (OpenStreetMap) ordainpeko gakoen beharrik gabe kokapen geografikoa bistaratzeko.

Irudien Karga: Glide URL-etatik datozen irudiak modu eraginkorrean eta cache bidez kudeatzeko.

Erabiltzaile Interfazea: Material Design, ChipNavigationBar eta ViewPager2.

Moduluen Funtzionamendua A. Hotel Bilaketa (API Integrazioa)
Aplikazioak Retrofit-en oinarritutako sare-arkitektura erabiltzen du.

Datuen Kontsumoa: Kanpoko hotel API zerbitzu batera konektatzen da (ApiService.java-n emandakoa) erabiltzaileak sartutako hiriaren arabera egonaldi, prezio eta erabilgarritasunari buruzko datuak denbora errealean lortzeko.

Datuen Fallback-a: Sistemak "Hotel Pertsonalizatuak" logika bat dauka, Donostia, Barcelona, Bilbao eta Madrid bezalako hiri garrantzitsuek beti emaitza esanguratsuak eta kokapen zehatzak erakuts ditzaten, nahiz eta kanpoko APIak atzerapena izan.

B. Esploratzailea eta Mapa Interaktiboa

Mapen modulua aplikazioaren atal nagusietako bat da:

Ordainpeko Gakorik Gabe: OpenStreetMap teknologia erabiltzen da OSMDroid liburutegiaren bidez, Google Maps API gakoen beharra ezabatuz.

Geokokapena: Hotel bakoitzak koordenatu errealak ditu (Latitudea/Longitudea). Mapan sartzean, aurkitutako hotel bakoitzarentzat markatzaile dinamikoak sortzen dira.

Interaktibitatea: Markatzaile batean sakatzean, hotelaren informazioa duen txartel bat bistaratzen da, erabiltzaileari haren xehetasunetara zuzenean nabigatzeko aukera emanez.

C. Erreserba Sistema eta Iraunkortasuna (Room DB)

Erreserben kudeaketarako, aplikazioak ez du etengabeko konexio baten menpekotasunik:

Tokiko Biltegiratzea: Room erabiltzen da, SQLite-ren gaineko abstrakzio-geruza gisa.

Entitateak: ReservationEntity klaseak datuen egitura definitzen du (Hotelaren izena, datak, prezio osoa eta irudia).

Eragiketak (DAO): Sistemak erreserba berriak txertatzeko eta ezabatzeko (ezeztapenak) aukera ematen du ExecutorService baten bidez modu asinkronoan, interfazea blokeatu ez dadin bermatuz.

D. Profilaren Kudeaketa

"Nire Erreserbak" atalak erabiltzaileari aukera ematen dio:

Uneko erreserba guztiak zerrendatzeko.

Check-in eta Check-out datak ikusteko.

Ezeztapena denbora errealean: Erreserbak zuzenean zerrendatik ezabatzeko, tokiko datu-basea berehala eguneratuz.

Datuen Egitura (Modeloak)
ItemModel: Hotelentzako modelo nagusia (Izena, Helbidea, Prezioa, Koordenatuak, Wifi-a, etab.).

ReservationModel: Erreserbak bistaratzeko optimizatutako modeloa.

StayResponse: APIaren JSON erantzunak mapatzeko klasea.
