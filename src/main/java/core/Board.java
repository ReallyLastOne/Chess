package core;

import core.move.Move;
import core.move.MoveValidator;
import core.move.executor.Executor;
import core.move.executor.ExecutorCalculator;
import core.pieces.*;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utilities.Display;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.PositionConstants.*;
import static utilities.Constants.*;
import static utilities.Display.convertPieceToSymbol;
import static core.GameUtilities.MoveInfo;

@Component
@Scope("prototype")
@Getter
/**
 *  Class that is a representation of chess board.
 */
public final class Board {
    public static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";
    public static int nr = 0;
    private Cell[][] cells;
    private Move lastMove;
    private final Deque<Move> moves = new ArrayDeque<>();

    private List<Cell> aliveWhitePiecesCells = new ArrayList<>();
    private List<Cell> aliveBlackPiecesCells = new ArrayList<>();

    private boolean turn = true;
    private King whiteKing;
    private King blackKing;

    private Cell whiteKingCell;
    private Cell blackKingCell;

    public Board() {
        initializeCells();
        nr++;
    }

    public Board(String FEN) {
        // TODO: fen handler
        initializeCells();
    }

    /**
     * Method responsible for executing valid move on board.
     */
    public void executeMove(Move move) {
        moves.add(move);
        lastMove = move.copy();
        Executor executor = ExecutorCalculator.calculate(move);
        executor.executeMove(this, move);
        updateAliveCells();
        turn = !turn;
        updatePawnsStatus();
    }

    /**
     * Method responsible for undo last move.
     */
    public void undoMove() {
        lastMove = moves.getLast();
        Executor executor = ExecutorCalculator.calculate(lastMove);
        executor.undoMove(this);
        moves.pollLast();
        updateAliveCells();
        turn = !turn;
        updatePawnsStatus();
    }

    public void updatePawnsStatus() {
        for (Cell x : aliveWhitePiecesCells) {
            if (x.getPiece() != null && x.getPiece() instanceof Pawn) {
                ((Pawn) x.getPiece()).setEnPassant(false);
            }
        }
        for (Cell x : aliveBlackPiecesCells) {
            if (x.getPiece() != null && x.getPiece() instanceof Pawn) {
                ((Pawn) x.getPiece()).setEnPassant(false);
            }
        }
        if(moves.size() > 0) {
            if (moves.getLast().getInfo() == MoveInfo.TWO_FORWARD) {
                ((Pawn) cells[moves.getLast().getEnd().getX()][moves.getLast().getEnd().getY()].getPiece()).setEnPassant(true);
            }
        }
    }

    private void initializeCells() { // extract to another class/ method?
        cells = new Cell[GRID_SIZE][GRID_SIZE];
        initializePieces();
        initializeEmptyCells();
        updateAliveCells();
    }

    private void initializePieces() {
        initializePawns();
        initializeWhitePieces();
        initializeBlackPieces();
    }

    public void updateAliveCells() {
        aliveWhitePiecesCells = new ArrayList<>();
        aliveBlackPiecesCells = new ArrayList<>();

        Arrays.stream(cells).flatMap(Stream::of).forEach(x ->
        {
            if (x.getPiece() != null && x.getPiece().isWhite()) {
                aliveWhitePiecesCells.add(x);
                if (x.getPiece() instanceof King) whiteKingCell = x;
            } else if (x.getPiece() != null && !x.getPiece().isWhite()) {
                aliveBlackPiecesCells.add(x);
                if (x.getPiece() instanceof King) blackKingCell = x;
            }
        });
    }

    private void initializePawns() {
        for (int i = 0; i < GRID_SIZE; i++) {
            /* creating white pawns */
            cells[i][WHITE_PAWN_ROW] = new Cell(i, WHITE_PAWN_ROW, new Pawn(true));
            /* creating black pawns */
            cells[i][BLACK_PAWN_ROW] = new Cell(i, BLACK_PAWN_ROW, new Pawn(false));
        }
    }

