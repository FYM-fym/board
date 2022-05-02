package view;

import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard round = new Chessboard(608,608);



    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);//决定窗体的大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addRemake();
        addReturnButton();


    }
    /**
     * 在游戏面板中添加棋盘
     */
    public void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */

    public void addLabel() {
        JLabel statusLabel = new JLabel("Sample label"+round.Round2);
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */


    public void addRemake() {
        JButton button = new JButton("Remake Click Here");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
                gameController = new GameController(chessboard);
                chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
                addChessboard();


            }
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 15));
        add(button);

    }

    public void addRound() {
        JLabel statusRound = new JLabel("Round:");
        statusRound.setLocation(HEIGTH, HEIGTH / 10 + 360);
        statusRound.setSize(200, 60);
        statusRound.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusRound);

    }
    public void addReturnButton(){
        JButton button = new JButton("return");
        button.setSize(200,60);
        button.setLocation(HEIGTH, HEIGTH / 10 + 400);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
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
            gameController.loadGameFromFile(path);
        });
    }

}
