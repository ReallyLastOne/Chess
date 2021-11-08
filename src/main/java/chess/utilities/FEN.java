package chess.utilities;

import chess.core.Board;
import chess.core.Cell;
import chess.core.pieces.Pawn;
import chess.core.pieces.Piece;

import static chess.core.Board.fitInBoard;
import static chess.utilities.Constants.COLUMN_TO_INT;
import static chess.utilities.Constants.GRID_SIZE;
import static chess.utilities.PositionConstants.*;

/**
 * Class that provides static methods to calculate Forsyth–Edwards Notation from given Board and to construct {@link Board}
 * from given String.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation</a>
 */
public class FEN {
    /**
     * Basic number of moves assigned to a piece when castling is not possible.
     */
    public static final int MOVES = 10;

    private FEN() {
        throw new AssertionError();
    }

    public static String calculatePiecePlacement(Board board) {
        Cell[][] cells = board.getCells();

        String piecePlacement = "";
        int freeCount = 0;
        for (int i = GRID_SIZE - 1; i >= 0; i--) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (cells[j][i].isOccupied()) {
                    if (freeCount != 0) {
                        piecePlacement += freeCount;
                        freeCount = 0;
                    }
                    piecePlacement += Board.convertPieceToSymbol(cells[j][i].getPiece());
                } else {
                    freeCount += 1;
                }
            }
            if (freeCount != 0) {
                piecePlacement += freeCount;
                freeCount = 0;
            }
            piecePlacement += "/";
        }
        piecePlacement = piecePlacement.substring(0, piecePlacement.length() - 1);
        return piecePlacement;
    }


    public static String from(Board board) {
        String enPassantCell = calculateEnPassantCell(board);

        return calculatePiecePlacement(board) + " " + (board.isTurn() ? "w" : "b") + " " + modifyCastlingCells(board) + " " +
                enPassantCell + " " + board.getHalfmoves() + " " + board.getFullmoves();
    }

    /* Note: wikipedia (https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation) says that en passant cell
     * is recorded regardless of whether there is a pawn in position to make an en passant capture but some chess program
     * doesn't. (e.g. lichess.org or python chess library) I do since it is easier to test basing on these programs. */

    /**
     * @return algebraic notation of cell that can be enpassanted.
     */
    private static String calculateEnPassantCell(Board board) {
        String enPassantCell = "-";
        Cell[][] cells = board.getCells();
        if (board.getLastMove() != null && board.getLastMove().getInfo() == GameUtilities.MoveInfo.TWO_FORWARD) {
            Cell end = board.getLastMove().getEnd();

            if (fitInBoard(end.getX() + 1, end.getY())) {
                if (cells[end.getX() + 1][end.getY()].isPawn() &&
                        cells[end.getX() + 1][end.getY()].getPiece().isWhite() != cells[end.getX()][end.getY()].getPiece().isWhite()) {
                    int forward = ((Pawn) cells[end.getX()][end.getY()].getPiece()).getForwardCount();
                    enPassantCell = cells[end.getX()][end.getY() - forward].toAlgebraicNotation();
                }
            }
            if (fitInBoard(end.getX() - 1, end.getY())) {
                if (cells[end.getX() - 1][end.getY()].isPawn() &&
                        cells[end.getX() - 1][end.getY()].getPiece().isWhite() != cells[end.getX()][end.getY()].getPiece().isWhite()) {
                    int forward = ((Pawn) cells[end.getX()][end.getY()].getPiece()).getForwardCount();
                    enPassantCell = cells[end.getX()][end.getY() - forward].toAlgebraicNotation();
                }
            }
        }

        return enPassantCell;
    }

    /**
     * @return castling availability for both sides.
     */
    private static String modifyCastlingCells(Board board) {
        Cell whiteKingCell = board.getWhiteKingCell();
        Cell blackKingCell = board.getBlackKingCell();
        String castlingAvailability = "";

        Cell[][] cells = board.getCells();
        if (!whiteKingCell.getPiece().hasMoved()) {
            /* Check white short castle availability */
            if (cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].isRook() &&
                    !cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "K";
            }

            /* Check white long castle availability */
            if (cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].isRook() &&
                    !cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "Q";
            }
        }

        if (!blackKingCell.getPiece().hasMoved()) {
            /* Check black short castle availability */
            if (cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].isRook() &&
                    !cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "k";
            }

            /* Check black long castle availability */
            if (cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].isRook() &&
                    !cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().hasMoved()) {
                castlingAvailability += "q";
            }
        }

        return castlingAvailability.equals("") ? "-" : castlingAvailability;
    }

    /**
     * Returns Board from given FEN.
     */
    public static Board boardFrom(String FEN) {
        return new Board(FEN);
    }

    /**
     * @return Cell[][] with pieces placed from given FEN.
     */
    public static Cell[][] calculatePiecePlacement(String FEN) {
        String[] elements = FEN.split(" ");
        String[] pieces = elements[0].split("/");

        Cell[][] cells = new Cell[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            int current = 0;
            for (int column = 0; column < pieces[row].length(); column++) {
                char piece = pieces[row].charAt(column);

                if (!Character.isDigit(piece)) {
                    cells[current][GRID_SIZE - row - 1] = new Cell(current, GRID_SIZE - row - 1, Piece.of(String.valueOf(piece)));
                    current += 1;
                } else {
                    int columnsFree = Character.getNumericValue(piece);
                    for (int i = 0; i < columnsFree; i++) {
                        cells[current][GRID_SIZE - row - 1] = new Cell(current, GRID_SIZE - row - 1, null);
                        current += 1;
                    }
                }
            }
        }

        cells = modifyCastlingCells(FEN, cells);
        cells = modifyEnPassantCell(FEN, cells);
        return cells;
    }

    /**
     * If provided Cell to en passant in FEN, then set proper Pawn status.
     *
     * @return Cell[][] with set correct en passant status
     */
    private static Cell[][] modifyEnPassantCell(String FEN, Cell[][] cells) {
        String[] elements = FEN.split(" ");
        String enPassantCell = elements[3];
        if (!enPassantCell.equals("-")) {
            int x = COLUMN_TO_INT.get(enPassantCell.charAt(0));
            int y = Character.getNumericValue(enPassantCell.charAt(1)) - 1;
            ((Pawn) cells[x][y + (calculateTurn(FEN) ? -1 : 1)].getPiece()).setEnPassant(true);
        }

        return cells;
    }

    /**
     * Returns true if white is on turn.
     */
    public static boolean calculateTurn(String FEN) {
        String[] elements = FEN.split(" ");
        String color = elements[1];
        return color.equals("w");
    }

    /**
     * Modifies given Cell[][] with pieces available (or not) to castle.
     */
    public static Cell[][] modifyCastlingCells(String FEN, Cell[][] cells) {
        String[] elements = FEN.split(" ");
        String castling = elements[2]; // castling can "KQkq" or any non empty combination of substrings or "-".
        // if no castling availability for white
        if (!castling.contains("K") && !castling.contains("Q")) {
            Cell kingCell = cells[KING_COLUMN][WHITE_PIECES_ROW];
            // if there is a white king on starting cell, set number of his moves.
            // if there is no king, there is no reason to modify his moves since number of his moves will increase if
            // he moves so castling won't be available.
            // same for rook: no reason to change number of moves for Rook since King's number of moves will decide.
            if (kingCell.isKing() && kingCell.getPiece().isWhite()) {
                cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().setMoves(MOVES);
            }
        } else {
            if (castling.contains("K")) {
                cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().setMoves(0);
                cells[ROOK_KINGSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().setMoves(0);
            }
            if (castling.contains("Q")) {
                cells[KING_COLUMN][WHITE_PIECES_ROW].getPiece().setMoves(0);
                cells[ROOK_QUEENSIDE_COLUMN][WHITE_PIECES_ROW].getPiece().setMoves(0);
            }
        }

        // if no castling availability for black
        if (!castling.contains("k") && !castling.contains("q")) {
            Cell kingCell = cells[KING_COLUMN][BLACK_PIECES_ROW];
            // explanation in white case
            if (kingCell.isKing() && !kingCell.getPiece().isWhite()) {
                cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().setMoves(MOVES);
            }
        } else {
            if (castling.contains("k")) {
                cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().setMoves(0);
                cells[ROOK_KINGSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().setMoves(0);
            }
            if (castling.contains("q")) {
                cells[KING_COLUMN][BLACK_PIECES_ROW].getPiece().setMoves(0);
                cells[ROOK_QUEENSIDE_COLUMN][BLACK_PIECES_ROW].getPiece().setMoves(0);
            }
        }

        return cells;
    }

    public static int calculateHalfmoves(String FEN) {
        String[] elements = FEN.split(" ");
        int halfmoves = Integer.parseInt(elements[4]);
        if (halfmoves < 0) throw new IllegalArgumentException();
        return halfmoves;
    }

    public static int calculateFullmoves(String FEN) {
        String[] elements = FEN.split(" ");
        int moves = Integer.parseInt(elements[5]);
        if (moves < 0) throw new IllegalArgumentException();
        return moves;
    }

}

