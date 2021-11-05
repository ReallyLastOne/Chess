import chess.core.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    public static final String basicBoardString = """
            | r | n | b | q | k | b | n | r | 8
            | p | p | p | p | p | p | p | p | 7
            |   |   |   |   |   |   |   |   | 6
            |   |   |   |   |   |   |   |   | 5
            |   |   |   |   |   |   |   |   | 4
            |   |   |   |   |   |   |   |   | 3
            | P | P | P | P | P | P | P | P | 2
            | R | N | B | Q | K | B | N | R | 1
              A   B   C   D   E   F   G   H  \s""";

    @Test
    public void initialGameBoardLayoutTest() {
        Assertions.assertEquals(basicBoardString, new Game().getBoard().toString());
    }
}
