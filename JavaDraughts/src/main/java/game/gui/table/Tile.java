package game.gui.table;

import game.control.movement.Movement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tile extends JButton{

    public Point position;

    public Tile (int x, int y) {
        position = new Point(x*100, y*100);

        setBounds(x*100, y*100, 100, 100);
        setBackground(Color.BLACK);

        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Movement.selectTile(Tile.this);
            }
        };
        addActionListener(actionListener);
        setLayout(null);

    }

    public static boolean isClear(JButton tile) {

        return tile.getComponents().length < 1;
    }

    public static double getDistance(JButton tile1, JButton tile2) {

        int x1 = tile1.getX();
        int y1 = tile1.getY();
        int x2 = tile2.getX();
        int y2 = tile2.getY();

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }
}
