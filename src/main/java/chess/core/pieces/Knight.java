package chess.core.pieces;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.utilities.Constants;

import java.util.ArrayList;
import java.util.List;

import static chess.core.move.Move.generateCaptureOrStandard;

public class Knight extends Piece {
    private static final int[] SHORT = {-1, 1};
    private static final int[] LONG = {-2, 2};

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

        for (int first : SHORT) {
            for (int second : LONG) {
                Move firstMove = generateCaptureOrStandard(cells, x + first, y + second, start, white);
                Move secondMove = generateCaptureOrStandard(cells, x + second, y + first, start, white);

                Constants.addAllNotNull(moves, firstMove, secondMove);
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

    @Override
    public int getEvaluation() {
        return white ? 3 : -3;
    }
}
