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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent  {
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
    public int[][] chessmatrix = new int[8][8];
    public static ArrayList<Step> steps = new ArrayList<>();
    String player1 = "White";
    String player2 = "Black";
    JLabel statusRound;


    public static void main(String[] args) {
        JFrame f = new JFrame("2022 CS102A Project Demo");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000,760);
        f.setLocationRelativeTo(null);
        Container c = f.getContentPane();
        c.setBackground(Color.WHITE);
        JPanel jPanel = new JPanel();
        f.add(jPanel);
        f.setLayout(null);

        JLabel label = new JLabel(new ImageIcon("./images/horse.jpg"));
        f.add(label);
        label.setBounds(0, 0, f.getWidth(), f.getHeight());
        label.setFont(new Font("Rockwell",Font.BOLD,20));
        label.setOpaque(true);

        JButton a = new JButton("开始游戏");
        c.add(a);
        a.setBounds(400,300,200,70);
        a.setFont(new Font("Rockwell", Font.BOLD, 20));
        a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.dispose();
                SwingUtilities.invokeLater(() -> {
                    ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                    mainFrame.setVisible(true);
                });
            }
        });

    }

    public Chessboard(int width, int height, JLabel statusRound) {
        this.statusRound = statusRound;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
        swapColor();


        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK, -9, 0);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK, -10, 0);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE, 9, 0);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE, 10, 0);
        initBishopOnBoard(0, 2, ChessColor.BLACK, -13);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK, -14);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE, 13);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE, 14);
        initKnightOnBoard(0, 1, ChessColor.BLACK, -11);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK, -12);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE, 11);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE, 12);
        initKingOnBoard(0, 4, ChessColor.BLACK, -16, 0);
        initKingOnBoard(7, 4, ChessColor.WHITE, 16, 0);
        initQueenOnBoard(0, 3, ChessColor.BLACK, -15);
        initQueenOnBoard(7, 3, ChessColor.WHITE, 15);
        initBlackPawnOnBoard(1, 0, ChessColor.BLACK, -1, 0);
        initBlackPawnOnBoard(1, 1, ChessColor.BLACK, -2, 0);
        initBlackPawnOnBoard(1, 2, ChessColor.BLACK, -3, 0);
        initBlackPawnOnBoard(1, 3, ChessColor.BLACK, -4, 0);
        initBlackPawnOnBoard(1, 4, ChessColor.BLACK, -5, 0);
        initBlackPawnOnBoard(1, 5, ChessColor.BLACK, -6, 0);
        initBlackPawnOnBoard(1, 6, ChessColor.BLACK, -7, 0);
        initBlackPawnOnBoard(1, 7, ChessColor.BLACK, -8, 0);
        initWhitePawnOnBoard(6, 0, ChessColor.WHITE, 1, 0);
        initWhitePawnOnBoard(6, 1, ChessColor.WHITE, 2, 0);
        initWhitePawnOnBoard(6, 2, ChessColor.WHITE, 3, 0);
        initWhitePawnOnBoard(6, 3, ChessColor.WHITE, 4, 0);
        initWhitePawnOnBoard(6, 4, ChessColor.WHITE, 5, 0);
        initWhitePawnOnBoard(6, 5, ChessColor.WHITE, 6, 0);
        initWhitePawnOnBoard(6, 6, ChessColor.WHITE, 7, 0);
        initWhitePawnOnBoard(6, 7, ChessColor.WHITE, 8, 0);
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

        chess1.WhetherFirst++;

        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;
        chess1.repaint();
        chess2.repaint();


        if (Round2 % 2 == 0){
            statusRound.setText("Round: " + Round + "  " + player2);
        }else {
            statusRound.setText("Round: " + Round + "  " + player1);
        }
        Round2++;
        Round += Round2%2;
        System.out.println(Round+"AAAAAAAAAAAAA"+Round2);

