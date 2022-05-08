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

    public BlackPawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int special,int WhetherFirst) {
        super(chessboardPoint, location, color, listener, size, special,WhetherFirst);
        initiatePawnImage(color);
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
            chessComponents[source.getX()][source.getY()].WhetherFirst++;
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
        chessComponents[source.getX()][source.getY()].WhetherFirst++;
        return true;
    }



    /*public boolean canMoveTo2(ChessComponent[][] chessComponents, ChessboardPoint destination,Step step,Chessboard chessboard,ClickController clickController){
        if (IfEatRoad(chessComponents,destination,step)){
            EatRoadPawn(chessComponents,destination,step,chessboard,clickController);
            return true;
        }else return false;
    }*/





    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(PawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
