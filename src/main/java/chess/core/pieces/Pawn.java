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

        List<Move> moves = new ArrayList<>(calculateTwoForward(cells, start));
        moves.addAll(calculateCaptures(start, cells));
        moves.addAll(calculateOneForward(cells, start));

        return moves;
    }

    private List<Move> calculateCaptures(Cell start, Cell[][] cells) {
        List<Move> moves = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();
        int destinationY = y + forwardCount;

        if (x != 0) {
            /* capture to left */
            if (canCaptureAt(cells, x - 1, destinationY, start)) {
                if (standsAtPromotionRank(destinationY)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x - 1][destinationY], info)));
                } else {
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* en passant to left */
            else if (standsAtEnPassantRank(y)) {
                if (canCaptureEnPassantAt(cells, x - 1, destinationY, y)) {
                    moves.add(new Move(start, cells[x - 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
        }
        if (x != GRID_SIZE - 1) {
            /* capture to right */
            if (canCaptureAt(cells, x + 1, destinationY, start)) {
                if (standsAtPromotionRank(destinationY)) {
                    promotions.forEach(info -> moves.add(new Move(start, cells[x + 1][destinationY], info)));
                } else {
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.CAPTURE));
                }
            }
            /* en passant to right */
            else if (standsAtEnPassantRank(y)) {
                if (canCaptureEnPassantAt(cells, x + 1, destinationY, y)) {
                    moves.add(new Move(start, cells[x + 1][destinationY], MoveInfo.EN_PASSANT));
                }
            }
        }

        return moves;
    }

    private List<Move> calculateOneForward(Cell[][] cells, Cell start) {
        List<Move> moves = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();

        int destinationY = y + forwardCount;
        if (!cells[x][destinationY].isOccupied()) {
            if (standsAtPromotionRank(destinationY)) {
                promotions.forEach(info -> moves.add(new Move(start, cells[x][destinationY], info)));
            } else {
                moves.add(new Move(start, cells[x][destinationY], MoveInfo.PAWN_MOVE));
            }
        }
        return moves;
    }

    private List<Move> calculateTwoForward(Cell[][] cells, Cell start) {
        List<Move> moves = new ArrayList<>();
        int x = start.getX();
        int y = start.getY();

        int destinationY = y + 2 * forwardCount;
        int betweenY = y + forwardCount;
        if (standsAtSecondRank(y) && canMoveTwoForward(cells, x, betweenY, destinationY)) {
            moves.add(new Move(start, cells[x][destinationY], MoveInfo.TWO_FORWARD));
        }

        return moves;
    }

    private boolean canMoveTwoForward(Cell[][] cells, int x, int betweenY, int destinationY) {
        return !cells[x][betweenY].isOccupied() && !cells[x][destinationY].isOccupied();
    }

    private boolean canCaptureAt(Cell[][] cells, int x, int destinationY, Cell start) {
        return cells[x][destinationY].isOccupied() && cells[x][destinationY].isOppositeColor(start.getPiece().isWhite());
    }

    private boolean canCaptureEnPassantAt(Cell[][] cells, int x, int destinationY, int y) {
        return !cells[x][destinationY].isOccupied() && cells[x][y].isPawn() &&
                ((Pawn) cells[x][y].getPiece()).canBeEnPassanted();
    }

    /* Checks if Pawn stands at rank available to move two forward */
    private boolean standsAtSecondRank(int y) {
        return (y == BLACK_PAWN_ROW && !white) || (y == WHITE_PAWN_ROW && white);
    }

    /* Checks if Pawn stands at rank available to en passant capture */
    private boolean standsAtEnPassantRank(int y) {
        return (white && y == 4) || (!white && y == 3);
    }

    /* Checks if Pawn stands at rank available to promote in next move */
    private boolean standsAtPromotionRank(int y) {
        return (white && y == BLACK_PIECES_ROW) || (!white && y == WHITE_PIECES_ROW);
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

    @Override
    public int getEvaluation() {
        return white ? 1 : -1;
    }
}
