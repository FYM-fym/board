package model;

import controller.ClickController;
import view.ChessboardPoint;
import view.Chessboard;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的兵
 */
public class BishopChessComponent extends ChessComponent{

    private static Image Bishop_WHITE;
    private static Image Bishop_BLACK;
    public Chessboard chessboard;
    private Image BishopImage;
    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    @Override
    public void loadResource() throws IOException {
        if (Bishop_WHITE == null){
            Bishop_WHITE = ImageIO.read(new File("./images/Bishop-white.png"));
        }
        if (Bishop_BLACK == null){
            Bishop_BLACK = ImageIO.read(new File("./images/Bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                BishopImage = Bishop_WHITE;
            } else if (color == ChessColor.BLACK) {
                BishopImage = Bishop_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size,int special,int WhetherFirst) {
        super(chessboardPoint, location, color, listener, size, special,WhetherFirst);
        initiateBishopImage(color);
    }
    JButton b1 = new JButton("后");
    JButton b2 = new JButton("车");
    JButton b3 = new JButton("象");
    JButton b4 = new JButton("马");
    Object[] os = {b1,b2,b3,b4};
    Icon icon = new ImageIcon("images/Pawn-white.png");
    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination){
        ChessboardPoint source = getChessboardPoint();
        if (Math.abs(source.getX()-destination.getX())==Math.abs(source.getY()-destination.getY())){
            int row = source.getX();
            int column = source.getY();

            if (source.getY()-destination.getY()==source.getX()-destination.getX()){
                for (int i = Math.min(source.getY(),destination.getY())+1; i < Math.max(source.getY(), destination.getY()) ; i++) {
                    if (!(chessComponents[i+row-column][i] instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            }else if (source.getY()-destination.getY()==destination.getX()-source.getX()){
                for (int i = Math.min(source.getY(),destination.getY())+1; i < Math.max(source.getY(), destination.getY()) ; i++) {
                    if (!(chessComponents[-i+column+row][i] instanceof EmptySlotComponent)){
                        return false;
                    }
                }
            }
        }else {
            return false;
        }
        return true;
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(BishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    /*if (canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination)){
                        System.out.println("123456789QWERTYUIOASDFGHJK");
                    }*/
                }
            }
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }


}
