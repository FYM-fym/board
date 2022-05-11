package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;
import view.Step;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BlackPawnChessComponent extends ChessComponent {
    private static Image Pawn_BLACK;
    private Image PawnImage;

    public void loadResource() throws IOException {
        if (Pawn_BLACK == null) {
            Pawn_BLACK = ImageIO.read(new File("./images/Pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.BLACK) {
                PawnImage = Pawn_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlackPawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, int special, int WhetherFirst) {
        super(chessboardPoint, location, color, listener, size, special, WhetherFirst);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() != 1) {
            if (destination.getX() == source.getX() + 1 && destination.getY() == source.getY()) {
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            } else if (destination.getX() == source.getX() + 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return true;
                } else {//吃过路兵
                    System.out.println(source.getX());
                    System.out.println(Chessboard.steps.get(Chessboard.steps.size() - 1).laterX);
                    System.out.println(Chessboard.steps.get(Chessboard.steps.size() - 1).laterY == destination.getY());
                    System.out.println(chessComponents[source.getX()][destination.getY()].WhetherFirst);
                    if (source.getX() == 4 && Chessboard.steps.get(Chessboard.steps.size() - 1).laterX == 6 &&
                            Chessboard.steps.get(Chessboard.steps.size() - 1).laterY == destination.getY() &&
                            chessComponents[source.getX()][destination.getY()].WhetherFirst == 1) {
                        return true;
                    }
                }
            }
            return false;
        } else if (source.getX() == 1) {
            if (destination.getX() == source.getX() + 1 && destination.getY() == source.getY()) {
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            } else if (destination.getX() == source.getX() + 1 && Math.abs(destination.getY() - source.getY()) == 1) {
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                    return true;
                }
            } else if (destination.getX() == source.getX() + 2 && destination.getY() == source.getY()) {
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent) {
                    return true;
                }
            } else return false;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(PawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
