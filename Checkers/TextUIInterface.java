/**
 * Created by dennisi1 on 5/1/17.
 */
public interface TextUIInterface {
    void printBoard();
    void promptPiece();
    boolean checkPiece(int pieceNum);
    void promptMove(int pieceNum);
    boolean checkMove(int move);
    void welcome();
    int declareWinner();
}
