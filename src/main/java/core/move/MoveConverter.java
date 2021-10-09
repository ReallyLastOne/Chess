package core.move;

import core.Board;
import core.Cell;
import core.GameUtilities;

import static core.Cell.*;

public class MoveConverter {

    /* Converts String of type "a2a4" to Move without Pieces placed. */
    public static Move convert(String move) {
        move = move.toLowerCase();
        String[] tiles = {"" + move.charAt(0) + move.charAt(1),
                "" + move.charAt(2) + move.charAt(3)};
        GameUtilities.MoveInfo info = null;
        if(move.length() == 5) {
            switch (move.charAt(4)) {
                case 'n' -> info = GameUtilities.MoveInfo.KNIGHT_PROMOTION;
                case 'b' -> info = GameUtilities.MoveInfo.BISHOP_PROMOTION;
                case 'r' -> info = GameUtilities.MoveInfo.ROOK_PROMOTION;
                case 'q' -> info = GameUtilities.MoveInfo.QUEEN_PROMOTION;
            }
        }
        Cell start = Cell.extractCellFromString(tiles[0]);
        Cell end = Cell.extractCellFromString(tiles[1]);
        return new Move(start, end, info);
    }

    /** Returns Move linked to given Board. */
    public static Move linkMove(Move move, Board board) {
        Cell startFromBoard = move.getStart().findCell(board.getCells());

        if(!isEmpty(startFromBoard)) {
            move.getStart().setPiece(startFromBoard.getPiece().copy());
        } else {
            move.getStart().clear();
        }

        Cell endFromBoard = move.getEnd().findCell(board.getCells());
        if(!isEmpty(endFromBoard)) {
            move.getEnd().setPiece(endFromBoard.getPiece().copy());
        } else {
            move.getEnd().clear();
        }


        return move;
    }
}
