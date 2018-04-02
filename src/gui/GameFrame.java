package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
    ArrayList<ArrayList<Object>> spielbrett=new ArrayList<ArrayList<Object>>();
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
        insertTextField.addActionListener(this);
        inputPanel.setBounds(10,8,215,35);
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
                    switch (e.getKeyChar())
                    {
                        case 'w':
                            break;
                        case 'a':
                            break;
                        case 's':
                            break;
                        case 'd':
                            break;
                    }
                }
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,250,100);
        setVisible(true);
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
                setupGame();
                insertLabel.setText("Gebe den ersten Spielernamen ein");
                insertTextField.setText("");
            }
            else {
                insertPlayers(e);
            }
        }
    }

    private void insertPlayers(ActionEvent e) {
        int count;
        String inputtext = insertLabel.getText();
        String input = insertTextField.getText();

            if(inputtext.equals("Gebe den ersten Spielernamen ein") )
            {
                System.out.println("erster Spieler");
                count =1;
                if(!input.equals(""))
                {
                    spielerArray.add(new Spieler(input,0,0));
                    return;
                }
            }
            else if(!input.equals(""))
            {
                count = Integer.parseInt(inputtext.substring(9,10));
                spielerArray.add(new Spieler(input, 0,0));
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
                fractionbuttons[i][j]=new JButton((o instanceof Spieler)?((Spieler) o).name.substring(0,1):((Fraction)o).toStringUnified());
                gamePanel.add(fractionbuttons[i][j]);
            }
        }
        remove(gamePanel);
        setSize(breite*50+40,laenge*50+100);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
        gamePanel.setBounds(10,10,breite*50,laenge*50);
        inputPanel.setBounds(10,gamePanel.getHeight()+15,gamePanel.getWidth(),getHeight()-(gamePanel.getHeight()+60));
        add(gamePanel);
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
