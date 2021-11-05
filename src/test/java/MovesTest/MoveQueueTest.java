package MovesTest;

import chess.core.Game;
import chess.core.move.Move;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Class that checks if moves stored in Board.class are same as moves entered by user.
 */
public class MoveQueueTest {
    Game game;

    @BeforeEach
    public void initialize() {
        game = new Game();
    }

    @Test
    public void shouldEqualsMoveList() {
        String[] moves = new String[]{"e2e4", "e7e5", "d1f3", "b8a6", "f1c4", "b7b6", "f3f7"};
        for (String move : moves) {
            game.makeMove(move);
        }
        Assertions.assertArrayEquals(game.getBoard().getMoves().stream().map(Move::toString).toArray(), moves);
    }

    @Test
    public void shouldEqualsMoveList2() {
        String[] moves = new String[]{"f2f4", "e7e5", "g2g4", "d8h4"};
        for (String move : moves) {
            game.makeMove(move);
        }
        Assertions.assertArrayEquals(game.getBoard().getMoves().stream().map(Move::toString).toArray(), moves);
    }

    @Test
    public void shouldEqualsMoveList3() {
        String[] moves = new String[]{"f2f3", "e7e5", "g2g4"};
        for (String move : moves) {
            game.makeMove(move);
        }

        Assertions.assertArrayEquals(game.getBoard().getMoves().stream().map(Move::toString).toArray(), moves);
    }
}
