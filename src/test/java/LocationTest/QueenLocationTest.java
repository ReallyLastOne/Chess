package LocationTest;

import core.Board;
import core.Game;
import core.pieces.Queen;
import core.pieces.Rook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

public class QueenLocationTest {
    Game game;
    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
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
