package game.control.players;

import game.control.Turn;
import game.control.movement.Movement;
import game.gui.pawn.Pawn;
import game.gui.table.Table;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BlackPlayer extends JComponent{

    public static ArrayList<JButton> blackPawns = new ArrayList<>();
    private static final File black = new File("images/black.png");


    public static void createBlackPawns() {
        blackPawns.clear();

        for (JButton tile : Table.getTiles()) {


            if (tile.getY() >= 500) {

                Pawn blackPawn = new Pawn("", "black") {

                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);

                        try {

                            BufferedImage bg = ImageIO.read(black);

                            g.drawImage(bg, 0, 0, this);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                };

                blackPawn.setBackground(Color.red);


                blackPawn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        if (Turn.turn == "black") {

                            Movement.selectPawn(blackPawn);
                        } else {

                            System.out.println("NON E' IL TUO TURNO");
                        }
                    }
                });

                tile.add(blackPawn);
                blackPawns.add(blackPawn);
                tile.updateUI();
            }
        }
    }
}
