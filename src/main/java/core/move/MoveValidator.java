package core.move;

import core.Board;
import core.Cell;
import core.pieces.King;
import core.GameUtilities;
import core.pieces.Pawn;

import java.util.List;
import java.util.Optional;

public class MoveValidator {

    public static boolean isValid(Move move, Board board) {
        Cell start = move.getStart();
        Optional<List<Move>> pseudoLegalMoves = Optional.ofNullable(start.getPiece().calculatePseudoLegalMoves(board, start));

        if (pseudoLegalMoves.isPresent()) {
            List<Move> moves = pseudoLegalMoves.get();
            boolean legal = moves.contains(move);

            if (legal) { // link move to move with description
                Move correspondingMove = moves.get(moves.indexOf(move));
                if (((correspondingMove.getInfo() != GameUtilities.MoveInfo.ROOK_PROMOTION)
                        && (correspondingMove.getInfo() != GameUtilities.MoveInfo.BISHOP_PROMOTION) && (correspondingMove.getInfo() != GameUtilities.MoveInfo.QUEEN_PROMOTION)
                        && (correspondingMove.getInfo() != GameUtilities.MoveInfo.KNIGHT_PROMOTION))) {
                    move.setInfo(moves.get(moves.indexOf(move)).getInfo());
                }
                // checking if move is in legal moves and if own king is not in check after move
                boolean kingNotCheck = !isKingInCheckAfterMove(move, board, start.getPiece().isWhite());
                return kingNotCheck;
            }
            return false;
        }
        return false;
    }

    public static boolean isKingInCheckAfterMove(Move move, Board board, boolean turn) {
        board.executeMove(move);
        boolean check = isKingInCheck(board, turn);
        board.undoMove();
        return check;
    }

    public static boolean isKingInCheck(Board board, boolean turn) { // if turn: checking if white king is in check
        List<Cell> piecesCells = turn ? board.getAliveBlackPiecesCells() : board.getAliveWhitePiecesCells();
        Cell kingCell = turn ? board.getWhiteKingCell() : board.getBlackKingCell();

        for (Cell pieceCell : piecesCells) { // For every piece, we check its possible moves. If there is a Cell that is also position of a king, return true.
            if (pieceCell.getPiece() instanceof King) continue;
            List<Move> moves = pieceCell.getPiece().calculatePseudoLegalMoves(board, pieceCell);
            if (moves.contains(new Move(pieceCell, kingCell))) {
                return true;
            }
        }
        return false;
    }

    public static boolean isKingInCheckmate(Board board, boolean turn) { // if turn: checking if white king is checkmated
        List<Move> kingMoves = turn ? board.getWhiteKingCell().getPiece().calculatePseudoLegalMoves(board, board.getWhiteKingCell()) :
                board.getBlackKingCell().getPiece().calculatePseudoLegalMoves(board, board.getBlackKingCell());
        List<Cell> piecesCell = turn ? board.getAliveWhitePiecesCells() : board.getAliveBlackPiecesCells();

        if (!isKingInCheck(board, turn)) return false;
        /* If king can't move and is in check: return true */
        // if (kingMoves.isEmpty() && isKingInCheck(board, turn)) return true; what if king cant move, is in check but some piece can block?
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

    public static boolean isDraw(Board board) {


        return false;
    }
}
