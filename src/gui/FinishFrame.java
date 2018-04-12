package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
class FinishFrame extends JFrame {

    FinishFrame(ArrayList<Spieler> spieler)
    {
        setUp(testScore(spieler),spieler);
    }
    FinishFrame(ArrayList<Spieler> spieler,ArrayList<Spieler> gewinner)
    {
        setUp(gewinner,spieler);
    }

    private void setUp(ArrayList<Spieler> gewinner, ArrayList<Spieler> spieler) {
        setTitle("GEWONNEN");
        JPanel p =new JPanel();
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        setLayout(null);

        JLabel[] winnerlabel = new JLabel[gewinner.size()];
        p.add(new JLabel("Gewonnen haben:"));
        for(int i=0;i<winnerlabel.length;i++)
        {
            winnerlabel[i]=new JLabel(gewinner.get(i).name+": "+gewinner.get(i).score.doubleValue());
            p.add(winnerlabel[i]);
        }

        JLabel[] playerlabel = new JLabel[spieler.size()];
        p.add(new JLabel("Spielerpunkte:"));
        for(int i=0;i<playerlabel.length;i++)
        {
            playerlabel[i]=new JLabel(spieler.get(i).name+": "+spieler.get(i).score.doubleValue());
            p.add(playerlabel[i]);
        }

        getContentPane().add(p);
        p.setBounds(80,10,140,(gewinner.size()+spieler.size())*30);
        setSize(300, (gewinner.size()+spieler.size())*30+50);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private ArrayList<Spieler> testScore(ArrayList<Spieler>spielerArray) {
        ArrayList<Spieler>gewinner= new ArrayList<>();
        Fraction highestScore=new Fraction("0","1");
        Spieler winner=null;
        for(Spieler sp:spielerArray) {
            //der Spieler mit der Höchstpunktzahl wird ermittelt
            if(sp.getScore().compareTo(highestScore)>=0) {
               winner = sp;
               highestScore=sp.getScore();
            }
        }
        gewinner.add(winner);
       return gewinner;
       //returned den Gewinner in einer Liste
    }
}

