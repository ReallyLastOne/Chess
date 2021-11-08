package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static chess.core.Board.fitInBoard;
import static chess.utilities.Constants.GRID_SIZE;
import static chess.utilities.GameUtilities.MoveInfo;

interface VerticallyMovable {
    default List<Move> calculateVerticalMoves(Board board, Cell start) {
        if (!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        /* move up */
        for (int dy = y + 1; dy <= GRID_SIZE - 1; dy++) {
            if (!fitInBoard(x, dy)) break;
            if (!cells[x][dy].isOccupied()) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.STANDARD));
            } else if (cells[x][dy].isOppositeColor(start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        /* move down */
        for (int dy = y - 1; dy >= 0; dy--) {
            if (!fitInBoard(x, dy)) break;
            if (!cells[x][dy].isOccupied()) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.STANDARD));
            } else if (cells[x][dy].isOppositeColor(start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        return moves;
    }
}
