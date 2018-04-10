package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * author: Paul Keller
 * date: 10.04.2018
 * version: 1.0
 */
public class StartFrame extends JFrame implements ActionListener {
    //der Startframe, welcher nur einen Button enthält um Spielinstanzen zu erstellen.
    private JButton neuesSpiel;
    StartFrame(){
        setLayout(new BorderLayout());
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-300,Toolkit.getDefaultToolkit().getScreenSize().height/2-300, 300, 300);
        setTitle("Das Franz-Spiel");
        neuesSpiel=new JButton("Neues Spiel");
        neuesSpiel.addActionListener(this);
        add(neuesSpiel,BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GameFrame();
    }
    public static void main(String[] args) {
        new StartFrame();
    }
}
