package gui;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author: Paul Keller
 * date: 19.04.2018
 * version: 1.0
 */
public class Spielbrett implements Serializable{
    //Diese Klasse speichert die aktuellen Spieler, das Spielbrett (bestehend aus Fractions und Spielern)und die Größe und Breite des Spiels
    private ArrayList<Spieler> spielerArray;
    private ArrayList<ArrayList<Object>> spielbrett;
    private int laenge;
    private int breite;

    public Spielbrett(){
        spielerArray=new ArrayList<>();
        spielbrett=new ArrayList<>();
        laenge=0;
        breite=0;
    }

    public int getBreite() {
        return breite;
    }

    public int getLaenge() {
        return laenge;
    }
    public void setBreite(int b){
        breite=b;
    }
    public void setLaenge(int l){
        laenge=l;
    }

    public ArrayList<ArrayList<Object>> getSpielbrett() {
        return spielbrett;
    }

    public ArrayList<Spieler> getSpielerArray() {
        return spielerArray;
    }

    public void setSpielbrett(ArrayList<ArrayList<Object>> spielbrett) {
        this.spielbrett = spielbrett;
    }

    public void setSpielerArray(ArrayList<Spieler> spielerArray) {
        this.spielerArray = spielerArray;
    }
    protected Object getElementSpielbrett(int x, int y) {
        // returnt das Element auf dem Spielbrett an den angegebenen Koordinaten
        // returnt null-referenz, wenn ein Fehler auftritt
        //(z.B. wenn die Koordinaten au?erhalb des Spielbretts liegen
        try {
            return spielbrett.get(y).get(x);
        } catch (Exception e) {
            return null;
        }
    }
    protected void setElementSpielbrett(Object obj, int x, int y) {
        // setzt ein Objekt auf das Feld an der angegebenen Position
        // kann Fehler produzieren, wenn nicht vorher koordinaten gepr?ft werden
        spielbrett.get(y).set(x, obj);
    }
}
