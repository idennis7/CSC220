/**
 * Created by dennisi1 on 2/22/17.
 */
public interface CheckersInterface {

    boolean getTurn();

    Gamepiece getApiece(int pieceNum);

    int selectPiece(int pieceNum);

    int moveChosenPiece(int move, int pieceNum);

    int checkGameOver();

    void switchTurn();
}
