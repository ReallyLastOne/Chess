package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;

import java.util.ArrayList;
import java.util.List;

import static chess.core.Board.fitInBoard;
import static chess.utilities.GameUtilities.MoveInfo;

public class Knight extends Piece {
    private static final int LEFT_SHORT = -1;
    private static final int RIGHT_SHORT = 1;
    private static final int UP_LONG = 2;
    private static final int DOWN_LONG = -2;

    private static final int LEFT_LONG = -2;
    private static final int RIGHT_LONG = 2;
    private static final int UP_SHORT = 1;
    private static final int DOWN_SHORT = -1;

    public Knight(boolean white) {
        super(white);
    }

    public Knight(boolean white, int moves) {
        super(white, moves);
    }

    @Override
    public List<Move> calculatePseudoLegalMoves(Board board, Cell start) {
        if (!(start.getPiece().equals(this))) return null;
        int x = start.getX();
        int y = start.getY();
        Cell[][] cells = board.getCells();
        List<Move> moves = new ArrayList<>();

        if (fitInBoard(x + LEFT_LONG, y + UP_SHORT)) {
            if (!cells[x + LEFT_LONG][y + UP_SHORT].isOccupied()) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + UP_SHORT], MoveInfo.STANDARD));
            } else if (cells[x + LEFT_LONG][y + UP_SHORT].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + UP_SHORT], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x + LEFT_SHORT, y + UP_LONG)) {
            if (!cells[x + LEFT_SHORT][y + UP_LONG].isOccupied()) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + UP_LONG], MoveInfo.STANDARD));
            } else if (cells[x + LEFT_SHORT][y + UP_LONG].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + UP_LONG], MoveInfo.CAPTURE));
            }
        }

        if (fitInBoard(x + LEFT_LONG, y + DOWN_SHORT)) {
            if (!cells[x + LEFT_LONG][y + DOWN_SHORT].isOccupied()) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + DOWN_SHORT], MoveInfo.STANDARD));
            } else if (cells[x + LEFT_LONG][y + DOWN_SHORT].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + LEFT_LONG][y + DOWN_SHORT], MoveInfo.CAPTURE));
            }
        }
        if (fitInBoard(x + LEFT_SHORT, y + DOWN_LONG)) {
            if (!cells[x + LEFT_SHORT][y + DOWN_LONG].isOccupied()) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + DOWN_LONG], MoveInfo.STANDARD));
            } else if (cells[x + LEFT_SHORT][y + DOWN_LONG].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + LEFT_SHORT][y + DOWN_LONG], MoveInfo.CAPTURE));
            }
        }
        if (fitInBoard(x + RIGHT_LONG, y + UP_SHORT)) {
            if (!cells[x + RIGHT_LONG][y + UP_SHORT].isOccupied()) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + UP_SHORT], MoveInfo.STANDARD));
            } else if (cells[x + RIGHT_LONG][y + UP_SHORT].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + UP_SHORT], MoveInfo.CAPTURE));
            }
        }
        if (fitInBoard(x + RIGHT_SHORT, y + UP_LONG)) {
            if (!cells[x + RIGHT_SHORT][y + UP_LONG].isOccupied()) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + UP_LONG], MoveInfo.STANDARD));
            } else if (cells[x + RIGHT_SHORT][y + UP_LONG].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + UP_LONG], MoveInfo.CAPTURE));
            }
        }
        if (fitInBoard(x + RIGHT_LONG, y + DOWN_SHORT)) {
            if (!cells[x + RIGHT_LONG][y + DOWN_SHORT].isOccupied()) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + DOWN_SHORT], MoveInfo.STANDARD));
            } else if (cells[x + RIGHT_LONG][y + DOWN_SHORT].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + RIGHT_LONG][y + DOWN_SHORT], MoveInfo.CAPTURE));
            }
        }
        if (fitInBoard(x + RIGHT_SHORT, y + DOWN_LONG)) {
            if (!cells[x + RIGHT_SHORT][y + DOWN_LONG].isOccupied()) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + DOWN_LONG], MoveInfo.STANDARD));
            } else if (cells[x + RIGHT_SHORT][y + DOWN_LONG].isOppositeColor(white)) {
                moves.add(new Move(start, cells[x + RIGHT_SHORT][y + DOWN_LONG], MoveInfo.CAPTURE));
            }
        }

        return moves;
    }

    @Override
    public Knight copy() {
        return new Knight(white);
    }

    @Override
    public String toSymbol() {
        return white ? "N" : "n";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Knight)) return false;
        Knight knight = (Knight) o;
        return white == knight.isWhite();
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 5;
    }
}
