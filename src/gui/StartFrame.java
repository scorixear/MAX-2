package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class StartFrame extends JFrame implements ActionListener {
    private StartFrame(){
        setLayout(new BorderLayout());
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-300,Toolkit.getDefaultToolkit().getScreenSize().height/2-300, 300, 300);
        setTitle("Das Franz-Spiel");
        JButton neuesSpiel = new JButton("Neues Spiel");
        neuesSpiel.addActionListener(this);
        add(neuesSpiel, BorderLayout.CENTER);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GameFrame();
    }
    public static void main(String[] args) {
        new StartFrame();
    }
}
