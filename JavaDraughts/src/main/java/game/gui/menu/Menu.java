package game.gui.menu;

import game.control.Controller;
import game.control.Turn;
import game.control.players.BlackPlayer;
import game.control.players.WhitePlayer;
import game.gui.GUI;

import javax.naming.ldap.Control;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {

    private static JPanel gamePanel = GUI.getMainPanel();
    public static JLayeredPane whiteCounter;
    public static JLayeredPane blackCounter;
    public static JLayeredPane turn;
    public static JButton retryButton;

    public static void createMenu() {

        whiteCounter = createCounter(900, 30, ""+WhitePlayer.whitePawns.size(), Color.white);
        blackCounter = createCounter(1200, 30, ""+ BlackPlayer.blackPawns.size(), Color.gray);
        turn = createCounter(900, 180 , Turn.turn, Color.white);
        retryButton = createRetryButton(1200, 180, "Retry", Color.white);

        gamePanel.add(whiteCounter);
        gamePanel.add(blackCounter);
        gamePanel.add(retryButton);
        gamePanel.add(turn);
    }

    private static JLayeredPane createCounter(int x, int y, String text, Color c) {

        JLayeredPane counter = new JLayeredPane() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(c);
                g.fillRect(0, 0, 150, 100);
            }
        };
        counter.setLayout(new GridBagLayout());
        counter.setBounds(x, y, 150, 100);

        JLabel txt = new JLabel(text);
        txt.setSize(50, 50);
        txt.setFont(new Font("Serif", Font.BOLD, 25));
        counter.setBorder(BorderFactory.createLineBorder(Color.black, 10));

        counter.add(txt);

        return counter;
    }

    private static JButton createRetryButton(int x, int y, String text, Color c) {

        JButton counter = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                g.setColor(c);
                g.fillRect(0, 0, 150, 100);
            }
        };
        counter.setLayout(new GridBagLayout());
        counter.setBounds(x, y, 150, 100);

        JLabel txt = new JLabel(text);
        txt.setSize(100, 100);
        txt.setFont(new Font("Serif", Font.BOLD, 25));
        counter.setBorder(BorderFactory.createLineBorder(Color.black, 10));

        counter.add(txt);

        counter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.restoreTable();
            }
        });

        return counter;
    }
}
