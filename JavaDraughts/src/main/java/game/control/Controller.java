package game.control;

import game.control.players.BlackPlayer;
import game.control.players.WhitePlayer;
import game.gui.GUI;
import game.gui.menu.Menu;
import game.gui.table.Table;

import javax.swing.*;


public class Controller {



    public static void main(String[] args) {

        GUI.buildEmptyGUI();
        Table.DrawTiles();

        BlackPlayer.createBlackPawns();
        WhitePlayer.createWhitePawns();

        Menu.createMenu();
        GUI.showGUI(true);
    }

    public static void restoreTable() {

        clearTable();

        BlackPlayer.createBlackPawns();
        WhitePlayer.createWhitePawns();

        ((JLabel) Menu.whiteCounter.getComponent(0)).setText(""+WhitePlayer.whitePawns.size());
        ((JLabel) Menu.blackCounter.getComponent(0)).setText(""+BlackPlayer.blackPawns.size());
    }

    private static void clearTable() {

        for (JButton tile : Table.getTiles()) {
            if (tile.getComponents().length > 0) {
                tile.remove(tile.getComponent(0));
                tile.updateUI();
            }
        }
    }
}
