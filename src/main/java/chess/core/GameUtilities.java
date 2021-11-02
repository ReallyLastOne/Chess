package chess.core;

public class GameUtilities {
    public enum GameStatus {
        IN_PROGRESS, DRAW, WHITE_WIN, BLACK_WIN;

        @Override
        public String toString() {
            if (this == DRAW) return "1/2-1/2";
            else if (this == WHITE_WIN) return "1-0";
            else if (this == BLACK_WIN) return "0-1";
            return "*";
        }
    }

    public enum MoveInfo {
        WHITE_SHORT_CASTLE, WHITE_LONG_CASTLE,
        BLACK_SHORT_CASTLE, BLACK_LONG_CASTLE,
        EN_PASSANT, TWO_FORWARD, KNIGHT_PROMOTION,
        BISHOP_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION,
        PAWN_MOVE, CAPTURE, STANDARD;

        public String getSymbolPromotion() {
            if (this == BISHOP_PROMOTION) return "b";
            if (this == QUEEN_PROMOTION) return "q";
            if (this == KNIGHT_PROMOTION) return "n";
            if (this == ROOK_PROMOTION) return "r";
            return "";
        }
    }
}
