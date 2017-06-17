/**
 * Created by Ian Dennis on 2/22/17.
 */
import java.util.Scanner;
public class CheckersGame implements CheckersInterface{

    private Gameboard checkersBoard;
    private boolean playerOneTurn;
    private Scanner moveReader;

    public CheckersGame() {
        checkersBoard = new Gameboard();
        playerOneTurn = true;
        // Move to Input
        //moveReader = new Scanner(System.in);
    } // end constructor

    public boolean getTurn(){return playerOneTurn;}
    public Gamepiece getApiece(int pieceNum){return checkersBoard.getPiece(pieceNum, playerOneTurn);}

    public int selectPiece(int pieceNum)
    {
         int pieceWithMove;
         Gamepiece chosenPiece;
         int[] possibleMoves;

        pieceWithMove = checkChosenPiece(pieceNum, playerOneTurn);

         if (pieceWithMove == 0)
         {
             calculateMoves();
             chosenPiece = checkersBoard.getPiece(pieceNum, playerOneTurn);
             possibleMoves = chosenPiece.getPossibleMoves();

             if (possibleMoves[0] + possibleMoves[1] + possibleMoves[2] + possibleMoves[3] == 0)
                 pieceWithMove = 4;
         }

        return pieceWithMove;
    }

    /*
        Checks to see if user selected piece is a valid choice
     */
    private int checkChosenPiece(int pieceNum, boolean color) {
        int result = 0;

        if (pieceNum > 11 || pieceNum < 0)
            result = 1;
        else if (checkersBoard.getPiece(pieceNum, color) == null)
            result = 2;
        else if (!playerOneTurn == color)
            result = 3;

        return result;
    } // end checkChosenPiece

    private void calculateMoves()
    {
        Gamepiece piece;

        for(int i = 0; i < 12; i++)
        {
            piece = checkersBoard.getPiece(i, playerOneTurn);
            piece.setPossibleMoves(checkForMoves(piece));
        }
    }

    /*
        Checks for all possible moves for a piece
     */
    private int[] checkForMoves(Gamepiece piece) {
        // Left, Right, King Left, King Right, Move Priority Threshold
        int[] moves = {0, 0, 0, 0, 1};
        int col = piece.getYpos();
        int row = piece.getXpos();
        boolean redPiece = piece.isRed();

        if (redPiece) {
            if (piece.isKing()) {
                moves[2] = checkifValid(row, col, 1, -1);
                moves[3] = checkifValid(row, col, 1, 1);
            }

            moves[0] = checkifValid(row, col, -1, -1);
            moves[1] = checkifValid(row, col, -1, 1);
        } else {
            if (piece.isKing()) {
                moves[2] = checkifValid(row, col, -1, -1);
                moves[3] = checkifValid(row, col, -1, 1);
            }
            moves[0] = checkifValid(row, col, 1, -1);
            moves[1] = checkifValid(row, col, 1, 1);
        }

        if (moves[0] > 1 || moves[1] > 1 || moves[2] > 1 || moves[3] > 1)
            moves[4] = 2;

        return moves;
    } // end checkForMoves

    /*
        Checks if a move is possible, moves to an empty space, or results in jump
     */
    private int checkifValid(int curRow, int curCol, int rowPattern, int colPattern) {
        int numMoves = 0;
        int newCol = curCol + colPattern;
        int newRow = curRow + rowPattern;

        //Check if within board
        if ((newCol > 0 && newCol < checkersBoard.getDim() - 1) && (newRow > 0 && newRow < checkersBoard.getDim() - 1)) {
            // Check if next space is empty
            if (checkersBoard.isOccupied(newRow, newCol) == 0)
                numMoves = 1;
                // If not empty, check if piece is jumpable and the next space after it is empty
            else if (checkersBoard.isOccupied(newRow + rowPattern, newCol + colPattern) == 0
                    && checkersBoard.jumpablePiece(curRow, curCol, newRow, newCol)) {
                numMoves = 2;
            }
        }
        return numMoves;
    } // end checkifValid

    /*
        Moves a chosen piece based on user selection
     */
    public int moveChosenPiece(int move, int pieceNum) {
        boolean jumpMove;
        int moreMoves = 0;
        int newRow, newCol, shiftRow, shiftCol;

        Gamepiece piece = checkersBoard.getPiece(pieceNum, playerOneTurn);
        int[] possibleMoves = piece.getPossibleMoves();
        int curRow = piece.getXpos();
        int curCol = piece.getYpos();

        shiftRow = shiftCol = 0;
        jumpMove = possibleMoves[move] > 1;

        switch (move) {
            case 0:
                if (piece.isRed()) {
                    shiftRow = -1;
                    shiftCol = -1;
                } else {
                    shiftRow = 1;
                    shiftCol = -1;
                }
                break;
            case 1:
                if (piece.isRed()) {
                    shiftRow = -1;
                    shiftCol = 1;
                } else {
                    shiftRow = 1;
                    shiftCol = 1;
                }
                break;
            case 2:
                if (piece.isRed()) {
                    shiftRow = 1;
                    shiftCol = -1;
                } else {
                    shiftRow = -1;
                    shiftCol = -1;
                }
                break;
            case 3:
                if (piece.isRed()) {
                    shiftRow = 1;
                    shiftCol = 1;
                } else {
                    shiftRow = -1;
                    shiftCol = 1;
                }
                break;
        }

        newRow = curRow + shiftRow;
        newCol = curCol + shiftCol;

        if (jumpMove)
        {
            Gamepiece jumpedPiece = checkersBoard.getPiece(newRow, newCol);
            checkersBoard.removePiece(jumpedPiece.getPieceNum(), !playerOneTurn);
            checkersBoard.movePiece(curRow, curCol, newRow + shiftRow, newCol + shiftCol);
            moreMoves = 1;
        }
        else
        {
            checkersBoard.movePiece(curRow, curCol, newRow, newCol);
        }

        return moreMoves;
    } // end moveChosenPiece

    /*
        Checks to see if the game is over
     */
    public void switchTurn(){
        playerOneTurn = !playerOneTurn;
    }

    public int checkGameOver()
    {
        int winner = 0;

        if (checkersBoard.getNumBlack() == 0)
            winner = 1;
        else if (checkersBoard.getNumRed() == 0)
            winner = -1;

        return winner;
    } // end checkGameOver


    public static void main(String[] args) {
        CheckersGame game = new CheckersGame();
        boolean tester = true;

        game.checkersBoard.printBoard();

        System.out.println(game.selectPiece(1));
        System.out.println(game.moveChosenPiece(1, 1));
        game.checkersBoard.printBoard();
        game.switchTurn();
        game.selectPiece(9);
        game.moveChosenPiece(1, 9);
        game.checkersBoard.printBoard();
        game.switchTurn();
        game.selectPiece(3);
        game.moveChosenPiece(0, 3);
        game.checkersBoard.printBoard();
        game.switchTurn();
        game.selectPiece(9);
        game.moveChosenPiece(0, 9);
        game.checkersBoard.printBoard();


    }
}