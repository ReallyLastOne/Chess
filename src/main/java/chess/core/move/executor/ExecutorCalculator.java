package chess.core.move.executor;

import chess.core.move.Move;

public class ExecutorCalculator { // factory pattern?
    private ExecutorCalculator() {
        throw new AssertionError();
    }

    /**
     * Method responsible for calculating correct {@link Executor} for given {@link Move}.
     *
     * @param move a move to be executed
     * @return executor correct for given move
     */
    public static Executor calculate(Move move) {
        return switch (move.getInfo()) {
            case SHORT_CASTLE, LONG_CASTLE -> new CastlingExecutor();
            case QUEEN_PROMOTION, BISHOP_PROMOTION, KNIGHT_PROMOTION, ROOK_PROMOTION -> new PromotionExecutor();
            default -> new StandardExecutor();
        };
    }
}
