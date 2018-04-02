package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * author: Paul Keller
 * date: 02.04.2018
 * version: 1.0
 */
public class GameMechanic implements KeyListener
{
   private GameFrame frame;
   private int playerToTurn;
   private Fraction punkteLimit;

    public GameMechanic(GameFrame frame)
    {
        this.frame=frame;
        playerToTurn = 0;
        punkteLimit=new Fraction("10","1");
    }

    public void setPlayerToTurn(int playerToTurn) {
        this.playerToTurn = playerToTurn;
    }

    public int getPlayerToTurn() {
        return playerToTurn;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        boolean contain=false;
        for(Component c:frame.getContentPane().getComponents()) {
            if(c.equals(frame.getInputPanel()))
            {
                contain = true;
                break;
            }
        }
        if(!contain)
        {
            if ("wasd".contains(e.getKeyChar()+""))
            {
                if(kannBewegen(frame.getSpielerArray().get(playerToTurn),e.getKeyChar()))
                {
                    bewegeSpieler(frame.getSpielerArray().get(playerToTurn),e.getKeyChar());
                    if(++playerToTurn==frame.getSpielerArray().size())
                        playerToTurn=0;
                    updateField();
                    testScore();

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
        Fraction frac=(Fraction) frame.getElementSpielbrett(xziel, yziel);
        //element an Ziel kann ohne test in Frac gecastet werden, da davor schon getestet wird, ob der Zug g?ltig ist
        spieler.addiere(frac);
        //Fraction am Ziel wird dem Score des Spielers hinzuaddiert
        frame.setElementSpielbrett(spieler, xziel, yziel);
        //Spieler wird auf dem Feld bewegt
        spieler.setPosX(xziel);
        spieler.setPosY(yziel);
        //koordinaten werden auch im Spieler-Obj gespeichert
        frame.setElementSpielbrett(new Fraction("0","1"), xquell, yquell);
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
            case 's': obj=frame.getElementSpielbrett(x,++y);
                break;
            case 'a': obj=frame.getElementSpielbrett(--x,y);
                break;
            case 'd': obj=frame.getElementSpielbrett(++x,y);
                break;
            case 'w': obj=frame.getElementSpielbrett(x,--y);
                break;
            default:
                return false;
        }
        return (obj instanceof Fraction);
        //wenn obj eine Fraction ist: true, wenn Spieler: false
    }
    public void testScore() {
        //?berpr?ft den Score der Spieler, ob sie das Limit bereits erreicht haben
        ArrayList<Spieler>gewinner=new ArrayList<Spieler>();
        //Liste der Gewinner
        for(Spieler sp:frame.getSpielerArray()) {
            //alle Spieler, die die Punktzahl erreicht haben, werden in der ArrayList gewinner gespeichert
            if(sp.getScore().compareTo(punkteLimit)>=0) {
                gewinner.add(sp);
            }
        }
        if(gewinner.size()>0) {
            //wenn es einen (oder mehrere gewinner gibt), werden diese ausgegeben
            frame.dispose();
            new FinishFrame(frame.getSpielerArray(),gewinner);


            //return true gibt an, dass das Spiel vorbei ist (ein gewinner gefunden wurde)
        }

        //return false gibt an, dass bis jetzt noch kein gewinner gefunden wurde (das spiel l?uft weiter)
    }
    private void updateField(){
        for(int i=0;i<frame.getFractionbuttons().length;i++)
        {
            for(int j=0;j<frame.getFractionbuttons()[i].length;j++)
            {
                Object o =frame.getSpielbrett().get(i).get(j);
                if(o instanceof Spieler)
                {
                    frame.getFractionbuttons()[i][j].setText(((Spieler) o).name.substring(0,1));
                    if(o.equals(frame.getSpielerArray().get(playerToTurn)))
                        frame.getFractionbuttons()[i][j].setBackground(Color.RED);
                    else
                        frame.getFractionbuttons()[i][j].setBackground(Color.WHITE);

                }
                else
                {
                    frame.getFractionbuttons()[i][j].setText(((Fraction)o).toStringUnified());
                    frame.getFractionbuttons()[i][j].setBackground(Color.WHITE);
                }
            }
        }
    }
}
