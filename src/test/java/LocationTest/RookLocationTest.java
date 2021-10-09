package LocationTest;

import core.Board;
import core.Game;
import core.pieces.Bishop;
import core.pieces.Rook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

public class RookLocationTest {
    Game game;
    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
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
