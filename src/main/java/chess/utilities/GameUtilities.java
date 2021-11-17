package chess.utilities;

public class GameUtilities {
    private GameUtilities() {
        throw new AssertionError();
    }

    public enum GameStatus {
        IN_PROGRESS, DRAW, WHITE_WIN, BLACK_WIN;

        @Override
        public String toString() {
            if (this == DRAW) return "1/2-1/2";
            else if (this == WHITE_WIN) return "1-0";
            else if (this == BLACK_WIN) return "0-1";
            return "*";
        }

        public Integer getEvaluation() {
            if (this == DRAW) return 0;
            else if (this == WHITE_WIN) return 100;
            else if (this == BLACK_WIN) return -100;
            return null;
        }
    }

    public enum MoveInfo {
        EN_PASSANT, TWO_FORWARD, KNIGHT_PROMOTION,
        BISHOP_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION,
        PAWN_MOVE, CAPTURE, STANDARD, LONG_CASTLE, SHORT_CASTLE;


        public String getSymbolPromotion() {
            if (this == BISHOP_PROMOTION) return "b";
            if (this == QUEEN_PROMOTION) return "q";
            if (this == KNIGHT_PROMOTION) return "n";
            if (this == ROOK_PROMOTION) return "r";
            return "";
        }
    }
}
