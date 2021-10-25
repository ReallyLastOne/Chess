package core.move.executor;

import core.Board;
import core.move.Move;

/**
 * Responsible for executing and undoing move on board.
 */
public interface Executor { // strategy pattern?
    /**
     * Method responsible for correct execution of {@link Move} for given {@link Board}.
     */
    void executeMove(Board board, Move move);
    /** Method responsible for correct revoke of last {@link Move} made in given {@link Board}. */
    void undoMove(Board board);
}
