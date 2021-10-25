package core;

/**
 * Class that consists multiple constants for position of pieces.
 */
public class PositionConstants {
    /**
     * Row position of white pawns.
     */
    public static final int WHITE_PAWN_ROW = 1;
    /**
     * Row position of white pieces.
     */
    public static final int WHITE_PIECES_ROW = 0;

    /**
     * Row position of black pawns.
     */
    public static final int BLACK_PAWN_ROW = 6;
    /**
     * Row position of black pieces.
     */
    public static final int BLACK_PIECES_ROW = 7;

    /**
     * Column position of king.
     */
    public static final int KING_COLUMN = 4;
    /**
     * Column position of king sided rook.
     */
    public static final int ROOK_KINGSIDE_COLUMN = 7;
    /**
     * Column position of queen sided rook.
     */
    public static final int ROOK_QUEENSIDE_COLUMN = 0;

    /**
     * Destination column for king when short castling.
     */
    public static final int KING_SHORT_COLUMN = 6;
    /**
     * Destination column for king when long castling.
     */
    public static final int KING_LONG_COLUMN = 2;

    /**
     * Destination column for rook when short castling.
     */
    public static final int ROOK_SHORT_COLUMN = 5;
    /**
     * Destination column for rook when long castling.
     */
    public static final int ROOK_LONG_COLUMN = 3;


}
