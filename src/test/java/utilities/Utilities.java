package utilities;

import chess.core.Board;
import chess.core.Cell;

import static chess.utilities.Constants.COLUMN_TO_INT;

public class Utilities {

    /**
     * example: getCellByName("a1") returns cell: cells[0][0].
     *
     * @return real cell by name
     */
    public static Cell getCellByName(Board board, String name) {
        char column = name.charAt(0);
        char charRow = name.charAt(1);
        int row = Character.getNumericValue(charRow) - 1;
        return board.getCells()[COLUMN_TO_INT.get(column)][row];
    }
}
