package core.pieces;

import core.Board;
import core.Cell;
import core.GameUtilities;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static core.Board.fitInBoard;
import static core.Cell.*;

interface HorizontallyMovable {
    default List<Move> calculateHorizontalMoves(Board board, Cell start) {
        if(!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        /* move right */
        for(int dx = x + 1; dx <= 7; dx++){
            if(!fitInBoard(dx, y)) break;
            if(isEmpty(cells[dx][y])) {
                moves.add(new Move(start, cells[dx][y], GameUtilities.MoveInfo.STANDARD));
            } else if(!isEmpty(cells[dx][y]) && isOppositeColor(cells[dx][y], start.getPiece().isWhite())){
                moves.add(new Move(start, cells[dx][y], GameUtilities.MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        /* move left */
        for(int dx = x - 1; dx >= 0; dx--){
            if(!fitInBoard(dx, y)) break;
            if(isEmpty(cells[dx][y])) {
                moves.add(new Move(start, cells[dx][y], GameUtilities.MoveInfo.STANDARD));
            } else if(!isEmpty(cells[dx][y]) && isOppositeColor(cells[dx][y], start.getPiece().isWhite())){
                moves.add(new Move(start, cells[dx][y], GameUtilities.MoveInfo.CAPTURE));
                break;
            } else {
                break;
            }
        }

        return moves;
    }
}
