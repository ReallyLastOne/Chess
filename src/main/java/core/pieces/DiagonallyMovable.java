package core.pieces;

import core.Board;
import core.Cell;
import core.GameUtilities;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;
import static core.Cell.*;
import static core.Board.*;

interface DiagonallyMovable {
    default List<Move> calculateDiagonalMoves(Board board, Cell start) {
        if(!(start.getPiece().equals(this))) return null;
        List<Move> moves = new ArrayList<>();

        for(Integer dx : new int[] {-1, 1}){
            for(Integer dy : new int[] {-1, 1}) {
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
        for(int i = 1; i <= 7; i++) {
            newX = newX + dx;
            newY = newY + dy;

            if(fitInBoard(newX, newY)) {
                if(isEmpty(cells[newX][newY])) {
                    moves.add(new Move(start, cells[newX][newY], GameUtilities.MoveInfo.STANDARD));
                } else if (!isEmpty(cells[newX][newY]) && isOppositeColor(cells[newX][newY], start.getPiece().isWhite())) {
                    moves.add(new Move(start, cells[newX][newY], GameUtilities.MoveInfo.CAPTURE));
                    break;
                } else {
                    break;
                }
            }
            else {
                break;
            }
        }
        return moves;
    }
}
