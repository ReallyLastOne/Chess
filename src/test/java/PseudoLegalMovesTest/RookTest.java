package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

public class RookTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void rookTest() {
        /* rooks in beginning don't have legal moves */
        Assert.assertEquals(board.getCellByName("a1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("a1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("h1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("h1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("a8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("a8")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("h8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("h8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
