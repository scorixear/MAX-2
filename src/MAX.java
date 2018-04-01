import gui.GameFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * @author Katharina Will, Jannik Niedermayer, Luca G?ttle
 * @version 1.0 28.12.2017
 * 
 */
public class MAX {
	int laenge; //laenge des Spielbretts
	int breite; // Breite "" ""
	int modus;  // ungenutzt. damit kann der modus des Spiels beeinflusst werden. (z.B. dass das Spielfeld offene grenzen hat (Spieler l?uft rechts aus dem Spielfeld und erscheint links wieder))
	Fraction limit; //limit, bei dessen erreich ein Spieler gewinnt
	ArrayList<ArrayList<Object>> spielbrett=new ArrayList<ArrayList<Object>>();
	// Array mit den Fractions und Spielern
	ArrayList<Spieler> spielerArray=new ArrayList<Spieler>();
	// Array, in dem die Spieler gespeichert sind
	
	
	public int getLaenge() {
		return laenge;
	}
	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}
	public int getBreite() {
		return breite;
	}
	public void setBreite(int breite) {
		this.breite = breite;
	}
	public int getModus() {
		return modus;
	}
	public void setModus(int modus) {
		this.modus = modus;
	}
	public ArrayList<ArrayList<Object>> getSpielbrett() {
		return spielbrett;
	}
	public void setSpielbrett(ArrayList<ArrayList<Object>> spielbrett) {
		this.spielbrett = spielbrett;
	}
	public void anmeldung(GameFrame frame) throws Exception {
		//Anmeldung der Spieler
		// erster Spieler wird gezwungen, sich anzumelden (muss so lange eingaben machen, bis er einen String eingibt)
		// jeder Weitere kann sich anmelden
		// zum Beenden der Anmeldung wird Enter bei leerer Eingabe gedr?ckt
		final String[] s = new String[1];
		int counter=1;

		IO.write("Anmeldung. Drücke Enter zum Beenden der Anmeldungen.\nSpieler sollten unterschiedliche Anfangsbuchstaben haben zur Unterscheidung.\nMindestens ein Spieler muss angemeldet werden" );
		while(true) {
			//erster Spieler wird gezwungen, sich anzumelden
			//bricht erst aus Schleife aus, wenn Spieler sich angemeldet hat
			frame.getInsertLabel().setText("Spieler "+(counter)+", gib deinen Namen ein");
			int finalCounter = counter;
			frame.getInsertTextField().addActionListener(e -> {
                if(frame.getInsertLabel().getText().equals("Spieler "+(finalCounter)+", gib deinen Namen ein"))
				{
					s[0] =frame.getInsertTextField().getText();
				}
				if(s[0].equals(""))
				{

				}
            });
			//s[0] =IO.promptAndRead("Spieler "+(counter)+", gib deinen Namen ein");
			if(s[0] !=null) {
				spielerArray.add(new Spieler(s[0],0,0));
				counter++;
				break;
			}
		}
		while(s[0].length()>0) {
			counter++;
			//jeder weitere Spieler kann sich anmelden. 
			//wird beendet, wenn a) kein neuer Name eingegeben wird, oder b) die max. Spieleranzahl von 8 erreicht ist
			spielerArray.add(new Spieler(s[0],0,0));
			if(spielerArray.size()>=8) {
				break;
			}
		}
		//Spieler werden zur kontrolle ausgegeben
		IO.write("\nSpieler sind: ");
		for(Spieler spieler:spielerArray) {
			IO.write(spieler.getName());
		}
	}
	
	public MAX() throws Exception {
		setLimit(new Fraction("80","1"));
		//Limit (bei dessen erreichen gewonnen wird) wird gesetzt
		
		anmeldung(); //ruft anmeldung auf, um die Spieler anzumelden
		int  dimension; //Variable f?r laenge, breite, die bestimmt wird
		do{
			dimension=IO.promptAndReadInt("Bitte gib die L?nge des Spielfeldes ein (mind. 8, max. 20)");
		}while(dimension<8 || dimension>20); //jeder wert ab 8 bis 20 wird akzeptiert
		setLaenge(dimension); //g?ltiger wert wird als laenge gesetzt
		do{
			dimension=IO.promptAndReadInt("Bitte gib die Breite des Spielfeldes ein (mind. 8, max. 20)");
		}while(dimension<8 || dimension>20);
		setBreite(dimension); //g?ltiger wert wird als breite gesetzt
		
		
		spielbrett=ZufallsBruchGenerator.tabelleFuellen(getLaenge(), getBreite());
		//das Spielbrett wird mit zuf?llig generierten Zahlen gef?llt.
		// genutzt wird daf?r eigene,statische Klasse
		int counter=0;
		for(Spieler spieler:spielerArray) {
			//Spieler werden auf das Spielfeld gesetzt
			generierePosition(spieler,counter);
			//Spieler werden Koordinaten nach festem Muster zugewiesen
			setElementSpielbrett(spieler,spieler.getPosX(),spieler.getPosY());
			// und auf das Brett gesetzt
			counter++;
		}
	}
	public void generierePosition(Spieler spieler, int x) {
		// bis zu 8 Spieler werden nach festem Muster statisch gesetzt 
		// dies geschieht in einem 8x8 Rahmen. 
		// dieser Rahmen wird innerhalb des Spielbretts "in die Mitte geschoben"
		// deswegen Laenge und Breite durch 2, mit derer der Rahmen verschoben wird
		int yFaktor=(getBreite()-8)/2;
		int xFaktor=(getLaenge()-8)/2;
		switch(x) {
			case 0: spieler.setPosX(4+xFaktor); spieler.setPosY(3+yFaktor);
				break;
			case 1: spieler.setPosX(3+xFaktor); spieler.setPosY(4+yFaktor);
				break;
			case 2: spieler.setPosX(2+xFaktor); spieler.setPosY(2+yFaktor);
				break;
			case 3: spieler.setPosX(5+xFaktor); spieler.setPosY(5+yFaktor);
				break;
			case 4: spieler.setPosX(6+xFaktor); spieler.setPosY(1+yFaktor);
				break;
			case 5: spieler.setPosX(1+xFaktor); spieler.setPosY(6+yFaktor);
				break;
			case 6: spieler.setPosX(0+xFaktor); spieler.setPosY(0+yFaktor);
				break;
			case 7: spieler.setPosX(7+xFaktor); spieler.setPosY(7+yFaktor);
				break;
		}
	}
	public Object getElementSpielbrett(int x, int y) {
		// returnt das Element auf dem Spielbrett an den angegebenen Koordinaten
		// returnt null-referenz, wenn ein Fehler auftritt 
			//(z.B. wenn die Koordinaten au?erhalb des Spielbretts liegen
		try {
			return spielbrett.get(y).get(x);
		}catch(Exception e) {
			return null;
		}
	}
	public void setElementSpielbrett(Object obj, int x, int y) {
		// setzt ein Objekt auf das Feld an der angegebenen Position
		// kann Fehler produzieren, wenn nicht vorher koordinaten gepr?ft werden
		spielbrett.get(y).set(x, obj);
	}
	public void ausgabe() {
		//Aufruf zurAusgabe des Spielbretts
		Ausgabe myAusgabe=new Ausgabe();
		myAusgabe.ausgabe(spielbrett, spielerArray);
		for(Spieler s:spielerArray) {
			//Punktest?nde werden angegeben (genau sowie gerundet)
			IO.write(s.getName()+" "+s.getScore()+" "+Math.round(s.getScore().doubleValue()));
		}
	}
	
	public Fraction getLimit() {
		return limit;
	}
	public void setLimit(Fraction limit) {
		this.limit = limit;
	}
	public void bewegeSpieler(Spieler spieler, int direction) {
		//direction: 8--> oben, 2--> unten, 6--> rechts, 4--> links
		//gibt die Anweisung, einen Spieler in eine der vier Himmelsrichtungen zu bewegen
		int x=spieler.getPosX();
		int y=spieler.getPosY();
		switch (direction) {
			case 2: bewegung(spieler,x,y,x,++y);	
				break;
			case 4: bewegung(spieler,x,y,--x,y);	
				break;
			case 6: bewegung(spieler,x,y,++x,y);	
				break;
			case 8: bewegung(spieler,x,y,x,--y);	
				break;
		}
	}
	public void bewegung(Spieler spieler, int xquell, int yquell, int xziel, int yziel) {
		// Ausf?hrung der Anweisung, einen Spieler zu bewegen
		// addiert Zielfeld zum Spieler-Score, bewegt Spieler, und setzt verlassenes Feld auf Null
		Fraction frac=(Fraction) getElementSpielbrett(xziel, yziel);
		//element an Ziel kann ohne test in Frac gecastet werden, da davor schon getestet wird, ob der Zug g?ltig ist
		spieler.addiere(frac);
		//Fraction am Ziel wird dem Score des Spielers hinzuaddiert
		setElementSpielbrett(spieler, xziel, yziel);
		//Spieler wird auf dem Feld bewegt
		spieler.setPosX(xziel);
		spieler.setPosY(yziel);
		//koordinaten werden auch im Spieler-Obj gespeichert
		setElementSpielbrett(new Fraction("0","1"), xquell, yquell);
		//das vom Spieler verlassene Feld wird auf Null gesetzt
	}
	public boolean kannBewegen(Spieler spieler, int direction) {
		//testet, ob ein vom Spieler ausgehender Zug g?ltig ist
		// -> ung?ltig, wenn a) Zug ausserhalb des Spielfeldes f?hrt
		// oder b) am Ziel bereits ein Spieler steht
		int x=spieler.getPosX();
		int y=spieler.getPosY();
		Object obj;
		switch (direction) {
			case 2: obj=getElementSpielbrett(x,++y);	
				break;
			case 4: obj=getElementSpielbrett(--x,y);	
				break;
			case 6: obj=getElementSpielbrett(++x,y);	
				break;
			case 8: obj=getElementSpielbrett(x,--y);	
				break;
			default:
				return false;
		}
		return (obj instanceof Fraction);
		//wenn obj eine Fraction ist: true, wenn Spieler: false
	}
	
	public void spiele() throws Exception {
		//startet Spiel
		IO.write("Spiel startet. Zum Steuern Nummernblock verwenden.\n2 und 8 zum vertikalen Bewegen,\n4 und 6 zum horizontalen Bewegen."
				+"\nSchreibe <Beenden> zum Beenden\n");
		while(true) {
			//Spiel-Schleife. wird sie beendet, ist das Spiel vorbei
			
			for(Spieler sp:spielerArray) {
				int counter=0;
				//zaehler gibt an, welcher Spieler dran ist
				//-> hilft beim L?schen (wenn dieser aufgibt)
				while(true) {
					ausgabe();
					//neue ausgabe wird gemacht
					String s=IO.promptAndRead(sp.getName()+" du bist am Zug");
					if(s.equalsIgnoreCase("beenden")) {
						IO.write("Unentschieden. Spiel wurde abgebrochen");
						return;
					}
					try {
						//es wird gepr?ft, ob der Zug g?ltig ist
						//und ausgef?hrt. der n?chste Spieler ist dann dran
						//bei ung?ltiger Eingabe wird der Spieler aufgefordert, erneut eine Eingabe zu machen
						if(kannBewegen(sp,Integer.parseInt(s))) {
							bewegeSpieler(sp,Integer.parseInt(s));
							counter++;
							break;
						}
						}catch(Exception e) {
					}
					counter++;
				}
				if(testScore()||spielerArray.size()==0) {
					//es wird ?berpr?ft, ob ein Spieler gewonnen hat, oder alle Spieler aufgegeben haben
					//wenn true, wird spiel beendet
					IO.write("Spiel wurde beendet");
					return;
				}
			}
		}
	}

	public boolean testScore() {
		//?berpr?ft den Score der Spieler, ob sie das Limit bereits erreicht haben
		ArrayList<Spieler>gewinner=new ArrayList<Spieler>();
			//Liste der Gewinner
		for(Spieler sp:spielerArray) {
			//alle Spieler, die die Punktzahl erreicht haben, werden in der ArrayList gewinner gespeichert
			if(sp.getScore().compareTo(getLimit())>=0) {
				gewinner.add(sp);
			}
		}
		if(gewinner.size()>0) {
			//wenn es einen (oder mehrere gewinner gibt), werden diese ausgegeben
			String s="";
			for(Spieler sp:gewinner) {
				s+=" "+sp.getName();
			}
			IO.write("Gewinner sind"+s+".");
			return true;
				//return true gibt an, dass das Spiel vorbei ist (ein gewinner gefunden wurde)
		}
		return false;
		//return false gibt an, dass bis jetzt noch kein gewinner gefunden wurde (das spiel l?uft weiter)
	}
	public static void main(String[] args) throws Exception {
		MAX mySpiel=new MAX();
		mySpiel.spiele();
	}
}
