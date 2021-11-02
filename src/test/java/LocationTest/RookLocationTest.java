package LocationTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.pieces.Rook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RookLocationTest {
    Game game;
    @Before
    public void initialize() {
        game = new Game();
    }

    @Test
    public void rooksLocationTest() {
        Board board = game.getBoard();
        /* a1 white rook */
        Assert.assertTrue(board.getCellByName("a1").getPiece() instanceof Rook);
        Assert.assertTrue(board.getCellByName("a1").getPiece().isWhite());
        /* h1 white rook */
        Assert.assertTrue(board.getCellByName("h1").getPiece() instanceof Rook);
        Assert.assertTrue(board.getCellByName("h1").getPiece().isWhite());

        /* a8 black rook */
        Assert.assertTrue(board.getCellByName("a8").getPiece() instanceof Rook);
        Assert.assertFalse(board.getCellByName("a8").getPiece().isWhite());
        /* h8 black rook */
        Assert.assertTrue(board.getCellByName("h8").getPiece() instanceof Rook);
        Assert.assertFalse(board.getCellByName("h8").getPiece().isWhite());
    }

    @After
    public void after() {
        game = null;
    }
}
