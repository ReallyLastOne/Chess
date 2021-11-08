package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static chess.utilities.Constants.GRID_SIZE;
import static chess.utilities.Constants.promotions;
import static chess.utilities.GameUtilities.MoveInfo;
import static chess.utilities.PositionConstants.*;

public class Pawn extends Piece {
    private static final int[] dx = {-1, 0, 1};


    @Setter
    /** Indicates if a pawn can be enpassanted.  */
    private boolean enPassant;
    @Getter
    /** Number representing direction of pawn movement. */
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
        Cell[][] cells = board.getCells();

        // implementations can be done better
        List<Move> moves = new ArrayList<>(calculateTwoForward(start, cells));
        moves.addAll(calculateCaptures(start, cells));
        moves.addAll(calculateOneForward(start, cells));

        return moves;
    }

    private List<Move> calculateCaptures(Cell start, Cell[][] cells) {
        List<Move> moves = new ArrayList<>();

        int x = start.getX();
        int y = start.getY();

        /* if stands at A rank then can only capture towards H rank */
        int destinationY = y + forwardCount;
        if (x == 0) {
            if (cells[x + 1][destinationY].isOccupied() && cells[x + 1][destinationY].isOppositeColor(start.getPiece().isWhite())) {
                /* add promotion to legal moves */
                if ((white && destinationY == BLACK_PIECES_ROW) || (!white && destinationY == WHITE_PIECES_ROW)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x + 1][destinationY], info)));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* en passant */
            if ((white && y == 4) || (!white && y == 3)) {
                if (!cells[x + 1][destinationY].isOccupied() && cells[x + 1][y].isPawn() &&
                        ((Pawn) cells[x + 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
        }
        /* if stands at H rank then can only capture towards A rank */
        else if (x == GRID_SIZE - 1) {
            if (cells[x - 1][destinationY].isOccupied() && cells[x - 1][destinationY].isOppositeColor(start.getPiece().isWhite())) {
                if ((white && destinationY == BLACK_PIECES_ROW) || (!white && destinationY == WHITE_PIECES_ROW)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x - 1][destinationY], info)));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* en passant */
            if ((white && y == 4) || (!white && y == 3)) {
                if (!cells[x - 1][destinationY].isOccupied() && cells[x - 1][y].isPawn() &&
                        ((Pawn) cells[x - 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
        }

        /* common moves for black and white pawns */
        else {
            /* capture to the right */
            if (cells[x + 1][destinationY].isOccupied() && cells[x + 1][destinationY].isOppositeColor(start.getPiece().isWhite())) {
                if ((white && destinationY == BLACK_PIECES_ROW) || (!white && destinationY == WHITE_PIECES_ROW)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x + 1][destinationY], info)));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* capture to the left */
            if (cells[x - 1][destinationY].isOccupied() && cells[x - 1][destinationY].isOppositeColor(start.getPiece().isWhite())) {
                if ((white && destinationY == BLACK_PIECES_ROW) || (!white && destinationY == WHITE_PIECES_ROW)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x - 1][destinationY], info)));
                } else { /* simply capture */
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* en passant to left */
            if ((white && y == 4) || (!white && y == 3)) {
                if (!cells[x - 1][destinationY].isOccupied() && cells[x - 1][y].isPawn() &&
                        ((Pawn) cells[x - 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
            /* en passant to right */
            if ((white && y == 4) || (!white && y == 3)) {
                if (!cells[x + 1][destinationY].isOccupied() && cells[x + 1][y].isPawn() &&
                        ((Pawn) cells[x + 1][y].getPiece()).canBeEnPassanted()) {
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
        }
        return moves;
    }

    private List<Move> calculateOneForward(Cell start, Cell[][] cells) {
        List<Move> moves = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();

        int destinationY = y + forwardCount;
        if (!cells[x][destinationY].isOccupied()) {
            if ((white && destinationY == BLACK_PIECES_ROW) || (!white && destinationY == WHITE_PIECES_ROW)) {
                promotions.forEach(info -> moves.add(new Move(start, cells[x][destinationY], info)));
            } else { /* simply one forward */
                moves.add(new Move(start, cells[x][destinationY], MoveInfo.PAWN_MOVE));
            }
        }
        return moves;
    }

    private List<Move> calculateTwoForward(Cell start, Cell[][] cells) {
        List<Move> moves = new ArrayList<>();

        int x = start.getX();
        int y = start.getY();

        int destinationY = y + 2 * forwardCount;
        int betweenY = y + forwardCount;
        /* if pawn is white and at 2nd rank, 2 cells forward */
        if (y == WHITE_PAWN_ROW && white) {
            if (!cells[x][betweenY].isOccupied() && !cells[x][destinationY].isOccupied()) {
                moves.add(new Move(start, cells[x][destinationY], MoveInfo.TWO_FORWARD));
            }
        }
        /* if pawn is black and is at 6th rank, 2 cells forward */
        else if (y == BLACK_PAWN_ROW && !white) {
            if (!cells[x][betweenY].isOccupied() && !cells[x][destinationY].isOccupied()) {
                moves.add(new Move(start, cells[x][destinationY], MoveInfo.TWO_FORWARD));
            }
        }

        return moves;
    }

    @Override
    public Pawn copy() {
        return new Pawn(white);
    }

    @Override
    public String toSymbol() {
        return white ? "P" : "p";
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
        return white == pawn.white && hasMoved() == pawn.hasMoved();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * (hasMoved() ? 13 : 17);
    }
}
