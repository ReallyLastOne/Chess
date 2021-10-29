package GameStatusTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import chess.spring.AppConfig;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GameOverTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = null;
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    // test for 50 games
    @Test
    public void gameOverTest() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/finishedGames.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals("1/2-1/2") || line.equals("1-0") || line.equals("0-1")) {
                    Assert.assertEquals(line, game.getGameStatus().toString());
                    game = context.getBean(Game.class);
                    board = game.getBoard();
                } else {
                   // System.out.println("Move: " + line);
                    game.makeMove(line);
                   // System.out.println(board);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
