package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import static utilities.Utilities.getCellByName;
public class BishopTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void bishopTest() {
        /* bishops in beginning don't have legal moves */
        Assert.assertEquals(getCellByName(board, "c1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "c1")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "f1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "f1")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "c8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "c8")), Collections.emptyList());
        Assert.assertEquals(getCellByName(board, "f8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "f8")), Collections.emptyList());
    }

    @After
    public void after() {
        game = null;
    }
}
