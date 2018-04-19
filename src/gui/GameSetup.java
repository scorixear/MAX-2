package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class GameSetup implements ActionListener
{
    private GameFrame frame;
    private String[] names = {"/derFreundlicheFranz.png","/HamsterFranz.png","/Doppelfranz.png"};
    GameSetup(GameFrame frame){
       this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JTextField)
        {
            //Dieser Teil liest die Parameter des Spielfeldes ein
            JLabel insertLabel = frame.getInsertLabel();
            JTextField insertTextField = frame.getInsertTextField();
            switch (insertLabel.getText()) {
                case "Gebe die Laenge des Spielfeldes an":
                    try {
                        frame.getSpielbrett().setLaenge(Integer.parseInt(insertTextField.getText()));
                    } catch (NumberFormatException ex) {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    if(Integer.parseInt(insertTextField.getText())<8)
                    {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    insertTextField.setBackground(Color.white);
                    insertLabel.setText("Gebe die Breite des Spielfeldes an");
                    insertTextField.setText("");
                    break;
                case "Gebe die Breite des Spielfeldes an":
                    try {
                        frame.getSpielbrett().setBreite(Integer.parseInt(insertTextField.getText()));
                    } catch (NumberFormatException ex) {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    if(Integer.parseInt(insertTextField.getText())<8)
                    {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    insertTextField.setBackground(Color.WHITE);
                    insertLabel.setText("Gebe den ersten Spielernamen ein");
                    insertTextField.setText("");
                    frame.getInputPanel().setBounds(10,8,270,100);
                    frame.setSize(300,150);
                    frame.getInputPanel().add(frame.getPicturePath());
                    frame.getInputPanel().add(frame.getPictureButton());
                    frame.repaint();
                    break;
                default:
                    insertPlayers();
                    break;
            }
        }else if(e.getSource() instanceof JButton)
        {
          JFileChooser c = new JFileChooser(new File(frame.getPicturePath().getText()));
          c.setFileSelectionMode(JFileChooser.FILES_ONLY);
          int rVal = c.showOpenDialog(frame);
          if(rVal==JFileChooser.APPROVE_OPTION)
          {
              frame.getPicturePath().setText(c.getSelectedFile().getAbsolutePath());
          }

        }
    }
    private void insertPlayers() {
        //Dieser Teil liest die Spieler ein
        int count;
        String inputtext = frame.getInsertLabel().getText();
        String input = frame.getInsertTextField().getText();



        if(inputtext.equals("Gebe den ersten Spielernamen ein")||inputtext.equals("Mindestens ein Spieler muss gesetzt werden") )
        {

            if(!input.equals(""))
            {
                frame.getSpielbrett().getSpielerArray().add(new Spieler(input,0,0,frame.getPicturePath().getText()));
                frame.getInsertTextField().setText("");
                frame.getInsertLabel().setText("Gebe den 2. Spielernamen ein");
                //Standard-Bild für Spieler 2
                frame.getPicturePath().setText(System.getProperty("java.class.path")+"/derFreundlicheFranz.png");
                return;
            }
            frame.getInsertLabel().setText("Mindestens ein Spieler muss gesetzt werden");
            return;
        }
        else if(!input.equals(""))
        {
            count = Integer.parseInt(inputtext.substring(9,10));
            frame.getSpielbrett().getSpielerArray().add(new Spieler(input, 0,0,frame.getPicturePath().getText()));
            //Standardbilder für folgende Spieler, zu ändern im String names
            frame.getPicturePath().setText(System.getProperty("java.class.path")+((names.length>count-2)?names[count-2]:"fff"));
            frame.getInsertTextField().setText("");
            if(count==8)
            {
                frame.getContentPane().remove(frame.getInputPanel());
                frame.getContentPane().requestFocusInWindow();
                setupGame();
                return;
            }
        }
        else
        {
            frame.getContentPane().remove(frame.getInputPanel());
            frame.getContentPane().requestFocusInWindow();
            frame.setJMenuBar(frame.getMb());
            setupGame();
            return;
        }
        frame.getInsertLabel().setText("Gebe den "+(++count)+". Spielernamen ein");
    }
    protected void setupGame() {
        //Erstellt das Spielfeld und synchronisiert erstmals jenes mit den Buttons
        frame.getGamePanel().setLayout(new GridLayout(frame.getSpielbrett().getLaenge(), frame.getSpielbrett().getBreite()));
        frame.setFractionbuttons(new JButton[frame.getSpielbrett().getLaenge()][frame.getSpielbrett().getBreite()]);
        int buttonWidth=75;
        int buttonHeight=60;

        frame.getSpielbrett().setSpielbrett(ZufallsBruchGenerator.tabelleFuellen(frame.getSpielbrett().getBreite(),frame.getSpielbrett().getLaenge()));
        for(int i = 0;i<frame.getSpielbrett().getSpielerArray().size();i++)
        {
            generierePosition(frame.getSpielbrett().getSpielerArray().get(i),i);
            frame.getSpielbrett().setElementSpielbrett(frame.getSpielbrett().getSpielerArray().get(i),frame.getSpielbrett().getSpielerArray().get(i).getPosX(),frame.getSpielbrett().getSpielerArray().get(i).getPosY());
        }

        for(int i=0;i<frame.getFractionbuttons().length;i++)
        {
            for(int j=0;j<frame.getFractionbuttons()[i].length;j++) {
                frame.getFractionbuttons()[i][j] = new JButton();
                frame.getFractionbuttons()[i][j].setBorder(null);
                frame.getGamePanel().add(frame.getFractionbuttons()[i][j]);
                frame.getFractionbuttons()[i][j].addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        frame.getContentPane().requestFocusInWindow();
                    }

                    @Override
                    public void focusLost(FocusEvent e) {

                    }
                });
            }
        }
        frame.getGameMechanics().updateField();
        //verändert den Frame so, dass die Buttons wie gewollt in der Mitte mit ausreichend Platz liegen und verschiebt den Frame wieder in die Mitte des Bildschirmes
        frame.remove(frame.getGamePanel());
        frame.setSize(frame.getSpielbrett().getBreite()*buttonWidth+40,frame.getSpielbrett().getLaenge()*buttonHeight+60);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getHeight()/2);
        frame.getGamePanel().setBounds(10,10,frame.getSpielbrett().getBreite()*buttonWidth,frame.getSpielbrett().getLaenge()*buttonHeight);
        frame.getInputPanel().setBounds(10,frame.getGamePanel().getHeight()+15,frame.getGamePanel().getWidth(),frame.getHeight()-(frame.getGamePanel().getHeight()+60));
        frame.add(frame.getGamePanel());
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Falls das Fenster geschlossen wird, zählt dies als Beendung des Spiels
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                frame.getGameMechanics().getArrowButton().dispose();
                new FinishFrame(frame.getSpielbrett().getSpielerArray());
            }
        });
        frame.getGameMechanics().setArrowActive();
        //aktives Fenster mit Pfeilen für Bildschirm-Steuerung. Wird hier gemacht, weil es zu diesem Zeitpunkt nötig wird (Spiel startet tatsächlich)

    }
    private void generierePosition(Spieler spieler, int x) {
        // bis zu 8 Spieler werden nach festem Muster statisch gesetzt
        // dies geschieht in einem 8x8 Rahmen.
        // dieser Rahmen wird innerhalb des Spielbretts "in die Mitte geschoben"
        // deswegen Laenge und Breite durch 2, mit derer der Rahmen verschoben wird
        int yFaktor=(frame.getSpielbrett().getBreite()-8)/2;
        int xFaktor=(frame.getSpielbrett().getLaenge()-8)/2;
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
            case 6: spieler.setPosX(xFaktor); spieler.setPosY(yFaktor);
                break;
            case 7: spieler.setPosX(7+xFaktor); spieler.setPosY(7+yFaktor);
                break;
        }
    }
}
