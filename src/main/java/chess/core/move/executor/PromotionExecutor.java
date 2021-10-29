package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.core.pieces.*;

import static chess.core.GameUtilities.MoveInfo.*;
import static chess.core.GameUtilities.MoveInfo.QUEEN_PROMOTION;

public class PromotionExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();

        if (move.getInfo() == KNIGHT_PROMOTION) {
            /* move */
            cells[end.getX()][end.getY()].setPiece(new Knight(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
            /* set starting Cell as empty */
            cells[start.getX()][start.getY()].clear();
        } else if (move.getInfo() == BISHOP_PROMOTION) {
            /* move */
            cells[end.getX()][end.getY()].setPiece(new Bishop(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
            /* set starting Cell as empty */
            cells[start.getX()][start.getY()].clear();
        } else if (move.getInfo() == ROOK_PROMOTION) {
            /* move */
            cells[end.getX()][end.getY()].setPiece(new Rook(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
            cells[end.getX()][end.getY()].getPiece().increaseMoves();
            /* set starting Cell as empty */
            cells[start.getX()][start.getY()].clear();
        } else if (move.getInfo() == QUEEN_PROMOTION) {
            /* move */
            cells[end.getX()][end.getY()].setPiece(new Queen(start.getPiece().isWhite(), cells[start.getX()][start.getY()].getPiece().getMoves()));
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

        if (lastMove.getInfo() == KNIGHT_PROMOTION) {
            /* set Pawn back to starting cell with correct number of moves made */
            cells[start.getX()][start.getY()].setPiece(new Pawn(cells[end.getX()][end.getY()].getPiece().isWhite(), cells[end.getX()][end.getY()].getPiece().getMoves()));
            /* set captured Piece back */
            cells[end.getX()][end.getY()].setPiece(end.getPiece());
        } else if (lastMove.getInfo() == BISHOP_PROMOTION) {
            /* set Pawn back to starting cell with correct number of moves made */
            cells[start.getX()][start.getY()].setPiece(new Pawn(cells[end.getX()][end.getY()].getPiece().isWhite(), cells[end.getX()][end.getY()].getPiece().getMoves()));
            /* set captured Piece back */
            cells[end.getX()][end.getY()].setPiece(end.getPiece());
        } else if (lastMove.getInfo() == ROOK_PROMOTION) {
            /* set Pawn back to starting cell with correct number of moves made */
            cells[start.getX()][start.getY()].setPiece(new Pawn(cells[end.getX()][end.getY()].getPiece().isWhite(), cells[end.getX()][end.getY()].getPiece().getMoves()));
            /* set captured Piece back */
            cells[end.getX()][end.getY()].setPiece(end.getPiece());
        } else if (lastMove.getInfo() == QUEEN_PROMOTION) {
            /* set Pawn back to starting cell with correct number of moves made */
            cells[start.getX()][start.getY()].setPiece(new Pawn(cells[end.getX()][end.getY()].getPiece().isWhite(), cells[end.getX()][end.getY()].getPiece().getMoves()));
            /* set captured Piece back */
            cells[end.getX()][end.getY()].setPiece(end.getPiece());
        }
    }
}