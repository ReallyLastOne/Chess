package core.move.executor;

import core.Board;
import core.move.Move;

/** Responsible for executing and undoing move on board. */
public interface Executor { // strategy pattern?
    void executeMove(Board board, Move move);
    void undoMove(Board board);
}
