package controller;


import model.BlackPawnChessComponent;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.WhitePawnChessComponent;
import view.ChessGameFrame;
import view.Chessboard;
import view.ChessboardPoint;
import view.Step;
import java.awt.*;


public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    int Round = 2;
    int Round2 = 1;



    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {//选中的棋子是chessComponent，并且当前状态是没选中棋子
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                /*if (IfEatRoad(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),chessboard)==1){
                    this.chessboard.EatBlackRoadPawn(destination);
                }
                if (IfEatRoad(chessboard.getChessComponents(), chessComponent.getChessboardPoint(),chessboard)==2){
                    chessboard.EatWhiteRoadPawn(destination);
                }*/





                //repaint in swap chess method.
                chessboard.swapChessMatrix(first,chessComponent);
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;






            }
        }
    }


    //判断是否可以吃过路兵
    public int IfEatRoad(ChessComponent[][] chessComponents, ChessboardPoint destination,Chessboard chessboard){
        ChessboardPoint source = first.getChessboardPoint();
        int x =source.getX();
        int y =source.getY();
        if (x==3&&destination.getX()==2&&chessComponents[x-1][destination.getY()]instanceof EmptySlotComponent){
            if (chessComponents[x][y-1]instanceof BlackPawnChessComponent||chessComponents[x][y+1]instanceof BlackPawnChessComponent){
                if (chessboard.steps.get(chessboard.steps.size()-1).initialX==1&&chessboard.steps.get(chessboard.steps.size()-1).initialY==destination.getY()){
                    return 1;
                }
            }
        }
        if (x==4&&destination.getX()==5&&chessComponents[x+1][destination.getY()]instanceof EmptySlotComponent){
            if (chessComponents[x][y-1]instanceof WhitePawnChessComponent||chessComponents[x][y+1]instanceof WhitePawnChessComponent){
                if (chessboard.steps.get(chessboard.steps.size()-1).initialX==6&&chessboard.steps.get(chessboard.steps.size()-1).initialY==destination.getY()){
                    return 2;
                }
            }
        }
        return 3;
    }





    /*public boolean KingRookChange(ChessComponent chessComponent){
        if (chessComponent.getChessboardPoint().getX()==first.getX()){
            if (chessComponent instanceof EmptySlotComponent){
                if(Math.abs(chessComponent.getChessboardPoint().getY()-first.getY())==2){
                    if ()
                }
            }
        }
    }*/
    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }





}
