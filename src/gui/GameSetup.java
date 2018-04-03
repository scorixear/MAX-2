package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * author: Paul Keller
 * date: 02.04.2018
 * version: 1.0
 */
public class GameSetup implements ActionListener
{
    private GameFrame frame;
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
                        frame.setLaenge(Integer.parseInt(insertTextField.getText()));
                    } catch (NumberFormatException ex) {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    insertTextField.setBackground(Color.white);
                    insertLabel.setText("Gebe die Breite des Spielfeldes an");
                    insertTextField.setText("");
                    break;
                case "Gebe die Breite des Spielfeldes an":
                    try {
                        frame.setBreite(Integer.parseInt(insertTextField.getText()));
                    } catch (NumberFormatException ex) {
                        insertTextField.setBackground(Color.red);
                        return;
                    }
                    insertTextField.setBackground(Color.WHITE);
                    insertLabel.setText("Gebe den ersten Spielernamen ein");
                    insertTextField.setText("");
                    break;
                default:
                    System.out.println("Hier sollte er landen");
                    insertPlayers();
                    break;
            }
            System.out.println("hier ist ende");
        }
    }
    private void insertPlayers() {
        //Dieser Teil liest die Spieler ein
        int count;
        String inputtext = frame.getInsertLabel().getText();
        String input = frame.getInsertTextField().getText();

        if(inputtext.equals("Gebe den ersten Spielernamen ein")||inputtext.equals("Mindestens ein Spieler muss gesetzt werden") )
        {
            System.out.println(input.equals(""));
            if(!input.equals(""))
            {
                frame.getSpielerArray().add(new Spieler(input,0,0));
                frame.getInsertTextField().setText("");
                frame.getInsertLabel().setText("Gebe den 2. Spielernamen ein");
                return;
            }
            frame.getInsertLabel().setText("Mindestens ein Spieler muss gesetzt werden");
            return;
        }
        else if(!input.equals(""))
        {
            count = Integer.parseInt(inputtext.substring(9,10));
            frame.getSpielerArray().add(new Spieler(input, 0,0));
            frame.getInsertTextField().setText("");
            if(count==8)
            {
                frame.getContentPane().remove(frame.getInputPanel());
                frame.getContentPane().requestFocusInWindow();
                System.out.println("Spieler Limit erreicht");

                setupGame();
                return;
            }
        }
        else
        {
            frame.getContentPane().remove(frame.getInputPanel());
            frame.getContentPane().requestFocusInWindow();
            System.out.println("Spieler input abgebrochen");
            setupGame();
            return;
        }
        frame.getInsertLabel().setText("Gebe den "+(++count)+". Spielernamen ein");
    }
    private void setupGame() {
        //Erstellt das Spielfeld und synchronisiert erstmals jenes mit den Buttons
        frame.getGamePanel().setLayout(new GridLayout(frame.getLaenge(), frame.getBreite()));
        frame.setFractionbuttons(new JButton[frame.getLaenge()][frame.getBreite()]);
        int buttonWidth=75;
        int buttonHeight=60;

        frame.setSpielbrett(ZufallsBruchGenerator.tabelleFuellen(frame.getLaenge(),frame.getBreite()));
        for(int i = 0;i<frame.getSpielerArray().size();i++)
        {
            generierePosition(frame.getSpielerArray().get(i),i);
            frame.setElementSpielbrett(frame.getSpielerArray().get(i),frame.getSpielerArray().get(i).getPosX(),frame.getSpielerArray().get(i).getPosY());
        }

        for(int i=0;i<frame.getFractionbuttons().length;i++)
        {
            for(int j=0;j<frame.getFractionbuttons()[i].length;j++)
            {
                frame.getFractionbuttons()[i][j]=new JButton();
                frame.getGamePanel().add(frame.getFractionbuttons()[i][j]);
            }
        }
        frame.getGameMechanics().updateField();
        //verändert den Frame so, dass die Buttons wie gewollt in der Mitte mit ausreichend Platz liegen und verschiebt den Frame wieder in die Mitte des Bildschirmes
        frame.remove(frame.getGamePanel());
        frame.setSize(frame.getBreite()*buttonWidth+40,frame.getLaenge()*buttonHeight+60);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getHeight()/2);
        frame.getGamePanel().setBounds(10,10,frame.getBreite()*buttonWidth,frame.getLaenge()*buttonHeight);
        frame.getInputPanel().setBounds(10,frame.getGamePanel().getHeight()+15,frame.getGamePanel().getWidth(),frame.getHeight()-(frame.getGamePanel().getHeight()+60));
        frame.add(frame.getGamePanel());
        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        //Falls das Fenster geschlossen wird, zählt dies als Beendung des Spiels
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.dispose();
                new FinishFrame(frame.getSpielerArray());
            }
        });
        frame.getGameMechanics().setArrowActive();
        //active Frame with arrows for on screen control. Done here because it becomes necessary at this point (Game actually starts)

    }
    private void generierePosition(Spieler spieler, int x) {
        // bis zu 8 Spieler werden nach festem Muster statisch gesetzt
        // dies geschieht in einem 8x8 Rahmen.
        // dieser Rahmen wird innerhalb des Spielbretts "in die Mitte geschoben"
        // deswegen Laenge und Breite durch 2, mit derer der Rahmen verschoben wird
        int yFaktor=(frame.getBreite()-8)/2;
        int xFaktor=(frame.getLaenge()-8)/2;
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
