import java.util.ArrayList;

public class ZufallsBruchGenerator {
	
	public static ArrayList<ArrayList<Object>> tabelleFuellen(int laenge, int breite){
		//f?llt eine Tabelle mit zuf?lligen Zahlen
		ArrayList<ArrayList<Object>> spielbrett= new ArrayList<ArrayList<Object>>();
		for(int i=0;i<breite;i++) {
			//f?gt der Tabelle soviele Listen (Spalten) hinzu, wie die Tabelle breit ist
			spielbrett.add(listeFuellen(laenge));
		}
		return spielbrett;
	}
	public static ArrayList<Object> listeFuellen(int laenge){
		ArrayList<Object> liste=new ArrayList<Object>();
		for(int i=0;i<laenge;i++) {
			//f?gt soviele Elemente hinzu, wie die Liste lang ist
			liste.add(zufallsBruch());
		}
		return liste;
	}
	public static Fraction zufallsBruch() {
		//erschafft einen zuf?lligen Bruch, der im Wertebereich von 1 bis 10 liegt
		//erschafft zuf?llige Zahl zwischen 1 und 99
		int nenner=zufallsZahl(1,99);
		//erschafft eine zuf?llige Zahl zwischen nenner und dem niedrigeren Wert von nenner*10 und 99
		int zaehler=zufallsZahl(nenner,Math.min(nenner*10, 99));
		//gibt den Bruch zur?ck
		return new Fraction(zaehler+"", nenner+"");
	}
	public static int zufallsZahl(int min,int max) {
		//erschafft eine zufllige Zahl zwischen dem angegebenem minimum und maximum
		return (int) (Math.random()*(max-min+1)+min);
	}
}
