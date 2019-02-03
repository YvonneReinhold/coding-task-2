package merge;

import java.util.ArrayList;

import merge.util.DebugMemoryAndTime;

/**
 * @author Yvonne Reinhold
 * <p>
 * Aufgabenstellung:<p>
 * Bereitstellen einer MERGE-Funktion, die eine Liste von Intervallen entgegennimmt und als
 * Ergebnis wiederum eine Liste von Intervallen zurückgibt. Im Ergebnis sollen alle sich
 * überlappenden Intervalle gemerged sein. Alle nicht überlappenden Intervalle bleiben unberührt.<br>
 * Beispiel: Input: [25,30] [2,19] [14, 23] [4,8] -> Output: [2,23] [25,30]
 */
public class MergeInterval 
{
  public static boolean DEBUG_MODE = true;

  /**
   * Die Methode führt die folgenden Schritte aus:
   * 
   * <ol><li>Protokollieren der Startzeit und des belegten Speichers für spätere Auswertungen.</li>
   *     <li>Übernahme der Befehlszeilenparameter. Diese werden syntaktisch geprüft. Im Fehlerfall wird das betreffende Intervall ignoriert und es wird eine entsprechende Meldung ausgegeben.</li>
   *     <li>Zusammenfassen (mergen) der Intervalldaten.</li>
   *     <li>Ausgabe der Ergebnisliste, welche die zusammengefassten Intervalle enthält.</li>
   *     <li>Protokollieren der Endzeit und des belegten Speichers.</li>
   *     <li>Auswertung der Kennzahlen "Programmlaufzeit" / "benötigter Speicher".</li></ol>
   *     
   * @param intervals Die Intervaldaten werden im Format [\d+,\d+] erwartet. Zwischen den einzelnen Intervallen muss ein Leerzeichen stehen. 
   *                  Mit dem Parameter <code>-f</code> (als ersten Parameter) kann eine Textdatei angeben werden, welche die Intervalldaten enthält.
   */
  public static void main(String[] intervals) 
  {
    DebugMemoryAndTime debugMemTime = new DebugMemoryAndTime();
    debugMemTime.start();
    
    // Objekt übernimmt das Mergen der Intervalldaten
    IntervalList myIntervalList = new IntervalList();
    // Die übergebenen Programm-Argumente initialisieren 
    if ( !myIntervalList.init(intervals) )
    {
      System.err.println( "Es wurden keine Intervaldaten angegeben. Bitte übergeben Sie die gewünschten Intervallgrenzen als Aufruf-Parameter.");
      return;
    }
    
    if (DEBUG_MODE){ debugMemTime.checkTimeAndReport("Nach dem Verarbeiten der Befehlszeilen-Argumente"); }
    
    ArrayList<int[]> mergedList = myIntervalList.merge();

    if (DEBUG_MODE){ debugMemTime.checkTimeAndReport("Nach dem Mergen"); }
    
    PRINT(mergedList);
    
    if (DEBUG_MODE)
    { 
      debugMemTime.checkTimeAndReport("Gesamt");
      System.out.println( "Anzahl Elemente in der Ausgangsliste: " + myIntervalList.getIntervalList().size());
      System.out.println( "Anzahl Elemente in der Ergebnisliste: " + mergedList.size());
    }
  }

  /**
   * Ausgabe der Liste im gewünschtem Zielformat. Dieses entspricht dem Eingabeformat der Intervalldaten.
   * Die Daten werden nach Standard-Out geschrieben.
   * 
   * @param list Die auszugebende Liste.
   */
  private static void PRINT( ArrayList<int[]> list) 
  {
    if ( list == null )
    {
      System.out.println("Liste ist leer.");
      return;
    }
    for (int[] is : list) {
      System.out.printf( "[%d,%d] ", is[0], is[1] );
    }
    System.out.println("\n");
  }
}
