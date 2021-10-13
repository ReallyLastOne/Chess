package FENParserTest;

import core.Board;
import core.Game;
import core.move.Move;
import core.pieces.Pawn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;
import utilities.FENParser;

import java.io.*;

public class GeneralFEN {
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

    @Test
    public void fenTest() {
        /* Testing FEN.
         * Format of file is as following:
         * Line 0: move made,
         * Line 1: fen in current position,
         * Line 2: move made...
         * games are separated by blank line */

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/FENs.txt"))) {
            StringBuffer textBuffer = new StringBuffer();
            String line;
            int counter = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()) {
                    counter = 0;
                    System.out.println("New game");
                    System.out.println("\n");
                    game = context.getBean(Game.class);
                    board = game.getBoard();
                } else {
                    if (counter % 2 == 0) {
                        game.makeMove(line);
                        counter++;
                    } else if (counter % 2 == 1) {
                        String result = new FENParser().calculateFEN(board);
                        Assert.assertEquals(line, result);
                        counter++;
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
