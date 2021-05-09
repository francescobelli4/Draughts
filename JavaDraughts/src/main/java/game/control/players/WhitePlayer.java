package game.control.players;

import game.control.Turn;
import game.control.movement.Movement;
import game.gui.pawn.Pawn;
import game.gui.table.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class WhitePlayer {

    public static ArrayList<JButton> whitePawns = new ArrayList<>();
    private static final File white = new File("images/white.png");

    public static void createWhitePawns() {
        whitePawns.clear();

        for (JButton tile : Table.getTiles()) {

            if (tile.getY() <= 200) {

                Pawn whitePawn = new Pawn("", "white") {

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        try {

                            BufferedImage bg = ImageIO.read(white);
                            g.drawImage(bg, 0, 0, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                whitePawn.setBackground(Color.WHITE);

                whitePawn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (Turn.turn == "white") {

                            Movement.selectPawn(whitePawn);
                        } else {

                            System.out.println("NON E' IL TUO TURNO");
                        }
                    }
                });

                tile.add(whitePawn);
                whitePawns.add(whitePawn);
                tile.updateUI();
            }
        }
    }

}
