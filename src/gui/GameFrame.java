package gui;

import javax.swing.*;
import java.awt.*;

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
    private JTextField picturePath;
    private JButton pictureButton;
    private JLabel insertLabel;                                                                                         //gibt Informationen über erwarteten Input
    private GameMechanic gameMechanics;
    private Spielbrett spielbrett;
    private JMenuBar mb;

    //Anzahl an Spalten des Spielfeldes
    GameFrame(){
        setTitle("Max-GUI");
        setLayout(null);
        spielbrett=new Spielbrett();
        GameSetup setup = new GameSetup(this);

        insertLabel=new JLabel("Gebe die Laenge des Spielfeldes an");
        insertTextField=new JTextField("");
        insertTextField.setHorizontalAlignment(JTextField.CENTER);

        picturePath=new JTextField(System.getProperty("java.class.path")+"/beleidigterFranz.png");
        pictureButton=new JButton("Durchsuchen...");
        pictureButton.addActionListener(setup);

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(insertLabel);
        inputPanel.add(insertTextField);
        insertTextField.addActionListener(setup);
        inputPanel.setBounds(10,8,270,35);


        getContentPane().add(inputPanel);
        gameMechanics=new GameMechanic(this);

        JMenu menu = new JMenu("Datei");
        JMenuItem menuItem[]= {new JMenuItem("Speichern"),new JMenuItem("Laden")};
        mb = new JMenuBar();
        menuItem[0].addActionListener(gameMechanics);
        menuItem[1].addActionListener(gameMechanics);
        menu.add(menuItem[0]);
        menu.add(menuItem[1]);
        mb.add(menu);
        getContentPane().addKeyListener(gameMechanics);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,300,100);
        //Setzt das Fenster in die Mitte des Bildschirms, abhängig von der eingestellten Auflösung des Bildschirms und der Größe des Frames
        setVisible(true);

        gamePanel = new JPanel();
    }

    protected JMenuBar getMb(){return mb;}

    protected Spielbrett getSpielbrett(){return spielbrett;}
    protected void setSpielbrett(Spielbrett brett){spielbrett=brett;}

    protected JButton getPictureButton(){return pictureButton;}

    protected JTextField getPicturePath(){return picturePath;}

    protected JTextField getInsertTextField() { return insertTextField; }
    protected JLabel getInsertLabel() { return insertLabel; }

    protected JButton[][] getFractionbuttons() { return fractionbuttons; }
    protected void setFractionbuttons(JButton[][] buttons) { fractionbuttons=buttons; }

    protected JPanel getGamePanel() { return gamePanel; }
    protected JPanel getInputPanel() { return inputPanel; }

    protected GameMechanic getGameMechanics(){
        return gameMechanics;
    }


}
