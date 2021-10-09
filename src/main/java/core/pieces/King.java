package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static core.Board.*;
import static core.Cell.*;
import static core.move.MoveValidator.*;
import static core.GameUtilities.MoveInfo;

public class King extends Piece {
    public King(boolean white) {
        super(white);
    }

    public King(boolean white, boolean moved) {
        this(white);
        this.moved = moved;
    }

    public boolean isWhiteShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* short castle, white*/
        if(white && start.getX() == 4 && start.getY() == 0 && !moved && !isKingInCheck(board, true) &&
                cells[7][0].getPiece() instanceof Rook && !cells[7][0].getPiece().hasMoved()) {
            for(int i = 5; i <= 6; i++) {
                if(!isEmpty(cells[i][0])) {
                    return false;
                } else {
                    Move move = new Move(start, cells[i][0]);
                    if(isKingInCheckAfterMove(move, board, true)) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isWhiteLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* long castle, white */
        if(white && start.getX() == 4 && start.getY() == 0 && !moved && !isKingInCheck(board, true) &&
                cells[0][0].getPiece() instanceof Rook && !cells[0][0].getPiece().hasMoved()) {
            for(int i = 3; i >= 0; i--) {
                if(!isEmpty(cells[i][0])) {
                    return false;
                } else {
                    Move move = new Move(start, cells[i][0]);
                    if(isKingInCheckAfterMove(move, board, true)) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackShortCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* short castle, black */
        if(!white && start.getX() == 4 && start.getY() == 7 && !moved && !isKingInCheck(board, false) &&
                cells[7][7].getPiece() instanceof Rook && !cells[7][7].getPiece().hasMoved()) {
            for(int i = 5; i <= 6; i++) {
                if(!isEmpty(cells[i][7])) {
                    return false;
                } else {
                    Move move = new Move(start, cells[i][7]);
                    if(isKingInCheckAfterMove(move, board, false)) return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackLongCastlingPossible(Board board, Cell start) {
        Cell[][] cells = board.getCells();
        /* long castle, white */
        if(!white && start.getX() == 4 && start.getY() == 7 && !moved && !isKingInCheck(board, false) &&
                cells[0][7].getPiece() instanceof Rook && !cells[0][7].getPiece().hasMoved()) {
            for(int i = 3; i >= 0; i--) {
                if(!isEmpty(cells[i][7])) {
                    return false;
                } else {
                    Move move = new Move(start, cells[i][7]);
                    if(isKingInCheckAfterMove(move, board, false)) return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        if(!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        if(isWhiteShortCastlingPossible(board, start)) moves.add(new Move(start, cells[6][0], MoveInfo.WHITE_SHORT_CASTLE));
        if(isWhiteLongCastlingPossible(board, start)) moves.add(new Move(start, cells[0][0], MoveInfo.WHITE_LONG_CASTLE));

        if(isBlackShortCastlingPossible(board, start)) moves.add(new Move(start, cells[6][7], MoveInfo.BLACK_SHORT_CASTLE));
        if(isBlackLongCastlingPossible(board, start)) moves.add(new Move(start, cells[0][7], MoveInfo.BLACK_LONG_CASTLE));

        // refactor
        if(fitInBoard(x + 1, y)) {
            if(isEmpty(cells[x + 1][y])) {
                moves.add(new Move(start, cells[x + 1][y]));
            } else if (!isEmpty(cells[x + 1][y]) && isOppositeColor(cells[x + 1][y], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y]));
            }
        }

        if(fitInBoard(x - 1, y)) {
            if(isEmpty(cells[x - 1][y])) {
                moves.add(new Move(start, cells[x - 1][y]));
            } else if (!isEmpty(cells[x - 1][y]) && isOppositeColor(cells[x - 1][y], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y]));
            }
        }

        if(fitInBoard(x + 1, y + 1)) {
            if(isEmpty(cells[x + 1][y + 1])) {
                moves.add(new Move(start, cells[x + 1][y + 1]));
            } else if (!isEmpty(cells[x + 1][y + 1]) && isOppositeColor(cells[x + 1][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y + 1]));
            }
        }

        if(fitInBoard(x - 1, y + 1)) {
            if(isEmpty(cells[x - 1][y + 1])) {
                moves.add(new Move(start, cells[x - 1][y + 1]));
            } else if (!isEmpty(cells[x - 1][y + 1]) && isOppositeColor(cells[x - 1][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y + 1]));
            }
        }

        if(fitInBoard(x, y + 1)) {
            if(isEmpty(cells[x][y + 1])) {
                moves.add(new Move(start, cells[x][y + 1]));
            } else if (!isEmpty(cells[x][y + 1]) && isOppositeColor(cells[x][y + 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][y + 1]));
            }
        }

        if(fitInBoard(x + 1, y - 1)) {
            if(isEmpty(cells[x + 1][y - 1])) {
                moves.add(new Move(start, cells[x + 1][y - 1]));
            } else if (!isEmpty(cells[x + 1][y - 1]) && isOppositeColor(cells[x + 1][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x + 1][y - 1]));
            }
        }

        if(fitInBoard(x - 1, y - 1)) {
            if(isEmpty(cells[x - 1][y - 1])) {
                moves.add(new Move(start, cells[x - 1][y - 1]));
            } else if (!isEmpty(cells[x - 1][y - 1]) && isOppositeColor(cells[x - 1][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x - 1][y - 1]));
            }
        }

        if(fitInBoard(x, y - 1)) {
            if(isEmpty(cells[x][y - 1])) {
                moves.add(new Move(start, cells[x][y - 1]));
            } else if (!isEmpty(cells[x][y - 1]) && isOppositeColor(cells[x][y - 1], start.getPiece().isWhite())) {
                moves.add(new Move(start, cells[x][y - 1]));
            }
        }

        return moves;
    }

    @Override
    public King copy() {
        return new King(white, moved);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof King)) return false;
        King king = (King) o;
        return white == king.isWhite() && moved == king.hasMoved();
    }
}
