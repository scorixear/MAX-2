package gui;


import javax.swing.*;
import java.awt.*;
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
   private ColorPlayer colorPlayer;
   private ArrowButton arrowButton; //Instance for on-screen controls
    private boolean doIwantAGUIwithButtons=true; // sprechende Name, my ass
    GameMechanic(GameFrame frame)
    {
        this.frame=frame;
        playerToTurn = 0;
        punkteLimit=new Fraction("20","1");
        colorPlayer=new ColorPlayer();
        if(doIwantAGUIwithButtons) arrowButton=new ArrowButton(this);
    }
    public ColorPlayer getColorPlayer() {
        return colorPlayer;
    }
    public void setColorPlayer(ColorPlayer p) {
        colorPlayer=p;
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
        for(Component c:frame.getContentPane().getComponents()) {                                                       //Überprüft, ob das Spiel bereits gestartet ist
            if(c.equals(frame.getInputPanel()))
            {
                contain = true;
                break;
            }
        }
        if(!contain)
        {
            if ("wasd".contains(e.getKeyChar()+""))                                                                     //Steuerung über W-A-S-D
            {
                if(kannBewegen(frame.getSpielerArray().get(playerToTurn),e.getKeyChar()))                               //Spieler kann sich in die gewählte Richtung bewegen?
                {
                    bewegeSpieler(frame.getSpielerArray().get(playerToTurn),e.getKeyChar());
                    if(++playerToTurn==frame.getSpielerArray().size())                                                  //Wählt den nächsten Spieler aus
                        playerToTurn=0;
                    updateField();
                    testScore();
                }
            }
        }
    }
    private void bewegeSpieler(Spieler spieler, char direction) {
        //direction: W--> oben, S--> unten, D--> rechts, A--> links
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
        // Ausführung der Anweisung, einen Spieler zu bewegen
        // addiert Zielfeld zum Spieler-Score, bewegt Spieler, und setzt verlassenes Feld auf Null
        Fraction frac=(Fraction) frame.getElementSpielbrett(xziel, yziel);
        //Element an Ziel kann ohne test in Frac gecastet werden, da davor schon getestet wird, ob der Zug g?ltig ist
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
        //testet, ob ein vom Spieler ausgehender Zug gültig ist
        // -> ungültig, wenn a) Zug ausserhalb des Spielfeldes f?hrt
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

    private void testScore() {
        //Überprüft den Score der Spieler, ob sie das Limit bereits erreicht haben
        ArrayList<Spieler>gewinner= new ArrayList<>();
        //Liste der Gewinner
        for(Spieler sp:frame.getSpielerArray()) {
            //alle Spieler, die die Punktzahl erreicht haben, werden in der ArrayList gewinner gespeichert - normalerweise sollte nur einer gewonnen haben
            if(sp.getScore().compareTo(punkteLimit)>=0) {
                gewinner.add(sp);
            }
        }
        if(gewinner.size()>0) {
            //wenn es einen (oder mehrere gewinner gibt), wird der Finish-Frame eingeblendet
            frame.dispose();
            new FinishFrame(frame.getSpielerArray(),gewinner);
        }
    }

    protected void updateField(){
        //Ersetzt die Texte auf den Buttons anhand der spielbrett-ArrayList. Spieler werden durch den ersten Buchstaben des Namens dargestellt
        for(int i=0;i<frame.getFractionbuttons().length;i++)
        {
            for(int j=0;j<frame.getFractionbuttons()[i].length;j++)
            {
                Object o =frame.getSpielbrett().get(i).get(j);
                if(o instanceof Spieler) {
                    frame.getFractionbuttons()[i][j].setText(((Spieler) o).name);
                    if(o.equals(frame.getSpielerArray().get(playerToTurn)))
                    {

                        if(!colorPlayer.isAlive())
                        {
                            colorPlayer=new ColorPlayer();
                            colorPlayer.start();
                        }
                        colorPlayer.setPlayer(frame.getFractionbuttons()[i][j]);

                    }
                }
                else
                {
                    if(((Fraction)o).isInteger())
                    {
                        frame.getFractionbuttons()[i][j].setText(((Fraction)o).getZaehler()+"");
                    }
                    else
                    {
                        frame.getFractionbuttons()[i][j].setText("<html><center>"+((Fraction)o).getZaehler()+"<br>----<br>"+((Fraction)o).getNenner()+"</center></html>");
                    }
                }
                     frame.getFractionbuttons()[i][j].setBackground(Color.WHITE);
            }
        }
    }
    protected void actionToMove(String s){
        //method receives external ActionCommand and passes it on
        try{
            char c=s.charAt(0);
            if ("wasd".contains(s))                                                                     //Steuerung über W-A-S-D
            {
                if(kannBewegen(frame.getSpielerArray().get(playerToTurn),c))                               //Spieler kann sich in die gewählte Richtung bewegen?
                {
                    bewegeSpieler(frame.getSpielerArray().get(playerToTurn),c);
                    if(++playerToTurn==frame.getSpielerArray().size())                                                  //Wählt den nächsten Spieler aus
                        playerToTurn=0;
                    updateField();
                    testScore();
                }
            }
        }catch(Exception e){
            System.out.println("Exception: Can't use Controls at this point");
        }
    }
    protected void setArrowActive(){
        if(doIwantAGUIwithButtons && arrowButton!=null){
            arrowButton.setArrowVisible(true);
        }
    }
}
class ColorPlayer extends Thread {
    private JButton player;

    @Override
    public void run(){
        boolean running=true;
        float h1 =0.0F;
        while(running)
        {
            if(player!=null)
            {
                player.setBackground(Color.getHSBColor(h1,1,1));
                h1=(h1>1.0F)?0.0F:h1+0.01F;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                running=false;
            }
        }
    }
    public void setPlayer(JButton p)
    {
        player=p;
    }

}