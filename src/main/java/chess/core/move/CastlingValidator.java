package chess.core.move;

import chess.core.Board;
import chess.core.Cell;
import chess.utilities.GameUtilities;

import static chess.utilities.PositionConstants.BLACK_PIECES_ROW;
import static chess.utilities.PositionConstants.WHITE_PIECES_ROW;
import static chess.core.move.MoveValidator.isKingInCheck;
import static chess.core.move.MoveValidator.isKingInCheckAfterMove;

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
        switch (move.getInfo()) {
            case WHITE_SHORT_CASTLE -> {
                if (isKingInCheck(board, true)) return false;
                for (int i = 5; i <= 6; i++) {
                    Move betweenMove = new Move(start, cells[i][WHITE_PIECES_ROW], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, true)) return false;
                }
                return true;
            }
            case WHITE_LONG_CASTLE -> {
                if (isKingInCheck(board, true)) return false;
                for (int i = 3; i >= 2; i--) {
                    Move betweenMove = new Move(start, cells[i][WHITE_PIECES_ROW], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, true)) return false;
                }
                return true;
            }
            case BLACK_SHORT_CASTLE -> {
                if (isKingInCheck(board, false)) return false;
                for (int i = 5; i <= 6; i++) {
                    Move betweenMove = new Move(start, cells[i][BLACK_PIECES_ROW], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, false)) return false;

                }
                return true;
            }
            case BLACK_LONG_CASTLE -> {
                if (isKingInCheck(board, false)) return false;
                for (int i = 3; i >= 2; i--) {
                    Move betweenMove = new Move(start, cells[i][BLACK_PIECES_ROW], GameUtilities.MoveInfo.STANDARD);
                    if (isKingInCheckAfterMove(betweenMove, board, false)) return false;
                }

                return true;
            }
        }
        return false;
    }
}
