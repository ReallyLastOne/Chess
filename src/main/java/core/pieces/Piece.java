package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Piece {
    /**
     * Indicates if piece is white or black.
     */
    boolean white;
    /**
     * Number of moves made by this piece.
     */
    int moves;

    /**
     * Basic constructor to initialize piece.
     */
    public Piece(boolean white) {
        this.white = white;
    }

    /**
     * Constructor used when initializing pieces that need to have initialized moves, e.g. in undoing promotion move.
     */
    public Piece(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    /**
     * @return list of pseudo legal Moves. (without checking checkmate yet)
     * @see Move
     */
    public abstract List<Move> calculatePseudoLegalMoves(Board board, Cell start);

    /**
     * @return copy of a piece
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
     * @return 1-char String representation of a Piece.
     */
    public abstract String toSymbol();

    /**
     * @param s symbol of a piece
     * @return piece from given symbol
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
