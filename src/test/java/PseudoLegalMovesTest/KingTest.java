package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class KingTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void kingTest() {
        /* kings in beginning don't have legal moves */
        Assert.assertEquals(board.getCellByName("e1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("e1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("e8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("e8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
