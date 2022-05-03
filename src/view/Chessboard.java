package view;

import model.ChessColor;
import model.ChessComponent;
import model.EmptySlotComponent;
import model.RookChessComponent;
import model.BishopChessComponent;
import model.KnightChessComponent;
import model.KingChessComponent;
import model.QueenChessComponent;
import model.BlackPawnChessComponent;
import model.WhitePawnChessComponent;
import controller.ClickController;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.BLACK;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    public int Round = 1;
    public int Round2 = 0;
    public JLabel statusRound;
    public static int[][] chessmatrix = new int[8][8];
    ArrayList<Step> steps= new ArrayList<>();


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();

        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK, -9);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK, -10);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE, 9);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE, 10);
        initBishopOnBoard(0, 2, ChessColor.BLACK, -13);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK, -14);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE, 13);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE, 14);
        initKnightOnBoard(0, 1, ChessColor.BLACK, -11);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK, -12);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE, 11);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE, 12);
        initKingOnBoard(0, 4, ChessColor.BLACK, -16);
        initKingOnBoard(7, 4, ChessColor.WHITE, 16);
        initQueenOnBoard(0, 3, ChessColor.BLACK, -15);
        initQueenOnBoard(7, 3, ChessColor.WHITE, 15);
        initBlackPawnOnBoard(1, 0, ChessColor.BLACK, -1);
        initBlackPawnOnBoard(1, 1, ChessColor.BLACK, -2);
        initBlackPawnOnBoard(1, 2, ChessColor.BLACK, -3);
        initBlackPawnOnBoard(1, 3, ChessColor.BLACK, -4);
        initBlackPawnOnBoard(1, 4, ChessColor.BLACK, -5);
        initBlackPawnOnBoard(1, 5, ChessColor.BLACK, -6);
        initBlackPawnOnBoard(1, 6, ChessColor.BLACK, -7);
        initBlackPawnOnBoard(1, 7, ChessColor.BLACK, -8);
        initWhitePawnOnBoard(6, 0, ChessColor.WHITE, 1);
        initWhitePawnOnBoard(6, 1, ChessColor.WHITE, 2);
        initWhitePawnOnBoard(6, 2, ChessColor.WHITE, 3);
        initWhitePawnOnBoard(6, 3, ChessColor.WHITE, 4);
        initWhitePawnOnBoard(6, 4, ChessColor.WHITE, 5);
        initWhitePawnOnBoard(6, 5, ChessColor.WHITE, 6);
        initWhitePawnOnBoard(6, 6, ChessColor.WHITE, 7);
        initWhitePawnOnBoard(6, 7, ChessColor.WHITE, 8);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }


    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        chess1.repaint();
        chess2.repaint();

        /*System.out.println(Round);
        Round++;
        Round+=(Round+1)/2;*/

        /*fym.addLabel();*/
        statusRound = new JLabel("Round:" + Round);
        statusRound.setLocation(760, 760 / 10 + 480);
        statusRound.setSize(200, 60);
        statusRound.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusRound);
        statusRound.setVisible(true);
    }

    public void swapChessMatrix(ChessComponent chess1, ChessComponent chess2) {//chess1是一开始选中的，chess2 是当前选中的

        int chess1special = chess1.special;
        int chess2special = chess2.special;
        int x1 = chess1.getChessboardPoint().getX();
        int y1 = chess1.getChessboardPoint().getY();
        int x2 = chess2.getChessboardPoint().getX();
        int y2 = chess2.getChessboardPoint().getY();







        if (chess2 instanceof EmptySlotComponent) {//当前选中的是空棋子
            chessmatrix[x1][y1] = chess2special;
            chessmatrix[x2][y2] = chess1special;
            //输出一下数组
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(chessmatrix[i][j]);
                }
                System.out.println();
            }
        }else {//当前选中的不是空棋子
            chessmatrix[x1][y1] = 0;
            chessmatrix[x2][y2] = chess1special;
            //输出一下数组
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(chessmatrix[i][j]);
                }
                System.out.println();
            }
        }


        //构造一个Step的对象，来记录这一步行棋
        Step step = new Step(chess1.getChessboardPoint().getX(),chess1.getChessboardPoint().getY()
                ,chess2.getChessboardPoint().getX(),chess2.getChessboardPoint().getY(),chessmatrix);
        //每下一步棋，在行棋的步骤记录这个ArrayList里面记录下棋的这一步操作
        steps.add(step);



    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                chessmatrix[i][j] = 0;
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        Round++;
        Round2 = Round / 2;

    }
    /*public int addRound(){
        Round++;
        Round2=Round/2;
        return Round2;
    }*/

    private void initRookOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[0][0] = -9;
        chessmatrix[0][7] = -10;
        chessmatrix[7][7] = 10;
        chessmatrix[7][0] = 9;

    }

    private void initBishopOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[0][2] = -13;
        chessmatrix[0][5] = -14;
        chessmatrix[7][2] = 13;
        chessmatrix[7][5] = 14;
    }

    private void initKnightOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[0][1] = -11;
        chessmatrix[0][6] = -12;
        chessmatrix[7][1] = 11;
        chessmatrix[7][6] = 12;
    }

    private void initKingOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[0][4] = -16;
        chessmatrix[7][4] = 16;
    }

    private void initQueenOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[0][3] = -15;
        chessmatrix[7][3] = 15;
    }

    private void initBlackPawnOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new BlackPawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[1][0] = -1;
        chessmatrix[1][1] = -2;
        chessmatrix[1][2] = -3;
        chessmatrix[1][3] = -4;
        chessmatrix[1][4] = -5;
        chessmatrix[1][5] = -6;
        chessmatrix[1][6] = -7;
        chessmatrix[1][7] = -8;

    }

    private void initWhitePawnOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new WhitePawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[6][0] = 1;
        chessmatrix[6][1] = 2;
        chessmatrix[6][2] = 3;
        chessmatrix[6][3] = 4;
        chessmatrix[6][4] = 5;
        chessmatrix[6][5] = 6;
        chessmatrix[6][6] = 7;
        chessmatrix[6][7] = 8;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessData) {
        chessData.forEach(System.out::println);
    }

    public int getRound() {
        return Round;
    }


    public boolean checkStep(ArrayList<Step> steps){
        int box =0;
        //判断第一次行棋是否正确
        int x0 = steps.get(0).initialX;
        int y0 = steps.get(0).initialY;
        int x1 = steps.get(1).laterX;
        int y1 = steps.get(1).laterY;
        int[][] chessboard0 = {{-9,-11,-13,-15,-16,-14,-12,-10},{-1,-2,-3,-4,-5,-6,-7,-8},{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0}
                ,{0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0},{1,2,3,4,5,6,7,8},{9,11,13,15,16,14,12,10}};
        int[][] chessboard1 = steps.get(0).laterChessboard;
        if (chessboard0[x0][y0]==chessboard1[x1][y1]&&chessboard0[x1][y1]==chessboard1[x0][y0]){
            int box2 =0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessboard0[i][j]!= chessboard1[i][j]){
                        box2++;
                    }
                }
            }
            if (box2!=2){
                return false;
            }
        }else {
            return false;
        }

        //判断第i次行棋是否正确（i>1）
        for (int i = 1; i < steps.size(); i++) {
            int x = steps.get(i).initialX;
            int y = steps.get(i).initialY;
            int X = steps.get(i).laterX;
            int Y = steps.get(i).laterY;
            int [][] firstChessboard = steps.get(i-1).laterChessboard;
            int [][] secondchessboard = steps.get(i).laterChessboard;
            if (firstChessboard[x][y]==secondchessboard[X][Y]&&firstChessboard[X][Y]==secondchessboard[x][y]){
                int box3=0;
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k <8; k++) {
                        if (firstChessboard[j][k]!=secondchessboard[j][k]){
                            box3++;
                        }
                    }
                }
                if (box3!=2){
                    box++;
                }
            }else {
                box++;
            }
            if (box!=0){
                return false;
            }
        }
        return true;
    }


}