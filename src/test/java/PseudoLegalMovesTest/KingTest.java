package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static utilities.Utilities.getCellByName;

public class KingTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void kingTest() {
        /* kings in beginning don't have legal moves */
        Assert.assertEquals(getCellByName(board, "e1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "e1")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "e8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "e8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
