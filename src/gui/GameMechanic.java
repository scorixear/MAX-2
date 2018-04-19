package gui;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class GameMechanic implements KeyListener, ActionListener
{
   private GameFrame frame;
   private int playerToTurn;
   private Fraction punkteLimit;
   private ColorPlayer colorPlayer;
   private ArrowButton arrowButton; //Instance for on-screen controls
   private boolean doIwantAGUIwithButtons=true; // sprechende Name
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

    public ArrowButton getArrowButton() {
        return arrowButton;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

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
                if(kannBewegen(frame.getSpielbrett().getSpielerArray().get(playerToTurn),e.getKeyChar()))                               //Spieler kann sich in die gewählte Richtung bewegen?
                {
                    bewegeSpieler(frame.getSpielbrett().getSpielerArray().get(playerToTurn),e.getKeyChar());
                    if(++playerToTurn==frame.getSpielbrett().getSpielerArray().size())                                                  //Wählt den nächsten Spieler aus
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
        Fraction frac=(Fraction) frame.getSpielbrett().getElementSpielbrett(xziel, yziel);
        //Element an Ziel kann ohne test in Frac gecastet werden, da davor schon getestet wird, ob der Zug g?ltig ist
        spieler.addiere(frac);
        //Fraction am Ziel wird dem Score des Spielers hinzuaddiert
        frame.getSpielbrett().setElementSpielbrett(spieler, xziel, yziel);
        //Spieler wird auf dem Feld bewegt
        spieler.setPosX(xziel);
        spieler.setPosY(yziel);
        //Koordinaten werden auch im Spieler-Obj gespeichert
        frame.getSpielbrett().setElementSpielbrett(new Fraction("0","1"), xquell, yquell);
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
            case 's': obj=frame.getSpielbrett().getElementSpielbrett(x,++y);
                break;
            case 'a': obj=frame.getSpielbrett().getElementSpielbrett(--x,y);
                break;
            case 'd': obj=frame.getSpielbrett().getElementSpielbrett(++x,y);
                break;
            case 'w': obj=frame.getSpielbrett().getElementSpielbrett(x,--y);
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
        for(Spieler sp:frame.getSpielbrett().getSpielerArray()) {
            //alle Spieler, die die Punktzahl erreicht haben, werden in der ArrayList gewinner gespeichert - normalerweise sollte nur einer gewonnen haben
            if(sp.getScore().compareTo(punkteLimit)>=0) {
                gewinner.add(sp);
            }
        }
        if(gewinner.size()>0) {
            //wenn es einen (oder mehrere gewinner gibt), wird der Finish-Frame eingeblendet
            frame.dispose();
            getArrowButton().dispose();
            new FinishFrame(frame.getSpielbrett().getSpielerArray(),gewinner);
        }
    }

    protected void updateField(){
        //Ersetzt die Texte auf den Buttons anhand der spielbrett-ArrayList. Spieler werden durch den ersten Buchstaben des Namens dargestellt
        try{
            arrowButton.setActivePlayer(frame.getSpielbrett().getSpielerArray().get(playerToTurn).getName());
        }catch(Exception ignored){}
        for(int i=0;i<frame.getFractionbuttons().length;i++)
        {
            for(int j=0;j<frame.getFractionbuttons()[i].length;j++) {
                Object o =frame.getSpielbrett().getSpielbrett().get(i).get(j);
                if(o instanceof Spieler) {
                    frame.getFractionbuttons()[i][j].setText(((Spieler) o).name);
                    try {

                        frame.getFractionbuttons()[i][j].setIcon(new ImageIcon(((Spieler)o).getIcon()));

                    } catch ( Exception e) {
                        frame.getFractionbuttons()[i][j].setIcon(null);
                    }
                    if(o.equals(frame.getSpielbrett().getSpielerArray().get(playerToTurn))) {

                        if(!colorPlayer.isAlive()) {
                            colorPlayer=new ColorPlayer();
                            colorPlayer.start();
                        }
                        colorPlayer.setPlayer(frame.getFractionbuttons()[i][j]);
                        frame.setTitle("Max-GUI "+((Spieler)o).getName());
                    }
                }
                else {
                    frame.getFractionbuttons()[i][j].setIcon(null);

                    if(((Fraction)o).isInteger()) {
                        frame.getFractionbuttons()[i][j].setText(((Fraction)o).getZaehler()+"");
                    }
                    else {
                        frame.getFractionbuttons()[i][j].setText("<html><center>"+((Fraction)o).getZaehler()+"<br>----<br>"+((Fraction)o).getNenner()+"</center></html>");
                    }
                }
                frame.getFractionbuttons()[i][j].setBackground(Color.WHITE);
            }
        }
    }
    protected void actionToMove(String s){
        //Methode erhält externen ActionCommand und reicht ihn weiter
        try{
            char c=s.charAt(0);
            if ("wasd".contains(s))                                                                     //Steuerung über W-A-S-D
            {
                if(kannBewegen(frame.getSpielbrett().getSpielerArray().get(playerToTurn),c))                               //Spieler kann sich in die gewählte Richtung bewegen?
                {
                    bewegeSpieler(frame.getSpielbrett().getSpielerArray().get(playerToTurn),c);
                    if(++playerToTurn==frame.getSpielbrett().getSpielerArray().size())                                                  //Wählt den nächsten Spieler aus
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()instanceof JMenuItem&&((JMenuItem) e.getSource()).getText().equals("Speichern")) {
            JFileChooser c= new JFileChooser();
            c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int rVal=c.showSaveDialog(frame);
            if(rVal==JFileChooser.APPROVE_OPTION)
            {
                File f = c.getCurrentDirectory();
                f.mkdirs();
                f=c.getSelectedFile();
                try{
                    FileOutputStream fs = new FileOutputStream(f);
                    ObjectOutputStream os = new ObjectOutputStream(fs);
                    os.writeObject(frame.getSpielbrett());
                    os.writeInt(playerToTurn);
                    os.close();

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }else if(e.getSource() instanceof JMenuItem)
        {
            JFileChooser c = new JFileChooser();
            c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int rVal=c.showOpenDialog(frame);
            if(rVal==JFileChooser.APPROVE_OPTION)
            {
                File f =c.getSelectedFile();
                try {
                    FileInputStream fs= new FileInputStream(f);
                    ObjectInputStream os = new ObjectInputStream(fs);
                    frame.setSpielbrett((Spielbrett)os.readObject());
                    playerToTurn=os.readInt();
                    reloadGame();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
    private void reloadGame(){


        Spielbrett brett = frame.getSpielbrett();
        System.out.println(brett.getSpielerArray().size());
        System.out.println(brett.getBreite());
        System.out.println(brett.getLaenge());
        System.out.println(brett.getSpielbrett().size());
        System.out.println(brett.getSpielbrett().get(0).get(0));


        //Erstellt das Spielfeld und synchronisiert erstmals jenes mit den Buttons
        frame.getGamePanel().setLayout(new GridLayout(brett.getLaenge(), brett.getBreite()));
        frame.getGamePanel().removeAll();
        frame.setFractionbuttons(new JButton[brett.getLaenge()][brett.getBreite()]);
        int buttonWidth=75;
        int buttonHeight=60;

        for(int i=0;i<brett.getLaenge();i++)
        {
            for(int j=0;j<brett.getBreite();j++) {
                frame.getFractionbuttons()[i][j] = new JButton();
                frame.getFractionbuttons()[i][j].setMargin(new Insets(0,0,0,0));
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
        updateField();
        //verändert den Frame so, dass die Buttons wie gewollt in der Mitte mit ausreichend Platz liegen und verschiebt den Frame wieder in die Mitte des Bildschirmes
        frame.remove(frame.getGamePanel());
        frame.setSize(frame.getSpielbrett().getBreite()*buttonWidth+40,frame.getSpielbrett().getLaenge()*buttonHeight+60);
        frame.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-frame.getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-frame.getHeight()/2);
        frame.getGamePanel().setBounds(10,10,frame.getSpielbrett().getBreite()*buttonWidth,frame.getSpielbrett().getLaenge()*buttonHeight);
        frame.getInputPanel().setBounds(10,frame.getGamePanel().getHeight()+15,frame.getGamePanel().getWidth(),frame.getHeight()-(frame.getGamePanel().getHeight()+60));
        frame.add(frame.getGamePanel());
        //Falls das Fenster geschlossen wird, zählt dies als Beendung des Spiels
        //aktives Fenster mit Pfeilen für Bildschirm-Steuerung. Wird hier gemacht, weil es zu diesem Zeitpunkt nötig wird (Spiel startet tatsächlich)

    }

}
class ColorPlayer extends Thread {
    //Diese Klasse ändert den Hintergrund der Bilder von den Spielern auf dem Spielfeld.
    private JButton player;
    @Override
    public void run(){
        boolean running=true;
        float h1 =0.0F;
        while(running)
        {
            if(player!=null) {
                player.setBackground(Color.getHSBColor(h1,1,1));
                h1=(h1>1.0F)?0.0F:h1+0.01F;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                running=false;
            }
        }
        player.setBackground(Color.WHITE);
    }
    public void setPlayer(JButton p)
    {
        player=p;
    }

}
