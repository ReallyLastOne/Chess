package utilities;

import core.Board;
import core.Cell;
import core.GameUtilities;
import core.pieces.Pawn;
import core.pieces.Rook;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.Board.fitInBoard;
import static core.PositionConstants.*;
import static utilities.Constants.GRID_SIZE;
import static utilities.Display.convertPieceToSymbol;

public class FEN {
    /** Returns pieces placement from given board. */
    public static String getPiecePlacement(Board board) {
        Cell[][] cells = board.getCells();

        String piecePlacement = "";
        int freeCount = 0;
        for(int i = GRID_SIZE - 1; i >= 0; i--) {
            for(int j = 0; j < GRID_SIZE; j++) {
                if (cells[j][i].getPiece() != null) {
                    if (freeCount != 0) {
                        piecePlacement += freeCount;
                        freeCount = 0;
                    }
                    piecePlacement += convertPieceToSymbol(cells[j][i].getPiece());
                } else {
                    freeCount++;
                }
            }
            if(freeCount != 0) {
                piecePlacement += freeCount;
                freeCount = 0;
            }
            piecePlacement += "/";
        }
        piecePlacement = piecePlacement.substring(0, piecePlacement.length() - 1);
        return piecePlacement;
    }

    /** Returns FEN from given board. */
    public static String of(Board board) {
        String enPassantCell = calculateEnPassantCell(board);

        return getPiecePlacement(board) + " " + (board.isTurn() ? "w" : "b") + " " + calculateCastlingAvailability(board) + " " +
                enPassantCell + " " + board.getHalfmoves() + " " + board.getFullmoves();
    }

    /* Note: wikipedia (https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation) says that en passant cell
    * is recorded regardless of whether there is a pawn in position to make an en passant capture but some chess program
    * doesn't. (e.g. lichess.org or python chess library) I do since it is easier to test basing on these programs. */
    /** Returns algebraic notation of cell that can be enpassanted. */
    private static String calculateEnPassantCell(Board board) {
        String enPassantCell = "-";
        Cell[][] cells = board.getCells();
        if(board.getLastMove() != null && board.getLastMove().getInfo() == GameUtilities.MoveInfo.TWO_FORWARD) {
            Cell end = board.getLastMove().getEnd();

            if(fitInBoard(end.getX() + 1, end.getY())) {
                if(cells[end.getX() + 1][end.getY()].getPiece() != null && cells[end.getX() + 1][end.getY()].getPiece()
                        instanceof Pawn && cells[end.getX() + 1][end.getY()].getPiece().isWhite() != cells[end.getX()][end.getY()].getPiece().isWhite()) {
                    int forward = ((Pawn) cells[end.getX()][end.getY()].getPiece()).getForwardCount();
                    enPassantCell = cells[end.getX()][end.getY() - forward].toAlgebraicNotation();
                }
            }
            if(fitInBoard(end.getX() - 1, end.getY())) {
                if(cells[end.getX() - 1][end.getY()].getPiece() != null && cells[end.getX() - 1][end.getY()].getPiece()
                        instanceof Pawn && cells[end.getX() - 1][end.getY()].getPiece().isWhite() != cells[end.getX()][end.getY()].getPiece().isWhite()) {
                    int forward = ((Pawn) cells[end.getX()][end.getY()].getPiece()).getForwardCount();
                    enPassantCell = cells[end.getX()][end.getY() - forward].toAlgebraicNotation();
                }
            }
        }

        return enPassantCell;
    }

    /** Returns castling availability for both sides. */
    private static String calculateCastlingAvailability(Board board) {
        Cell whiteKingCell = board.getWhiteKingCell();
        Cell blackKingCell = board.getBlackKingCell();
        String castlingAvailability = "";

        Cell[][] cells = board.getCells();
        if(!whiteKingCell.getPiece().hasMoved()) {
            /* Check white short castle availability */
            if(cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() != null &&
                    cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() instanceof Rook &&
                    !cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "K";
            }

            /* Check white long castle availability */
            if(cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() != null &&
                    cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() instanceof Rook &&
                    !cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "Q";
            }
        }

        if(!blackKingCell.getPiece().hasMoved()) {
            /* Check black short castle availability */
            if(cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() != null &&
                    cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() instanceof Rook &&
                    !cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "k";
            }

            /* Check black long castle availability */
            if(cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() != null &&
                    cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() instanceof Rook &&
                    !cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "q";
            }
        }

        return castlingAvailability.equals("") ? "-" : castlingAvailability;
    }
}

