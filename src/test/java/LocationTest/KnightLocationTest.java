package LocationTest;

import core.Board;
import core.Game;
import core.pieces.Knight;
import core.pieces.Rook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

public class KnightLocationTest {
    Game game;
    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
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
