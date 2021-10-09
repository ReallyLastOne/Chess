package LocationTest;

import core.Board;
import core.Cell;
import core.Game;
import core.pieces.Pawn;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

import static core.PositionConstants.*;

public class PawnLocationTest {
    Game game;
    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
    }

    /* Testing pawn locations for initial board. */
    @Test
    public void pawnsLocationTest() {
        Board board = game.getBoard();
        Cell[][] cells = board.getCells();
        for (int i = 0; i < 8; i++) {
            /* white pawns, checks if they are on 2 rank. */
            Assert.assertTrue(cells[i][WHITE_PAWN_ROW].getPiece() instanceof Pawn);
            /* checks if pawns are white */
            Assert.assertTrue(cells[i][WHITE_PAWN_ROW].getPiece().isWhite());
        }
        for (int i = 0; i < 8; i++) {
            /* black pawns, checks if they are on 2 rank. */
            Assert.assertTrue(cells[i][BLACK_PAWN_ROW].getPiece() instanceof Pawn);
            /* checks if pawns are black */
            Assert.assertFalse(cells[i][BLACK_PAWN_ROW].getPiece().isWhite());
        }
    }

    @After
    public void after() {
        game = null;
    }
}
