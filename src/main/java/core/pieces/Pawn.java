package core.pieces;

import core.Board;
import core.Cell;
import core.move.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static core.GameUtilities.MoveInfo;

import static core.Cell.*;
import static core.PositionConstants.*;

public class Pawn extends Piece {
    @Setter
    private boolean enPassant;
    @Getter
    private final int forwardCount;

    public Pawn(boolean white) {
        super(white);
        forwardCount = white ? 1 : -1;
    }

    public Pawn(boolean white, int moves) {
        super(white, moves);
        forwardCount = white ? 1 : -1;
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        if (!(start.getPiece().equals(this))) return null;

        int x = start.getX();
        int y = start.getY();

        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        if (white) {
            /* if pawn is white and at 2nd rank, 2 cells forward */
            if (y == WHITE_PAWN_ROW) {
                if (isEmpty(cells[x][y + forwardCount]) && isEmpty(cells[x][y + 2 * forwardCount])) {
                    moves.add(new Move(start, cells[x][y + 2 * forwardCount], MoveInfo.TWO_FORWARD));
                }
            }
        }

        if (!white) {
            /* if pawn is black and is at 6th rank, 2 cell forward */
            if (y == BLACK_PAWN_ROW) {
                if (isEmpty(cells[x][y + forwardCount]) && isEmpty(cells[x][y + 2 * forwardCount])) {
                    moves.add(new Move(start, cells[x][y + 2 * forwardCount], MoveInfo.TWO_FORWARD));
                }
            }
        }

        /* if stands at A rank then can only capture towards H rank */
        if (x == 0) {
            if (!isEmpty(cells[x + 1][y + forwardCount]) && isOppositeColor(cells[x + 1][y + forwardCount], start.getPiece().isWhite())) {
                /* add promotion to legal moves */
                if ((white && y + forwardCount == BLACK_PIECES_ROW) || (!white && y + forwardCount == WHITE_PIECES_ROW)) {
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.KNIGHT_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.BISHOP_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.ROOK_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.QUEEN_PROMOTION));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.CAPTURE));
                }
            }
            /* en passant */
            if ((white && y == 4) || (!white && y == 3)) {
                if (isEmpty(cells[x + 1][y + forwardCount]) && cells[x + 1][y].getPiece() instanceof Pawn &&
                        ((Pawn) cells[x + 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.EN_PASSANT));
                }
            }
        }
        /* if stands at H rank then can only capture towards A rank */
        else if (x == 7) {
            if (!isEmpty(cells[x - 1][y + forwardCount]) && isOppositeColor(cells[x - 1][y + forwardCount], start.getPiece().isWhite())) {
                if ((white && y + forwardCount == BLACK_PIECES_ROW) || (!white && y + forwardCount == WHITE_PIECES_ROW)) {
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.KNIGHT_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.BISHOP_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.ROOK_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.QUEEN_PROMOTION));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.CAPTURE));
                }
            }
            /* en passant */
            if ((white && y == 4) || (!white && y == 3)) {
                if (isEmpty(cells[x - 1][y + forwardCount]) && cells[x - 1][y].getPiece() instanceof Pawn &&
                        ((Pawn) cells[x - 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.EN_PASSANT));
                }
            }
        }
        /* common moves for black and white pawns */
        else {
            /* capture to the right */
            if (!isEmpty(cells[x + 1][y + forwardCount]) && isOppositeColor(cells[x + 1][y + forwardCount], start.getPiece().isWhite())) {
                if ((white && y + forwardCount == BLACK_PIECES_ROW) || (!white && y + forwardCount == WHITE_PIECES_ROW)) {
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.KNIGHT_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.BISHOP_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.ROOK_PROMOTION));
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.QUEEN_PROMOTION));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.CAPTURE));
                }
            }
            /* capture to the left */
            if (!isEmpty(cells[x - 1][y + forwardCount]) && isOppositeColor(cells[x - 1][y + forwardCount], start.getPiece().isWhite())) {
                if ((white && y + forwardCount == BLACK_PIECES_ROW) || (!white && y + forwardCount == WHITE_PIECES_ROW)) {
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.KNIGHT_PROMOTION));
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.BISHOP_PROMOTION));
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.ROOK_PROMOTION));
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.QUEEN_PROMOTION));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.CAPTURE));
                }
            }
            /* en passant to left */
            if ((white && y == 4) || (!white && y == 3)) {
                if (isEmpty(cells[x - 1][y + forwardCount]) && cells[x - 1][y].getPiece() instanceof Pawn &&
                        ((Pawn) cells[x - 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x - 1][y + forwardCount], MoveInfo.EN_PASSANT));
                }
            }
            /* en passant to right */
            if ((white && y == 4) || (!white && y == 3)) {
                if (isEmpty(cells[x + 1][y + forwardCount]) && cells[x + 1][y].getPiece() instanceof Pawn &&
                        ((Pawn) cells[x + 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x + 1][y + forwardCount], MoveInfo.EN_PASSANT));
                }
            }
        }

        /* one cell forward */
        if (isEmpty(cells[x][y + forwardCount])) {
            if ((white && y + forwardCount == BLACK_PIECES_ROW) || (!white && y + forwardCount == WHITE_PIECES_ROW)) {
                moves.add(new Move(start, cells[x][y + forwardCount], MoveInfo.KNIGHT_PROMOTION));
                moves.add(new Move(start, cells[x][y + forwardCount], MoveInfo.BISHOP_PROMOTION));
                moves.add(new Move(start, cells[x][y + forwardCount], MoveInfo.ROOK_PROMOTION));
                moves.add(new Move(start, cells[x][y + forwardCount], MoveInfo.QUEEN_PROMOTION));
            } else { /* simply one forward */
                moves.add(new Move(start, cells[x][y + forwardCount], MoveInfo.PAWN_MOVE));
            }
        }

        return moves;
    }

    @Override
    public Pawn copy() {
        return new Pawn(white);
    }

    public boolean canBeEnPassanted() {
        return enPassant;
    }

    @Override
    public String toString() {
        return "Pawn{white=" + white + ", canBeEnpassanted=" + enPassant + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Pawn)) return false;
        Pawn pawn = (Pawn) o;
        return white == pawn.isWhite() && hasMoved() == pawn.hasMoved();
    }
}
