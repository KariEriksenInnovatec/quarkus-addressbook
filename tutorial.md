1995/1996 - året Java kom ut
----
- Språk som fantes - C, C++, SmallTalk, Pascal, Lisp, Fortran

Java/JVM
---
- Ingen pekere, kun referanser - sikrere kode
- Inkluderer mer default biblioteker enn Gnu C++
  - Collections
  - Trådmodell
  - Nettverk io
  - GUI: JavaFX, Swing, SWT
- Gratis å bruke (Microsoft C++ var ikke gratis)
- WORA - samme kodebase kan kjøre på flere operativ system uten rekompilering
- Garbage collection - automatisk rydding av heap minne
- Støtter dynamisk innlasting av kode (ClassLoader)
- Sikkerhetsmodell
- Tok vekk multiple arv

Bli kjent med disse
-------------------
Under java.util pakke:
- Collection: List (ArrayList, LinkedList), Set (HashSet, TreeSet)
- Map: HashMap
- Arrays: metoder for manipulering av arrays (sortering, kopiering, osv)
Under java.lang:
- System: hente ut system variabler, hente ut tid, osv
- Wrapper for primitive typer: String, Byte, Short, Integer, Long, Float, Double
Under java.io:
- Serializable
- File
- InputStream, OutputStream

Oppsett av Postgres database (Docker)
-------------------------------------
- Installere Docker
- Hent seneste versjon av postgres: docker pull postgres:latest
- Opprett mappe der postgres data skal lagres
- Kjør Postgres i Docker (fra mappe som skal inneholde postgres data): sudo docker run -d --rm --name postgres-db -v /home/eb/mnt/quarkus-adressebok:/var/lib/postgresql -p 5432:5432 -e POSTGRES_PASSWORD=<passord> postgres -d postgres:latest
- Installere DBeaver
- Opprett kobling til database fra DBeaver: localhost:5432

Opprett ny database og brukere
------------------------------
-- Grant connect and database privileges
CREATE USER admin WITH PASSWORD 'admin123';
ALTER ROLE "admin" NOSUPERUSER CREATEDB CREATEROLE NOINHERIT LOGIN NOREPLICATION NOBYPASSRLS;
ALTER DATABASE innovatec OWNER TO "admin";
GRANT CONNECT ON DATABASE innovatec TO admin;
GRANT ALL PRIVILEGES ON DATABASE innovatec TO admin;

-- Grant schema privileges (USAGE required first, then CREATE)
GRANT USAGE, CREATE ON SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;

-- Default privileges for future tables/sequences (crucial for Flyway)
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO admin;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO admin;

Bygging av native-image (valgfritt)
-----------------------------------
På grunn av at Quarkus-jooq kun kjører i runtime og ikke i bygg tid så har jeg konfigurert
både quarkus-jooq og "vanlig" jooq i pom filen. Den vanlige brukes for å generere kode under
bygging, men quarkus-jooq brukes i runtime og trenges for å kunne utlede klassene som trenges
for native image. Derfor må man bygge ting trinnvis med følgende kommandoer:

Før man bygger med GraalVM så må man sette:
export JAVA_HOME=<sti til graalvm>
export PATH=$JAVA_HOME/bin:$PATH

# Sjekk at postgres er opp og kjørbare
sudo docker ps

# Bygger selve kjørbare fil
mvn clean generate-sources compile package -Pnative

# Bygg image basert på Dockerfil.native
sudo docker build -f src/main/docker/Dockerfile.native   -t adressebok-api .


Bygging av JVM image (hvis ikke man bygger native)
--------------------------------------------------
# Bygger selve kjørbare fil
mvn clean generate-sources compile package

# Sjekk at postgres er opp og kjørbare
sudo docker ps

# Bygg image basert på Dockerfil.jvm
sudo docker build -f src/main/docker/Dockerfile.jvm -t adressebok-api:jvm .

Få opp tjeneste med hjelp av Docker compose
-------------------------------------------
Nå må vi få startet både Postgres og adressebok-api og sørge for at de bruker et
nettverk. Dette gjør vi med å opprette docker-compose.yaml fil som ligger i rot av 
prosjektet.

```
services:
  postgres-db:
    image: postgres:latest
    environment:
      POSTGRES_DB: innovatec
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: <passord>
    volumes:
      - /home/eb/mnt/quarkus-adressebok:/var/lib/postgresql
    ports:
      - "5432:5432"
    networks:
      - app-network

  adressebok-api:
    image: adressebok-api:jvm
    environment:
      QUARKUS_DATASOURCE_JDBC_URL: jdbc:postgresql://postgres-db:5432/innovatec
      QUARKUS_DATASOURCE_USERNAME: ab_user
      QUARKUS_DATASOURCE_PASSWORD: <passord>
      REPOSITORY_TYPE: database
      QUARKUS_HTTP_PORT: 3000
      QUARKUS_HTTP_HOST: 0.0.0.0      
      #QUARKUS_HTTP_CORS: "true"
      #QUARKUS_HTTP_CORS_ORIGINS: "*"  # Allow all origins (dev only)
      # Or specific: QUARKUS_HTTP_CORS_ORIGINS: "http://localhost:8080,http://127.0.0.1:8080"
      #QUARKUS_HTTP_CORS_HEADERS: "accept, authorization, content-type, x-requested-with"
      #QUARKUS_HTTP_CORS_METHODS: "GET, POST, PUT, DELETE, OPTIONS"      
    ports:
      - "3000:3000"
    depends_on:
      - postgres-db
    networks:
      - app-network

networks:
  app-network:
    driver: bridge

```

Stoppe Postgres container hvis den kjører.
Legg merke til at jeg byttet fra port 8080 til 3000. Dette har noe med WSL2 å gjøre
og brannmurs instillinger. Jeg valgte å bytte port for å gjøre ting enklere. 

# Få opp containere
sudo docker compose up

# Sjekk at de kjører
sudo docker compose ps

