package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Queen extends Piece implements HorizontallyMovable, VerticallyMovable, DiagonallyMovable {
    public Queen(boolean white) {
        super(white);
    }

    public Queen(boolean white, int moves) {
        super(white, moves);
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell cell) {
        List<Move> vertical = calculateVerticalMoves(board, cell);
        List<Move> horizontal = calculateHorizontalMoves(board, cell);
        List<Move> diagonal = calculateDiagonalMoves(board, cell);

        return Stream.of(vertical, horizontal, diagonal)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Queen copy() {
        return new Queen(white);
    }

    @Override
    public String toSymbol() {
        return white ? "Q" : "q";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Queen)) return false;
        Queen queen = (Queen) o;
        return white == queen.isWhite();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * (hasMoved() ? 7 : 11);
    }

    @Override
    public int getEvaluation() {
        return white ? 9 : -9;
    }
}
