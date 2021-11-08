package chess.core;

import chess.core.pieces.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

import static chess.utilities.Constants.*;

@Getter
/**
 *  Class that is a representation of single cell in chess board.
 */
public class Cell {
    // column
    private final int x;
    // row
    private final int y;
    @Setter
    private Piece piece;

    public Cell(int x, int y, Piece piece) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) throw new IllegalArgumentException("x=" + x + ", y="
                + y + ", piece=" + piece);
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    /**
     * Returns artificial (new) Cell from given String.
     */
    public static Cell extractCellFromString(String s) {
        return new Cell(COLUMN_TO_INT.get(s.charAt(0)),
                Character.getNumericValue(s.charAt(1)) - 1, null);
    }

    public Cell findCell(Cell[][] cells) {
        return cells[x][y];
    }

    public boolean isOppositeColor(boolean white) {
        return Optional.ofNullable(piece).filter(c -> c.isWhite() != white).isPresent();
    }

    public String toAlgebraicNotation() {
        return INT_TO_COLUMN.get(this.getX()) + "" + (this.getY() + 1);
    }

    @Override
    public String toString() {
        return "Cell{x=" + x + ", y=" + y + ", piece=" + piece + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.getX() && y == cell.getY() && ((cell.piece != null && piece.equals(cell.getPiece()) || cell.getPiece() == null && piece == null));
    }

    @Override
    public int hashCode() {
        int hash = piece != null ? piece.hashCode() : 0;
        hash = hash + (x * 29 + y * 31);
        return hash;
    }

    public Cell copy() {
        if (piece == null) {
            return new Cell(x, y, null);
        }
        return new Cell(x, y, piece.copy());
    }

    public void clear() {
        piece = null;
    }

    public boolean isOccupied() {
        return piece != null;
    }

    public boolean isWhite() {
        return (x + 1) % 2 == (y + 1) % 2;
    }

    public boolean isPawn() {
        return piece instanceof Pawn;
    }

    public boolean isBishop() {
        return piece instanceof Bishop;
    }

    public boolean isKnight() {
        return piece instanceof Knight;
    }

    public boolean isRook() {
        return piece instanceof Rook;
    }

    public boolean isQueen() {
        return piece instanceof Queen;
    }

    public boolean isKing() {
        return piece instanceof King;
    }
}
