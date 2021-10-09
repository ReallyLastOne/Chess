package core;

import core.pieces.Piece;
import lombok.Getter;
import lombok.Setter;

import static utilities.Constants.*;

@Getter
/** Class that is a representation of single cell in chess board. */
public class Cell {
    // column
    private final int x;
    // row
    private final int y;
    @Setter
    private Piece piece;

    public Cell(int x, int y, Piece piece) {
        if(x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) throw new IllegalArgumentException("x=" + x + ", y="
                + y + ", piece=" + piece);
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    /** Returns artificial (new) Cell from given String. */
    public static Cell extractCellFromString(String s) {
        return new Cell(COLUMN_TO_INT.get(s.charAt(0)),
                 Character.getNumericValue(s.charAt(1)) - 1, null);
    }

    /** Returns Cell that is in same position, always present. */
    public Cell findCell(Cell[][] cells) {
        return cells[x][y];
    }


    public static boolean isEmpty(Cell cell) {
        return cell.getPiece() == null;
    }

    public static boolean isOppositeColor(Cell cell, boolean white) {
        return cell.getPiece().isWhite() != white;
    }

    public boolean areInSamePosition(Cell cell) {
        return this.getX() == cell.getX() && this.getY() == cell.getY();
    }

    @Override
    public String toString() {
        return "Cell{x=" + x + ", y=" + y + ", piece=" + piece + "}";
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(!(o instanceof Cell)) return false;
        Cell cell = (Cell) o;
        return x == cell.getX() && y == cell.getY() && ((cell.piece != null && piece.equals(cell.getPiece()) || cell.getPiece() == null && piece == null));
    }

    public Cell copy() {
        if(piece == null) {
            return new Cell(x, y, null);
        }
        return new Cell(x, y, piece.copy());
    }

    /* Deletes current Piece occupying this Cell. */
    public void clear() {
        piece = null;
    }

    public boolean isOccupied() {
        return piece != null;
    }
}