    private void initializeWhitePieces() {
        /* create white rooks */
        cells[0][0] = new Cell(0, WHITE_PIECES_ROW, new Rook(true));
        cells[7][0] = new Cell(7, WHITE_PIECES_ROW, new Rook(true));
        /* create white knights */
        cells[1][0] = new Cell(1, WHITE_PIECES_ROW, new Knight(true));
        cells[6][0] = new Cell(6, WHITE_PIECES_ROW, new Knight(true));
        /* create white bishops */
        cells[2][0] = new Cell(2, WHITE_PIECES_ROW, new Bishop(true));
        cells[5][0] = new Cell(5, WHITE_PIECES_ROW, new Bishop(true));
        /* create white queen */
        cells[3][0] = new Cell(3, WHITE_PIECES_ROW, new Queen(true));
        /* create white king */
        whiteKing = new King(true);
        cells[4][0] = new Cell(4, WHITE_PIECES_ROW, whiteKing);
    }

    private void initializeBlackPieces() {
        /* create black rooks */
        cells[0][7] = new Cell(0, BLACK_PIECES_ROW, new Rook(false));
        cells[7][7] = new Cell(7, BLACK_PIECES_ROW, new Rook(false));
        /* create black knights */
        cells[1][7] = new Cell(1, BLACK_PIECES_ROW, new Knight(false));
        cells[6][7] = new Cell(6, BLACK_PIECES_ROW, new Knight(false));
        /* create black bishops */
        cells[2][7] = new Cell(2, BLACK_PIECES_ROW, new Bishop(false));
        cells[5][7] = new Cell(5, BLACK_PIECES_ROW, new Bishop(false));
        /* create black queen */
        cells[3][7] = new Cell(3, BLACK_PIECES_ROW, new Queen(false));
        /* create black king */
        blackKing = new King(false);
        cells[4][7] = new Cell(4, BLACK_PIECES_ROW, blackKing);
    }

    private void initializeEmptyCells() {
        for (int i = 2; i <= 5; i++) {
            for (int j = 0; j <= 7; j++) {
                cells[j][i] = new Cell(j, i, null);
            }
        }
    }

    void printAllPossibleMoves() {
        System.out.println("white");
        for (Cell cell : aliveWhitePiecesCells)
            System.out.println(cell.getPiece().getClass() + " " + cell.getPiece().calculatePseudoLegalMoves(this, cell));
        System.out.println("black");
        for (Cell cell : aliveBlackPiecesCells)
            System.out.println(cell.getPiece().getClass() + " " + cell.getPiece().calculatePseudoLegalMoves(this, cell));
    }

    public static boolean fitInBoard(int x, int y) {
        return x >= 0 && x <= GRID_SIZE - 1 && y >= 0 && y <= GRID_SIZE - 1;
    }

    public Cell getCellByName(String name) {
        char column = name.charAt(0);
        char charRow = name.charAt(1);
        int row = Character.getNumericValue(charRow) - 1;
        return cells[COLUMN_TO_INT.get(column)][row];
    }

    public void displayBoard() {
        Display.displayBoard(this);
    }


    public String getFEN() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            board.append("| ");
            for (int j = 0; j < 8; j++) {
                board.append((convertPieceToSymbol(cells[j][i].getPiece())));
                board.append(" | ");
            }
            board.append(i + 1).append("\n");
        }
        board.append(("  A   B   C   D   E   F   G   H   "));

        return board.toString();
    }

    public List<Move> getLegalMoves() {
        List<Move> moves;
        List<Move> legal = new ArrayList<>();
        List<Cell> cells = turn ? aliveWhitePiecesCells : aliveBlackPiecesCells;

        for (Cell x : cells) {
            moves = x.getPiece().calculatePseudoLegalMoves(this, x);
            for (Move move : moves) {
                if (!MoveValidator.isKingInCheckAfterMove(move, this, move.getStart().getPiece().isWhite())) legal.add(move);
            }
        }

        return legal;
    }

    public List<Move> getPseudoLegalMoves() {
        List<Move> pseudoLegal = new ArrayList<>();
        List<Cell> cells = turn ? aliveWhitePiecesCells : aliveBlackPiecesCells;

        for (Cell x : cells) {
            pseudoLegal.addAll(x.getPiece().calculatePseudoLegalMoves(this, x));
        }
        return pseudoLegal;
    }
}
