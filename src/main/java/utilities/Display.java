package utilities;

import core.pieces.Piece;

public class Display {
    /**
     * Returns 1-char String representation of given Piece.
     */
    public static String convertPieceToSymbol(Piece piece) {
        if (piece == null) return " ";
        return piece.toSymbol();
    }
}
