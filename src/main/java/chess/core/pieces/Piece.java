package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class Piece {
    boolean white;
    /**
     * Number of moves made by this piece.
     */
    int moves;

    public Piece(boolean white) {
        this.white = white;
    }

    public Piece(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    /**
     * @return list of pseudo legal Moves. (without checking checkmate yet)
     * @see Move
     */
    public abstract List<Move> calculatePseudoLegalMoves(Board board, Cell start);

    public abstract Piece copy();

    public void increaseMoves() {
        moves += 1;
    }

    public void decreaseMoves() {
        moves -= 1;
    }

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

    @Override
    public int hashCode() {
        return white ? 2 : 1;
    }

    public abstract int getEvaluation();
}
