import java.math.BigInteger;
import java.util.ArrayList;

public class Ausgabe {
	//Klasse zum Ausgeben des Spielfeldes
	
	public static void ausgabe(ArrayList<ArrayList<Object>> spielbrett, ArrayList<Spieler> spielerArray) {
		//gibt das Spielfeld in der Konsole aus
		//setzt mit "-" und "|" waagerechte bzw senkrechte Trennlinien
		IO.write(printNTimesChar(6*spielbrett.get(0).size()+1,"-"));
			//"zieht" Trennlinien. Jedes Element im Spielbrett ben?tigt 6 Zeichenpl?tze (5+1 senkrechte Trennlinie)
		for(ArrayList<Object> line:spielbrett) {
			//jede Zeile wird separat ausgegeben
			String up="|",mid="|",down="|";
			//jede Zeilenausgabe hat drei Zeilen
			for(Object obj:line) {
				if(obj instanceof Spieler) {
					//plus statt leerzeichen, um sie besser zu finden
					up+="  +  ";
					mid+=" +"+((Spieler)obj).getName().substring(0,1)+"+ ";
					down+="  +  ";
				}else {
					Fraction frac=(Fraction)obj;
					
					String frac10=" ";
					if(!(frac.compareTo(Fraction.TEN)==0)) {
						//wenn frac 10 wird, wird ein leerzeichen eingef?gt, damit die Formatierung gleich ist
						frac10+=" ";
					}
					if(frac.isInteger()) {
						//wenn Bruch Integer ist, wird er in der mittleren Zeile ausgegeben
						up+="     ";
						mid+="  "+frac.toString()+frac10;
						down+="     ";
					}else {
						//tats?chliche Br?che werden wie folgt ausgegeben
						//zaehler in der oberen Zeile, Trennstrich in der mittleren, nenner in der unteren zeile
						String[] s=vereinheitliche(frac);
							//Bruch wird in externer Methode vereinheitlicht
						up+=s[0];
						mid+="-----";
						down+=s[1];
					}
				}
				up  +="|";
				mid +="|";
				down+="|";
			}
			IO.write(up+"\n"+mid+"\n"+down);
				//Zeile wird ausgegeben
			IO.write(printNTimesChar(6*spielbrett.get(0).size()+1,"-"));
				//und Trennlinie gezogen
		}
	}
	public static String[] vereinheitliche(Fraction frac) {
		//trennt Bruch in zwei vereinheitliche Strings, die in Array zur?ckgegeben werden
		//Ziel: Darstellung (PLatzverbrauch) vereinheitlichen, damit Zeilen und Spalten b?ndig dargestellt werden
		String[] s=new String[2];
			
		if(frac.zaehler.compareTo(BigInteger.TEN)>=0) {
			//ein zaehler gr??er/gleich 10 ben?tigt nur ein nachlaufendes Leerzeichen
			//da die zahl selbst zwei Zeichenpl?tze braucht
			s[0]="  "+frac.zaehler+" ";
		}else {
			//einem zaehler kleiner 10 werden zwei Leerzeichen angeh?ngt
			s[0]="  "+frac.zaehler+"  ";
		}
		if(frac.nenner.compareTo(BigInteger.TEN)>=0) {
			//selbiges wie oben, nur mit nenner
			s[1]="  "+frac.nenner+" ";
		}else {
			s[1]="  "+frac.nenner+"  ";
		}
		return s;
	}
	public static String printNTimesChar(int n, String c) {
		//Methode zum n-fachen Ausgeben eines Characters  
		//Verwendung: Trennlinien in der Ausgabe
		String s="";
		for(;n>0;n--) {
			s+=c;
		}
		return s;
	}
}
