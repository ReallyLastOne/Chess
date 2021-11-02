package chess.core.move;

import chess.core.Board;
import chess.core.Cell;
import chess.core.pieces.King;
import chess.core.GameUtilities;

import java.util.List;
import java.util.Optional;

public class MoveValidator {
    /**
     * API to move verification for standard user move flow. Also links move to given board.
     *
     * @param move  move to be verified
     * @param board the board on which move has to be performed
     * @return if move is available for given board
     */
    public static boolean isValid(Move move, Board board) {
        Cell start = move.getStart();
        Optional<List<Move>> pseudoLegalMoves = Optional.ofNullable(start.getPiece().calculatePseudoLegalMoves(board, start));

        if (pseudoLegalMoves.isPresent()) {
            List<Move> moves = pseudoLegalMoves.get();
            boolean legal = moves.contains(move);

            if (legal) { // link move to move with description
                Move correspondingMove = moves.get(moves.indexOf(move));
                // if move is promotion, then set new info
                if (((correspondingMove.getInfo() != GameUtilities.MoveInfo.ROOK_PROMOTION)
                        && (correspondingMove.getInfo() != GameUtilities.MoveInfo.BISHOP_PROMOTION) && (correspondingMove.getInfo() != GameUtilities.MoveInfo.QUEEN_PROMOTION)
                        && (correspondingMove.getInfo() != GameUtilities.MoveInfo.KNIGHT_PROMOTION))) {
                    move.setInfo(moves.get(moves.indexOf(move)).getInfo());
                }

                // if move is castle, check all between squares
                if (move.getInfo() == GameUtilities.MoveInfo.WHITE_LONG_CASTLE || move.getInfo() == GameUtilities.MoveInfo.WHITE_SHORT_CASTLE
                        || move.getInfo() == GameUtilities.MoveInfo.BLACK_LONG_CASTLE || move.getInfo() == GameUtilities.MoveInfo.BLACK_SHORT_CASTLE) {
                    return CastlingValidator.isValid(board, move);
                }

                // checking if move is in legal moves and if own king is not in check after move
                boolean kingNotCheck = !isKingInCheckAfterMove(move, board, start.getPiece().isWhite());
                return kingNotCheck;
            }
            return false;
        }
        return false;
    }

    /**
     * Checks if king is in check after specific Move at given Board.
     *
     * @param move  valid move to be executed
     * @param board the board to be verified
     * @param turn  which king we want to check
     * @return if king is in check after specific move for given board
     */
    public static boolean isKingInCheckAfterMove(Move move, Board board, boolean turn) {
        board.executeMove(move);
        boolean check = isKingInCheck(board, turn);
        board.undoMove();
        return check;
    }

    /**
     * Checks if king is in check at given board.
     *
     * @param board the board to be verified
     * @param turn  which king we want to check
     * @return if king is in check for given board
     */
    public static boolean isKingInCheck(Board board, boolean turn) {

        List<Cell> piecesCells = turn ? board.getAliveBlackPiecesCells() : board.getAliveWhitePiecesCells();
        Cell kingCell = turn ? board.getWhiteKingCell() : board.getBlackKingCell();
        for (Cell pieceCell : piecesCells) { // For every piece, we check its possible moves. If there is a Cell that is also position of a king, return true.
            List<Move> moves = pieceCell.getPiece().calculatePseudoLegalMoves(board, pieceCell);
            if (moves.contains(new Move(pieceCell, kingCell))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if king is in checkmate at given Board.
     *
     * @param board the board to be verified
     * @param turn  which king we want to check
     * @return if king is in checkmate for given board
     */
    public static boolean isKingInCheckmate(Board board, boolean turn) {
        if (!isKingInCheck(board, turn)) return false;
        List<Move> kingMoves = turn ? board.getWhiteKingCell().getPiece().calculatePseudoLegalMoves(board, board.getWhiteKingCell()) :
                board.getBlackKingCell().getPiece().calculatePseudoLegalMoves(board, board.getBlackKingCell());
        List<Cell> piecesCell = turn ? board.getAliveWhitePiecesCells() : board.getAliveBlackPiecesCells();

        /* Check if king can move away from check */
        for (Move x : kingMoves) {
            if (!isKingInCheckAfterMove(x, board, board.isTurn())) return false;
        }

        /* Check if any piece can block out check */
        for (Cell cell : piecesCell) {
            if (cell.getPiece() instanceof King) continue;
            List<Move> moves = cell.getPiece().calculatePseudoLegalMoves(board, cell);
            for (Move move : moves) {
                if (!isKingInCheckAfterMove(move, board, turn)) return false;
            }
        }

        return true;
    }
}
