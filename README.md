# Coding Task 2: Zusammenfassen von Intervallen

## Getroffene Annahmen / Vorgaben
1. Die Intervaldaten werden im Format [\d+,\d+] erwartet. Zwischen den einzelnen Intervallen muss ein Leerzeichen stehen.  
   Jeder Wert einer Bereichsgrenze ist ganzzahlig und positiv.
2. F�r die Bereichsgrenzen gilt der Zahlenbereich von 0 bis bis 2.147.483.646 (32 Bit). 
3. Eine Intervallangabe darf innerhalb der eckigen Klammern keine Leerzeichen enthalten. 

## Systemvoraussetzungen
* Java Version 8 oder h�her
* Ant Version 1.9 oder h�her
* Die Umgebungsvariablen JAVA\_HOME und ANT\_HOME m�ssen gesetzt sein und auf die korrekten Verzeichnisse verweisen

## Projekt bauen
Das Projekt wird �ber das Ant-Buildfile gebaut.
0. Dieses Git-Projekt in ein beliebiges lokales Verzeichnis herunterladen
1. Unter Windows eine Kommandozeile �ffnen
2. In das Projekt-Verzeichnis wechseln 
3. 'ant compile' ausf�hren

## Projekt ausf�hren
1. Unter Windows eine Kommandozeile �ffnen
2. In das Projekt-Verzeichnis wechseln 
3. MERGE.cmd ausf�hren

Folgende Befehlszeilenparameter stehen zur Verf�gung  

**-f <dateiname>**
  (optional) Intervaldaten werden als Textdatei �bergeben. Die Option -f muss als erster Parameter angegeben werden.  

#### Beispiele f�r den Aufruf

* Direkte Angabe der Intervalle �ber die Befehlszeile.
    
    MERGE [25,30] [2,19] [14,23] [4,8]

* Angabe der Intervalle �ber eine Textdatei
    
    MERGE -f test/testdaten.txt


##Profiling
Zum Messen der Programmlaufzeit und des Speicherbedarfs steht in der Hauptklasse MergeInterval eine statische Variable 'MergeInterval.DEBUG_MODE' zur Verf�gung.
Wird diese auf TRUE gesetzt, werden zus�tzliche Informationen zum Profiling auf Stdout ausgegeben.

Folgende Feststellungen konnten getroffen werden:   

Der gr��te Teil der Verarbeitungszeit wird f�r die Ein- und Ausgabe der Intervalldaten ben�tigt.  
F�r das Mergen der Daten wird ein relativ geringer Teil der gesamten Laufzeit ben�tigt.  
Getestet wurde mit verschiedenen Datenmengen. Aus Zeitgr�nden wurden keine Durchschnittswerte mit mehreren L�ufen ermittelt.

* **Ausgangsliste mit 5 Intervallen, Ergebnisliste mit 2 Intervallen**  
Verarbeitung der Eingabeparameter: 3 ms  
Mergen der Intervalldaten: 2 ms  
Speicherverbrauch/Gesamtlaufzeit: 683896 Byte (667kB), 29 ms   


* **Ausgangsliste mit 436 Intervallen, Ergebnisliste mit 3 Intervallen**  
Verarbeitung der Eingabeparameter: 10 ms  
Mergen der Intervalldaten: 3 ms  
Speicherverbrauch/Gesamtlaufzeit: 682608 Byte (666kB), 40 ms  


* **Ausgangsliste mit 50000 Intervallen, Ergebnisliste mit 3 Intervallen**  
Verarbeitung der Eingabeparameter: 132 ms  
Mergen der Intervalldaten: 56 ms  
Speicherverbrauch/Gesamtlaufzeit: 25596808 Byte (24996kB), 221 ms  
