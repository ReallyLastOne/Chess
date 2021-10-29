package MovesTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import chess.spring.AppConfig;
import chess.utilities.FEN;

public class CastlingTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    @Test
    public void shouldWhiteCastleShort() {
        String[] moves = {"g1f3", "g8f6", "e2e4", "e7e5", "d2d4", "d7d5", "b1c3", "b8c6", "c1e3", "c8e6", "f1d3", "f8d6",
        "d1d2", "d8d7"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/R3K2R", FEN.calculatePiecePlacement(board));
        game.makeMove("e1g1");
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/R4RK1", FEN.calculatePiecePlacement(board));
    }

    @Test
    public void shouldWhiteCastleLong() {
        String[] moves = {"g1f3", "g8f6", "e2e4", "e7e5", "d2d4", "d7d5", "b1c3", "b8c6", "c1e3", "c8e6", "f1d3", "f8d6",
                "d1d2", "d8d7"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/R3K2R", FEN.calculatePiecePlacement(board));
        game.makeMove("e1c1");
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/2KR3R", FEN.calculatePiecePlacement(board));
    }

    @Test
    public void shouldBlackCastleShort() {
        String[] moves = {"g1f3", "g8f6", "e2e4", "e7e5", "d2d4", "d7d5", "b1c3", "b8c6", "c1e3", "c8e6", "f1d3", "f8d6",
                "d1d2", "d8d7", "e1c1"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/2KR3R", FEN.calculatePiecePlacement(board));
        game.makeMove("e8g8");
        Assert.assertEquals("r4rk1/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/2KR3R", FEN.calculatePiecePlacement(board));
    }

    @Test
    public void shouldBlackCastleLong() {
        String[] moves = {"g1f3", "g8f6", "e2e4", "e7e5", "d2d4", "d7d5", "b1c3", "b8c6", "c1e3", "c8e6", "f1d3", "f8d6",
                "d1d2", "d8d7", "e1c1"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("r3k2r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/2KR3R", FEN.calculatePiecePlacement(board));
        game.makeMove("e8c8");
        Assert.assertEquals("2kr3r/pppq1ppp/2nbbn2/3pp3/3PP3/2NBBN2/PPPQ1PPP/2KR3R", FEN.calculatePiecePlacement(board));
    }

}
