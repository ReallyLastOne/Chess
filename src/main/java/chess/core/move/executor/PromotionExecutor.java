package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.core.pieces.*;

public class PromotionExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();

        switch (move.getInfo()) {
            case KNIGHT_PROMOTION -> {
                cells[end.getX()][end.getY()].setPiece(new Knight(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
                cells[start.getX()][start.getY()].clear();
            }
            case BISHOP_PROMOTION -> {
                cells[end.getX()][end.getY()].setPiece(new Bishop(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
                cells[start.getX()][start.getY()].clear();
            }
            case ROOK_PROMOTION -> {
                cells[end.getX()][end.getY()].setPiece(new Rook(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
                cells[end.getX()][end.getY()].getPiece().increaseMoves();
                cells[start.getX()][start.getY()].clear();
            }
            case QUEEN_PROMOTION -> {
                cells[end.getX()][end.getY()].setPiece(new Queen(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
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
            case KNIGHT_PROMOTION, BISHOP_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION -> {
                cells[start.getX()][start.getY()].setPiece(new Pawn(cells[end.getX()][end.getY()].getPiece().isWhite(), cells[end.getX()][end.getY()].getPiece().getMoves()));
                cells[end.getX()][end.getY()].setPiece(end.getPiece());
            }
        }
    }
}
