package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class KingChessComponent extends ChessComponent {

    private static Image KING_WHITE;
    private static Image KING_BLACK;

    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, int special, int WhetherFirst) {
        super(chessboardPoint, location, color, listener, size, special, WhetherFirst);
        initiateKingImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX() == 0 && source.getY() == 4 && destination.getY() == 2 && destination.getX() == 0 &&
                chessComponents[0][0] instanceof RookChessComponent &&
                chessComponents[0][1] instanceof EmptySlotComponent &&
                chessComponents[0][2] instanceof EmptySlotComponent &&
                chessComponents[0][3] instanceof EmptySlotComponent &&
                chessComponents[0][4].WhetherFirst == 0 &&
                chessComponents[0][0].WhetherFirst == 0) {
            //对车操作
            return true;
        }


        if (source.getX() == 0 && source.getY() == 4 && destination.getY() == 6 && destination.getX() == 0 &&
                chessComponents[0][7] instanceof RookChessComponent &&
                chessComponents[0][6] instanceof EmptySlotComponent &&
                chessComponents[0][5] instanceof EmptySlotComponent &&
                chessComponents[0][4].WhetherFirst == 0 &&
                chessComponents[0][7].WhetherFirst == 0) {
            //对车操作
            return true;
        }
        if (source.getX() == 7 && source.getY() == 4 && destination.getY() == 2 && destination.getX() == 7 &&
                chessComponents[7][0] instanceof RookChessComponent &&
                chessComponents[7][1] instanceof EmptySlotComponent &&
                chessComponents[7][2] instanceof EmptySlotComponent &&
                chessComponents[7][3] instanceof EmptySlotComponent &&
                chessComponents[7][4].WhetherFirst == 0 &&
                chessComponents[7][0].WhetherFirst == 0) {
            //对车操作
            return true;
        }


        if (source.getX() == 7 && source.getY() == 4 && destination.getY() == 6 && destination.getX() == 7 &&
                chessComponents[7][7] instanceof RookChessComponent &&
                chessComponents[7][6] instanceof EmptySlotComponent &&
                chessComponents[7][5] instanceof EmptySlotComponent &&
                chessComponents[7][4].WhetherFirst == 0 &&
                chessComponents[7][7].WhetherFirst == 0) {
            //对车操作
            return true;
        }




        if (Math.abs(source.getX() - destination.getX()) <= 1 && Math.abs(source.getY() - destination.getY()) <= 1) {
            chessComponents[source.getX()][source.getY()].WhetherFirst = 1;
            return true;
        } else return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }
}
