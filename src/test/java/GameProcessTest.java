import core.Game;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.Assert;
import spring.AppConfig;
import core.GameUtilities;

public class GameProcessTest {
    Game game;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        String[] moves = new String[]{"d2d4", "c7c5", "d4c5", "d8a5", "b1c3", "h7h5", "e1d2", "a5c5", "d2d3", "c5c4", "d3d4"}; // illegal move, white king is in check after C5 C4
        for (String move : moves) {
            game.makeMove(move);
        }
    }

    @Test
    public void shouldBeWinForWhite() {
        String[] moves = new String[]{"e2e4", "e7e5", "d1f3", "b8a6", "f1c4", "b7b6", "f3f7"};
        for (String move : moves) {
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.WHITE_WIN);
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.BLACK_WIN);
            game.makeMove(move);
        }
        Assert.assertEquals(game.getGameStatus(), GameUtilities.GameStatus.WHITE_WIN);
    }

    @Test
    public void shouldBeWinForBlack() {
        String[] moves = new String[]{"f2f4", "e7e5", "g2g4", "d8h4"};
        for (String move : moves) {
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.WHITE_WIN);
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.BLACK_WIN);
            game.makeMove(move);
        }
        Assert.assertEquals(game.getGameStatus(), GameUtilities.GameStatus.BLACK_WIN);
    }

    @Test
    public void shouldBeDraw() { //fivefold repetition
        String[] moves = {"g1f3", "g8f6", "f3g1", "f6g8", "g1f3", "g8f6", "f3g1", "f6g8", "g1f3", "g8f6", "f3g1",
                "f6g8", "g1f3", "g8f6", "f3g1", "f6g8", "g1f3"};
        for (String move : moves) {
            game.makeMove(move);
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.WHITE_WIN);
            Assert.assertNotEquals(game.getGameStatus(), GameUtilities.GameStatus.BLACK_WIN);
        }
        Assert.assertEquals(game.getGameStatus(), GameUtilities.GameStatus.DRAW);
    }

    @Test
    public void shouldBeInProgress() {
        String[] moves = new String[]{"f2f3", "e7e5", "g2g4"};
        for (String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals(game.getGameStatus(), GameUtilities.GameStatus.IN_PROGRESS);
    }

    @Test
    public void shouldBeInProgress2() {
        String[] moves = ("d2d4 g8f6 c2c4 c7c5 d4d5 e7e6 b1c3 e6d5 c4d5 d7d6 g1f3 g7g6 c1g5 f8g7 f3d2 h7h6 " +
                "g5h4 g6g5 h4g3 f6h5 d2c4 h5g3 h2g3 e8g8 e2e3 d8e7 f1e2 f8d8 e1g1 b8d7 a2a4 d7e5 " +
                "c4e5 e7e5 a4a5 a8b8 a1a2 c8d7 c3b5 d7b5 e2b5 b7b6 a5a6 b8c8 d1d3 c8c7 b2b3 e5c3 " +
                "d3c3 g7c3 a2c2 c3f6 g3g4 c7e7 c2c4 d8c8 g2g3 f6g7 f1d1 c8f8 d1d3 g8h7 g1g2 h7g6 " +
                "d3d1 h6h5 g4h5 g6h5 g3g4 h5g6 c4c2 f8h8 b5d3 g6f6 g2g3 e7e8 d3b5 e8e4 c2c4 e4c4 " +
                "b3c4 f6e7 b5a4 g7e5 g3f3 h8h4 d1g1 f7f5").split(" ");
        for (String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals(game.getGameStatus(), GameUtilities.GameStatus.IN_PROGRESS);
    }

    @After
    public void after() {
        game = null;
    }
}
