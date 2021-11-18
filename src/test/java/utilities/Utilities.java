package utilities;

import chess.core.Board;
import chess.core.Cell;
import chess.core.Game;
import chess.core.move.Move;

import java.util.List;

import static chess.utilities.Constants.COLUMN_TO_INT;

public class Utilities {
    public static List<String> possibleOutcomes = List.of("1/2-1/2", "1-0", "0-1");

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

    public static int countNumberOfMoves(Game game, int depth) {
        if (depth == 0) return 1;

        int positions = 0;
        for (Move move : game.getBoard().getLegalMoves()) {
            game.makeMove(move.toString());
            positions += countNumberOfMoves(game, depth - 1);
            game.undoMove();
        }

        return positions;
    }
}
