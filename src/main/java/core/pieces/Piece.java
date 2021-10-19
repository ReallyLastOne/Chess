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

    /**
     * Basic constructor to initialize Piece.
     */
    public Piece(boolean white) {
        this.white = white;
    }

    /**
     * Constructor used when initializing Pieces that need to have initialized moves, e.g. in undoing promotion move.
     */
    public Piece(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    /**
     * Returns list of pseudo legal Moves. (without checking checkmate yet)
     */
    public abstract List<Move> calculatePseudoLegalMoves(Board board, Cell start);

    /**
     * Returns copy of Piece.
     */
    public abstract Piece copy();

    /**
     * Method for increasing number of moves made by Piece.
     */
    public void increaseMoves() {
        moves += 1;
    }

    /**
     * Method for decreasing number of moves made by Piece.
     */
    public void decreaseMoves() {
        moves -= 1;
    }

    /**
     * Returns true if Piece has ever moved.
     */
    public boolean hasMoved() {
        return moves > 0;
    }

    /**
     * Returns 1-char String representation of a Piece.
     */
    public abstract String toSymbol();

    /**
     * Returns piece from given symbol.
     */
    public static Piece of(String s) {
        boolean white = Character.isUpperCase(s.charAt(0));
        return switch (s) {
            case "P", "p" -> new Pawn(white);
            case "B", "b" -> new Bishop(white);
            case "N", "n" -> new Knight(white);
            case "R", "r" -> new Rook(white);
            case "Q", "q" -> new Queen(white);
            case "K", "k" -> new King(white);
            default -> null;
        };
    }
}
