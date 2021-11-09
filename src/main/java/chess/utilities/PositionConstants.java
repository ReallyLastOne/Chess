package chess.utilities;

public class PositionConstants {
    private PositionConstants() {
        throw new AssertionError();
    }

    public static final int WHITE_PAWN_ROW = 1;
    public static final int WHITE_PIECES_ROW = 0;
    public static final int BLACK_PAWN_ROW = 6;
    public static final int BLACK_PIECES_ROW = 7;
    public static final int KING_COLUMN = 4;
    public static final int ROOK_KINGSIDE_COLUMN = 7;
    public static final int ROOK_QUEENSIDE_COLUMN = 0;
    public static final int KING_SHORT_COLUMN = 6;
    public static final int KING_LONG_COLUMN = 2;
    public static final int ROOK_SHORT_COLUMN = 5;
    public static final int ROOK_LONG_COLUMN = 3;

    public static int getPiecesRow(boolean white) {
        if(white) return WHITE_PIECES_ROW;
        return BLACK_PIECES_ROW;
    }
}
