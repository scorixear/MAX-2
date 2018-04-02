package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * author: Paul Keller
 * date: 01.04.2018
 * version: 1.0
 */
public class GameFrame extends JFrame {
    private JButton[][] fractionbuttons;
    private JPanel gamePanel;
    private JPanel inputPanel;
    private JTextField insertTextField;
    private JLabel insertLabel;
    private ArrayList<Spieler> spielerArray = new ArrayList<>();
    private ArrayList<ArrayList<Object>> spielbrett=new ArrayList<ArrayList<Object>>();
    private int laenge;
    private int breite;
    public GameFrame(){
        setTitle("Max-GUI");
        setLayout(null);
        insertLabel=new JLabel("Gebe die Laenge des Spielfeldes an");
        insertTextField=new JTextField("");
        gamePanel = new JPanel();
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(insertLabel);
        inputPanel.add(insertTextField);
        insertTextField.addActionListener(new GameSetup(this));
        inputPanel.setBounds(10,8,270,35);
        getContentPane().add(inputPanel);
        getContentPane().addKeyListener(new GameMechanic(this));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,300,100);
        setVisible(true);
    }

    public Object getElementSpielbrett(int x, int y) {
        // returnt das Element auf dem Spielbrett an den angegebenen Koordinaten
        // returnt null-referenz, wenn ein Fehler auftritt
        //(z.B. wenn die Koordinaten au?erhalb des Spielbretts liegen
        try {
            return spielbrett.get(y).get(x);
        } catch (Exception e) {
            return null;
        }
    }
    public JTextField getInsertTextField() {
        return insertTextField;
    }

    public JLabel getInsertLabel() {
        return insertLabel;
    }

    public int getLaenge(){
        return laenge;
    }
    public void setLaenge(int laenge){
        this.laenge=laenge;
    }
    public void setBreite(int breite){
        this.breite=breite;
    }
    public int getBreite(){
        return breite;
    }

    public ArrayList<Spieler> getSpielerArray() {
        return spielerArray;
    }
    public JButton[][] getFractionbuttons() {
        return fractionbuttons;
    }
    public ArrayList<ArrayList<Object>> getSpielbrett() {
        return spielbrett;
    }
    public void setSpielbrett(ArrayList<ArrayList<Object>> list) {
        spielbrett=list;
    }
    public JPanel getGamePanel() {
        return gamePanel;
    }
    public void setFractionbuttons(JButton[][] buttons) {
        fractionbuttons=buttons;
    }
    public JPanel getInputPanel() {
        return inputPanel;
    }
    public void setElementSpielbrett(Object obj, int x, int y) {
        // setzt ein Objekt auf das Feld an der angegebenen Position
        // kann Fehler produzieren, wenn nicht vorher koordinaten gepr?ft werden
        spielbrett.get(y).set(x, obj);
    }
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}