//兵的升变，在这里实现
        int x;
        for (int i = 0; i <= 7; i++) {
            if (chessmatrix[0][i] >= 1 && chessmatrix[0][i] <= 8) {
                repaint();
                String[] options = {"后", "车", "象", "马"};
                Icon icon = new ImageIcon("images/Pawn-white.png");
                x = JOptionPane.showOptionDialog(null, "需要将兵升级成哪种棋子", "兵的升变", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
                if (x == 0) {
                    initQueenOnBoard(0, i, ChessColor.WHITE, 15);
                    repaint();
                    break;
                } else if (x == 1) {
                    initRookOnBoard(0, i, ChessColor.WHITE, 9, 1);
                    repaint();
                    break;
                } else if (x == 2) {
                    initBishopOnBoard(0, i, ChessColor.WHITE, 13);
                    repaint();
                    break;
                } else if (x == 3) {
                    initKnightOnBoard(0, i, ChessColor.WHITE, 11);
                    repaint();
                    break;
                }
            }
        }
        for (int i = 0; i <= 7; i++) {
            if (chessmatrix[7][i] <= -1 && chessmatrix[7][i] >= -8) {
                repaint();
                String[] options = {"后", "车", "象", "马"};
                Icon icon = new ImageIcon("images/Pawn-black.png");
                x = JOptionPane.showOptionDialog(null, "需要将兵升级成哪种棋子", "兵的升变", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, icon, options, null);
                if (x == 0) {
                    initQueenOnBoard(7, i, ChessColor.BLACK, -15);
                    repaint();
                    break;
                } else if (x == 1) {
                    initRookOnBoard(7, i, ChessColor.BLACK, -9, 1);
                    repaint();
                    break;
                } else if (x == 2) {
                    initBishopOnBoard(7, i, ChessColor.BLACK, -13);
                    repaint();
                    break;
                } else if (x == 3) {
                    initKnightOnBoard(7, i, ChessColor.BLACK, -11);
                    repaint();
                    break;
                }
            }
        }
        int[][] matrixclone = new int[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matrixclone[i][j]=chessmatrix[i][j];
            }
        }

        //进行王车移位的操作
        if (chessComponents[0][2]instanceof KingChessComponent&&chessComponents[0][0]instanceof RookChessComponent){
            if (chessComponents[0][2].WhetherFirst==1&&chessComponents[0][0].WhetherFirst==0){
                initiateEmptyChessboard(0,0);
                chessmatrix[0][0]=0;
                initRookOnBoard(0,3,ChessColor.BLACK,-9,1);
                repaint();
            }
        }
        if (chessComponents[0][6]instanceof KingChessComponent&&chessComponents[0][7]instanceof RookChessComponent){
            if (chessComponents[0][6].WhetherFirst==1&&chessComponents[0][7].WhetherFirst==0){
                initiateEmptyChessboard(0,7);
                initRookOnBoard(0,5,ChessColor.BLACK,-10,1);
                repaint();
            }
        }if (chessComponents[7][2]instanceof KingChessComponent&&chessComponents[7][0]instanceof RookChessComponent){
            if (chessComponents[7][2].WhetherFirst==1&&chessComponents[7][0].WhetherFirst==0){
                initiateEmptyChessboard(7,0);
                initRookOnBoard(7,3,ChessColor.WHITE,9,1);
                repaint();
            }
        }if (chessComponents[7][6]instanceof KingChessComponent&&chessComponents[7][7]instanceof RookChessComponent){
            if (chessComponents[7][6].WhetherFirst==1&&chessComponents[7][7].WhetherFirst==0){
                initiateEmptyChessboard(7,7);
                initRookOnBoard(7,5,ChessColor.WHITE,10,1);
                repaint();
            }
        }



        //构造一个Step的对象，来记录这一步行棋，值得注意的是这里的存储发生在行棋交换顺序之后，而且是（如果的话）发生兵的升变之后。
        Step step = new Step(chess1.getChessboardPoint().getX(), chess1.getChessboardPoint().getY()
                , chess2.getChessboardPoint().getX(), chess2.getChessboardPoint().getY(), matrixclone);
        //每下一步棋，在行棋的步骤记录这个ArrayList里面记录下棋的这一步操作
        steps.add(step);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(steps.get(steps.size()-1).laterChessboard[i][j]);
            }
            System.out.println();
        }

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
            /*for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(chessmatrix[i][j]);
                }
                System.out.println();
            }*/
        } else {//当前选中的不是空棋子
            chessmatrix[x1][y1] = 0;
            chessmatrix[x2][y2] = chess1special;
            //输出一下数组
            /*for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(chessmatrix[i][j]);
                }
                System.out.println();
            }*/
        }
        repaint();
    }

    public void initiateEmptyChessboard() {//Demo自带，初始化时候用
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                chessmatrix[i][j] = 0;
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }
    public void initiateEmptyChessboard(int x, int y){
        chessmatrix[x][y]=0;
        putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(x,y),calculatePoint(x,y),clickController,CHESS_SIZE));
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }


    public void initRookOnBoard(int row, int col, ChessColor color, int special, int WhetherFirst) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, WhetherFirst);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    public void initBishopOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, 1);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    public void initKnightOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, 1);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initKingOnBoard(int row, int col, ChessColor color, int special, int WhetherFirst) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, WhetherFirst);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initQueenOnBoard(int row, int col, ChessColor color, int special) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, 1);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initBlackPawnOnBoard(int row, int col, ChessColor color, int special, int WhetherFirst) {
        ChessComponent chessComponent = new BlackPawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, WhetherFirst);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        chessmatrix[row][col] = special;
    }

    private void initWhitePawnOnBoard(int row, int col, ChessColor color, int special, int WhetherFirst) {
        ChessComponent chessComponent = new WhitePawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE, special, WhetherFirst);
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


    public boolean checkStep(ArrayList<Step> steps) {//需要进一步检查，吃过路兵和王车移位还有升变。
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
        if (chessboard0[x0][y0] == chessboard1[x1][y1]) {
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
        if (steps.size()==1){
            newGame();
            repaint();
        }else {
            //输出了一次
            for (int k=steps.size()-1;k>=0;k--) {
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        System.out.print(steps.get(steps.size() - 1).laterChessboard[i][j]);
                    }
                    System.out.println();
                }
            }
            int[][] chessboardafter = steps.get(steps.size() - 2).laterChessboard.clone();
            int [][]bb= new int[8][8];
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    bb [i][j]=chessboardafter[i][j];
                }
            }
            repaint();
            int b = 0;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    b=chessboardafter[i][j];
                    if (b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7 || b == 8) {
                        initWhitePawnOnBoard(i, j, ChessColor.WHITE, b, 1);
                    } else if (b == -1 || b == -2 || b == -3 || b == -4 || b == -5 || b == -6 || b == -7 || b == -8) {
                        initBlackPawnOnBoard(i, j, ChessColor.BLACK, b, 1);
                    } else if (b == 9 || b == 10 || b == -9 || b == -10) {
                        if (b > 0) initRookOnBoard(i, j, ChessColor.WHITE, b, 0);
                        else initRookOnBoard(i, j, ChessColor.BLACK, b, 0);
                    } else if (b == 11 || b == 12 || b == -11 || b == -12) {
                        if (b > 0) initKnightOnBoard(i, j, ChessColor.WHITE, b);
                        else initKnightOnBoard(i, j, ChessColor.BLACK, b);
                    } else if (b == 13 || b == 14 || b == -13 || b == -14) {
                        if (b > 0) initBishopOnBoard(i, j, ChessColor.WHITE, b);
                        else initBishopOnBoard(i, j, ChessColor.BLACK, b);
                    } else if (b == 15 || b == -15) {
                        if (b > 0) initQueenOnBoard(i, j, ChessColor.WHITE, b);
                        else initQueenOnBoard(i, j, ChessColor.BLACK, b);
                    } else if (b == 16 || b == -16) {
                        if (b > 0) initKingOnBoard(i, j, ChessColor.WHITE, b, 0);
                        else initKingOnBoard(i, j, ChessColor.BLACK, b, 0);
                    }else if (b==0){
                        initiateEmptyChessboard(i,j);
                    }
                }
            }
            repaint();
        }
        swapColor();
        System.out.println(Round+"AAAAAAAAAAAAA"+Round2);
        Round -= Round2%2;
        System.out.println(Round+"AAAAAAAAAAAAA"+Round2);
        Round2--;
        System.out.println(Round+"AAAAAAAAAAAAA"+Round2);
        if (Round2 % 2 == 0){
            if (Round==0){
                Round = 1;
                Round2 = 0;
                statusRound.setText("Round: 1  White");
                currentColor = ChessColor.WHITE;
            }else statusRound.setText("Round: " + Round + "  " + player1);
        }else {
            if (Round==0){
                Round = 1;
                Round2 = 0;
                statusRound.setText("Round: 1  White");
                currentColor = ChessColor.WHITE;
            }else statusRound.setText("Round: " + Round + "  " + player2);
        }
        steps.remove(steps.size() - 1);
        /*for (int k=steps.size()-1;k>=0;k--) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(steps.get(k).laterChessboard[i][j]);
                }
                System.out.println();
            }
        }*/

    }
        /*if (steps.size() == 1) {
            newGame();
        } else {
            int x = steps.get(steps.size() - 1).initialX;//最后一步（上一步）动的棋子移动之前的x坐标
            int y = steps.get(steps.size() - 1).initialY;//最后一步（上一步）动的棋子移动之前的y坐标
            int X = steps.get(steps.size() - 1).laterX;//最后一步（上一步）动的棋子移动之后的x坐标
            int Y = steps.get(steps.size() - 1).laterY;//最后一步（上一步）动的棋子移动之后的y坐标
            int[][] chessboardafter = steps.get(steps.size() - 2).laterChessboard;//最后一步之前的棋盘,倒数第二步的棋盘
            int b = chessboardafter[x][y];//最后一步的挪动的那个棋子spacial
            int after = chessboardafter[X][Y];//最后一步的目标棋子special
            //把矩阵变成上一步
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    chessmatrix[i][j] = chessboardafter[i][j];
                }
            }


            steps.remove(steps.size() - 1);

            //开始把（x,y)处的棋子变回原位
            if (b == 1 || b == 2 || b == 3 || b == 4 || b == 5 || b == 6 || b == 7 || b == 8) {
                initWhitePawnOnBoard(X, Y, ChessColor.WHITE, b, 1);
                System.out.println("AAAAA");
            } else if (b == -1 || b == -2 || b == -3 || b == -4 || b == -5 || b == -6 || b == -7 || b == -8) {
                initBlackPawnOnBoard(X, Y, ChessColor.BLACK, b, 1);
            } else if (b == 9 || b == 10 || b == -9 || b == -10) {
                if (b > 0) initRookOnBoard(X, Y, ChessColor.WHITE, b, 0);
                else initRookOnBoard(X, Y, ChessColor.BLACK, b, 0);
            } else if (b == 11 || b == 12 || b == -11 || b == -12) {
                if (b > 0) initKnightOnBoard(X, Y, ChessColor.WHITE, b);
                else initKnightOnBoard(X, Y, ChessColor.BLACK, b);
            } else if (b == 13 || b == 14 || b == -13 || b == -14) {
                if (b > 0) initBishopOnBoard(X, Y, ChessColor.WHITE, b);
                else initBishopOnBoard(X, Y, ChessColor.BLACK, b);
            } else if (b == 15 || b == -15) {
                if (b > 0) initQueenOnBoard(X, Y, ChessColor.WHITE, b);
                else initQueenOnBoard(X, Y, ChessColor.BLACK, b);
            } else if (b == 16 || b == -16) {
                if (b > 0) initKingOnBoard(X, Y, ChessColor.WHITE, b, 0);
                else initKingOnBoard(X, Y, ChessColor.BLACK, b, 0);
            }


            //之后把（X,Y)处的棋子变回原位

            System.out.println(b);
            System.out.println(after);


            if (after == 1 || after == 2 || after == 3 || after == 4 || after == 5 || after == 6 || after == 7 || after == 8) {
                initWhitePawnOnBoard(x, y, ChessColor.WHITE, after, 1);
            } else if (after == -1 || after == -2 || after == -3 || after == -4 || after == -5 || after == -6 || after == -7 || after == -8) {
                initBlackPawnOnBoard(x, y, ChessColor.BLACK, after, 1);
                System.out.println("BBBBBB");
            } else if (after == 9 || after == 10 || after == -9 || after == -10) {
                if (after > 0) initRookOnBoard(x, y, ChessColor.WHITE, after, 0);
                else initRookOnBoard(x, y, ChessColor.BLACK, after, 0);
            } else if (after == 11 || after == 12 || after == -11 || after == -12) {
                if (after > 0) initKnightOnBoard(x, y, ChessColor.WHITE, after);
                else initKnightOnBoard(x, y, ChessColor.BLACK, after);
            } else if (after == 13 || after == 14 || after == -13 || after == -14) {
                if (after > 0) initBishopOnBoard(x, y, ChessColor.WHITE, after);
                else initBishopOnBoard(x, y, ChessColor.BLACK, after);
            } else if (after == 15 || after == -15) {
                if (after > 0) initQueenOnBoard(x, y, ChessColor.WHITE, after);
                else initQueenOnBoard(x, y, ChessColor.BLACK, after);
            } else if (after == 16 || after == -16) {
                if (after > 0) initKingOnBoard(x, y, ChessColor.WHITE, after, 0);
                else initKingOnBoard(x, y, ChessColor.BLACK, after, 0);
            } else if (after == 0) {
                ChessComponent chessComponentkong = new EmptySlotComponent(new ChessboardPoint(x, y), calculatePoint(x, y), clickController, CHESS_SIZE);
                putChessOnBoard(chessComponentkong);
                chessComponentkong.setVisible(true);
            }
            repaint();
        }
    }*/

    public void newGame() {
        Round = 1;
        Round2 = 0;
        statusRound.setText("Round: 1  White");
        currentColor = ChessColor.WHITE;

        //都变成空棋子
        initiateEmptyChessboard();
        //重置其它的棋子
        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK, -9, 0);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK, -10, 0);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE, 9, 0);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE, 10, 0);
        initBishopOnBoard(0, 2, ChessColor.BLACK, -13);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK, -14);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE, 13);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE, 14);
        initKnightOnBoard(0, 1, ChessColor.BLACK, -11);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK, -12);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE, 11);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE, 12);
        initKingOnBoard(0, 4, ChessColor.BLACK, -16, 0);
        initKingOnBoard(7, 4, ChessColor.WHITE, 16, 0);
        initQueenOnBoard(0, 3, ChessColor.BLACK, -15);
        initQueenOnBoard(7, 3, ChessColor.WHITE, 15);
        initBlackPawnOnBoard(1, 0, ChessColor.BLACK, -1, 0);
        initBlackPawnOnBoard(1, 1, ChessColor.BLACK, -2, 0);
        initBlackPawnOnBoard(1, 2, ChessColor.BLACK, -3, 0);
        initBlackPawnOnBoard(1, 3, ChessColor.BLACK, -4, 0);
        initBlackPawnOnBoard(1, 4, ChessColor.BLACK, -5, 0);
        initBlackPawnOnBoard(1, 5, ChessColor.BLACK, -6, 0);
        initBlackPawnOnBoard(1, 6, ChessColor.BLACK, -7, 0);
        initBlackPawnOnBoard(1, 7, ChessColor.BLACK, -8, 0);
        initWhitePawnOnBoard(6, 0, ChessColor.WHITE, 1, 0);
        initWhitePawnOnBoard(6, 1, ChessColor.WHITE, 2, 0);
        initWhitePawnOnBoard(6, 2, ChessColor.WHITE, 3, 0);
        initWhitePawnOnBoard(6, 3, ChessColor.WHITE, 4, 0);
        initWhitePawnOnBoard(6, 4, ChessColor.WHITE, 5, 0);
        initWhitePawnOnBoard(6, 5, ChessColor.WHITE, 6, 0);
        initWhitePawnOnBoard(6, 6, ChessColor.WHITE, 7, 0);
        initWhitePawnOnBoard(6, 7, ChessColor.WHITE, 8, 0);
        repaint();
    }


    public boolean WhiteKingDanger(ChessComponent[][] chessComponents, KingChessComponent kingChessComponent) {
        int x = kingChessComponent.getChessboardPoint().getX();
        int y = kingChessComponent.getChessboardPoint().getY();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint(x,y))){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean BlackKingDanger(ChessComponent[][] chessComponents, KingChessComponent kingChessComponent) {
        int x = kingChessComponent.getChessboardPoint().getX();
        int y = kingChessComponent.getChessboardPoint().getY();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessComponents[i][j].canMoveTo(chessComponents,new ChessboardPoint(x,y))){
                    return true;
                }
            }
        }
        return false;
    }

    //执行吃过路兵的操作
    public void EatBlackRoadPawn(ChessboardPoint destination){
        chessmatrix[destination.getX()+1][destination.getY()] = 0;
        remove(chessComponents[destination.getX()+1][destination.getY()]);
    }

    public void EatWhiteRoadPawn(ChessboardPoint destination){
        chessmatrix[destination.getX()-1][destination.getY()] = 0;
        remove(chessComponents[destination.getX()-1][destination.getY()]);
    }



    public boolean KingRookChange(Chessboard chessboard){
        return true;
    }





}