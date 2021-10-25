package core.move.executor;

import core.Board;
import core.Cell;
import core.move.Move;

import static core.GameUtilities.MoveInfo.*;
import static core.GameUtilities.MoveInfo.BLACK_LONG_CASTLE;
import static core.PositionConstants.*;
import static core.PositionConstants.BLACK_PIECES_ROW;

public class CastlingExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();

        if (move.getInfo().equals(WHITE_SHORT_CASTLE)) {
            cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();

            /* Set Rook and King on destination Cells. */
            cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece());

            /* Clear starting Cells. */
            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].clear();
            cells[KING_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (move.getInfo().equals(WHITE_LONG_CASTLE)) {
            cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();

            /* Set Rook and King on destination Cells. */
            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece());

            /* Clear starting Cells. */
            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].clear();
            cells[KING_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (move.getInfo().equals(BLACK_SHORT_CASTLE)) {
            cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();

            /* Set Rook and King on destination Cells. */
            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece());

            /* Clear starting Cells. */
            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].clear();
            cells[KING_COLUMN][BLACK_PIECES_ROW].clear();
        } else if (move.getInfo().equals(BLACK_LONG_CASTLE)) {
            cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();

            /* Set Rook and King on destination Cells. */
            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece());

            /* Clear starting Cells. */
            cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].clear();
            cells[KING_COLUMN][BLACK_PIECES_ROW].clear();
        }
    }

    @Override
    public void undoMove(Board board) {
        Cell[][] cells = board.getCells();
        Move lastMove = board.getLastMove();
        if (lastMove.getInfo() == WHITE_SHORT_CASTLE) {
            cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();

            /* Set Rook and King at their initial (before castling) positions. */
            cells[KING_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece());

            /* Clear last positions of King and Rook. */
            cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].clear();
            cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == WHITE_LONG_CASTLE) {
            /* Set Rook's and King's field: moved on false. */
            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();

            /* Set Rook and King at their initial (before castling) positions. */
            cells[KING_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].getPiece());

            /* Clear last positions of King and Rook. */
            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].clear();
            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == BLACK_SHORT_CASTLE) {
            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();

            /* Set Rook and King at their initial (before castling) positions. */
            cells[KING_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece());

            /* Clear last positions of King and Rook. */
            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].clear();
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == BLACK_LONG_CASTLE) {
            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();

            /* Set Rook and King at their initial (before castling) positions. */
            cells[KING_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].getPiece());

            /* Clear last positions of King and Rook. */
            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].clear();
            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].clear();
        }
    }
}
