package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;

import java.util.List;

public class Bishop extends Piece implements DiagonallyMovable {
    public Bishop(boolean white) {
        super(white);
    }

    public Bishop(boolean white, int moves) {
        super(white, moves);
    }

    @Override
    public Bishop copy() {
        return new Bishop(white);
    }

    @Override
    public String toSymbol() {
        return white ? "B" : "b";
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell cell) {
        return calculateDiagonalMoves(board, cell);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Bishop)) return false;
        Bishop bishop = (Bishop) o;
        return white == bishop.isWhite();
    }
}
