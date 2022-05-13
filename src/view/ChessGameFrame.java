package view;


import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard chessboard;



    public ChessGameFrame(int width, int height) {

        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);//决定窗体的大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addHelloButton();
        addLoadButton();
        addRemake();
        addReturnButton();

        JLabel statusRound = new JLabel("Round: 1  White" );
        statusRound.setLocation(760, 760 / 10 +30);
        statusRound.setSize(200, 60);
        statusRound.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusRound.setVisible(true);
        add(statusRound);
        chessboard = new Chessboard(608,608,statusRound);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        addLabel();

    }
    /**
     * 在游戏面板中添加棋盘
     */


    /**
     * 在游戏面板中添加标签
     */

    public void addLabel() {
        JLabel statusLabel = new JLabel(new ImageIcon("./images/background.jpeg"));
        statusLabel.setLocation(0, 0);
        statusLabel.setSize(1000, 760);
        statusLabel.setOpaque(true);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    public void addRemake() {
        JButton button = new JButton("新游戏");
        button.addActionListener(e -> chessboard.newGame());
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);
    }

    public void addReturnButton(){
        JButton button = new JButton("悔棋");
        button.setSize(200,60);
        button.setLocation(HEIGTH, HEIGTH / 10 + 400);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(222);
                chessboard.remake(chessboard.steps);
                System.out.println(333);
            }
        });
        add(button);
    }

    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter(path));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            StringBuilder stringBuilder = new StringBuilder();
            //遍历输出x,y,X,Y,chessBoardMatrix
            for (int i = 0; i < Chessboard.steps.size(); i++) {
                int x = Chessboard.steps.get(i).initialX;//后来的x
                int y = Chessboard.steps.get(i).initialY;//....
                int X = Chessboard.steps.get(i).laterX;//....
                int Y = Chessboard.steps.get(i).laterY;
                int round2 = Chessboard.steps.get(i).Round2;//....
                int[][] chessBoardMatrix = Chessboard.steps.get(i).laterChessboard;//....
                try {
                    writer.write(x + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(' ');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(y + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(' ');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(X + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(' ');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(Y + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(' ');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.write(round2 + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    writer.newLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        try {
                            writer.write((chessBoardMatrix[j][k]) + '0');//一行一行存
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        //System.out.println("saved: "+(char) (chessBoardMatrix[j][k]+'0'));
                    }
                    try {
                        writer.newLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            try {
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }



        });
    }
}
