package MovesTest;

import chess.core.Board;
import chess.core.Game;
import chess.utilities.FEN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class EnPassantTest {
    private static final String[] moves = {"e2e4", "d7d5", "e4e5", "f7f5"};

    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void shouldEnPassant() {
        for (String move : moves) {
            game.makeMove(move);
        }
        Assertions.assertEquals("rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR", FEN.calculatePiecePlacement(board));
        // en passant
        game.makeMove("e5f6");
        Assertions.assertEquals("rnbqkbnr/ppp1p1pp/5P2/3p4/8/8/PPPP1PPP/RNBQKBNR", FEN.calculatePiecePlacement(board));
    }

    @Test
    public void shouldEnPassant2() {
        String[] additionalMoves = new String[]{"e5f6", "d5d4", "c2c4"};
        String[] allMoves = Arrays.copyOf(moves, moves.length + additionalMoves.length);
        System.arraycopy(additionalMoves, 0, allMoves, moves.length, additionalMoves.length);

        for (String move : allMoves) {
            game.makeMove(move);
        }
        Assertions.assertEquals("rnbqkbnr/ppp1p1pp/5P2/8/2Pp4/8/PP1P1PPP/RNBQKBNR", FEN.calculatePiecePlacement(board));
        // en passant
        game.makeMove("d4c3");
        Assertions.assertEquals("rnbqkbnr/ppp1p1pp/5P2/8/8/2p5/PP1P1PPP/RNBQKBNR", FEN.calculatePiecePlacement(board));
    }
}
