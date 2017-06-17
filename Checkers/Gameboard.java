/**
 * Created by Ian Dennis on 2/22/17.
 */
public class Gameboard {

    private Gamepiece[][] board;
    private Gamepiece[] redPieces;
    private Gamepiece[] blackPieces;
    private int numRed;
    private int numBlack;
    private int dim;

    public Gameboard() {
        this(8);
    }

    public Gameboard(int dimensions) {
        dim = dimensions;
        redPieces = new Gamepiece[12];
        blackPieces = new Gamepiece[12];
        board = new Gamepiece[dim][dim];
        numRed = numBlack = 0;
        initializeBoard();
    } // end constructor

    /*
     * Populates the Gameboard with red and black pieces according to rules
     */
    public void initializeBoard() {
        //Populate board with Red and Black pieces
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                //Black pieces at bottom of board
                if (((i == 0 || i == 2) && (j % 2 == 1)) || (i == 1 && (j % 2 == 0))) {

                Gamepiece newBlackPiece = new Gamepiece(false, i, j, numBlack);
                board[i][j] = newBlackPiece;
                blackPieces[numBlack] = newBlackPiece;
                numBlack++;
                }
                //Red pieces at top of board
                else if (((i == 5 || i == 7) && (j % 2 == 0)) || (i == 6 && (j % 2 == 1))) {
                    Gamepiece newRedPiece = new Gamepiece(true, i, j, numRed);
                    board[i][j] = newRedPiece;
                    redPieces[numRed] = newRedPiece;
                    numRed++;
                }
                else
                    board[i][j] = null;
            }
        }
    } //end initializeBoard

    // Consider returning a deep copy
    public Gamepiece getPiece(int pieceNum, boolean color)
    {
        if(color)
            return redPieces[pieceNum];
        else
            return blackPieces[pieceNum];
    }

    public Gamepiece getPiece(int row, int col)
    {
        return board[row][col];
    }

    public int getDim()
    {
        return dim;
    }

    public int getNumRed() {return numRed; }

    public int getNumBlack() {return numBlack;}

    /*
        Prints out a textual representation of the Gameboard
     */
    //TODO: Move to TextUI
    public void printBoard() {
        System.out.println("\n    A     B     C     D     E     F     G     H");
        System.out.println("  ------------------------------------------------");
        for (int i = 0; i < dim; i++) {
            System.out.print(i);
            for (int j = 0; j < dim; j++) {
                if (board[i][j] == null)
                    System.out.print("|     ");

                else if (board[i][j].isRed())
                    if(board[i][j].isKing())
                        System.out.print("|(R" + String.format("%02d", board[i][j].getPieceNum()) + ")");
                    else
                        System.out.print("|(r" + String.format("%02d", board[i][j].getPieceNum()) + ")");
                else if(board[i][j].isKing())
                    System.out.print("|(B" + String.format("%02d", board[i][j].getPieceNum()) + ")");
                else
                    System.out.print("|(b" + String.format("%02d", board[i][j].getPieceNum()) + ")");
            }
            System.out.println("|");
            System.out.println("  ------------------------------------------------");
        }
    } //end printBoard

    /*
        Checks to see if a given space is occupied by a piece.
        Returns:
            1  - Occupied by red piece
            0  - Not Occupied
            -1 - Occupied by black piece
     */
    public int isOccupied(int row, int col)
    {
        int occupied;

        if (board[row][col] == null)
            occupied = 0;
        else if(board[row][col].isRed())
            occupied = 1; //occupied by a red piece
        else
            occupied = -1; //occupied by a black piece

        return occupied;
    } // end isOccupied

    /*
        Checks to see if a given piece can jump another piece
     */
    public boolean jumpablePiece(int pieceRow, int pieceCol, int newRow, int newCol)
    {
        return board[newRow][newCol].isRed() != board[pieceRow][pieceCol].isRed();
    } // end jumpablePiece

    /*
        Moves a piece to a new location.
        Makes the piece a king if conditions met.
     */
    public void movePiece(int oldRow, int oldCol, int newRow, int newCol)
    {
        board[newRow][newCol] = board[oldRow][oldCol];
        board[newRow][newCol].setXpos(newRow);
        board[newRow][newCol].setYpos(newCol);
        board[oldRow][oldCol] = null;

        if((board[newRow][newCol].isRed() && newRow == dim - 1) || (!board[newRow][newCol].isRed() && newRow == 0))
            board[newRow][newCol].setKing();
    } //end movePiece

    /*
        Removes a piece from the Gameboard
     */
    public void removePiece(int pieceNum, boolean color){
        int row, col;
        Gamepiece[] pieces;

        if(color) {
            pieces = redPieces;
            numRed--;
        }
        else {
            pieces = blackPieces;
            numBlack--;
        }

        row = pieces[pieceNum].getXpos();
        col = pieces[pieceNum].getYpos();
        pieces[pieceNum] = null;
        board[row][col] = null;

    } // end removePiece

    public static void main(String[] args) {
        Gameboard checkersBoard = new Gameboard();
        Gamepiece piece;;
        checkersBoard.printBoard();
        System.out.println("Reds " + checkersBoard.numRed + " Blacks " + checkersBoard.numBlack);
        piece = checkersBoard.getPiece(0, false);
        System.out.println(piece.getXpos());
        System.out.println(piece.getYpos());
        checkersBoard.removePiece(0, false);
        checkersBoard.printBoard();
        checkersBoard.movePiece(2, 1, 3, 2);
        checkersBoard.printBoard();

        for( int i = 0; i < 12; i++)
        {
            System.out.println(checkersBoard.redPieces[i]);
        }
        for( int i = 0; i < 12; i++)
        {
            System.out.println(checkersBoard.blackPieces[i]);
        }
    }
} //end GameBoard