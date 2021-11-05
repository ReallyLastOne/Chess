package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static utilities.Utilities.getCellByName;

public class BishopTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void bishopTest() {
        /* bishops in beginning don't have legal moves */
        Assertions.assertEquals(getCellByName(board, "c1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "c1")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "f1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "f1")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "c8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "c8")), Collections.emptyList());
        Assertions.assertEquals(getCellByName(board, "f8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "f8")), Collections.emptyList());
    }
}
