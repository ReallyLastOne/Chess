package LocationTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.pieces.Queen;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class QueenLocationTest {
    Game game;
    @Before
    public void initialize() {
        game = new Game();
    }

    @Test
    public void queensLocationTest() {
        Board board = game.getBoard();
        /* d1 white queen */
        Assert.assertTrue(board.getCellByName("d1").getPiece() instanceof Queen);
        Assert.assertTrue(board.getCellByName("d1").getPiece().isWhite());

        /* d8 black queen */
        Assert.assertTrue(board.getCellByName("d8").getPiece() instanceof Queen);
        Assert.assertFalse(board.getCellByName("d8").getPiece().isWhite());
    }

    @After
    public void after() {
        game = null;
    }
}
