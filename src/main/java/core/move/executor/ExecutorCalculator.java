package core.move.executor;

import core.move.Move;

public class ExecutorCalculator { // factory pattern?
    /**
     * Method responsible for calculating correct {@link Executor} for given {@link Move}.
     *
     * @param move a move to be executed
     * @return executor correct for given move
     */
    public static Executor calculate(Move move) {
        return switch (move.getInfo()) {
            case WHITE_SHORT_CASTLE, WHITE_LONG_CASTLE, BLACK_LONG_CASTLE, BLACK_SHORT_CASTLE -> new CastlingExecutor();
            case QUEEN_PROMOTION, BISHOP_PROMOTION, KNIGHT_PROMOTION, ROOK_PROMOTION -> new PromotionExecutor();
            default -> new StandardExecutor();
        };
    }
}
