package FENParserTest;

import chess.core.Board;
import chess.core.Game;
import chess.utilities.FEN;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GeneralFENTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void fenTest() throws IOException {
        /* Testing FEN.
         * Format of file is as following:
         * Line 0: move made,
         * Line 1: fen in current position,
         * Line 2: move made...
         * games are separated by blank line */

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/FENs.txt"));
        String line;
        int counter = 0;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isBlank()) {
                counter = 0;
                System.out.println("New game");
                System.out.println("\n");
                game = new Game();
                board = game.getBoard();
            } else {
                if (counter % 2 == 0) {
                    game.makeMove(line);
                    counter += 1;
                } else if (counter % 2 == 1) {
                    // line is correct fen now
                    String realFEN = FEN.from(board);
                    Assertions.assertEquals(line, realFEN);
                    counter += 1;
                }
            }
        }
    }
}
