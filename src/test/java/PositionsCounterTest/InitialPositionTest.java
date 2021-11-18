package PositionsCounterTest;

import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utilities.Utilities;

public class InitialPositionTest {
    private static final int[] positionCounter = {20, 400, 8902}; //next values: 197281, 4865609

    @Test
    public void testNumberOfPositions() { // so slow lol
        for (int i = 0; i < positionCounter.length; i++) {
            Assertions.assertEquals(positionCounter[i], Utilities.countNumberOfMoves(new Game(), i + 1));
        }
    }
}
