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
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-300,Toolkit.getDefaultToolkit().getScreenSize().height/2-300);
        setSize(75, 100);
        setTitle("Das Franz-Spiel");
        JButton neuesSpiel = new JButton("Neues Spiel");
        neuesSpiel.addActionListener(this);
        panel.add(neuesSpiel, BorderLayout.CENTER);
        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        new GameFrame();
    }
    public static void main(String[] args) {
        new StartFrame();
    }
}
