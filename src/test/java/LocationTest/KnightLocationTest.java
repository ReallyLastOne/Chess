package LocationTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.pieces.Knight;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KnightLocationTest {
    Game game;
    @Before
    public void initialize() {
        game = new Game();
    }

    @Test
    public void knightsLocationTest() {
        Board board = game.getBoard();
        /* b1 white knight */
        Assert.assertTrue(board.getCellByName("b1").getPiece() instanceof Knight);
        Assert.assertTrue(board.getCellByName("b1").getPiece().isWhite());
        /* g1 white knight */
        Assert.assertTrue(board.getCellByName("g1").getPiece() instanceof Knight);
        Assert.assertTrue(board.getCellByName("g1").getPiece().isWhite());

        /* b8 black knight */
        Assert.assertTrue(board.getCellByName("b8").getPiece() instanceof Knight);
        Assert.assertFalse(board.getCellByName("b8").getPiece().isWhite());
        /* g8 black knight */
        Assert.assertTrue(board.getCellByName("g8").getPiece() instanceof Knight);
        Assert.assertFalse(board.getCellByName("g8").getPiece().isWhite());
    }

    @After
    public void after() {
        game = null;
    }
}
