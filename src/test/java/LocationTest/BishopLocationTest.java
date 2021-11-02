package LocationTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.pieces.Bishop;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BishopLocationTest {
    Game game;
    @Before
    public void initialize() {
        game = new Game();
    }

    @Test
    public void bishopsLocationTest() {
        Board board = game.getBoard();
        /* c1 white bishop */
        Assert.assertTrue(board.getCellByName("c1").getPiece() instanceof Bishop);
        Assert.assertTrue(board.getCellByName("c1").getPiece().isWhite());
        /* f1 white bishop */
        Assert.assertTrue(board.getCellByName("f1").getPiece() instanceof Bishop);
        Assert.assertTrue(board.getCellByName("f1").getPiece().isWhite());

        /* c8 black bishop */
        Assert.assertTrue(board.getCellByName("c8").getPiece() instanceof Bishop);
        Assert.assertFalse(board.getCellByName("c8").getPiece().isWhite());
        /* f8 black bishop */
        Assert.assertTrue(board.getCellByName("f8").getPiece() instanceof Bishop);
        Assert.assertFalse(board.getCellByName("f8").getPiece().isWhite());
    }

    @After
    public void after() {
        game = null;
    }
}
