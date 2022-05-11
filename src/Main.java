import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame("2022 CS102A Project Demo");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000,760);
        f.setLocationRelativeTo(null);
        Container c = f.getContentPane();
        c.setBackground(Color.WHITE);
        f.setLayout(null);
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
}
