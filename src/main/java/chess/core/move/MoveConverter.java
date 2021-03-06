package chess.core.move;

import chess.core.Board;
import chess.core.Cell;
import chess.utilities.GameUtilities;

public class MoveConverter {
    private MoveConverter() {
        throw new AssertionError();
    }

    /**
     * Converts String of type "a2a4", "a7b8q" to Move able to be validated.
     *
     * @return Move converted from String form
     */
    public static Move convert(String move, Board board) {
        move = move.toLowerCase();
        String[] tiles = {"" + move.charAt(0) + move.charAt(1),
                "" + move.charAt(2) + move.charAt(3)};
        GameUtilities.MoveInfo info = null;
        if (move.length() == 5) {
            switch (move.charAt(4)) {
                case 'n' -> info = GameUtilities.MoveInfo.KNIGHT_PROMOTION;
                case 'b' -> info = GameUtilities.MoveInfo.BISHOP_PROMOTION;
                case 'r' -> info = GameUtilities.MoveInfo.ROOK_PROMOTION;
                case 'q' -> info = GameUtilities.MoveInfo.QUEEN_PROMOTION;
            }
        }
        Cell start = Cell.extractCellFromString(tiles[0]);
        Cell end = Cell.extractCellFromString(tiles[1]);

        return linkMove(new Move(start, end, info), board);
    }

    /**
     * @return Move linked to given Board.
     */
    private static Move linkMove(Move move, Board board) {
        Cell startFromBoard = move.getStart().findCell(board.getCells());

        if (startFromBoard.isOccupied()) {
            move.getStart().setPiece(startFromBoard.getPiece().copy());
        } else {
            move.getStart().clear();
        }

        Cell endFromBoard = move.getEnd().findCell(board.getCells());
        if (endFromBoard.isOccupied()) {
            move.getEnd().setPiece(endFromBoard.getPiece().copy());
        } else {
            move.getEnd().clear();
        }
        return move;
    }
}

