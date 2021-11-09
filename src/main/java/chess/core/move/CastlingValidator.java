package chess.core.move;

import chess.core.Board;
import chess.core.Cell;
import chess.utilities.GameUtilities;

import static chess.core.move.MoveValidator.isKingInCheck;
import static chess.core.move.MoveValidator.isKingInCheckAfterMove;
import static chess.utilities.PositionConstants.*;

/**
 * Class that is responsible for validating castling moves.
 */
public class CastlingValidator {
    private CastlingValidator() {
        throw new AssertionError();
    }

    /**
     * Checks if castling is available.
     *
     * @param board
     * @param move  to check
     * @return if castling is available
     */
    public static boolean isValid(Board board, Move move) {
        Cell start = move.getStart();
        Cell[][] cells = board.getCells();
        boolean whiteness = move.getStart().getPiece().isWhite();;
        int row = getPiecesRow(whiteness);

        switch (move.getInfo()) {
            case SHORT_CASTLE -> {
                if (isKingInCheck(board, whiteness)) return false;
                for (int i = 5; i <= KING_SHORT_COLUMN; i++) {
                    Move betweenMove = new Move(start, cells[i][row], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, whiteness)) return false;
                }
                return true;
            }
            case LONG_CASTLE -> {
                if (isKingInCheck(board, whiteness)) return false;
                for (int i = 3; i >= KING_LONG_COLUMN; i--) {
                    Move betweenMove = new Move(start, cells[i][row], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, whiteness)) return false;
                }
                return true;
            }
        }
        return false;
    }
}
