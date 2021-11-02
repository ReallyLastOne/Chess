package FENParserTest;

import chess.core.Board;
import chess.core.Game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import chess.utilities.FEN;

import java.io.*;

public class FENCreatorTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void fenTest() {
        /* Testing if calculated Board from FEN is same as real one.
         * Format of file is as following:
         * Line 0: move made,
         * Line 1: fen in current position,
         * Line 2: move made...
         * games are separated by blank line */

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/FENs.txt"))) {
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
                        String realFEN = FEN.from(board);
                        Board calculatedBoard = FEN.boardFrom(realFEN);

                        Assert.assertEquals(calculatedBoard.toString(), board.toString());
                        counter += 1;
                    }
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
