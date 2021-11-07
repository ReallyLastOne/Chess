package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.utilities.GameUtilities;

import java.util.ArrayList;
import java.util.List;

import static chess.core.Board.fitInBoard;
import static chess.utilities.Constants.GRID_SIZE;

interface DiagonallyMovable {
    default List<Move> calculateDiagonalMoves(Board board, Cell start) {
        if (!(start.getPiece().equals(this))) return null;
        List<Move> moves = new ArrayList<>();

        for (Integer dx : new int[]{-1, 1}) {
            for (Integer dy : new int[]{-1, 1}) {
                moves.addAll(calculateDiagonal(board, start, dx, dy));
            }
        }

        return moves;
    }

    private List<Move> calculateDiagonal(Board board, Cell start, int dx, int dy) {
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();
        int newX = start.getX();
        int newY = start.getY();
        for (int i = 1; i <= GRID_SIZE - 1; i++) {
            newX = newX + dx;
            newY = newY + dy;

            if (fitInBoard(newX, newY)) {
                if (!cells[newX][newY].isOccupied()) {
                    moves.add(new Move(start, cells[newX][newY], GameUtilities.MoveInfo.STANDARD));
                } else if (cells[newX][newY].isOccupied() && cells[newX][newY].isOppositeColor(start.getPiece().isWhite())) {
                    moves.add(new Move(start, cells[newX][newY], GameUtilities.MoveInfo.CAPTURE));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return moves;
    }
}
