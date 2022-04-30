package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class BlackBishopChessComponent extends ChessComponent {
    private static Image BISHOP_BLACK;
    private Image bishopImage;
    public void loadResource() throws IOException {

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BlackBishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if (source.getX()!=1) {
            if (destination.getX()==source.getX()+1&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }else if (destination.getX()==source.getX()+1&&Math.abs(destination.getY()-source.getY())==1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                    return false;
                }
            }else return false;
            return true;
        }else if (source.getX()==1){
            if (destination.getX()==source.getX()+1&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }else if (destination.getX()==source.getX()+1&&Math.abs(destination.getY()-source.getY())==1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                    return false;
                }
            }else if (destination.getX()==source.getX()+2&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }else return false;
        }

        return true;




        /*if (destination.getX()==source.getX()+1&&destination.getY()==source.getY()){
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                return false;
            }
        }else if (destination.getX()==source.getX()+1&&Math.abs(destination.getY()-source.getY())==1){
            if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                return false;
            }
        }else return false;*/

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
