package chess.core.move;

import chess.core.Cell;
import lombok.Getter;
import lombok.Setter;
import chess.utilities.GameUtilities;

import static chess.utilities.Constants.INT_TO_COLUMN;

@Getter
@Setter
public class Move {
    private Cell start;
    private Cell end;
    private GameUtilities.MoveInfo info;

    public Move(Cell start, Cell end) {
        this.start = start.copy();
        this.end = end.copy();
    }

    public Move(Cell start, Cell end, GameUtilities.MoveInfo info) {
        this(start, end);
        this.info = info;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return start.equals(move.getStart()) && end.equals(move.getEnd());
    }

    @Override
    public String toString() {
        return INT_TO_COLUMN.get(start.getX()) + "" + (start.getY() + 1) + "" + INT_TO_COLUMN.get(end.getX()) + ""
                + (end.getY() + 1) + info.getSymbolPromotion();
    }

    public Move copy() {
        return new Move(start.copy(), end.copy(), info);
    }
}
