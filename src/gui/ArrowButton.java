package gui;
//import org.omg.PortableInterceptor.DISCARDING;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

public class ArrowButton extends JDialog{
    /*
       class ArrowButton
       second Frame for on-screen controls
       @author Luca Göttle
     */
    private GameMechanic myGame;
    private ActionListener actList=new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            String command=e.getActionCommand();
            if("NORTHSOUTHEASTWEST".contains(command)) {
                System.out.println("Action: " + command);
                switch(command){
                    //translate Button name for command to equivalent key event name
                    //so class GameMechanic can use these events
                    case "NORTH":   command ="w";break;
                    case "EAST":    command ="d";break;
                    case "SOUTH":   command ="s";break;
                    case "WEST":    command ="a";break;
                    default: return;
                }
                myGame.actionToMove(command);
            }
        }
    };

    public ArrowButton(GameMechanic gameMechanic){
        this.myGame=gameMechanic;

        BasicArrowButton north, west, east, south;
        north	=new BasicArrowButton(BasicArrowButton.NORTH);
        north.setActionCommand("NORTH");
        west	=new BasicArrowButton(BasicArrowButton.WEST);
        west.setActionCommand("WEST");
        east	=new BasicArrowButton(BasicArrowButton.EAST);
        east.setActionCommand("EAST");
        south	=new BasicArrowButton(BasicArrowButton.SOUTH);
        south.setActionCommand("SOUTH");

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
}
