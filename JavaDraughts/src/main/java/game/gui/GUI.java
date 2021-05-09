package game.gui;

import game.gui.menu.Menu;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI {

    private static JFrame GUI;
    private static JPanel mainPanel;
    private static JPanel tablePanel;

    private static void createJFrame() {

        GUI = new JFrame("Dama");
        GUI.setSize(1500, 900);
        GUI.setResizable(false);
        GUI.setLayout(null);
        GUI.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private static void createMainJPanel() {
        final File img = new File("images/back.png");

        // Drawing background
        mainPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                try {

                    BufferedImage bg = ImageIO.read(img);
                    g.drawImage(bg, 0, 0, this);
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mainPanel.setBounds(0, 0, 1500, 900);
        mainPanel.setLayout(null);

        GUI.add(mainPanel);
    }

    private static void createTableJPanel() {

        tablePanel = new JPanel();
        tablePanel.setBounds(30, 30, 800, 800);
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        tablePanel.setLayout(null);

        mainPanel.add(tablePanel);
    }

    public static void buildEmptyGUI() {
        createJFrame();
        createMainJPanel();
        createTableJPanel();
    }

    public static void showGUI(boolean show) {

        mainPanel.setVisible(show);
        GUI.setVisible(show);
        tablePanel.setVisible(show);
    }

    public static JFrame getGUI() {
        return GUI;
    }

    public static JPanel getMainPanel() {
        return mainPanel;
    }

    public static JPanel getTablePanel() {
        return tablePanel;
    }
}
