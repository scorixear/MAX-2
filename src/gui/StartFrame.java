package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class StartFrame extends JFrame implements ActionListener {
    private StartFrame(){
        setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-300,Toolkit.getDefaultToolkit().getScreenSize().height/2-300);
        setSize(150,200);
        setTitle("Das Franz-Spiel");
        JButton neuesSpiel = new JButton("Neues Spiel");
        neuesSpiel.setMargin(new Insets(10,0,10,0));
        JButton ladeSpiel = new JButton("Lade Spiel");
        ladeSpiel.setMargin(new Insets(10,0,10,0));
        ladeSpiel.addActionListener(this);
        neuesSpiel.addActionListener(this);
        panel.add(neuesSpiel);
        panel.add(ladeSpiel);
        add(panel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Neues Spiel"))
            new GameFrame();
        else
        {
            GameFrame frame= new GameFrame();
            JFileChooser c = new JFileChooser();
            c.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int rVal=c.showOpenDialog(frame);
            if(rVal==JFileChooser.APPROVE_OPTION)
            {
                File f =c.getSelectedFile();
                try {
                    FileInputStream fs= new FileInputStream(f);
                    ObjectInputStream os = new ObjectInputStream(fs);
                    Spielbrett brett = (Spielbrett)os.readObject();
                    frame.setSpielbrett(brett);
                    frame.getGameMechanics().setPlayerToTurn(os.readInt());
                    frame.getSetup().setupGame();
                    frame.setSpielbrett(brett);
                    frame.getGameMechanics().reloadGame();
                    frame.remove(frame.getInputPanel());
                    os.close();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
            }
            else
            {
                frame.dispose();
            }
        }
    }
    public static void main(String[] args) {
        new StartFrame();
    }
}
