package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class BishopTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void bishopTest() {
        /* bishops in beginning don't have legal moves */
        Assert.assertEquals(board.getCellByName("c1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("c1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("f1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("f1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("c8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("c8")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("f8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("f8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
