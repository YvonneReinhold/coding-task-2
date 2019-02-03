package test;

import java.util.Random;

/**
 * Hilfsklasse zur Generierung von zufälligen Testdaten.
 */
public class GenerateTestData 
{

  public static void main(String[] args) 
  {
    generateTestData( 10 );
    System.out.println( "\n" );
    generateBigTestData( 10000 );
  }

  private static void generateTestData( int count )
  {
    Random random = new Random();
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(70) ); 
      int upperBound = Math.abs( random.nextInt(10) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
  }
  
  private static void generateBigTestData( int count )
  {
    Random random = new Random();
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(70) ); 
      int upperBound = Math.abs( random.nextInt(7) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(200) ) + 80; 
      int upperBound = Math.abs( random.nextInt(10) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(400) ) + 200; 
      int upperBound = Math.abs( random.nextInt(10) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(600) ) + 400; 
      int upperBound = Math.abs( random.nextInt(10) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
    for(int i=0; i<count; i++)
    {
      int lowerBound = Math.abs( random.nextInt(700) ) + 600; 
      int upperBound = Math.abs( random.nextInt(20) ) + lowerBound;
      System.out.printf( "[%d,%d] ", lowerBound, upperBound );
    }
  }
}
