package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static utilities.Utilities.getCellByName;

public class RookTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void rookTest() {
        /* rooks in beginning don't have legal moves */
        Assertions.assertEquals(getCellByName(board, "a1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "a1")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "h1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "h1")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "a8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "a8")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "h8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "h8")), Collections.emptyList());
    }
}
