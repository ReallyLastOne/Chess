package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static utilities.Utilities.getCellByName;

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
        Assert.assertEquals(getCellByName(board, "a1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "a1")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "h1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "h1")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "a8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "a8")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "h8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "h8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
