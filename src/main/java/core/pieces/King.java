package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static core.Board.fitInBoard;
import static core.Cell.isEmpty;
import static core.Cell.isOppositeColor;
import static core.GameUtilities.MoveInfo;
import static core.PositionConstants.*;

public class King extends Piece {

    public King(boolean white) {
        super(white);
    }

    public King(boolean white, int moves) {
        this(white);
        this.moves = moves;
    }

    public boolean isWhiteShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* short castle, white*/
        if (white && start.getX() == KING_COLUMN && start.getY() == WHITE_PIECES_ROW && !hasMoved()  &&
                cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() instanceof Rook &&
                !cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
            for (int i = 5; i <= 6; i++) {
                if (!isEmpty(cells[i][WHITE_PIECES_ROW])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isWhiteLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* long castle, white */
        if (white && start.getX() == KING_COLUMN && start.getY() == WHITE_PIECES_ROW && !hasMoved() &&
                cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece() instanceof Rook &&
                !cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
            if (!isEmpty(cells[1][WHITE_PIECES_ROW])) return false;
            for (int i = 3; i >= 2; i--) {
                if (!isEmpty(cells[i][WHITE_PIECES_ROW])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* short castle, black */
        if (!white && start.getX() == KING_COLUMN && start.getY() == BLACK_PIECES_ROW && !hasMoved() &&
                cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() instanceof Rook &&
                !cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
            for (int i = 5; i <= 6; i++) {
                if (!isEmpty(cells[i][BLACK_PIECES_ROW])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* long castle, white */
        if (!white && start.getX() == KING_COLUMN && start.getY() == BLACK_PIECES_ROW && !hasMoved() &&
                cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece() instanceof Rook &&
                !cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
            if (!isEmpty(cells[1][BLACK_PIECES_ROW])) return false;
            for (int i = 3; i >= 2; i--) {
                if (!isEmpty(cells[i][BLACK_PIECES_ROW])) {
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

        // check all 8 moves around king
        if (fitInBoard(x + 1, y)) {
            if (isEmpty(cells[x + 1][y])) {
                moves.add(new Move(start, cells[x + 1][y], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x + 1][y]) && isOppositeColor(cells[x + 1][y], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x - 1, y)) {
            if (isEmpty(cells[x - 1][y])) {
                moves.add(new Move(start, cells[x - 1][y], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x - 1][y]) && isOppositeColor(cells[x - 1][y], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x + 1, y + 1)) {
            if (isEmpty(cells[x + 1][y + 1])) {
                moves.add(new Move(start, cells[x + 1][y + 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x + 1][y + 1]) && isOppositeColor(cells[x + 1][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y + 1], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x - 1, y + 1)) {
            if (isEmpty(cells[x - 1][y + 1])) {
                moves.add(new Move(start, cells[x - 1][y + 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x - 1][y + 1]) && isOppositeColor(cells[x - 1][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y + 1], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x, y + 1)) {
            if (isEmpty(cells[x][y + 1])) {
                moves.add(new Move(start, cells[x][y + 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x][y + 1]) && isOppositeColor(cells[x][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][y + 1], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x + 1, y - 1)) {
            if (isEmpty(cells[x + 1][y - 1])) {
                moves.add(new Move(start, cells[x + 1][y - 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x + 1][y - 1]) && isOppositeColor(cells[x + 1][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y - 1], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x - 1, y - 1)) {
            if (isEmpty(cells[x - 1][y - 1])) {
                moves.add(new Move(start, cells[x - 1][y - 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x - 1][y - 1]) && isOppositeColor(cells[x - 1][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y - 1], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x, y - 1)) {
            if (isEmpty(cells[x][y - 1])) {
                moves.add(new Move(start, cells[x][y - 1], MoveInfo.STANDARD));
            } else if (!isEmpty(cells[x][y - 1]) && isOppositeColor(cells[x][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][y - 1], MoveInfo.CAPTURE));
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
}
