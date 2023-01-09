# Management Primarie
## Cerinta
- Se cere implementarea unui sistem de management al unei priamrii ce presupune gestionarea cererilor cetatenilor de catre functionarii publici.
- Cerinta urmareste crearea si utilizarea unei baze de date in care informatiile sunt stocate sub forma diferitelor colectii care usureaza functionalitatile necesare 

 ## Implementare
 ### Descrierea claselor 
 #### ManagementPrimarie
 - Contine toate birourile primariei si colectia de utilizatori
 - Metoda "main" a acestei clase instantiaza un obiect de tip ManagementPrimarie, care va reprezenta baza noastra de date pentru cerintele urmatoare. Fisierul primit prin "args" este apoi citit, iar comenzile necesare sunt apelate din clasa "CommandLine" prin intermediul unui switch. 
#### Utilizator
- Aceasta clasa este una abstracta ce urmeaza sa fie mostenita de catre fiecare tip de utilizator.
- Contine campurile comune ale tuturor utilizatorilor: nume, biroul specific si colectiile de cereri in asteptare si finalizate.
#### Elev, Pensionar, Angajat, Entitate juridica, Persoana
- Extind clasa "Utilizator" si adauga campurile specifice lor.
- Suprascriu metodele abstracte ale clasei " Utilizator"
#### Cerere
- Contine campuri care specifica data depunerii, prioriatea si tipul cererii, dar si o referinta catre utilizatorul care a depus-o.
#### Birou
- Retine cererile specifice instantei respective si functionarii sai prin intermediul unor colectii.
- Fiecare utilizator pastreaza o referinta catre biroul tipului sau de utilizator pentru a usura operatiile din cerinta
#### FunctionarPublic
- Contine campul "nume", folosit pentru a determina numele fisierului propriu care trebuie creat
#### ComparaCereriFunctieData si ComparatorCerere
- Implementeaza interfata "Comparator\<Cerere\>".
- Suprascriu metoda "compare" astfel incat sa permita compararea a doua instante ale clasei "Cerere".
- Sunt folosite pentru a fi trimise ca argumente ale altor metode
#### CommandLine
- Aceasta clasa este neinstantiata si populata doar de metode statice apelate din clasa "ManagementPrimarie" pentru a executa comenzile necesare. Consider ca aceasta clasa este folositoare deoarece evita supraincarcarea clasei "ManagementPrimarie" si lasacodul mai lizibil si aerisit.

### Descrierea modului de implementare
- Citirea comenzilor se realizeaza in "ManagementPrimarie", care apeleaza metodele aferente din clasa "CommandLine" si paseaza argumentele (separate prin metoda "split") din linia citita.
- Metodele clasei "CommandLine" prelucreaza argumentele primite si folosesc/modifica colectiile din baza de date

## Tipurile de colectii folosite
- Pentru stocarea utilizatorilor am ales sa folosesc ArrayList deoarece numarul total de utilizatori nu este specificat in prealabil si nu am avut nevoie in implementare ca aceasta colectie de date sa fie stocata ordonat. Astfel, parcurgerea/adaugarea/stergerea elementelor au fost facilitate.
- Pentru stocarea cererilor in asteptare si finalizate ale utilizatorilor am folosit ArrayList din aceleasi motive ca mai sus. Pentru afisarea ordonata cronologic a cererilor din cadrul comenzilor: afiseaza_cereri_finalizate si afiseaza_cereri_in_asteptare am folosit metoda "sort", trimitand ca parametru o instanta a clasei "ComparaCereriFunctieData" pentru a-i folosi metoda "compare" suprascrisa acolo.
- Stocarea functionarilor publici din clasa "Birou" este realizata tot cu ArrayList la fel ca mai sus.
- In final, stocarea cererilor din birou este realizata cu PriorityQueue, create prin constructorul care primeste dimensiunea cozii si functia de comparare : "ComparatorCerere". Intrucat functionarii trebuie sa rezolve mereu cererile care sunt primele in coada, respectiv indeplinesc conditiile specificate in cerinta, aceasta colectie trebuie sa fie in permanenta ordonata corespunzator. Constructorul descris mai sus ofera cozii criteriile de comparare a doua cereri si seteaza o dimensiune mai mare decat cea standard (11).

