package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.core.pieces.Pawn;

public class StandardExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();
        switch (move.getInfo()) {
            case STANDARD, PAWN_MOVE, CAPTURE -> {
                cells[end.getX()][end.getY()].setPiece(start.getPiece());
                cells[end.getX()][end.getY()].getPiece().increaseMoves();
                cells[start.getX()][start.getY()].clear();
            }
            case EN_PASSANT -> {
                cells[end.getX()][end.getY()].setPiece(cells[start.getX()][start.getY()].getPiece());
                cells[end.getX()][end.getY()].getPiece().increaseMoves();
                cells[start.getX()][start.getY()].clear();
                cells[end.getX()][start.getY()].clear();
            }
            case TWO_FORWARD -> {
                ((Pawn) start.getPiece()).setEnPassant(true);
                cells[end.getX()][end.getY()].setPiece(start.getPiece());
                cells[end.getX()][end.getY()].getPiece().increaseMoves();
                cells[start.getX()][start.getY()].clear();
            }
        }
    }

    @Override
    public void undoMove(Board board) {
        Cell[][] cells = board.getCells();
        Move lastMove = board.getLastMove();
        Cell start = lastMove.getStart();
        Cell end = lastMove.getEnd();

        switch (lastMove.getInfo()) {
            case STANDARD, PAWN_MOVE, CAPTURE -> {
                cells[start.getX()][start.getY()].setPiece(start.getPiece());
                cells[start.getX()][start.getY()].getPiece().decreaseMoves();
                cells[end.getX()][end.getY()].setPiece(end.getPiece());
            }
            case EN_PASSANT -> {
                cells[start.getX()][start.getY()].setPiece(cells[end.getX()][end.getY()].getPiece());
                cells[start.getX()][start.getY()].getPiece().decreaseMoves();
                cells[end.getX()][start.getY()].setPiece(new Pawn(!cells[start.getX()][start.getY()].getPiece().isWhite()));
                ((Pawn) cells[end.getX()][start.getY()].getPiece()).setEnPassant(true);
                cells[end.getX()][end.getY()].clear();
            }
            case TWO_FORWARD -> {
                cells[start.getX()][start.getY()].setPiece(cells[end.getX()][end.getY()].getPiece());
                cells[start.getX()][start.getY()].getPiece().decreaseMoves();
                ((Pawn) cells[start.getX()][start.getY()].getPiece()).setEnPassant(false);
                cells[end.getX()][end.getY()].clear();
            }
        }
    }
}
