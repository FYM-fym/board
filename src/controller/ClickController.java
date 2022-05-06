package controller;


import model.BlackPawnChessComponent;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.WhitePawnChessComponent;
import view.ChessGameFrame;
import view.Chessboard;
import view.Step;


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
                //repaint in swap chess method.
                chessboard.swapChessMatrix(first,chessComponent);
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                first.setSelected(false);
                first = null;
                Round++;
                Round2=Round/2;
            }
        }
    }



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
