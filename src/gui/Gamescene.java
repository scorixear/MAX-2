package gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.awt.event.FocusEvent;

/**
 * author: Paul Keller
 * date: 05.04.2018
 * version: 1.0
 */
public class Gamescene {
    @FXML private GridPane gameGrid;
    public void setupGame(GameFrame frame){
        Button[][] fractionbuttons = new Button[frame.getLaenge()][frame.getBreite()];
        int buttonWidth=75;
        int buttonHeight=60;

        frame.setSpielbrett(ZufallsBruchGenerator.tabelleFuellen(frame.getBreite(),frame.getLaenge()));
        for(int i=0;i<frame.getSpielerArray().size();i++)
        {
            generierePosition(frame.getSpielerArray().get(i),i,frame);
            frame.setElementSpielbrett(frame.getSpielerArray().get(i),frame.getSpielerArray().get(i).getPosX(),frame.getSpielerArray().get(i).getPosY());
        }

        for(int i=0;i<fractionbuttons.length;i++)
        {
            for(int j=0;j<fractionbuttons[j].length;i++)
            {
                fractionbuttons[i][j]=new Button();
                gameGrid.add(fractionbuttons[i][j],i,j);
                fractionbuttons[i][j].focusedProperty().addListener((observable, oldValue, newValue) -> {
                    

                });
            }
        }
    }
    private void generierePosition(Spieler spieler, int x, GameFrame frame) {
        // bis zu 8 Spieler werden nach festem Muster statisch gesetzt
        // dies geschieht in einem 8x8 Rahmen.
        // dieser Rahmen wird innerhalb des Spielbretts "in die Mitte geschoben"
        // deswegen Laenge und Breite durch 2, mit derer der Rahmen verschoben wird
        int yFaktor=(frame.getBreite()-8)/2;
        int xFaktor=(frame.getLaenge()-8)/2;
        switch(x) {
            case 0: spieler.setPosX(4+xFaktor); spieler.setPosY(3+yFaktor);
                break;
            case 1: spieler.setPosX(3+xFaktor); spieler.setPosY(4+yFaktor);
                break;
            case 2: spieler.setPosX(2+xFaktor); spieler.setPosY(2+yFaktor);
                break;
            case 3: spieler.setPosX(5+xFaktor); spieler.setPosY(5+yFaktor);
                break;
            case 4: spieler.setPosX(6+xFaktor); spieler.setPosY(1+yFaktor);
                break;
            case 5: spieler.setPosX(1+xFaktor); spieler.setPosY(6+yFaktor);
                break;
            case 6: spieler.setPosX(xFaktor); spieler.setPosY(yFaktor);
                break;
            case 7: spieler.setPosX(7+xFaktor); spieler.setPosY(7+yFaktor);
                break;
        }
    }

}
