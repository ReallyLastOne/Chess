package core.move.executor;

import core.Board;
import core.Cell;
import core.move.Move;
import core.pieces.Pawn;

import static core.GameUtilities.MoveInfo.*;
import static core.GameUtilities.MoveInfo.TWO_FORWARD;

public class StandardExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();
        boolean white = start.getPiece().isWhite();
        if (move.getInfo() == null || move.getInfo() == PAWN_MOVE || move.getInfo() == CAPTURE) {
            /* move */
            cells[end.getX()][end.getY()].setPiece(start.getPiece());
            /* set starting Cell as empty */
            cells[start.getX()][start.getY()].clear();
        } else if (move.getInfo() == EN_PASSANT) {
            /* Put pawn */
            cells[end.getX()][end.getY()].setPiece(cells[start.getX()][start.getY()].getPiece());
            /* Clear pawn capturing and pawn captured */
            cells[start.getX()][start.getY()].clear();
            cells[end.getX()][start.getY()].clear();
        } else if (move.getInfo() == TWO_FORWARD) {
            // same as above but set pawn status to be enpassanted
            ((Pawn) start.getPiece()).setEnPassant(true);
            /* move */
            cells[end.getX()][end.getY()].setPiece(start.getPiece());
            /* set starting Cell as empty */
            cells[start.getX()][start.getY()].clear();
        }
    }

    @Override
    public void undoMove(Board board) {
        Cell[][] cells = board.getCells();
        Move lastMove = board.getLastMove();
        Cell start = lastMove.getStart();
        Cell end = lastMove.getEnd();
        if (lastMove.getInfo() == null || lastMove.getInfo() == PAWN_MOVE || lastMove.getInfo() == CAPTURE) {
            cells[start.getX()][start.getY()].setPiece(start.getPiece());
            cells[end.getX()][end.getY()].setPiece(end.getPiece());
        } else if (lastMove.getInfo() == EN_PASSANT) {
            /* Put capturing Pawn back at beginning */
            cells[start.getX()][start.getY()].setPiece(cells[end.getX()][end.getY()].getPiece());
            /* Clear end Cell */
            cells[end.getX()][end.getX()].clear();
            /* Put captured Pawn back */
            cells[end.getX()][start.getY()].setPiece(new Pawn(!cells[start.getX()][start.getY()].getPiece().isWhite()));
            ((Pawn) cells[end.getX()][start.getY()].getPiece()).setEnPassant(true);
        } else if (lastMove.getInfo() == TWO_FORWARD) {
            /* Set Pawn back */
            cells[start.getX()][start.getY()].setPiece(cells[end.getX()][end.getY()].getPiece());
            ((Pawn) cells[start.getX()][start.getY()].getPiece()).setEnPassant(false);
            /* set end Cell as empty */
            cells[end.getX()][end.getY()].clear();
        }
    }
}
