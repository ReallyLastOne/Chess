package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.move.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static chess.utilities.Constants.GRID_SIZE;
import static chess.utilities.Constants.INT_TO_COLUMN;
import static utilities.Utilities.getCellByName;

public class PawnTest {
    List<List<String>> validMoves = Arrays.asList(
            Arrays.asList("a2a3", "a2a4"),
            Arrays.asList("b2b3", "b2b4"),
            Arrays.asList("c2c3", "c2c4"),
            Arrays.asList("d2d3", "d2d4"),
            Arrays.asList("e2e3", "e2e4"),
            Arrays.asList("f2f3", "f2f4"),
            Arrays.asList("g2g3", "g2g4"),
            Arrays.asList("h2h3", "h2h4")
    );


    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void pawnTest() {
        List<String> moves;
        List<String> valid;
        for (int i = 0; i < GRID_SIZE; i++) {
            valid = validMoves.get(i);
            moves = getCellByName(board, INT_TO_COLUMN.get(i) + "2").getPiece().
                    calculatePseudoLegalMoves(board, getCellByName(board, INT_TO_COLUMN.get(i) + "2")).
                    stream().map(Move::toString).collect(Collectors.toList());
            Assertions.assertTrue(moves.containsAll(valid) && valid.containsAll(moves));
        }
    }
}
