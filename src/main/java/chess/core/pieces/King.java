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

    private Move generateLongCastling(Cell[][] cells, Cell start) {
        boolean whiteness = start.getPiece().isWhite();
        int row = getPiecesRow(whiteness);

        if (isValidState(cells, ROOK_QUEENSIDE_COLUMN, row, start)) {
            if (cells[1][row].isOccupied()) return null;
            for (int i = 3; i >= KING_LONG_COLUMN; i--) {
                if (cells[i][row].isOccupied()) {
                    return null;
                }
            }
            return new Move(start, cells[KING_LONG_COLUMN][row], MoveInfo.LONG_CASTLE);
        }
        return null;
    }

    private Move generateShortCastling(Cell[][] cells, Cell start) {
        boolean whiteness = start.getPiece().isWhite();
        int row = getPiecesRow(whiteness);

        if (isValidState(cells, ROOK_KINGSIDE_COLUMN, row, start)) {
            for (int i = 5; i <= KING_SHORT_COLUMN; i++) {
                if (cells[i][row].isOccupied()) {
                    return null;
                }
            }
            return new Move(start, cells[KING_SHORT_COLUMN][row], MoveInfo.SHORT_CASTLE);
        }
        return null;
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        /* Includes castling but without checking if crossing squares are attacked .*/
        if (!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        Move shortCastle = generateShortCastling(cells, start);
        Move longCastle = generateLongCastling(cells, start);
        if (shortCastle != null) moves.add(shortCastle);
        if (longCastle != null) moves.add(longCastle);

        for (int first : DELTA) {
            for (int second : DELTA) {
                if (first != 0 || second != 0) {
                    Move firstMove = generateCaptureOrStandard(cells, x + first, y + second, start, white);
                    Move secondMove = generateCaptureOrStandard(cells, x + second, y + first, start, white);

                    if (firstMove != null) moves.add(firstMove);
                    if (secondMove != null) moves.add(secondMove);
                }
            }
        }

        return moves;
    }

    /* Checks if king and rook are at valid positions and hasn't moved yet. */
    private boolean isValidState(Cell[][] cells, int rookColumn, int piecesRow, Cell start) {
        return start.getX() == KING_COLUMN && start.getY() == piecesRow && !hasMoved() &&
                cells[rookColumn][piecesRow].isRook() &&
                !cells[rookColumn][piecesRow].getPiece().hasMoved();
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
