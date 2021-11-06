package chess.core.move.executor;

import chess.core.Board;
import chess.core.Cell;
import chess.core.move.Move;
import chess.core.pieces.Pawn;
import chess.core.pieces.Piece;

public class StandardExecutor implements Executor {
    @Override
    public void executeMove(Board board, Move move) {
        Cell[][] cells = board.getCells();
        Cell start = move.getStart();
        Cell end = move.getEnd();
        switch (move.getInfo()) {
            case STANDARD, PAWN_MOVE, CAPTURE -> {
                putPieceOnDestinationCell(cells, start.getPiece(), end, true);
                cells[start.getX()][start.getY()].clear();
            }
            case EN_PASSANT -> {
                putPieceOnDestinationCell(cells, start.getPiece(), end, true);
                cells[start.getX()][start.getY()].clear();
                cells[end.getX()][start.getY()].clear();
            }
            case TWO_FORWARD -> {
                ((Pawn) start.getPiece()).setEnPassant(true);
                putPieceOnDestinationCell(cells, start.getPiece(), end, true);
                cells[start.getX()][start.getY()].clear();
            }
        }
    }

    @Override
    public void undoMove(Board board) {
        Cell[][] cells = board.getCells();
        Move lastMove = board.getLastMove();
        Cell start = lastMove.getStart();
        Cell end = lastMove.getEnd();

        switch (lastMove.getInfo()) {
            case STANDARD, PAWN_MOVE, CAPTURE -> {
                putPieceOnDestinationCell(cells, start.getPiece(), start, false);
                cells[end.getX()][end.getY()].setPiece(end.getPiece());
            }
            case EN_PASSANT -> {
                putPieceOnDestinationCell(cells, start.getPiece(), start, false);
                cells[end.getX()][start.getY()].setPiece(new Pawn(!start.getPiece().isWhite()));
                ((Pawn) cells[end.getX()][start.getY()].getPiece()).setEnPassant(true);
                cells[end.getX()][end.getY()].clear();
            }
            case TWO_FORWARD -> {
                putPieceOnDestinationCell(cells, start.getPiece(), start, false);
                ((Pawn) cells[start.getX()][start.getY()].getPiece()).setEnPassant(false);
                cells[end.getX()][end.getY()].clear();
            }
        }
    }

    private void putPieceOnDestinationCell(Cell[][] cells, Piece piece, Cell destination, boolean incrementation) {
        cells[destination.getX()][destination.getY()].setPiece(piece);
        if (incrementation) {
            cells[destination.getX()][destination.getY()].getPiece().increaseMoves();
        } else {
            cells[destination.getX()][destination.getY()].getPiece().decreaseMoves();
        }
    }
}
