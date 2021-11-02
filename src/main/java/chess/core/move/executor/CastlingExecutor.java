package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import static chess.utilities.GameUtilities.MoveInfo.*;
import static chess.utilities.GameUtilities.MoveInfo.BLACK_LONG_CASTLE;
import static chess.utilities.PositionConstants.*;
import static chess.utilities.PositionConstants.BLACK_PIECES_ROW;

public class CastlingExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();

        if (move.getInfo().equals(WHITE_SHORT_CASTLE)) {
            cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();

            cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece());

            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].clear();
            cells[KING_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (move.getInfo().equals(WHITE_LONG_CASTLE)) {
            cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().increaseMoves();

            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece());

            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].clear();
            cells[KING_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (move.getInfo().equals(BLACK_SHORT_CASTLE)) {
            cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();

            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece());

            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].clear();
            cells[KING_COLUMN][BLACK_PIECES_ROW].clear();
        } else if (move.getInfo().equals(BLACK_LONG_CASTLE)) {
            cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();
            cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().increaseMoves();

            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece());

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

            cells[KING_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].getPiece());

            cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW].clear();
            cells[ROOK_SHORT_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == WHITE_LONG_CASTLE) {
            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].getPiece().decreaseMoves();

            cells[KING_COLUMN][WHITE_PIECES_ROW].setPiece(cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].getPiece());
            cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].setPiece(cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].getPiece());

            cells[KING_LONG_COLUMN][WHITE_PIECES_ROW].clear();
            cells[ROOK_LONG_COLUMN][WHITE_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == BLACK_SHORT_CASTLE) {
            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();

            cells[KING_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].getPiece());

            cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW].clear();
            cells[ROOK_SHORT_COLUMN][BLACK_PIECES_ROW].clear();
        } else if (lastMove.getInfo() == BLACK_LONG_CASTLE) {
            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();
            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].getPiece().decreaseMoves();

            cells[KING_COLUMN][BLACK_PIECES_ROW].setPiece(cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].getPiece());
            cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].setPiece(cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].getPiece());

            cells[KING_LONG_COLUMN][BLACK_PIECES_ROW].clear();
            cells[ROOK_LONG_COLUMN][BLACK_PIECES_ROW].clear();
        }
    }
}
