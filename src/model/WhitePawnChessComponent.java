package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

public class WhitePawnChessComponent extends ChessComponent{
    private static Image Pawn_WHITE;
    private Image PawnImage;
    public void loadResource() throws IOException {

        if (Pawn_WHITE == null) {
            Pawn_WHITE = ImageIO.read(new File("./images/Pawn-white.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                PawnImage = Pawn_WHITE;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public WhitePawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size, int special) {
        super(chessboardPoint, location, color, listener, size,special);
        initiatePawnImage(color);
    }

    JButton b1 = new JButton("后");
    JButton b2 = new JButton("车");
    JButton b3 = new JButton("象");
    JButton b4 = new JButton("马");
    Object[] os = {b1,b2,b3,b4};
    Icon icon = new ImageIcon("images/Pawn-white.png");
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        b1.addMouseListener(new twt());
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                remove(chessComponents[source.getX()][source.getY()]);
            }
        });
        if (source.getX()!=6) {
            if (destination.getX()==source.getX()-1&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }else if (destination.getX()==0){
                    JOptionPane.showOptionDialog(null,"需要将兵升级成哪种棋子","兵的升变",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION,icon,os,null);
                }
            }else if (destination.getX()==source.getX()-1&&Math.abs(destination.getY()-source.getY())==1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                    return false;
                }else if (destination.getX()==0){
                    JOptionPane.showOptionDialog(null,"需要将兵升级成哪种棋子","兵的升变",JOptionPane.DEFAULT_OPTION,JOptionPane.DEFAULT_OPTION,icon,os,null);
                }
            }else return false;
            return true;
        }else if (source.getX()==6){
            if (destination.getX()==source.getX()-1&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }else if (destination.getX()==source.getX()-1&&Math.abs(destination.getY()-source.getY())==1){
                if (chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent){
                    return false;
                }
            }else if (destination.getX()==source.getX()-2&&destination.getY()==source.getY()){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)){
                    return false;
                }
            }else return false;
        }
        return true;
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

    class twt implements MouseListener
    {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            if(e.getSource().equals(b1))
            {
                Chessboard chessboard = new Chessboard(608,608);//当前的chessboard
                chessboard.putChessOnBoard(chessboard.putChessOnBoard(););
            }

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

