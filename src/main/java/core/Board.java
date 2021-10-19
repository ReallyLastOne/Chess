package core;

import core.move.Move;
import core.move.MoveValidator;
import core.move.executor.Executor;
import core.move.executor.ExecutorCalculator;
import core.pieces.*;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utilities.FEN;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private Map<String, Integer> positionsOccurred = new HashMap<>();
    private boolean turn = true;
    private List<Move> legalMoves;
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
        for (Cell[] row : cells) {
            for (Cell x : row) {
                if (x.isOccupied() && x.getPiece() instanceof King) {
                    if (x.getPiece().isWhite()) whiteKingCell = x;
                    else if (!x.getPiece().isWhite()) blackKingCell = x;
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
        addPosition();
    }

    /**
     * Method responsible for undo last move.
     */
    public void undoMove() {
        removePosition();
        if (turn) fullmoves--;
        lastMove = moves.getLast();
        Executor executor = ExecutorCalculator.calculate(lastMove);
        executor.undoMove(this);
        moves.pollLast();
        updateAliveCells();
        turn = !turn;
        updatePawnsStatus();
    }

    /**
     * Add current position. Position is defined as all parts of FEN except two last ones.
     */
    private void addPosition() {
        String FEN = utilities.FEN.from(this);
        String[] fenCalculated = FEN.split(" ");
        String current = fenCalculated[0] + " " + fenCalculated[1] + " " + fenCalculated[2] + " " + fenCalculated[3];

        if (positionsOccurred.containsKey(current)) {
            positionsOccurred.put(current, positionsOccurred.get(current) + 1);
        } else {
            positionsOccurred.put(current, 1);
        }
    }

    private void removePosition() {
        String FEN = utilities.FEN.from(this);
        String[] fenCalculated = FEN.split(" ");
        String current = fenCalculated[0] + " " + fenCalculated[1] + " " + fenCalculated[2] + " " + fenCalculated[3];

        if (positionsOccurred.containsKey(current)) {
            if (positionsOccurred.get(current) == 0) positionsOccurred.remove(current);
            else {
                positionsOccurred.put(current, positionsOccurred.get(current) - 1);
            }
        }
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
                halfmoves += 1;
            }
        }
        return halfmoves;
    }

    public Move getLastMove() {
        if (moves.size() == 0) return null;
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

    /**
     * Get List of legal Moves for current state.
     */
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

    /**
     * Get List of pseudo legal Moves for current state. (pseudo legal move = move that can leave King in check.
     */
    public List<Move> getPseudoLegalMoves() {
        List<Move> pseudoLegal = new ArrayList<>();
        List<Cell> cells = turn ? aliveWhitePiecesCells : aliveBlackPiecesCells;

        for (Cell x : cells) {
            pseudoLegal.addAll(x.getPiece().calculatePseudoLegalMoves(this, x));
        }
        return pseudoLegal;
    }

    public boolean canBeClaimedDraw() {
        return isThreefoldRepetition() || is50MovesRule();
    }

    /**
     * Checks if there was no pawn move, capture or promotion in last 50 moves.
     */
    private boolean is50MovesRule() {
        if (fullmoves >= 50) {
            Iterator<Move> it = moves.iterator();
            int checkedMoves = 0;
            while (it.hasNext() || checkedMoves == 100) {
                MoveInfo info = it.next().getInfo();
                if (info == MoveInfo.CAPTURE || info == MoveInfo.PAWN_MOVE || info == MoveInfo.EN_PASSANT ||
                        info == MoveInfo.TWO_FORWARD || info == MoveInfo.BISHOP_PROMOTION || info == MoveInfo.KNIGHT_PROMOTION
                        || info == MoveInfo.QUEEN_PROMOTION || info == MoveInfo.ROOK_PROMOTION) return false;
                checkedMoves += 1;
            }
        }

        return true;
    }

    /**
     * Checks if the same position (with same legal moves) occurred three times during the game.
     */
    private boolean isThreefoldRepetition() {
        return positionsOccurred.values().stream().anyMatch(x -> x >= 3);
    }

    private boolean isFivefoldRepetition() {
        return positionsOccurred.values().stream().anyMatch(x -> x >= 5);
    }

    public boolean isDraw() {
        return isInsufficientMaterial() || isFivefoldRepetition();
    }

    /**
     * Checks if there is following combination of pieces in board:
     * king vs king,
     * king and bishop vs king,
     * king and knight vs king,
     * king and bishop vs. king and bishop of the same color as the opponent's bishop.
     */
    private boolean isInsufficientMaterial() {
        return isKingVsKing() || isBishopAndKingVsKing() || isKnightAndKingVsKing() || isKingAndBishopVsKingAndBishop();
    }

    private boolean isKingAndBishopVsKingAndBishop() {
        boolean isKingAndBishopVsKingAndBishop = (aliveBlackPiecesCells.size() == 2 && (aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight)) &&
                aliveWhitePiecesCells.size() == 2 && (aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight)));
        if (!isKingAndBishopVsKingAndBishop) return false;

        Optional<Cell> whiteBishopCell = aliveWhitePiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).findFirst();
        Optional<Cell> blackBishopCell = aliveBlackPiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).findFirst();
        if (whiteBishopCell.isPresent() && blackBishopCell.isPresent()) {
            return whiteBishopCell.get().isWhite() == blackBishopCell.get().isWhite();
        }

        return false;
    }

    private boolean isKnightAndKingVsKing() {
        return (aliveBlackPiecesCells.size() == 2 && (aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight)) ||
                aliveWhitePiecesCells.size() == 2 && (aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                        && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight)));
    }

    private boolean isBishopAndKingVsKing() {
        return (aliveBlackPiecesCells.size() == 2 && (aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop)) ||
                aliveWhitePiecesCells.size() == 2 && (aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                        && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop)));
    }

    private boolean isKingVsKing() {
        return aliveBlackPiecesCells.size() == 1 && aliveBlackPiecesCells.get(0).getPiece() instanceof King
                && aliveWhitePiecesCells.size() == 1 && aliveWhitePiecesCells.get(0).getPiece() instanceof King;
    }
}
