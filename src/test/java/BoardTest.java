import chess.core.Board;
import chess.core.Game;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import chess.spring.AppConfig;

public class BoardTest {
    public static final String basicBoardString = """
                                                 | r | n | b | q | k | b | n | r | 8
                                                 | p | p | p | p | p | p | p | p | 7
                                                 |   |   |   |   |   |   |   |   | 6
                                                 |   |   |   |   |   |   |   |   | 5
                                                 |   |   |   |   |   |   |   |   | 4
                                                 |   |   |   |   |   |   |   |   | 3
                                                 | P | P | P | P | P | P | P | P | 2
                                                 | R | N | B | Q | K | B | N | R | 1
                                                   A   B   C   D   E   F   G   H  \s""";
    
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    @Test
    public void toStringTest() {
        Assert.assertEquals(basicBoardString, board.toString());
    }

    @After
    public void after() {
        game = null;
    }
}
