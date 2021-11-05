import chess.core.Board;
import chess.core.Game;
import chess.core.move.Move;
import chess.utilities.FEN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static utilities.Utilities.possibleOutcomes;

/**
 * Test that checks everything. It checks FEN, legal moves and result of the game. Test is executed on confirmed information from 100 games.
 */
public class GODTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void test() throws IOException {
        int counter = 0;
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/complexGameInformation.txt"));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (possibleOutcomes.contains(line)) {
                System.out.println("");
                Assertions.assertEquals(line, game.getGameStatus().toString());
                game = new Game();
                board = game.getBoard();
                counter = -1;
            } else if (counter % 3 == 0) { // line is move
                game.makeMove(line);
            } else if (counter % 3 == 1) { // line is FEN
                Assertions.assertEquals(line, FEN.from(board));
            } else if (counter % 3 == 2) { // line contains legal moves separated by space

                /* get correct legal moves from file */
                List<String> legalMoves = new ArrayList<>();
                if (!line.trim().equals("")) {
                    legalMoves = Arrays.asList(line.split(" "));
                }

                /* get legal moves from board */
                List<String> boardMoves = board.getLegalMoves().stream().map(Move::toString).collect(Collectors.toList());

                /* check if they are same */
                boolean condition = legalMoves.containsAll(boardMoves) && boardMoves.containsAll(legalMoves);
                Set<String> ad = new HashSet<>(boardMoves);
                Set<String> bd = new HashSet<>(legalMoves);
                ad.removeAll(bd);
                if (!condition) {
                    System.out.println(legalMoves.isEmpty());
                    System.out.println(boardMoves.isEmpty());
                    System.out.println("legalMoves = " + legalMoves);
                    System.out.println("boardMoves = " + boardMoves);
                    System.out.println(ad);
                }
                Assertions.assertTrue(condition);
            }
            counter++;
        }

    }
}
