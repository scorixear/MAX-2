package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 * author: Paul Keller
 * date: 01.04.2018
 * version: 1.0
 */
public class GameFrame extends JFrame implements ActionListener {
    private JButton[][] fractionbuttons;
    private JPanel gamePanel;
    private JPanel inputPanel;
    private JTextField insertTextField;
    private JLabel insertLabel;
    private ArrayList<Spieler> spielerArray = new ArrayList<>();
    private ArrayList<ArrayList<Object>> spielbrett=new ArrayList<ArrayList<Object>>();
    private int playerToTurn;
    private int laenge;
    private int breite;
    private Fraction punkteLimit;
    public GameFrame(){
        setTitle("Max-GUI");
        setLayout(null);
        insertLabel=new JLabel("Gebe die Laenge des Spielfeldes an");
        insertTextField=new JTextField("");
        gamePanel = new JPanel();
        punkteLimit = new Fraction("10","1");
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(insertLabel);
        inputPanel.add(insertTextField);
        insertTextField.addActionListener(this);
        inputPanel.setBounds(10,8,270,35);
        getContentPane().add(inputPanel);
        getContentPane().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                boolean contain=false;
                for(Component c:getContentPane().getComponents()) {
                    if(c.equals(inputPanel))
                    {
                        contain = true;
                        break;
                    }
                }
                if(!contain)
                {
                   if ("wasd".contains(e.getKeyChar()+""))
                   {
                       if(kannBewegen(spielerArray.get(playerToTurn),e.getKeyChar()))
                       {
                          bewegeSpieler(spielerArray.get(playerToTurn),e.getKeyChar());
                          if(++playerToTurn==spielerArray.size())
                              playerToTurn=0;
                          updateField();
                          testScore();

                       }
                   }
                }
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,300,100);
        setVisible(true);
    }
    public void testScore() {
        //?berpr?ft den Score der Spieler, ob sie das Limit bereits erreicht haben
        ArrayList<Spieler>gewinner=new ArrayList<Spieler>();
        //Liste der Gewinner
        for(Spieler sp:spielerArray) {
            //alle Spieler, die die Punktzahl erreicht haben, werden in der ArrayList gewinner gespeichert
            if(sp.getScore().compareTo(punkteLimit)>=0) {
                gewinner.add(sp);
            }
        }
        if(gewinner.size()>0) {
            //wenn es einen (oder mehrere gewinner gibt), werden diese ausgegeben
            dispose();
            new FinishFrame(spielerArray,gewinner);


            //return true gibt an, dass das Spiel vorbei ist (ein gewinner gefunden wurde)
        }

        //return false gibt an, dass bis jetzt noch kein gewinner gefunden wurde (das spiel l?uft weiter)
    }
    private void updateField(){
        for(int i=0;i<fractionbuttons.length;i++)
        {
            for(int j=0;j<fractionbuttons[i].length;j++)
            {
                Object o =spielbrett.get(i).get(j);
                if(o instanceof Spieler)
                {
                    fractionbuttons[i][j].setText(((Spieler) o).name.substring(0,1));
                    if(o.equals(spielerArray.get(playerToTurn)))
                        fractionbuttons[i][j].setBackground(Color.RED);
                    else
                        fractionbuttons[i][j].setBackground(Color.WHITE);

                }
                else
                {
                    fractionbuttons[i][j].setText(((Fraction)o).toStringUnified());
                    fractionbuttons[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
    private void bewegeSpieler(Spieler spieler, char direction) {
        //direction: 8--> oben, 2--> unten, 6--> rechts, 4--> links
        //gibt die Anweisung, einen Spieler in eine der vier Himmelsrichtungen zu bewegen
        int x=spieler.getPosX();
        int y=spieler.getPosY();
        switch (direction) {
            case 's': bewegung(spieler,x,y,x,++y);
                break;
            case 'a': bewegung(spieler,x,y,--x,y);
                break;
            case 'd': bewegung(spieler,x,y,++x,y);
                break;
            case 'w': bewegung(spieler,x,y,x,--y);
                break;
        }
    }
    private void bewegung(Spieler spieler, int xquell, int yquell, int xziel, int yziel) {
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
    private boolean kannBewegen(Spieler spieler, char direction) {
        //testet, ob ein vom Spieler ausgehender Zug g?ltig ist
        // -> ung?ltig, wenn a) Zug ausserhalb des Spielfeldes f?hrt
        // oder b) am Ziel bereits ein Spieler steht
        int x=spieler.getPosX();
        int y=spieler.getPosY();
        Object obj;
        switch (direction) {
            case 's': obj=getElementSpielbrett(x,++y);
                break;
            case 'a': obj=getElementSpielbrett(--x,y);
                break;
            case 'd': obj=getElementSpielbrett(++x,y);
                break;
            case 'w': obj=getElementSpielbrett(x,--y);
                break;
            default:
                return false;
        }
        return (obj instanceof Fraction);
        //wenn obj eine Fraction ist: true, wenn Spieler: false
    }
    private Object getElementSpielbrett(int x, int y) {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JTextField)
        {
            if (insertLabel.getText().equals("Gebe die Laenge des Spielfeldes an")) {
                try {
                    laenge=Integer.parseInt(insertTextField.getText());
                } catch (NumberFormatException ex) {
                    insertTextField.setBackground(Color.red);
                    return;
                }
                insertTextField.setBackground(Color.white);
                insertLabel.setText("Gebe die Breite des Spielfeldes an");
                insertTextField.setText("");
            }
            else if(insertLabel.getText().equals("Gebe die Breite des Spielfeldes an"))
            {
                try {
                    breite=Integer.parseInt(insertTextField.getText());
                } catch(NumberFormatException ex)
                {
                    insertTextField.setBackground(Color.red);
                    return;
                }
                insertTextField.setBackground(Color.WHITE);
                //setupGame();
                insertLabel.setText("Gebe den ersten Spielernamen ein");
                insertTextField.setText("");
            }
            else {
                System.out.println("Hier sollte er landen");
                insertPlayers(e);
            }
            System.out.println("hier ist ende");
        }
    }

    private void insertPlayers(ActionEvent e) {
        int count;
        String inputtext = insertLabel.getText();
        String input = insertTextField.getText();

            if(inputtext.equals("Gebe den ersten Spielernamen ein")||inputtext.equals("Mindestens ein Spieler muss gesetzt werden") )
            {
                count =1;
                System.out.println(input.equals(""));
                if(!input.equals(""))
                {
                    spielerArray.add(new Spieler(input,0,0));
                    insertTextField.setText("");
                    insertLabel.setText("Gebe den 2. Spielernamen ein");
                    return;
                }
                insertLabel.setText("Mindestens ein Spieler muss gesetzt werden");
                return;
            }
            else if(!input.equals(""))
            {
                count = Integer.parseInt(inputtext.substring(9,10));
                spielerArray.add(new Spieler(input, 0,0));
                insertTextField.setText("");
                if(count==8)
                {
                    getContentPane().remove(inputPanel);
                    getContentPane().requestFocusInWindow();
                    System.out.println("Spieler Limit erreicht");

                    setupGame();
                    return;
                }
            }
            else
            {
               getContentPane().remove(inputPanel);
               getContentPane().requestFocusInWindow();
                System.out.println("Spieler input abgebrochen");
               setupGame();
               return;
            }
            insertLabel.setText("Gebe den "+(++count)+". Spielernamen ein");

    }

    private void setupGame() {
        gamePanel.setLayout(new GridLayout(laenge, breite));
        fractionbuttons=new JButton[laenge][breite];
        int buttonWidth=75;
        int buttonHeight=30;
        playerToTurn=0;

        spielbrett =  ZufallsBruchGenerator.tabelleFuellen(laenge,breite);
        for(int i = 0;i<spielerArray.size();i++)
        {
           generierePosition(spielerArray.get(i),i);
           setElementSpielbrett(spielerArray.get(i),spielerArray.get(i).getPosX(),spielerArray.get(i).getPosY());
        }

        for(int i=0;i<fractionbuttons.length;i++)
        {
            for(int j=0;j<fractionbuttons[i].length;j++)
            {
                Object o =spielbrett.get(i).get(j);
                if(o instanceof Spieler)
                {
                    fractionbuttons[i][j]=new JButton(((Spieler) o).name.substring(0,1));
                    if(((Spieler)o).equals(spielerArray.get(0)))
                        fractionbuttons[i][j].setBackground(Color.RED);
                    else
                        fractionbuttons[i][j].setBackground(Color.WHITE);

                }
                else
                {
                    fractionbuttons[i][j]=new JButton(((Fraction)o).toStringUnified());
                    fractionbuttons[i][j].setBackground(Color.WHITE);
                }
               gamePanel.add(fractionbuttons[i][j]);
            }
        }
        remove(gamePanel);
        setSize(breite*buttonWidth+40,laenge*buttonHeight+60);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
        gamePanel.setBounds(10,10,breite*buttonWidth,laenge*buttonHeight);
        inputPanel.setBounds(10,gamePanel.getHeight()+15,gamePanel.getWidth(),getHeight()-(gamePanel.getHeight()+60));
        add(gamePanel);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new FinishFrame(spielerArray);
            }
        });


    }
    public void generierePosition(Spieler spieler, int x) {
        // bis zu 8 Spieler werden nach festem Muster statisch gesetzt
        // dies geschieht in einem 8x8 Rahmen.
        // dieser Rahmen wird innerhalb des Spielbretts "in die Mitte geschoben"
        // deswegen Laenge und Breite durch 2, mit derer der Rahmen verschoben wird
        int yFaktor=(breite-8)/2;
        int xFaktor=(laenge-8)/2;
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
    public void setElementSpielbrett(Object obj, int x, int y) {
        // setzt ein Objekt auf das Feld an der angegebenen Position
        // kann Fehler produzieren, wenn nicht vorher koordinaten gepr?ft werden
        spielbrett.get(y).set(x, obj);
    }
    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}
