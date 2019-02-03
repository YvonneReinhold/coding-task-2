package merge;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntervalList {

  private static int OBERGRENZE = 1;
  private static int UNTERGRENZE = 0;
  private static Pattern STRINGPATTERN = Pattern.compile("\\[(\\d+),(\\d+)\\]");
  private ArrayList<int[]> intervalList;

  public ArrayList<int[]> getIntervalList() 
  {
    return intervalList;
  }

  public IntervalList()
  {
    intervalList = new ArrayList<int[]>();
  }

  public boolean init(String[] args) 
  {
    // Pr�fen, ob Daten �bergeben wurden. Falls nicht, gibt es nichts zu verarbeiten und das Programm wird beendet.
    if (args == null || args.length == 0) {
      return false;
    }

    if ( args[0].equalsIgnoreCase("-f") )
    {
      // Die Daten werden per Textdatei �bergeben
      File argsFile = new File(args[1]);
      if ( argsFile.exists() )
      {
        try (BufferedReader bufReader = new BufferedReader(new FileReader(argsFile))) 
        {
          String line = null;
          String[] fargs = null;
          while ( (line = bufReader.readLine()) != null)
          {
            fargs = line.split("\\s");
            for (String farg : fargs) 
            {
              if (!add( farg )) 
              {
                System.err.println("WARNUNG: Das angegebene Intervall '" + farg + "' entspricht nicht der vorgegebenen Syntax. Dieses Intervall wird nicht verarbeitet.");
              }
            }
          }
        } 
        catch (IOException e) 
        {
          System.err.println("FEHLER: Die angegebene Datei '" + args[1] + "' kann nicht gelesen werden. " + e.getMessage() );
          return false;
        }
      }
      else
      {
        System.err.println( "FEHLER: Die angegebene Datei '" + args[1] +"' konnte nicht gefunden werden. Bitte pr�fen Sie den Dateipfad/-namen.");
        return false;
      }
    }
    else
    {
      // Die Daten werden direkt als Befehlszeilenargumente �bergeben
      for (int i=0; i<args.length; i++ )
      {
        if (!add( args[i] )) 
        {
          System.err.println("WARNUNG: Das angegebene Intervall '" + args[i] + "' entspricht nicht der vorgegebenen Syntax. Dieses Intervall wird nicht verarbeitet.");
        }
      }
    }
    return true;
  }
  
  /**
   * F�gt ein Intervall hinzu. Das angegebene Intervall muss dem vorgegebenen Format entsprechen.<br>
   * <i>Hinweis: Ist der erste Intervallgrenzwert gr��er als der zweite Wert, dann werden die Grenzwerte vertauscht.
   * Damit wird sichergestellt, dass die untere Intervallgrenze immer den kleineren Wert enth�lt. Dies ist eine wichtige Vorraussetzung f�r den Merge-Algorithmus.</i>
   * 
   * @param pInterval String im Format <code>[\d+,\d+]</code>
   * @return true - Das Intervall wurde erfolgreich hinzugef�gt.<br>
   *         false - Der Intervalltext entspricht nicht dem gefordertem Format. Die Daten werden nicht verarbeitet.
   * 
   * @throws IntervalListException
   */
  public boolean add( String pInterval )
  {
    // den Intervalltext parsen
    Matcher matcher = STRINGPATTERN.matcher( pInterval );
    if ( matcher.matches() )
    {
      // Intervall wurde im geforderten Format angegeben
      int limit1 = Integer.parseInt( matcher.group(1));
      int limit2 = Integer.parseInt( matcher.group(2));
      // Intervallwerte der Liste hinzuf�gen, dabei wird der kleinere Wert als Untergrenze gesetzt, der gr��ere Wert als Obergrenze
      intervalList.add(new int[] {Math.min(limit1, limit2),Math.max(limit1, limit2)});
      return true;
    }
    else
    {
      // Der Intervalltext entspricht nicht dem gefordertem Format. Die Daten werden nicht verarbeitet.
      return false;
    }
  }

  /**
   * Sortiert die Liste mit Intervallen aufsteigend anhand der unteren Intervallgrenzen.<br>
   * Beispiel einer sortierten Liste: [2,19] [4,8] [14,23] [25,30] 
   */
  private void sort() {
    intervalList.sort( new Comparator<int[]>() {

      @Override
      public int compare(int[] o1, int[] o2) {
        if ( o1[UNTERGRENZE] < o2[UNTERGRENZE] ) { return -1; }
        if ( o1[UNTERGRENZE] > o2[UNTERGRENZE] ) { return 1; }
        return 0;
      }
    });
    
  }
  
  /**
   * Fa�t die Bereiche zusammen. Die Eingangsliste bleibt dabei unver�ndert. Es wird eine neue Liste mit den zusammengefassten Bereichen zur�ckgegeben.<p>
   * 
   * Vorbedingungen f�r den Merge-Algorithmus sind:
   * <ol><li>Die Liste ist anhand der Intervalluntergrenzen aufsteigend sortiert</li>
   *     <li>Der untere Grenzwert eines Intervalls ist immer kleiner als der obere Grenzwert</li></ol>
   * 
   * Funktionsweise des Merge-Algorithmus:<p>
   * Da die Liste sortiert vorliegt, kann davon ausgegangen werden, dass es bei aufeinanderfolgenden Bereichen keine kleineren Untergrenzen gibt.
   * Der Merge-Algorithmus arbeitet sequenziell. Die ArrayList wird genau einmal durchlaufen.
   * Es werden immer 2 Bereiche auf �berlappung gepr�ft. Falls sich diese �berlappen, so werden sie zusammengefasst.
   * Gibt es keine �berlappung, dann wird der neue zusammengefasste Bereich in die Ergebnisliste �bernommen.
   * Alle Bereiche ohne �berlappung werden unver�ndert in die Ergebnisliste �bernommen.
   *  
   * @return Eine neue Liste mit den gemergten Intervallen.
   */
  public ArrayList<int[]> merge()
  {
    if ( intervalList.size() <= 1 )
    {
      return intervalList;
    }
    // Im ersten Schritt werden die Intervalle sortiert
    sort();

    // Liste mit zusammengefassten Intervallen
    ArrayList<int[]> mergedList = new ArrayList<int[]>();
    
    // Die Intervallgrenzen des ersten Eintrags der Liste werden als Startwerte verwendet
    int[] intervalMerged = intervalList.get(0);

    // Mit den n�chsten Elementen der Liste fortfahren
    for ( int idx = 1; idx < intervalList.size(); idx++ ) 
    {
      int[] intervalNext = intervalList.get(idx);
      if ( overlap(intervalMerged, intervalNext) )
      {
        // Maximum der neuen Obergrenze/Untergrenze bestimmen
        intervalMerged = mergeInterval( intervalMerged, intervalNext );
      }
      else
      {
        // Die beiden Bereiche �berlappen sich nicht. Daher wird der erste Bereich in die Ergebnisliste �bernommen.
        // Die Pr�fung wird mit dem zweiten Bereich als Ausgangsbereich fortgesetzt.
        mergedList.add( intervalMerged );
        intervalMerged = intervalNext;
      }
    }
    // damit der letzte Eintrag in der Liste nicht verloren geht, diesen ggf. noch �bernehmen
    if ( !mergedList.contains( intervalMerged) )
    {
      mergedList.add(intervalMerged);
    }
    // die Liste mit den zusammengefassten Bereichen wird zur�ckgegeben
    return mergedList;
  }

  /**
   * Liefert das neue gemergte Intervall, basierend auf den beiden �bergebenen Intervallen, zur�ck.
   * 
   * @param interval1 Bereichsgrenzen des ersten Intervalls.
   * @param interval2 Bereichsgrenzen des zweiten Intervalls.
   * @return Liefert ein neues Intervall zur�ck, welches das Minimum der Untergrenzen, sowie das Maximum der Obergrenzen, der beiden �bergebenen Intervalle enth�lt.
   */
  private int[] mergeInterval(int[] interval1, int[] interval2) 
  {
    int[] newInterval = new int[2];
    // die kleinste unterste Grenze finden
    newInterval[UNTERGRENZE] = Math.min( interval1[UNTERGRENZE], interval2[UNTERGRENZE] );
    // die gr��te oberste Grenze finden
    newInterval[OBERGRENZE] = Math.max( interval1[OBERGRENZE], interval2[OBERGRENZE] );
    return newInterval;
  }

  /**
   * Pr�ft, ob sich 2 Intervalle �berlappen.
   * 
   * @param interval1 Bereichsgrenzen des ersten Intervalls.
   * @param interval2 Bereichsgrenzen des zweiten Intervalls.
   * @return true - Beide Intervalle �berlappen sich.<br>
   *         false - Die Intervalle haben keine �berschneidung.
   */
  private boolean overlap( int[] interval1, int[] interval2 )
  {
    // Die untere Grenze vom Bereich2 muss sich zwischen der Unter- und Obergrenze vom Bereich1 befinden, damit sich die beiden Intervalle �berlappen
    // Annahme: Durch die geforderte Sortierung der Daten liegt das erste Intervall immer vor dem zweiten Intervall.
    if ( interval2[UNTERGRENZE] >= interval1[UNTERGRENZE] && 
         interval2[UNTERGRENZE] <= interval1[OBERGRENZE])
    {
      return true;
    }
    return false;
  }
}
