package GameStatusTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static utilities.Utilities.possibleOutcomes;

public class GameOverTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    // test for 50 games
    @Test
    public void gameOverTest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/finishedGames.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (possibleOutcomes.contains(line)) {
                Assertions.assertEquals(line, game.getGameStatus().toString());
                game = new Game();
                board = game.getBoard();
            } else {
                // System.out.println("Move: " + line);
                game.makeMove(line);
                // System.out.println(board);
            }
        }
    }
}
