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
import utilities.FEN;

import java.util.*;
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
    private Cell[][] cells;
    private Move lastMove;
    private final Deque<Move> moves = new ArrayDeque<>();
    private int halfmoves = 0;
    private int fullmoves = 1;
    private List<Cell> aliveWhitePiecesCells = new ArrayList<>();
    private List<Cell> aliveBlackPiecesCells = new ArrayList<>();

    private boolean turn = true;
    private King whiteKing;
    private King blackKing;

    private Cell whiteKingCell;
    private Cell blackKingCell;

    public Board() {
        this(STARTING_FEN);
    }

    public Board(String FEN) {
        this.cells = utilities.FEN.calculatePiecePlacement(FEN);
        this.turn = utilities.FEN.calculateTurn(FEN);
        this.halfmoves = utilities.FEN.calculateHalfmoves(FEN);
        this.fullmoves = utilities.FEN.calculateFullmoves(FEN);
        updateAliveCells();
        assignKingCells();
    }

    private void assignKingCells() {
        for(Cell[] row : cells) {
            for(Cell x : row) {
                if(x.isOccupied() && x.getPiece() instanceof King) {
                    if(x.getPiece().isWhite()) whiteKingCell = x;
                    else if(!x.getPiece().isWhite()) blackKingCell = x;
                }
            }
        }
    }

    /**
     * Method responsible for executing valid move on board.
     */
    public void executeMove(Move move) {
        if (!turn) fullmoves++;
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
        if (turn) fullmoves--;
        lastMove = moves.getLast();
        Executor executor = ExecutorCalculator.calculate(lastMove);
        executor.undoMove(this);
        moves.pollLast();
        updateAliveCells();
        turn = !turn;
        updatePawnsStatus();
    }

    public int getHalfmoves() {
        Iterator<Move> itr = moves.descendingIterator();

        halfmoves = 0;
        while (itr.hasNext()) {
            Move move = itr.next();
            MoveInfo info = move.getInfo();
            if (info == MoveInfo.CAPTURE || info == MoveInfo.PAWN_MOVE || info == MoveInfo.EN_PASSANT || info == MoveInfo.TWO_FORWARD) {
                return halfmoves;
            } else {
                halfmoves++;
            }
        }
        return halfmoves;
    }

    public Move getLastMove() {
        if(moves.size() == 0) return null;
        return moves.getLast();
    }

    public void updatePawnsStatus() {
        for (Cell x : aliveWhitePiecesCells) {
            if (x.isOccupied() && x.getPiece() instanceof Pawn) {
                ((Pawn) x.getPiece()).setEnPassant(false);
            }
        }
        for (Cell x : aliveBlackPiecesCells) {
            if (x.isOccupied() && x.getPiece() instanceof Pawn) {
                ((Pawn) x.getPiece()).setEnPassant(false);
            }
        }
        if (moves.size() > 0) {
            if (moves.getLast().getInfo() == MoveInfo.TWO_FORWARD) {
                ((Pawn) cells[moves.getLast().getEnd().getX()][moves.getLast().getEnd().getY()].getPiece()).setEnPassant(true);
            }
        }
    }


    public void updateAliveCells() {
        aliveWhitePiecesCells = new ArrayList<>();
        aliveBlackPiecesCells = new ArrayList<>();

        Arrays.stream(cells).flatMap(Stream::of).forEach(x ->
        {
            if (x.isOccupied() && x.getPiece().isWhite()) {
                aliveWhitePiecesCells.add(x);
                if (x.getPiece() instanceof King) whiteKingCell = x;
            } else if (x.isOccupied() && !x.getPiece().isWhite()) {
                aliveBlackPiecesCells.add(x);
                if (x.getPiece() instanceof King) blackKingCell = x;
            }
        });
    }

    void printAllPossibleMoves() {
        System.out.println("white");
        for (Cell cell : aliveWhitePiecesCells)
            System.out.println(cell.getPiece().getClass() + " " + cell.getPiece().calculatePseudoLegalMoves(this, cell));
        System.out.println("black");
        for (Cell cell : aliveBlackPiecesCells)
            System.out.println(cell.getPiece().getClass() + " " + cell.getPiece().calculatePseudoLegalMoves(this, cell));
    }

    /**
     * Checks if specified coordinates fit inside chess board.
     */
    public static boolean fitInBoard(int x, int y) {
        return x >= 0 && x <= GRID_SIZE - 1 && y >= 0 && y <= GRID_SIZE - 1;
    }

    public Cell getCellByName(String name) {
        char column = name.charAt(0);
        char charRow = name.charAt(1);
        int row = Character.getNumericValue(charRow) - 1;
        return cells[COLUMN_TO_INT.get(column)][row];
    }

    public String getFEN() {
        return FEN.from(this);
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
                if (!MoveValidator.isKingInCheckAfterMove(move, this, move.getStart().getPiece().isWhite()))
                    legal.add(move);
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
