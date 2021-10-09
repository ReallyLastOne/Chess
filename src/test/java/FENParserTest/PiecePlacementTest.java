package FENParserTest;

import core.Board;
import core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;
import utilities.FENParser;

public class PiecePlacementTest {
    Game game;
    Board board;
    FENParser parser;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
        parser = new FENParser();
    }

    @Test
    public void basicPiecePlacementTest() {
        Assert.assertEquals(parser.getPiecePlacement(board), "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
    }

    @After
    public void after() {
        game = null;
    }
}
