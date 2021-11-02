package FENParserTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import chess.utilities.FEN;

public class PiecePlacementTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void basicPiecePlacementTest() {
        Assert.assertEquals(FEN.calculatePiecePlacement(board), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    @After
    public void after() {
        game = null;
    }
}
