package game.gui.table;

import game.gui.GUI;

import javax.swing.*;
import java.util.ArrayList;

public class Table {

    private static ArrayList<JButton> tiles = new ArrayList<>();
    public static void DrawTiles() {
        boolean drawTile = false;

        for (int y = 0; y < 8; y++) {

            for (int x = 0; x < 8; x++) {

                if (drawTile) {
                    Tile t = new Tile(x, y);
                    GUI.getTablePanel().add(t);

                    tiles.add(t);
                }

                drawTile = !drawTile;
            }
            drawTile = !drawTile;
        }
    }

    public static ArrayList<JButton> getTiles() {
        return tiles;
    }
}
