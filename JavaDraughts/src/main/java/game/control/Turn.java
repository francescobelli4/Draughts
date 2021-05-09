package game.control;

import game.control.players.WhitePlayer;
import game.gui.menu.Menu;

import javax.swing.*;

public class Turn {

    public static String turn = "black";

    public static void newTurn() {

        if (turn == "black") {
            turn = "white";
        } else {
            turn = "black";
        }
        ((JLabel) Menu.turn.getComponent(0)).setText(""+ turn);
    }

}
