package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Rook extends Piece implements HorizontallyMovable, VerticallyMovable {

    public Rook(boolean white) {
        super(white);
    }

    public Rook(boolean white, int moves) {
        super(white, moves);
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell cell) {
        List<Move> vertical = calculateVerticalMoves(board, cell);
        List<Move> horizontal = calculateHorizontalMoves(board, cell);

        return Stream.of(vertical, horizontal)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Rook copy() {
        return new Rook(white, moves);
    }

    @Override
    public String toSymbol() {
        return white ? "R" : "r";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Rook)) return false;
        Rook rook = (Rook) o;
        return white == rook.isWhite() && hasMoved() == rook.hasMoved();
    }
}
