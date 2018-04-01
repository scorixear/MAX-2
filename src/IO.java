/*
 * class IO  
 * 
 * @author Katharina Will, Jannik Niedermayer, Luca G�ttle
 * @version 3.0 11.12.2017
 * 
 * Klasse IO stellt Methoden f�r die Keyboard-Eingabe zur Verf�gung
 * unterst�tzt alle primitiven Datentypen sowie BigInteger, BigDecimal und Fraction
 * 
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;

class IO{
	//IO-Klasse zur Ausgabe und Eingabe
	public IO() {
		}
		public static void write(String s) {
			writeAndFlush(s);
		}
		
		public static String promptAndRead(String s) throws Exception{
			writeAndFlush(s);
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		public static int promptAndReadInt(String s) throws Exception{
			writeAndFlush(s);
			try {
				return Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());
			}catch(Exception e) {
				return promptAndReadInt(s);
			}
		}
		public static double promptAndReadDouble(String s) throws Exception{
			writeAndFlush(s);
			return Double.parseDouble(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
		public static float promptAndReadFloat(String s) throws Exception{
			writeAndFlush(s);
			return Float.parseFloat(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
		public static long promptAndReadLong(String s) throws Exception{
			writeAndFlush(s);
			return Long.parseLong(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
		public static BigInteger promptAndReadBigInteger(String s) throws Exception{
			writeAndFlush(s);
			return new BigInteger(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
		public static BigDecimal promptAndReadBigDecimal(String s) throws Exception{
			writeAndFlush(s);
			return new BigDecimal(new BufferedReader(new InputStreamReader(System.in)).readLine());
		}
		
		private static void writeAndFlush(String s){
			System.out.println(s);
			System.out.flush();
		}
		public static Fraction promptAndReadFraction(String s) throws IOException {
			writeAndFlush(s); //sagt User, was er eingeben soll
			//z.B. "3/5"
			Fraction frac=convertStringToFraction(new BufferedReader(new InputStreamReader(System.in)).readLine());
			return frac;
		}
		public static Fraction convertStringToFraction(String s) {
			try {
				int posSlash=s.indexOf("/");
				return new Fraction(s.substring(0,posSlash),s.substring(posSlash+1,s.length()));
			}catch(NumberFormatException e) {
				writeAndFlush("Ihre Eingabe konnte nicht in eine Fraction konvertiert werden.\n Fractions bitte im Format XXX/YYY");
				return null;
			}
		}
}