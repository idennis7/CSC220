/**
 * Created by Ian Dennis on 2/22/17.
 */
public class Gamepiece {

    private boolean isRed;
    private boolean isKing;
    private int xPos;
    private int yPos;
    private int pieceNum;
    private int[] possibleMoves;

    public Gamepiece()
    {
        isRed = false;
        isKing = false;
    }

    public Gamepiece(boolean isRed, int rowPos, int colPos, int pieceNum)
    {
        this.isRed = isRed;
        isKing = false;
        xPos = rowPos;
        yPos = colPos;
        this.pieceNum = pieceNum;
    }

    public int getXpos()
    {return this.xPos;}

    public void setXpos(int newXpos)
    {xPos = newXpos;}

    public void setYpos(int newYpos){
        yPos = newYpos;
    }

    public int getYpos()
    {return this.yPos;}

    public boolean isRed()
    {return this.isRed;}

    public boolean isKing()
    {return this.isKing;}

    void setKing()
    {isKing = true;}

    public int getPieceNum()
    {return this.pieceNum;}

    public int[] getPossibleMoves(){return possibleMoves;}

    public void setPossibleMoves(int[] moves){possibleMoves = moves;}

    public String toString()
    {
        String color;
        if (this.isRed()) {color = "Red";}
        else {color = "Black";}

        return (color + " Number:" + pieceNum + " xPos:" + xPos + " yPos" + yPos);
    }
} //end Gamepiece
