# VentiLife - Sistem za praćenje stanja pacijenta na respiratoru

## Opis projekta
Sistem za praćenje stanja pacijenta na respiratoru, koji ima za cilj unapređenje praćenja pacijenata u operacionim salama. Motivisan potrebom za smanjenjem ljudskih grešaka i bržim odgovorom na promene, sistem automatski prati vitalne parametre (pO2, pCO2, itd.) i parametre respiratora (FiO2, mod rada), te generiše preporuke za prilagođavanje i upozorenja. Baziran na informacijama o medicinskim aspektima i kliničkim istraživanjima, koristi forward i backward chaining za odlučivanje i prilagođavanje modova respiratora u realnom vremenu, osiguravajući optimalne uslove za pacijente.

## Pokretanje aplikacije
#### Aplikaciju čine četiri komponente:
- #### Simulator (Python)
- #### Backend (Spring Boot)
- #### Frontend (Angular)
- #### Baza podataka (MySQL)

### 1. MySQL baza podataka

MySQL baza podataka `ventilifedb` sa korisničkim imenom `root` i lozinkom `root123`.

#### Pokretanje MySQL baze
1. Instalirati MySQL server.
2. Kreirati bazu podataka sa sledećom komandom:
    ```sql
    CREATE DATABASE ventilifedb;
    ```
3. Kreirati korisnika i dodeliti privilegije:
    ```sql
    CREATE USER 'root'@'localhost' IDENTIFIED BY 'root123';
    GRANT ALL PRIVILEGES ON ventilifedb.* TO 'root'@'localhost';
    ```

### 2. Simulator
Simulatorski deo aplikacije je izrađen u programskom jeziku Python.
##### Pokretanje
1. Pozicionirati se u direktorijum `ventilife-simulator`.
2. Izvršiti sledeću komandu:

    ```bash
    python main.py
    ```

### 3. Backend
Backend aplikacija se sastoji iz tri dela:

#### 3.1 Model

Model je Maven aplikacija koja definiše entitete i služi kao dependency za Kjar i Service aplikacije.

##### Pokretanje Model aplikacije
1. Pozicionirati se u direktorijum `model`.
2. Izvršiti sledeću komandu:

    ```bash
    mvn clean install
    ```

#### 3.2 Kjar

Kjar je Maven aplikacija koja sadrži Drools pravila i koristi Model kao dependency.

##### Pokretanje Kjar aplikacije
1. Pozicionirati se u direktorijum `kjar`.
2. Izvršiti sledeću komandu:

    ```bash
    mvn clean install
    ```

#### 3.3 Service

Service je glavna Spring Boot aplikacija koja se pokreće kao server na portu 8080 i koristi Model i Kjar kao dependency.

##### Pokretanje Service aplikacije
1. Pozicionirati se u direktorijum `service`.
2. Izvršiti sledeću komandu:

    ```bash
    mvn spring-boot:run
    ```

### 4. Frontend
Frontend deo aplikacije je izrađen u Angular framework-u.
##### Pokretanje
1. Pozicionirati se u direktorijum `ventilife-frontend`.
2. Izvršiti sledeće komande:

    ```bash
    npm install
    ```
     ```bash
    ng serve
    ```

## Članovi tima:
- Miloš Čuturić - SV11/2020
- Marko Janošević - SV46/2020
