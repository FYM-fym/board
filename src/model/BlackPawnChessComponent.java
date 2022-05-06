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
            chessComponents[source.getX()][source.getY()].WhetherFirst=2;
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
        chessComponents[source.getX()][source.getY()].WhetherFirst=1;
        return true;
    }



    public boolean canMoveTo2(ChessComponent[][] chessComponents, ChessboardPoint destination,Step step,Chessboard chessboard,ClickController clickController){
        if (IfEatRoad(chessComponents,destination,step)){
            EatRoadPawn(chessComponents,destination,step,chessboard,clickController);
            return true;
        }else return false;
    }


    //判断是否可以吃过路兵
    public boolean IfEatRoad(ChessComponent[][] chessComponents, ChessboardPoint destination, Step step){
        ChessboardPoint source = getChessboardPoint();
        int x =source.getX();
        int y =source.getY();
        if (x==3&&destination.getX()==2){
            if (chessComponents[x][y-1]instanceof BlackPawnChessComponent||chessComponents[x][y+1]instanceof BlackPawnChessComponent){
                if (step.initialX==1&&step.initialY==destination.getY()){
                    return true;
                }
            }
        }
        return false;
    }


    //执行吃过路兵的操作
    public void EatRoadPawn(ChessComponent[][] chessComponents, ChessboardPoint destination,Step step,Chessboard chessboard,ClickController clickController){
        if (IfEatRoad(chessComponents, destination,step)){
            Chessboard.chessmatrix[destination.getX()][destination.getY()+1] = 0;
            chessboard.putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(destination.getX(), destination.getY()+1), new Point(destination.getX() * 76, (destination.getY()+1) * 76), clickController, 76));
        }
    }

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
