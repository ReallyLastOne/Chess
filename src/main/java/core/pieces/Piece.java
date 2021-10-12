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
    int moves;
    public Piece(boolean white) {
        this.white = white;
    }

    public Piece(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    /** Returns list of pseudo legal Moves. (without checking checkmate yet) */
    public abstract List<Move> calculatePseudoLegalMoves(Board board, Cell start);

    public abstract Piece copy();

    public void increaseMoves() {
        moves++;
    }

    public void decreaseMoves() {
        moves--;
    }

    public boolean hasMoved() {
        return moves > 0;
    }
}
