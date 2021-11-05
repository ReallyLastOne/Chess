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

import static utilities.Utilities.getCellByName;

public class KnightTest {
    List<List<String>> validMoves = Arrays.asList(
            Arrays.asList("b1a3", "b1c3"),
            Arrays.asList("g1f3", "g1h3"),
            Arrays.asList("b8a6", "b8c6"),
            Arrays.asList("g8f6", "g8h6")
    );
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void knightTest() {
        List<String> moves = getCellByName(board, "b1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "b1")).
                stream().map(Move::toString).collect(Collectors.toList());
        Assertions.assertTrue(moves.containsAll(validMoves.get(0)) && validMoves.get(0).containsAll(moves));

        moves = getCellByName(board, "g1").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "g1")).
                stream().map(Move::toString).collect(Collectors.toList());
        Assertions.assertTrue(moves.containsAll(validMoves.get(1)) && validMoves.get(1).containsAll(moves));

        moves = getCellByName(board, "b8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "b8")).
                stream().map(Move::toString).collect(Collectors.toList());
        Assertions.assertTrue(moves.containsAll(validMoves.get(2)) && validMoves.get(2).containsAll(moves));

        moves = getCellByName(board, "g8").getPiece().calculatePseudoLegalMoves(board, getCellByName(board, "g8")).
                stream().map(Move::toString).collect(Collectors.toList());
        Assertions.assertTrue(moves.containsAll(validMoves.get(3)) && validMoves.get(3).containsAll(moves));
    }
}
