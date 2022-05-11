package view;


public class Step {

    public int initialX;
    public int initialY;
    public int laterX;
    public int laterY;
    public int[][] laterChessboard ;
    public int Round2;

    public Step(int initialX, int initialY, int laterX, int laterY, int[][] laterChessboard,int Round2) {
        this.initialX = initialX;
        this.initialY = initialY;
        this.laterX = laterX;
        this.laterY = laterY;
        this.laterChessboard = laterChessboard;
        this.Round2=Round2;
    }
}