package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import chess.spring.AppConfig;

import java.util.Collections;

public class QueenTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    @Test
    public void queenTest() {
        /* queens in beginning don't have legal moves */
        Assert.assertEquals(board.getCellByName("d1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("d1")), Collections.emptyList());
        Assert.assertEquals(board.getCellByName("d8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("d8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
