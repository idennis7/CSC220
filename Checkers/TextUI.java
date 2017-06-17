/**
 * Created by dennisi1 on 5/1/17.
 */
public class TextUI implements TextUIInterface {

    private CheckersInterface Checkers;
    private boolean[] moveChoices;

    public TextUI()
    {
        Checkers = new CheckersGame();
        moveChoices = new boolean[4];
    }//end constructor

    public void printBoard() {
        System.out.println("\n    A     B     C     D     E     F     G     H");
        System.out.println("  ------------------------------------------------");
        /*for (int i = 0; i < 8; i++) {
            System.out.print(i);
            for (int j = 0; j < 8; j++) {
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
            }*/
            System.out.println("|");
            System.out.println("  ------------------------------------------------");
       // }
    }//end printBoard

    public void promptPiece(){
        //while (!validPiece) {
            //checkersBoard.printBoard();
        if (Checkers.getTurn())
            System.out.println("Player one, please select a (R) piece!");
        else
            System.out.println("Player two, please select a (B) piece!");

        System.out.println("Please enter a piece number: ");
        //pieceNum = moveReader.nextInt();
        //TODO: Catch input mismatches
    }//end promptPiece

    public boolean checkPiece(int pieceNum)
    {
        int moveFlags = Checkers.selectPiece(pieceNum);
        boolean validPiece = false;

        switch (moveFlags)
        {
            case 0:
                validPiece = true;
                break;
            case 1:
                System.out.println("Sorry, the number you entered is out of range. (0 - 11)");
                break;
            case 2:
                System.out.println("Sorry, you picked an empty space.");
                break;
            case 3:
                System.out.println("Sorry, you picked the wrong colored piece.");
                break;
            case 4:
                System.out.println("Sorry this piece has no possible moves. Please select another one.");
                break;
        }

        return validPiece;
    }//end checkPiece

    public boolean checkMove(int move)
    {
        boolean validMove;

        if (moveChoices[move])
        {
            validMove = true;
        }
        else
        {
            validMove = false;
            System.out.println("Invalid move selected, please select another one.");
        }

        return validMove;
    }

    public int moveAndCheckState(int move, int pieceNum)
    {
        //stateFlag - 0 - Don't move again
        //            1 - move again
        //           -1 - Game Over
        int stateFlag = Checkers.moveChosenPiece(move, pieceNum);
        int gameOver = declareWinner();
        boolean player = Checkers.getTurn();

        if (gameOver != 0)
        {
            stateFlag = -1;
        }
        else if (stateFlag == 1)
        {
            if(player)
                System.out.println("Player 1 goes again.");
            else
                System.out.println("player 2 goes again.");
        }
        else
            Checkers.switchTurn();

        return stateFlag;
    }

    public void promptMove(int pieceNum){
        int[] possibleMoves = Checkers.getApiece(pieceNum).getPossibleMoves();
        int moveThreshold = possibleMoves[4];

        System.out.println("Which way do you want to move?\nPlease enter the number choice to select your move.");
        for (int i = 0; i < 4; i++) {
            if (possibleMoves[i] == moveThreshold)
            {
                moveChoices[i] = true;
                System.out.print("\t[" + i + "] ");

                switch (i) {
                    case 0:
                        System.out.println("Move Left");
                        break;
                    case 1:
                        System.out.println("Move Right");
                        break;
                    case 2:
                        System.out.println("Move Back Left");
                        break;
                    case 3:
                        System.out.println("Move Back Right");
                        break;
                }
            }
            else
                moveChoices[i] = false;
        }
    } //end promptMove

    public void welcome(){
        System.out.print("\t\tWelcome to Checkers!!!\t\t\nPlayer One is Red!\nPlayer Two is Black!\n\n");
    }

    public int declareWinner(){
        int winner = Checkers.checkGameOver();

        if (winner > 0)
            System.out.println("PLAYER 1 WINS!!!!");
        else if (winner < 0)
            System.out.println("PLAYER 2 WINS!!!!");

        return winner;
    }//end declareWinner

    public static void main (String[] args){
        TextUI test = new TextUI();
        test.welcome();
        test.promptPiece();
        test.checkPiece(1);
        test.promptMove(1);
        test.checkMove(1);
        test.moveAndCheckState(1, 1);

    }
}

