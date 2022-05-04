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
import java.io.*;
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
    ArrayList<Step> steps = new ArrayList<>();


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
        } else {//当前选中的不是空棋子
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
        Step step = new Step(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY()
                , chess2.getChessboardPoint().getX(), chess2.getChessboardPoint().getY(), chessmatrix);
        //每下一步棋，在行棋的步骤记录这个ArrayList里面记录下棋的这一步操作
        steps.add(step);

        int x;
        for (int i = 0;i<=7;i++){
            if (chessmatrix[0][i]>=1 && chessmatrix[0][i]<=8){
                String[] options = {"后","车","象","马"};
                Icon icon = new ImageIcon("images/Pawn-white.png");
                x = JOptionPane.showOptionDialog(null,"需要将兵升级成哪种棋子","兵的升变",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,icon,options,null);
                if (x==0){
                    initQueenOnBoard(0,i,ChessColor.WHITE,15);
                    repaint();
                    break;
                }
            }
        }

    }

    public void initiateEmptyChessboard() {//Demo自带，初始化时候用
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
        chessmatrix[row][col] = special;

    }

    private void initBishopOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;

    }

    private void initKnightOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initKingOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initQueenOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initBlackPawnOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new BlackPawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initWhitePawnOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new WhitePawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
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


    public boolean checkStep(ArrayList<Step> steps) {
        int box = 0;
        //判断第一次行棋是否正确
        int x0 = steps.get(0).initialX;
        int y0 = steps.get(0).initialY;
        int x1 = steps.get(1).laterX;
        int y1 = steps.get(1).laterY;
        int[][] chessboard0 = {{-9, -11, -13, -15, -16, -14, -12, -10}, {-1, -2, -3, -4, -5, -6, -7, -8}, {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}
                , {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}, {1, 2, 3, 4, 5, 6, 7, 8}, {9, 11, 13, 15, 16, 14, 12, 10}};
        int[][] chessboard1 = steps.get(0).laterChessboard;


        //对棋盘大小8*8的检测
        if (steps.size() != 8) return false;
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).laterChessboard.length != 8) {
                return false;
            }
        }


        //对棋盘信息的检测
        if (chessboard0[x0][y0] == chessboard1[x1][y1] && chessboard0[x1][y1] == chessboard1[x0][y0]) {
            int box2 = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (chessboard0[i][j] != chessboard1[i][j]) {
                        box2++;
                    }
                }
            }
            if (box2 != 2) {
                return false;
            }
        } else {
            return false;
        }

        //判断第i次行棋是否正确（i>1）
        for (int i = 1; i < steps.size(); i++) {
            int x = steps.get(i).initialX;
            int y = steps.get(i).initialY;
            int X = steps.get(i).laterX;
            int Y = steps.get(i).laterY;
            int[][] firstChessboard = steps.get(i - 1).laterChessboard;
            int[][] secondchessboard = steps.get(i).laterChessboard;
            if (firstChessboard[x][y] == secondchessboard[X][Y] && firstChessboard[X][Y] == secondchessboard[x][y]) {
                int box3 = 0;
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        if (firstChessboard[j][k] != secondchessboard[j][k]) {
                            box3++;
                        }
                    }
                }
                if (box3 != 2) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }


    public void Writer(ArrayList<Step> steps) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\DELL\\IdeaProjects\\Store"));
        StringBuilder stringBuilder = new StringBuilder();
        //遍历输出x,y,X,Y,chessBoardMatrix
        for (int i = 0; i < steps.size(); i++) {
            int x = steps.get(i).initialX;
            int y = steps.get(i).initialY;
            int X = steps.get(i).laterX;
            int Y = steps.get(i).laterY;
            int[][] chessBoardMatrix = steps.get(i).laterChessboard;
            writer.write(x);
            writer.write(y);
            writer.write(X);
            writer.write(Y);
            writer.newLine();
            for (int j = 0; j < 8; j++) {
                for (int k = 0; k < 8; k++) {
                    writer.write(chessBoardMatrix[j][k]);//一行一行存
                }
                writer.newLine();
            }
        }
    }

    public void Reader(ArrayList<Step> steps) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\DELL\\IdeaProjects\\Store"));
        int linenumber = 1;
        String s = null;
        int conveniece = linenumber % 9;//帮助余数
        int round = (linenumber - conveniece + 9) / 9;//代表第几轮
        while ((s = reader.readLine()) != null) {

            if (conveniece == 1) {
                steps.get(round - 1).initialX = s.charAt(0) - '0';
                steps.get(round - 1).initialY = s.charAt(1) - '0';
                steps.get(round - 1).laterX = s.charAt(2) - '0';
                steps.get(round - 1).laterY = s.charAt(3) - '0';
            } else {
                steps.get(round - 1).laterChessboard[conveniece - 2][0] = s.charAt(0) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][1] = s.charAt(1) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][2] = s.charAt(2) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][3] = s.charAt(3) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][4] = s.charAt(4) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][5] = s.charAt(5) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][6] = s.charAt(6) - '0';
                steps.get(round - 1).laterChessboard[conveniece - 2][7] = s.charAt(7) - '0';

            }
            linenumber++;//循环回合数++
        }
    }


    public void remake(ArrayList<Step> steps) {
        int x = steps.get(steps.size() - 1).initialX;
        int y = steps.get(steps.size() - 1).initialY;
        int X = steps.get(steps.size() - 1).laterX;
        int Y = steps.get(steps.size() - 1).laterY;
        int[][] chessboardbefore = steps.get(steps.size() - 2).laterChessboard;//倒数第一步之前的棋盘
        int before = chessboardbefore[x][y];//倒数第一步的挪动的那个棋子spacial
        int after = chessboardbefore[X][Y];//倒数第一步的目标棋子special
        chessmatrix[x][y]=before;
        chessmatrix[X][Y]=after;
        steps.remove(steps.size() - 1);
        //开始把（x,y)处的棋子变回原位
        if (before==1||before==2||before==3||before==4||before==5||before==6||before==7||before==8){
            initWhitePawnOnBoard(x,y,ChessColor.WHITE,before);
        }else if (before==-1||before==-2||before==-3||before==-4||before==-5||before==-6||before==-7||before==-8){
            initBlackPawnOnBoard(x,y,ChessColor.BLACK,before);
        }else if (before==9||before==10||before==-9||before==-10){
            if (before>0)initRookOnBoard(x,y,ChessColor.WHITE,before);
            else initRookOnBoard(x,y,ChessColor.BLACK,before);
        }else if (before==11||before==12||before==-11||before==-12){
            if (before>0)initKnightOnBoard(x,y,ChessColor.WHITE,before);
            else initKnightOnBoard(x,y,ChessColor.BLACK,before);
        }else if (before==13||before==14||before==-13||before==-14){
            if (before>0)initBishopOnBoard(x,y,ChessColor.WHITE,before);
            else initBishopOnBoard(x,y,ChessColor.BLACK,before);
        }else if (before==15||before==-15){
            if (before>0)initQueenOnBoard(x,y,ChessColor.WHITE,before);
            else initQueenOnBoard(x,y,ChessColor.BLACK,before);
        }else if (before==16||before==-16){
            if (before>0)initKingOnBoard(x,y,ChessColor.WHITE,before);
            else initKingOnBoard(x,y,ChessColor.BLACK,before);
        }




        //之后把（X,Y)处的棋子变回原位
        if (after==1||after==2||after==3||after==4||after==5||after==6||after==7||after==8){
            initWhitePawnOnBoard(X,Y,ChessColor.WHITE,after);
        }else if (after==-1||after==-2||after==-3||after==-4||after==-5||after==-6||after==-7||after==-8){
            initBlackPawnOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==9||after==10||after==-9||after==-10){
            if (after>0)initRookOnBoard(X,Y,ChessColor.WHITE,after);
            else initRookOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==11||after==12||after==-11||after==-12){
            if (after>0)initKnightOnBoard(X,Y,ChessColor.WHITE,after);
            else initKnightOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==13||after==14||after==-13||after==-14){
            if (after>0)initBishopOnBoard(X,Y,ChessColor.WHITE,after);
            else initBishopOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==15||after==-15){
            if (after>0)initQueenOnBoard(X,Y,ChessColor.WHITE,after);
            else initQueenOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==16||after==-16){
            if (after>0)initKingOnBoard(X,Y,ChessColor.WHITE,after);
            else initKingOnBoard(X,Y,ChessColor.BLACK,after);
        }else if (after==0){
            ChessComponent chessComponentkong = new EmptySlotComponent(new ChessboardPoint(X, Y), calculatePoint(X, Y), clickController, CHESS_SIZE);
            putChessOnBoard(chessComponentkong);
            chessComponentkong.setVisible(true);
        }
    }

    public void newGame(){


        //都变成空棋子
        initiateEmptyChessboard();
        //重置其它的棋子
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
}