package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的兵
 */
public class PawnChessComponent extends ChessComponent{

    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    private Image pawnImage;
    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    @Override
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null){
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }
        if (PAWN_BLACK == null){
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX()-destination.getX())==Math.abs(source.getY()-destination.getY())){
            int row = source.getX();
            int column = source.getY();

            if (source.getY()-destination.getY()==source.getX()-destination.getX()){
                for (int i = Math.min(source.getY(),destination.getY())+1; i < Math.max(source.getY(), destination.getY()) ; i++) {
                    if (!(chessComponents[i+row-column][i] instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            }else if (source.getY()-destination.getY()==destination.getX()-source.getX()){
                for (int i = Math.min(source.getY(),destination.getY())+1; i < Math.max(source.getY(), destination.getY()) ; i++) {
                    if (!(chessComponents[-i+column+row][i] instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            }
        }else {
            return false;
        }
        return true;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }


}
