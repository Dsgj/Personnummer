# Kunskapstest för Omegapoint - Personnummer

## Programstruktur
* Main
* ValidityCheck 
* Luhn
* SysLog

### Main
Startar programmet som via terminalen tar emot användardata, som via __ValidityCheck__ kontrolleras.

Programmet avslutas genom att ange *None* som argument.
### ValidityCheck
Kontrollerar ifall indata
* Har ett giltigt format (inte innehåller ogiltiga tecken)
* Innehåller giltigt datum
* Är ett personnummer
* Är ett samordningsnummer
* Är ett organisationsnummer



### Luhn
Genomför kontroll av ett nummer godkänt i __ValidityCheck__ för att se ifall det är ett giltigt nummer enligt Luhns Algoritm.

### SysLog
Skapar den logg som innehåller Exceptions från de olika kontroller som genomförs i __ValidityCheck__.

Loggen sparas i samma mapp som programmet i form av en __.log__-fil.

## Övrigt
Kompileras med __pom.xml__ i Maven.