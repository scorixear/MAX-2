package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * author: Paul Keller
 * date: 01.04.2018
 * version: 1.0
 */
public class GameFrame extends JFrame implements ActionListener {
    private JButton[][] fractionbuttons;
    private JPanel gamePanel;
    private JPanel inputPanel;
    private JTextField insertTextField;
    private JLabel insertLabel;
    int laenge;
    int breite;
    public GameFrame(){
        setTitle("Max-GUI");
        setLayout(null);
        insertLabel=new JLabel("Gebe die Laenge des Spielfeldes an");
        insertTextField=new JTextField("");
        gamePanel = new JPanel();
        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel,BoxLayout.Y_AXIS));
        inputPanel.add(insertLabel);
        inputPanel.add(insertTextField);
        insertTextField.addActionListener(this);
        inputPanel.setBounds(10,8,215,35);
        getContentPane().add(inputPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Toolkit.getDefaultToolkit().getScreenSize().width/2-125, Toolkit.getDefaultToolkit().getScreenSize().height/2-50,250,100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JTextField)
        {
            if (insertLabel.getText().equals("Gebe die Laenge des Spielfeldes an")) {
                try {
                    laenge=Integer.parseInt(insertTextField.getText());
                } catch (NumberFormatException ex) {
                    insertTextField.setBackground(Color.red);
                    return;
                }
                insertTextField.setBackground(Color.white);
                insertLabel.setText("Gebe die Breite des Spielfeldes an");
                insertTextField.setText("");
            }
            else if(insertLabel.getText().equals("Gebe die Breite des Spielfeldes an"))
            {
                try {
                    breite=Integer.parseInt(insertTextField.getText());
                } catch(NumberFormatException ex)
                {
                    insertTextField.setBackground(Color.red);
                    return;
                }
                insertTextField.setBackground(Color.WHITE);
                setupGame();
                insertLabel.setText("Gebe den ersten Spielernamen ein");
                insertTextField.setText("");

            }
        }
    }

    private void setupGame() {
        gamePanel.setLayout(new GridLayout(laenge, breite));
        fractionbuttons=new JButton[laenge][breite];
        for(JButton[] buttons:fractionbuttons)
        {
            for(JButton button:buttons)
            {
                button=new JButton("");
                gamePanel.add(button);
            }
        }
        remove(gamePanel);
        setSize(breite*50+40,laenge*50+100);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-getWidth()/2, Toolkit.getDefaultToolkit().getScreenSize().height/2-getHeight()/2);
        gamePanel.setBounds(10,10,breite*50,laenge*50);
        inputPanel.setBounds(10,gamePanel.getHeight()+15,gamePanel.getWidth(),getHeight()-(gamePanel.getHeight()+60));
        add(gamePanel);
    }

    public static void main(String[] args) {
        GameFrame frame = new GameFrame();
    }
}
