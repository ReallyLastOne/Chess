package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.core.pieces.*;
import chess.utilities.GameUtilities;

public class PromotionExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();
        GameUtilities.MoveInfo info = move.getInfo();

        switch (info) {
            case KNIGHT_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION, BISHOP_PROMOTION -> {
                cells[end.getX()][end.getY()].setPiece(pieceOf(info, start.getPiece().isWhite(), start.getPiece().getMoves()));
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
            case KNIGHT_PROMOTION, BISHOP_PROMOTION, ROOK_PROMOTION, QUEEN_PROMOTION -> {
                cells[start.getX()][start.getY()].setPiece(new Pawn(start.getPiece().isWhite(),
                        start.getPiece().getMoves() - 1));
                cells[end.getX()][end.getY()].setPiece(end.getPiece());
            }
        }
    }

    private Piece pieceOf(GameUtilities.MoveInfo info, boolean white, int moves) {
        switch (info) {
            case KNIGHT_PROMOTION -> {
                return new Knight(white, moves);
            }
            case BISHOP_PROMOTION -> {
                return new Bishop(white, moves);
            }
            case ROOK_PROMOTION -> {
                return new Rook(white, moves);
            }
            case QUEEN_PROMOTION -> {
                return new Queen(white, moves);
            }
        }
        return null;
    }
}
