package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static utilities.Utilities.getCellByName;

public class KingTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void kingTest() {
        /* kings in beginning don't have legal moves */
        Assertions.assertEquals(getCellByName(board, "e1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "e1")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "e8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "e8")), Collections.emptyList());
    }
}
