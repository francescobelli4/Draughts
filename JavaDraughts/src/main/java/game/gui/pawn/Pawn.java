package game.gui.pawn;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pawn extends JButton {
    private static final File black = new File("images/crown.png");

    public String col;
    public boolean isKing;
    public boolean canMove;

    public Pawn(String label, String color) {
        super(label);

        setBackground(Color.lightGray);
        setFocusable(false);

        setBounds(0, 0, 92, 92);

        setContentAreaFilled(false);
        setLayout(null);

        this.col = color;
        this.isKing = false;
        this.canMove = true;
    }

    public static void setKing(Pawn pawn) {
        pawn.isKing = true;

        JLabel king = new JLabel() {
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

        king.setBounds(20,20,50, 50);
        //king.setFocusable(false);

        pawn.add(king);
        king.setVisible(true);
    }

    // From StackOverflow //
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.gray);
        } else {
            g.setColor(getBackground());
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);

        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(Color.darkGray);
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }


    Shape shape;
    public boolean contains(int x, int y) {
        // If the button has changed size,  make a new shape object.
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return shape.contains(x, y);
    }

}
