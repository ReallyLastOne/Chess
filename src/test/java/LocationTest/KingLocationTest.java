package LocationTest;

import core.Board;
import core.Game;
import core.pieces.King;
import core.pieces.Queen;
import core.pieces.Rook;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

public class KingLocationTest {
    Game game;
    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
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
