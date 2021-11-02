package LocationTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.pieces.King;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KingLocationTest {
    Game game;
    @Before
    public void initialize() {
        game = new Game();
    }

    @Test
    public void kingsLocationTest() {
        Board board = game.getBoard();
        /* e1 white king */
        Assert.assertTrue(board.getCellByName("e1").getPiece() instanceof King);
        Assert.assertTrue(board.getCellByName("e1").getPiece().isWhite());

        /* e8 black king */
        Assert.assertTrue(board.getCellByName("e8").getPiece() instanceof King);
        Assert.assertFalse(board.getCellByName("e8").getPiece().isWhite());
    }

    @After
    public void after() {
        game = null;
    }
}
