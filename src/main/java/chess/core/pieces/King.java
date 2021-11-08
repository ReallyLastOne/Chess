package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static chess.core.move.Move.generateCaptureOrStandard;
import static chess.utilities.GameUtilities.MoveInfo;
import static chess.utilities.PositionConstants.*;

public class King extends Piece {
    private static final int[] DELTA = {1, -1, 0};

    public King(boolean white) {
        super(white);
    }

    public King(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    public boolean isWhiteShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        if (white && start.getX() == KING_COLUMN && start.getY() == WHITE_PIECES_ROW && !hasMoved() &&
                cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].isRook() &&
                !cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
            for (int i = 5; i <= 6; i++) {
                if (cells[i][WHITE_PIECES_ROW].isOccupied()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isWhiteLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        if (white && start.getX() == KING_COLUMN && start.getY() == WHITE_PIECES_ROW && !hasMoved() &&
                cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].isRook() &&
                !cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
            if (cells[1][WHITE_PIECES_ROW].isOccupied()) return false;
            for (int i = 3; i >= 2; i--) {
                if (cells[i][WHITE_PIECES_ROW].isOccupied()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        if (!white && start.getX() == KING_COLUMN && start.getY() == BLACK_PIECES_ROW && !hasMoved() &&
                cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].isRook() &&
                !cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
            for (int i = 5; i <= 6; i++) {
                if (cells[i][BLACK_PIECES_ROW].isOccupied()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        if (!white && start.getX() == KING_COLUMN && start.getY() == BLACK_PIECES_ROW && !hasMoved() &&
                cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].isRook() &&
                !cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
            if (cells[1][BLACK_PIECES_ROW].isOccupied()) return false;
            for (int i = 3; i >= 2; i--) {
                if (cells[i][BLACK_PIECES_ROW].isOccupied()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        /* Includes castling but without checking if crossing squares are attacked .*/
        if (!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        if (isWhiteShortCastlingPossible(board, start))
            moves.add(new Move(start, cells[KING_SHORT_COLUMN][WHITE_PIECES_ROW], MoveInfo.WHITE_SHORT_CASTLE));
        if (isWhiteLongCastlingPossible(board, start))
            moves.add(new Move(start, cells[KING_LONG_COLUMN][WHITE_PIECES_ROW], MoveInfo.WHITE_LONG_CASTLE));

        if (isBlackShortCastlingPossible(board, start))
            moves.add(new Move(start, cells[KING_SHORT_COLUMN][BLACK_PIECES_ROW], MoveInfo.BLACK_SHORT_CASTLE));
        if (isBlackLongCastlingPossible(board, start))
            moves.add(new Move(start, cells[KING_LONG_COLUMN][BLACK_PIECES_ROW], MoveInfo.BLACK_LONG_CASTLE));

        for (int first : DELTA) {
            for (int second : DELTA) {
                if (first != 0 || second != 0) {
                    Move firstMove = generateCaptureOrStandard(x + first, y + second, start, cells, white);
                    Move secondMove = generateCaptureOrStandard(x + second, y + first, start, cells, white);

                    if (firstMove != null) moves.add(firstMove);
                    if (secondMove != null) moves.add(secondMove);
                }
            }
        }

        return moves;
    }

    @Override
    public King copy() {
        return new King(white, moves);
    }

    @Override
    public String toSymbol() {
        return white ? "K" : "k";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof King)) return false;
        King king = (King) o;
        return white == king.white && hasMoved() == king.hasMoved();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * (hasMoved() ? 7 : 11);
    }
}
