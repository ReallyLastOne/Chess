package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.Objects;

import static chess.utilities.PositionConstants.*;

public class CastlingExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        switch (move.getInfo()) {
            case WHITE_SHORT_CASTLE -> {
                changeKingAndRookMovesBy(cells, ROOK_KINGSIDE_COLUMN, WHITE_PIECES_ROW, true);
                putKingAndRookOn(cells, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, KING_COLUMN, ROOK_KINGSIDE_COLUMN, WHITE_PIECES_ROW);
                clearKingAndRookCells(cells, KING_COLUMN, ROOK_KINGSIDE_COLUMN, WHITE_PIECES_ROW);
            }
            case WHITE_LONG_CASTLE -> {
                changeKingAndRookMovesBy(cells, ROOK_QUEENSIDE_COLUMN, WHITE_PIECES_ROW, true);
                putKingAndRookOn(cells, KING_LONG_COLUMN, ROOK_LONG_COLUMN, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, WHITE_PIECES_ROW);
                clearKingAndRookCells(cells, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, WHITE_PIECES_ROW);
            }
            case BLACK_SHORT_CASTLE -> {
                changeKingAndRookMovesBy(cells, ROOK_KINGSIDE_COLUMN, BLACK_PIECES_ROW, true);
                putKingAndRookOn(cells, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, KING_COLUMN, ROOK_KINGSIDE_COLUMN, BLACK_PIECES_ROW);
                clearKingAndRookCells(cells, KING_COLUMN, ROOK_KINGSIDE_COLUMN, BLACK_PIECES_ROW);
            }
            case BLACK_LONG_CASTLE -> {
                changeKingAndRookMovesBy(cells, ROOK_QUEENSIDE_COLUMN, BLACK_PIECES_ROW, true);
                putKingAndRookOn(cells, KING_LONG_COLUMN, ROOK_LONG_COLUMN, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, BLACK_PIECES_ROW);
                clearKingAndRookCells(cells, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, BLACK_PIECES_ROW);
            }
        }
    }

    @Override
    public void undoMove(Board board) {
        Cell[][] cells = board.getCells();
        Move lastMove = board.getLastMove();
        switch (Objects.requireNonNull(lastMove).getInfo()) {
            case WHITE_SHORT_CASTLE -> {
                putKingAndRookOn(cells, KING_COLUMN, ROOK_KINGSIDE_COLUMN, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, WHITE_PIECES_ROW);
                changeKingAndRookMovesBy(cells, ROOK_KINGSIDE_COLUMN, WHITE_PIECES_ROW, false);
                clearKingAndRookCells(cells, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, WHITE_PIECES_ROW);
            }
            case WHITE_LONG_CASTLE -> {
                putKingAndRookOn(cells, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, KING_LONG_COLUMN, ROOK_LONG_COLUMN, WHITE_PIECES_ROW);
                changeKingAndRookMovesBy(cells, ROOK_QUEENSIDE_COLUMN, WHITE_PIECES_ROW, false);
                clearKingAndRookCells(cells, KING_LONG_COLUMN, ROOK_LONG_COLUMN, WHITE_PIECES_ROW);
            }
            case BLACK_SHORT_CASTLE -> {
                putKingAndRookOn(cells, KING_COLUMN, ROOK_KINGSIDE_COLUMN, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, BLACK_PIECES_ROW);
                changeKingAndRookMovesBy(cells, ROOK_KINGSIDE_COLUMN, BLACK_PIECES_ROW, false);
                clearKingAndRookCells(cells, KING_SHORT_COLUMN, ROOK_SHORT_COLUMN, BLACK_PIECES_ROW);
            }
            case BLACK_LONG_CASTLE -> {
                putKingAndRookOn(cells, KING_COLUMN, ROOK_QUEENSIDE_COLUMN, KING_LONG_COLUMN, ROOK_LONG_COLUMN, BLACK_PIECES_ROW);
                changeKingAndRookMovesBy(cells, ROOK_QUEENSIDE_COLUMN, BLACK_PIECES_ROW, false);
                clearKingAndRookCells(cells, KING_LONG_COLUMN, ROOK_LONG_COLUMN, BLACK_PIECES_ROW);
            }
        }
    }

    private void putKingAndRookOn(Cell[][] cells, int kingDestinationCol, int rookDestinationCol, int kingCol, int rookCol, int row) {
        cells[kingDestinationCol][row].setPiece(cells[kingCol][row].getPiece());
        cells[rookDestinationCol][row].setPiece(cells[rookCol][row].getPiece());
    }

    private void clearKingAndRookCells(Cell[][] cells, int kingCol, int rookColumn, int row) {
        cells[kingCol][row].clear();
        cells[rookColumn][row].clear();
    }

    private void changeKingAndRookMovesBy(Cell[][] cells, int rookColumn, int row, boolean incrementation) {
        if (incrementation) {
            cells[KING_COLUMN][row].getPiece().increaseMoves();
            cells[rookColumn][row].getPiece().increaseMoves();
        } else {
            cells[KING_COLUMN][row].getPiece().decreaseMoves();
            cells[rookColumn][row].getPiece().decreaseMoves();
        }
    }
}
