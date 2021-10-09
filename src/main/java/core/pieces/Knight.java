package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static core.Cell.*;
import static core.Board.fitInBoard;

public class Knight extends Piece {
    private static final int LEFT_SHORT = -1;
    private static final int RIGHT_SHORT = 1;
    private static final int UP_LONG = 2;
    private static final int DOWN_LONG = -2;

    private static final int LEFT_LONG = -2;
    private static final int RIGHT_LONG = 2;
    private static final int UP_SHORT = 1;
    private static final int DOWN_SHORT = -1;

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        if(!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        if(fitInBoard(x + LEFT_LONG, y + UP_SHORT)) {
            if(isEmpty(cells[x + LEFT_LONG][y + UP_SHORT]) || isOppositeColor(cells[x + LEFT_LONG][y + UP_SHORT], white)) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + UP_SHORT]));
            }
        }

        if(fitInBoard(x + LEFT_SHORT, y + UP_LONG)) {
            if(isEmpty(cells[x + LEFT_SHORT][y + UP_LONG]) || isOppositeColor(cells[x + LEFT_SHORT][y + UP_LONG], white)) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + UP_LONG]));
            }
        }
        if(fitInBoard(x + LEFT_LONG, y + DOWN_SHORT)){
            if(isEmpty(cells[x + LEFT_LONG][y + DOWN_SHORT]) || isOppositeColor(cells[x + LEFT_LONG][y + DOWN_SHORT], white)) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + DOWN_SHORT]));
            }
        }
        if(fitInBoard(x + LEFT_SHORT, y + DOWN_LONG)) {
            if(isEmpty(cells[x + LEFT_SHORT][y + DOWN_LONG]) || isOppositeColor(cells[x + LEFT_SHORT][y + DOWN_LONG], white) ) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + DOWN_LONG]));
            }
        }
        if(fitInBoard(x + RIGHT_LONG,  y + UP_SHORT)) {
            if(isEmpty(cells[x + RIGHT_LONG][y + UP_SHORT]) || isOppositeColor(cells[x + RIGHT_LONG][y + UP_SHORT], white)) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + UP_SHORT]));
            }
        }
        if(fitInBoard(x + RIGHT_SHORT, y + UP_LONG)){
            if(isEmpty(cells[x + RIGHT_SHORT][y + UP_LONG]) || isOppositeColor(cells[x + RIGHT_SHORT][y + UP_LONG], white)) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + UP_LONG]));
            }
        }
        if(fitInBoard(x + RIGHT_LONG, y + DOWN_SHORT)){
            if(isEmpty(cells[x + RIGHT_LONG][y + DOWN_SHORT]) || isOppositeColor(cells[x + RIGHT_LONG][y + DOWN_SHORT], white)) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + DOWN_SHORT]));
            }
        }
        if(fitInBoard(x + RIGHT_SHORT, y + DOWN_LONG)){
            if(isEmpty(cells[x + RIGHT_SHORT][y + DOWN_LONG]) || isOppositeColor(cells[x + RIGHT_SHORT][y + DOWN_LONG], white)) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + DOWN_LONG]));
            }
        }

        return moves;
    }

    @Override
    public Knight copy() {
        return new Knight(white);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Knight)) return false;
        Knight knight = (Knight) o;
        return white == knight.isWhite() && moved == knight.hasMoved();
    }

}
