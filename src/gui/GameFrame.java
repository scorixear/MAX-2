package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class GameFrame extends JFrame {
    private JButton[][] fractionbuttons;                                                                                //Fraction - Feld Darstellung durch Buttons
    private JPanel gamePanel;                                                                                           //enthält die Buttons über ein GridLayout
    private JPanel inputPanel;                                                                                          //enthält Label und Inputtextfield
    private JTextField insertTextField;
    private JLabel insertLabel;                                                                                         //gibt Informationen über erwarteten Input
    private ArrayList<Spieler> spielerArray = new ArrayList<>();                                                        //Liste aller Spieler
    private ArrayList<ArrayList<Object>> spielbrett= new ArrayList<>();
    private GameMechanic gameMechanics;
    private int laenge;                                                                                                 //Anzahl an Reihen des Spielfeldes
    private int breite;
    //Anzahl an Spalten des Spielfeldes
    GameFrame(){
        setTitle("Max-GUI");
        setLayout(null);

        insertLabel=new JLabel("Gebe die Laenge des Spielfeldes an");
        insertTextField=new JTextField("");
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(insertLabel);
        inputPanel.add(insertTextField);
        insertTextField.addActionListener(new GameSetup(this));
        inputPanel.setBounds(10,8,270,35);


        getContentPane().add(inputPanel);
        gameMechanics=new GameMechanic(this);
        getContentPane().addKeyListener(gameMechanics);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,300,100);
        //Setzt das Fenster in die Mitte des Bildschirms, abhängig von der eingestellten Auflösung des Bildschirms und der Größe des Frames
        setVisible(true);

        gamePanel = new JPanel();
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
    protected JTextField getInsertTextField() { return insertTextField; }
    protected JLabel getInsertLabel() { return insertLabel; }

    protected int getLaenge(){ return laenge; }
    protected void setLaenge(int laenge){ this.laenge=laenge; }

    protected int getBreite(){ return breite; }
    protected void setBreite(int breite){ this.breite=breite; }


    protected ArrayList<Spieler> getSpielerArray() { return spielerArray; }

    protected ArrayList<ArrayList<Object>> getSpielbrett() { return spielbrett; }
    protected void setSpielbrett(ArrayList<ArrayList<Object>> list) { spielbrett=list; }

    protected JButton[][] getFractionbuttons() { return fractionbuttons; }
    protected void setFractionbuttons(JButton[][] buttons) { fractionbuttons=buttons; }

    protected JPanel getGamePanel() { return gamePanel; }
    protected JPanel getInputPanel() { return inputPanel; }

    protected GameMechanic getGameMechanics(){
        return gameMechanics;
    }


}
