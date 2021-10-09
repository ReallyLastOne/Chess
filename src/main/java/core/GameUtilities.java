package core;

public class GameUtilities {
    public enum GameStatus {
        IN_PROGRESS, DRAW, WHITE_WIN, BLACK_WIN
    }

    public enum MoveInfo {
        WHITE_SHORT_CASTLE, WHITE_LONG_CASTLE,
        BLACK_SHORT_CASTLE, BLACK_LONG_CASTLE,
        EN_PASSANT, TWO_FORWARD, KNIGHT_PROMOTION,
        BISHOP_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION,
        PAWN_MOVE, CAPTURE
    }
}
