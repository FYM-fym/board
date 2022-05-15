package view;


import controller.GameController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private  int WIDTH;
    private  int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    Chessboard chessboard;
    JLabel statusRound, time, statusLabel;
    JButton button1, button2, button3, button4;
    int counter = 1;


    public ChessGameFrame(int width, int height) {

        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);//决定窗体的大小
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        changeSkin();
        addLoadButton();
        addRemake();
        addReturnButton();

        statusRound = new JLabel("Round: 1  White" );
        statusRound.setLocation(760, 106);
        statusRound.setSize(200, 60);
        statusRound.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusRound);

        time = new JLabel("Time: ");
        time.setBounds(760,760 / 10 +70,200,60);
        time.setFont(new Font("Rockwell",Font.BOLD,20));
        add(time);

        chessboard = new Chessboard(608,608,statusRound,time);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        add(chessboard);
        addLabel();
    }

    public void addLabel() {
        statusLabel = new JLabel(new ImageIcon("./images/background1.jpeg"));
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
        button1 = new JButton("新游戏");
        button1.addActionListener(e -> chessboard.newGame());
        button1.setLocation(760, 560);
        button1.setSize(200, 61);
        button1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button1);
    }

    public void addReturnButton(){
        button2 = new JButton("悔棋");
        button2.setSize(200,60);
        button2.setLocation(760, 476);
        button2.setFont(new Font("Rockwell", Font.BOLD, 20));

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessboard.remake(Chessboard.steps);

            }
        });
        add(button2);
    }

    /*public void painting(Graphics g){

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 == 0 && j % 2 == 0){
                    chessboard.draw(g).setColor(Color.cyan);
                    chessboard.draw(g).fillRoundRect(200,200,50,50,40,40);
                }else {
                    chessboard.draw(g).setColor(Color.BLACK);
                    chessboard.draw(g).fillRoundRect(100,100,50,50,40,40);
                }
            }
        }
    }*/
    public void reader(){

    }

    private void changeSkin() {
        button3 = new JButton("切换皮肤");
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (counter%4==1){
                    statusLabel.setIcon(new ImageIcon("./images/background2.jpeg"));
                    counter++;
                }else if (counter%4==2){
                    statusLabel.setIcon(new ImageIcon("./images/background3.jpeg"));
                    counter++;
                }else if (counter%4==3){
                    statusLabel.setIcon(new ImageIcon("./images/background4.jpeg"));
                    counter++;
                }else if (counter%4==0){
                    statusLabel.setIcon(new ImageIcon("./images/background1.jpeg"));
                    counter++;
                }

                repaint();
            }
        });
        button3.setLocation(760, 236);
        button3.setSize(200, 60);
        button3.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button3);
    }

    private void addLoadButton() {
        button4 = new JButton("Load");
        button4.setLocation(760, 316);
        button4.setSize(200, 60);
        button4.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button4);
        button4.addActionListener(e -> {
            if (chessboard.count==1){
                chessboard.timer1.stop();
            }
            if (chessboard.count==2){
                chessboard.timer2.stop();
            }
            if (chessboard.count==3){
                chessboard.timer3.stop();
            }
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
                    writer.write(y + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    writer.write(X + '0');
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                try {
                    writer.write(Y + '0');
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
