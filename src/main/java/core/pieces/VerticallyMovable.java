package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static core.Board.fitInBoard;
import static core.Cell.isEmpty;
import static core.Cell.isOppositeColor;
import static core.GameUtilities.MoveInfo;

interface VerticallyMovable {
    default List<Move> calculateVerticalMoves(Board board, Cell start) {
        if (!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        /* move up */
        for (int dy = y + 1; dy <= 7; dy++) {
            if (!fitInBoard(x, dy)) break;
            if (isEmpty(cells[x][dy])) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x][dy]) && isOppositeColor(cells[x][dy], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        /* move down */
        for (int dy = y - 1; dy >= 0; dy--) {
            if (!fitInBoard(x, dy)) break;
            if (isEmpty(cells[x][dy])) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x][dy]) && isOppositeColor(cells[x][dy], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][dy], MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        return moves;
    }
}
