package AI;

import chess.ai.Minimax;
import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BestMoveTest {
    @Test
    public void shouldFindCheckmateIn1() {
        Game game = new Game("8/8/8/8/8/r2k4/8/3K4 b - - 0 1");
        Minimax minimax = new Minimax();
        Assertions.assertEquals(minimax.solve(game, 2).toString(), "a3a1");
        Assertions.assertEquals(minimax.getBestScore(), -100);
    }

    @Test
    public void shouldFindDrawIn1() {
        Game game = new Game("8/8/8/8/8/3k4/8/2rK4 w - - 0 1");
        Minimax minimax = new Minimax();
        Assertions.assertEquals(minimax.solve(game, 2).toString(), "d1c1");
        Assertions.assertEquals(minimax.getBestScore(), 0);
    }

    @Test
    public void shouldFindMoveLeadingToCheckmate() {
        Game game = new Game("8/8/8/8/8/3k4/2r5/3K4 b - - 0 1");
        Minimax minimax = new Minimax();
        Assertions.assertEquals(minimax.solve(game, 5).toString(), "c2c4");
        Assertions.assertEquals(minimax.getBestScore(), -100);
    }
}

