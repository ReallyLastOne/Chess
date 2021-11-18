package PositionsCounterTest;

import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.Utilities;

/**
 * CLass that contains positions from https://www.chessprogramming.org/Perft_Results
 */
public class PerftResults {
    private static final String[] fens = {
            "r3k2r/p1ppqpb1/bn2pnp1/3PN3/1p2P3/2N2Q1p/PPPBBPPP/R3K2R w KQkq - 0 1",
            "8/2p5/3p4/KP5r/1R3p1k/8/4P1P1/8 w - - 0 1",
            "r3k2r/Pppp1ppp/1b3nbN/nP6/BBP1P3/q4N2/Pp1P2PP/R2Q1RK1 w kq - 0 1",
            "rnbq1k1r/pp1Pbppp/2p5/8/2B5/8/PPP1NnPP/RNBQK2R w KQ - 1 8",
            "r4rk1/1pp1qppp/p1np1n2/2b1p1B1/2B1P1b1/P1NP1N2/1PP1QPPP/R4RK1 w - - 0 10"
    };

    private static final int[][] positionCounter = {
            {48, 2039}, {14, 191, 2812}, {6, 264}, {44, 1486}, {46, 2079}
    };

    @Test
    public void positionTest() {
        for (int i = 0; i < positionCounter.length; i++) {
            for (int j = 0; j < positionCounter[i].length; j++) {
                Assertions.assertEquals(positionCounter[i][j], Utilities.countNumberOfMoves(new Game(fens[i]), j + 1));
            }
        }
    }
}
