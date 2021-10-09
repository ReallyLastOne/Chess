package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Piece {
    boolean white;
    boolean moved; // used to en passant and castling

    public Piece(boolean white) {
        this.white = white;
    }

    public boolean hasMoved() {
        return moved;
    }

    /** Returns list of pseudo legal Moves. (without checking checkmate yet) */
    public abstract List<Move> calculatePseudoLegalMoves(Board board, Cell start);

    public abstract Piece copy();
}
