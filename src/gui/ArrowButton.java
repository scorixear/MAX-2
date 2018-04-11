package gui;
//import org.omg.PortableInterceptor.DISCARDING;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionListener;
/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */

public class ArrowButton extends JDialog{
    /*
       class ArrowButton
       second Frame for on-screen controls
     */
    private GameMechanic myGame;

    ArrowButton(GameMechanic gameMechanic){
        this.myGame=gameMechanic;

        BasicArrowButton north, west, east, south;
        north	=new BasicArrowButton(BasicArrowButton.NORTH);
        north.setActionCommand("w");
        west	=new BasicArrowButton(BasicArrowButton.WEST);
        west.setActionCommand("a");
        east	=new BasicArrowButton(BasicArrowButton.EAST);
        east.setActionCommand("d");
        south	=new BasicArrowButton(BasicArrowButton.SOUTH);
        south.setActionCommand("s");

        setSize(200,250);
        setTitle("MAX Controls");
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3,3));

        add(new JPanel()).setBackground(Color.lightGray);
        add(north);
        add(new JPanel()).setBackground(Color.lightGray);
        add(west);
        add(new JPanel()).setBackground(Color.lightGray);
        add(east);
        add(new JPanel()).setBackground(Color.lightGray);
        add(south);
        add(new JPanel()).setBackground(Color.lightGray);

        for(Component c:getContentPane().getComponents()){
            if(c instanceof BasicArrowButton) {
                ActionListener actList = e -> {
                    String command = e.getActionCommand();
                    if ("wasd".contains(command)) {
                        System.out.println("Action: " + command);
                        myGame.actionToMove(command);
                    }
                };
                ((BasicArrowButton) c).addActionListener(actList);
                (c).setBackground(new Color(35,120,35));
            }
        }
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2+200, Toolkit.getDefaultToolkit().getScreenSize().height/2-50);
        setVisible(false);
        //setUndecorated(true);
        //only set it visible when game starts
    }
    public void setArrowVisible(boolean b){
        setVisible(b);
    }
    public void setActivePlayer(String s){
        this.setTitle(s);
    }
}
