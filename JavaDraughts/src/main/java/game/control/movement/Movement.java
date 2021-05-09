package game.control.movement;

import game.control.Controller;
import game.control.Turn;
import game.control.players.BlackPlayer;
import game.control.players.WhitePlayer;
import game.gui.GUI;
import game.gui.menu.Menu;
import game.gui.pawn.Pawn;
import game.gui.table.Table;
import game.gui.table.Tile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class Movement {

    private static JButton selectedPawn;
    private static JButton selectedTile;
    private static JButton oldTile;
    private static JButton blockingTile;
    private static boolean isDoubleEating = false;
    private static boolean isKing = false;
    private static JButton firstEatingTile;

    private static ArrayList<JButton> ediblePawns = new ArrayList<>();

    public static void selectPawn(JButton pawn) {

        if (((Pawn) pawn).canMove) {

            for (JButton tile : Table.getTiles()) {
                tile.setBackground(Color.black);
            }
            selectedPawn = pawn;
            oldTile = (JButton) selectedPawn.getParent();
            getAvailableTiles(oldTile);

            if (isInEnemyBase()) {
                Pawn.setKing((Pawn) selectedPawn);
            }
            isKing = ((Pawn) selectedPawn).isKing;



            for (JButton tile : getAvailableTiles(oldTile)) {
                tile.setBackground(Color.CYAN);
            }
        }
    }

    public static void selectTile(JButton tile) {

        if (selectedPawn != null && getAvailableTiles(oldTile).contains(tile) ) {
            selectedTile = tile;

            movePawn();
            Turn.newTurn();
        }

    }

    private static void movePawn() {

        oldTile.remove(selectedPawn);
        selectedTile.add(selectedPawn);
        oldTile.updateUI();
        isDoubleEating = false;

        if (oldTile.getY() - selectedTile.getY() == 200 || oldTile.getY() - selectedTile.getY() == -200) {
            eatPawn();
        }

        selectedPawn = null;
        selectedTile = null;

        for (JButton tile : Table.getTiles()) {
            tile.setBackground(Color.black);
        }
        ediblePawns.clear();

    }

    private static boolean isInEnemyBase() {

        if (Turn.turn.equals("black")) {

            return selectedPawn.getParent().getY() == 0;
        } else {

            return selectedPawn.getParent().getY() == 700;
        }
    }

    private static boolean canDoubleEat(JButton tile) {

        ArrayList<JButton> closestTiles = getClosestTiles(tile);
        ArrayList<JButton> notClearedTiles = new ArrayList<>();
        //oldTile.setBackground(Color.RED);

        for (JButton t : closestTiles) {

            if (!Tile.isClear(t)) {
                Pawn targetPawn = (Pawn) t.getComponent(0);

                if (!Turn.turn.equals(targetPawn.col)) {
                    notClearedTiles.add(t);
                }
            }
        }

        for (JButton t : notClearedTiles) {


            for (JButton t2 : getClosestTiles(t)) {

                //System.out.println("Y OLD: " + oldTile.getY() + ", X: " +oldTile.getX() + " | Y T2: " + t2.getY() + ", X: " + t2.getX());

                if (Tile.isClear(t2) && (t2.getY() != tile.getY() && t2.getX() != tile.getX())) {
                    return true;
                }
            }
        }


        return false;
    }

    private static void eatPawn() {

        JButton middleTile ;

        if (canDoubleEat(selectedTile)) {

            Turn.newTurn();
            isDoubleEating = true;
            firstEatingTile = oldTile;

            for (JButton pawn : WhitePlayer.whitePawns) {

                if (pawn != selectedPawn) {
                    ((Pawn) pawn).canMove = false;
                }
            }

            for (JButton pawn : BlackPlayer.blackPawns) {

                if (pawn != selectedPawn) {
                    ((Pawn) pawn).canMove = false;
                }
            }

        } else {

            for (JButton pawn : WhitePlayer.whitePawns) {

                if (pawn != selectedPawn) {
                    ((Pawn) pawn).canMove = true;
                }
            }

            for (JButton pawn : BlackPlayer.blackPawns) {

                if (pawn != selectedPawn) {
                    ((Pawn) pawn).canMove = true;
                }
            }

            firstEatingTile = null;
        }

        if (selectedTile.getX() < oldTile.getX() && selectedTile.getY() > oldTile.getY()) {

            middleTile = (JButton) GUI.getTablePanel().getComponentAt(oldTile.getX() - 100, oldTile.getY() + 100);
            blockingTile = middleTile;
        } else if (selectedTile.getX() < oldTile.getX() && selectedTile.getY() < oldTile.getY()){

            middleTile = (JButton) GUI.getTablePanel().getComponentAt(oldTile.getX() - 100, oldTile.getY() - 100);
            blockingTile = middleTile;
        } else if (selectedTile.getX() > oldTile.getX() && selectedTile.getY() > oldTile.getY()) {

            middleTile = (JButton) GUI.getTablePanel().getComponentAt(oldTile.getX() + 100, oldTile.getY() + 100);
            blockingTile = middleTile;
        } else {

            middleTile = (JButton) GUI.getTablePanel().getComponentAt(oldTile.getX() + 100, oldTile.getY() - 100);
            blockingTile = middleTile;
        }

        WhitePlayer.whitePawns.remove((JButton) middleTile.getComponent(0));
        BlackPlayer.blackPawns.remove((JButton) middleTile.getComponent(0));
        middleTile.remove(middleTile.getComponent(0));

        blockingTile.updateUI();
        middleTile.updateUI();

        ((JLabel) Menu.whiteCounter.getComponent(0)).setText(""+WhitePlayer.whitePawns.size());
        ((JLabel) Menu.blackCounter.getComponent(0)).setText(""+BlackPlayer.blackPawns.size());

        if (WhitePlayer.whitePawns.size() == 0 || BlackPlayer.blackPawns.size() == 0) Controller.restoreTable();
    }

    public static ArrayList<JButton> getClosestTiles(JButton currentTile) {

        ArrayList<JButton> closestTiles = new ArrayList<>();

        for (JButton tile : Table.getTiles()) {

            double distance = Tile.getDistance(currentTile, tile);
            boolean isNear = distance == Math.sqrt(100 * 100 + 100 * 100);

            if (isDoubleEating) {
                isNear = distance == Math.sqrt(200 * 200 + 200 * 200);
            }

            if (Turn.turn.equals("black")) {

                if (isNear && tile.getY() != oldTile.getY() && (oldTile.getY() > tile.getY() || isKing )) {

                    if (tile.getComponents().length > 0) {

                        if (!((Pawn) tile.getComponent(0)).col.equals("black")) {
                            closestTiles.add(tile);
                        }
                    } else {

                        if (firstEatingTile != null) {

                            if (tile != firstEatingTile) {
                                closestTiles.add(tile);
                            }
                        } else{
                            closestTiles.add(tile);
                        }

                    }
                }
            } else {

                if (isNear && tile.getY() != oldTile.getY() && (oldTile.getY() < tile.getY() || isKing )) {
                    if (tile.getComponents().length > 0) {

                        if (!((Pawn) tile.getComponent(0)).col.equals("white")) {
                            closestTiles.add(tile);
                        }
                    } else {
                        if (firstEatingTile != null) {

                            if (tile != firstEatingTile) {
                                closestTiles.add(tile);
                            }
                        } else{
                            closestTiles.add(tile);
                        }
                    }
                }
            }
        }

        return closestTiles;
    }

    private static void setEdible(JButton edibleTile, ArrayList<JButton> availableTiles) {

        Pawn ediblePawn = (Pawn) edibleTile.getComponent(0);
        String ediblePawnColor = ediblePawn.col;

        Pawn playingPawn;
        String playingPawnColor;

        if (oldTile.getComponents().length > 0) {

            playingPawn = (Pawn) oldTile.getComponent(0);
        } else {

            playingPawn = (Pawn) selectedTile.getComponent(0);
        }
        playingPawnColor = playingPawn.col;

        if (!ediblePawnColor.equals(playingPawnColor)) {


            ArrayList<JButton> closestTilesToTarget = getClosestTiles(edibleTile);

            for (JButton tile : closestTilesToTarget) {

                if (tile.getX() != oldTile.getX() && Tile.isClear(tile) && tile != oldTile) {
                    availableTiles.add(tile);
                    ediblePawns.add(ediblePawn);
                    blockingTile = edibleTile;
                }
            }
        }
    }

    public static ArrayList<JButton> getAvailableTiles(JButton currentTile) {

        ArrayList<JButton> availableTiles = new ArrayList<>();

        // Check the closest tiles to the current tile
        for (JButton tile : getClosestTiles(currentTile)) {

            //tile.setBackground(Color.magenta);

            // If there is no pawn on the closest tile, add it to available tiles
            if (Tile.isClear(tile)) {

                availableTiles.add(tile);
            } else {

                setEdible(tile, availableTiles);
            }
        }


        return availableTiles;
    }
}
