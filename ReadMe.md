# Projekt

Anforderungen:
- Es sollen Unit / Component Tests sichtbar sein
- Ein Mocking Framework soll verwendet werden
  - JUnit
  - Mockito
  - ...

- Code Coverage soll einsehbar sein & Code Coverage sollte irgendwie als Report ersichtlich sein (done)
  - Verwendung von JaCoCo als Testcoverage tracking Tool. 
  - Nach Terminal Befehl im Backend Ordner: ```mvn test```
  - Einsehbar im File target/site/jacoco/index.html -> führt zu einer Webseite in dem die Coverage der einzelnen Klassen bis zu den Methoden einsehbar sind
  - HTML-Coverage-Report (Zeilen-Coverage, Methoden-Coverage, übersicht über alle Klassen)

- Automatisiert sollen in irgendwelcher Form Test Reports dargestellt werden (done)
  - Macht Maven automatisch unter target/surefire-reports/ dort findet man für jede Testklasse ein eigenes txt-file mit einem Test-Report
  - Testreport zeigt:
    - Welche Tests wurden ausgeführt
    - Welche Tests sind fehlgeschalgen
    - Stacktraces
    - Laufzeit
    - Zusammenfassung aller Testklassen

- TDD soll mal ausprobiert werden
  - Dafür müssen wir eine zusätzliche methode implementieren die irgendwie sinn ergibt
  - In diesem Fall können wir dann zuerst die Testfälle schreiben und dann die Methode implementieren 

- Es sind aktiv pro Team Member 3 Pull Requests einzusehen die aktiv kommentiert, challenged und konstruktive Beiträge haben

- Es soll eine kleine Dokumentation erstellt werden
  - Kurze Planung (ohne GANT)
  - Grobe Architektur soll visualisiert werden
  - Soll ein kleines Test Konzept vorweisen
  - Kleine Reflexion über TDD und Code Reviews