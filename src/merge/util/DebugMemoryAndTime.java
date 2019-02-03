package merge.util;

/**
 * Protokolliert Speicher und Dauer während der Programmausführung.
 */
public class DebugMemoryAndTime {
  private long timeStart;
  private long memoryStart;

  /**
   * Protokolliert die Startbedingungen. Diese werden für spätere Vergleiche benötigt
   */
  public void start() {
    timeStart = System.currentTimeMillis();
    memoryStart = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    //System.out.println( "Speicherbelegung zum Programmstart:\n" + printMemoryInfo());
  }

  /**
   * Ermittelt Programmlaufzeit und den benötigten Speicherbedarf.
   * Gibt die Kennzahlen nach Standard-Out aus.
   */
  public void checkTimeAndReport(String headline) 
  {
    long usedMemory =Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory() - memoryStart;
    System.out.println(headline + ": Speicherverbrauch " + usedMemory + " Byte (" + ( usedMemory / 1024) + "kB), Dauer: " + (System.currentTimeMillis() - timeStart) + " ms");
  }
}
