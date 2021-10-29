package chess.core;

import chess.core.move.Move;
import chess.core.move.MoveValidator;
import chess.core.move.executor.Executor;
import chess.core.move.executor.ExecutorCalculator;
import chess.core.pieces.*;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import chess.utilities.FEN;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static chess.utilities.Constants.*;
import static chess.utilities.Display.convertPieceToSymbol;
import static chess.core.GameUtilities.MoveInfo;

@Component
@Scope("prototype")
@Getter
/**
 *  Class that is a representation of a chess board.
 */
public final class Board {
    public static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";

    private Cell[][] cells;
    /**
     * Last move made on this board.
     */
    private Move lastMove;
    /**
     * Deque that stores all moves made on this board.
     */
    private final Deque<Move> moves = new ArrayDeque<>();
    /**
     * Number of halfmoves. (number of moves from last pawn move, promotion or capture.)
     */
    private int halfmoves = 0;
    /**
     * Number of fullmoves made in this board. Counter is increased when black has made a move.
     */
    private int fullmoves = 1;
    /**
     * List of cells that stores cells with white pieces placed.
     */
    private List<Cell> aliveWhitePiecesCells = new ArrayList<>();
    /**
     * List of cells that stores cells with black pieces placed.
     */
    private List<Cell> aliveBlackPiecesCells = new ArrayList<>();
    /**
     * Map that stores occurred positions in a board. By position, we mean first four elements of FEN.
     */
    private Map<String, Integer> positionsOccurred = new HashMap<>();
    /**
     * Stores information about current player to move.
     */
    private boolean turn;
    /**
     * The cell where the white king is.
     */
    private Cell whiteKingCell;
    /**
     * The cell where the black king is.
     */
    private Cell blackKingCell;

    /**
     * Basic class constructor for standard position.
     */
    public Board() {
        this(STARTING_FEN);
    }

    /**
     * Class constructor when providing custom FEN.
     */
    public Board(String FEN) {
        this.cells = chess.utilities.FEN.calculatePiecePlacement(FEN);
        this.turn = chess.utilities.FEN.calculateTurn(FEN);
        this.halfmoves = chess.utilities.FEN.calculateHalfmoves(FEN);
        this.fullmoves = chess.utilities.FEN.calculateFullmoves(FEN);
        updateAliveCells();
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
        String FEN = chess.utilities.FEN.from(this);
        String[] fenCalculated = FEN.split(" ");
        String current = fenCalculated[0] + " " + fenCalculated[1] + " " + fenCalculated[2] + " " + fenCalculated[3];

        if (positionsOccurred.containsKey(current)) {
            positionsOccurred.put(current, positionsOccurred.get(current) + 1);
        } else {
            positionsOccurred.put(current, 1);
        }
    }

    /**
     * Decrease number of occurrences of a position or removes current position.
     */
    private void removePosition() {
        String FEN = chess.utilities.FEN.from(this);
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
            if (info == MoveInfo.CAPTURE || info == MoveInfo.PAWN_MOVE || info == MoveInfo.EN_PASSANT || info == MoveInfo.TWO_FORWARD ||
                    info == MoveInfo.BISHOP_PROMOTION || info == MoveInfo.KNIGHT_PROMOTION ||
                    info == MoveInfo.QUEEN_PROMOTION || info == MoveInfo.ROOK_PROMOTION) {
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

    /**
     * Handling en passant availability.
     */
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

    /**
     * Updates alive cells and king cells.
     */
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

    /**
     * Checks if specified coordinates fit inside chess board.
     */
    public static boolean fitInBoard(int x, int y) {
        return x >= 0 && x <= GRID_SIZE - 1 && y >= 0 && y <= GRID_SIZE - 1;
    }

    /**
     * example: getCellByName("a1") returns cell: cells[0][0].
     *
     * @return real cell by name
     */
    public Cell getCellByName(String name) {
        char column = name.charAt(0);
        char charRow = name.charAt(1);
        int row = Character.getNumericValue(charRow) - 1;
        return cells[COLUMN_TO_INT.get(column)][row];
    }

    /**
     * @return FEN of a board
     */
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
     * @return List of legal moves
     */
    public List<Move> getLegalMoves() {
        List<Move> moves;
        List<Move> legal = new ArrayList<>();
        List<Cell> cells = turn ? aliveWhitePiecesCells : aliveBlackPiecesCells;

        for (Cell x : cells) {
            moves = x.getPiece().calculatePseudoLegalMoves(this, x);
            for (Move move : moves) {
                if (MoveValidator.isValid(move, this))
                    legal.add(move);
            }
        }

        return legal;
    }

    /**
     * Get List of pseudo legal Moves for current state. (pseudo legal move = move that can leave King in check.
     *
     * @return List of pseudo legal moves
     */
    public List<Move> getPseudoLegalMoves() {
        List<Move> pseudoLegal = new ArrayList<>();
        List<Cell> cells = turn ? aliveWhitePiecesCells : aliveBlackPiecesCells;

        for (Cell x : cells) {
            pseudoLegal.addAll(x.getPiece().calculatePseudoLegalMoves(this, x));
        }
        return pseudoLegal;
    }

    /**
     * Checks if current player can claim a draw due to threefold repetition and 50 moves rule.
     */
    public boolean canBeClaimedDraw() {
        return isNfoldRepetition(3) || isNmovesRule(50);
    }

    /**
     * Checks if there was no pawn move, capture or promotion in last 50 moves.
     */
    private boolean isNmovesRule(int N) {
        if (fullmoves >= N) {
            Iterator<Move> it = moves.descendingIterator();
            int checkedMoves = 0;
            while (it.hasNext()) {
                Move temp = it.next();
                MoveInfo info = temp.getInfo();
                if (info == MoveInfo.CAPTURE || info == MoveInfo.PAWN_MOVE || info == MoveInfo.EN_PASSANT ||
                        info == MoveInfo.TWO_FORWARD || info == MoveInfo.BISHOP_PROMOTION || info == MoveInfo.KNIGHT_PROMOTION
                        || info == MoveInfo.QUEEN_PROMOTION || info == MoveInfo.ROOK_PROMOTION) return false;
                checkedMoves += 1;
                if (checkedMoves == 2 * N) {
                    return true;
                }
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Checks if the same position (with same legal moves) occurred N times during the game.
     * n = 3: threefold repetition,
     * n = 5: fivefold repetition.
     */
    private boolean isNfoldRepetition(int N) {
        return positionsOccurred.values().stream().anyMatch(x -> x >= N);
    }

    /**
     * @return if there is draw on a board
     */
    public boolean isDraw() {
        return isInsufficientMaterial() || isNmovesRule(75) || isNfoldRepetition(5) || isKingStuck();
    }

    /**
     * (example of king stuck: 8/8/8/8/8/5K2/5P2/5k2 b)
     *
     * @return if one side can't move anywhere and king is not in check
     */
    private boolean isKingStuck() {
        if (getLegalMoves().size() == 0) return true;
        return false;
    }

    /**
     * Checks if there is following combination of pieces in board:
     * king vs king,
     * king and bishop vs king,
     * king and knight vs king,
     * king and bishop vs. king and bishop of the same color as the opponent's bishop.
     *
     * @return if one of above situation occurs
     */
    private boolean isInsufficientMaterial() {
        return isKingVsKing() || isBishopAndKingVsKing() || isKnightAndKingVsKing() || isKingAndBishopVsKingAndBishop()
                || isKingVsBishopsSameColor();
    }

    /**
     * Checks if there is king vs king and 2 same colored bishops on a board.
     */
    private boolean isKingVsBishopsSameColor() {
        List<Cell> whiteBishops = aliveWhitePiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).collect(Collectors.toList());
        List<Cell> blackBishops = aliveBlackPiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).collect(Collectors.toList());

        List<Cell> whiteKing = aliveWhitePiecesCells.stream().filter(x -> x.getPiece() instanceof King).collect(Collectors.toList());
        List<Cell> blackKing = aliveWhitePiecesCells.stream().filter(x -> x.getPiece() instanceof King).collect(Collectors.toList());

        if (aliveWhitePiecesCells.size() == 3 && whiteBishops.size() == 2 && whiteKing.size() == 1 && aliveBlackPiecesCells.size() == 1) {
            return whiteBishops.get(0).isWhite() == whiteBishops.get(1).isWhite();
        } else if (aliveBlackPiecesCells.size() == 3 && blackBishops.size() == 2 && blackKing.size() == 1 && aliveWhitePiecesCells.size() == 1) {
            return blackBishops.get(0).isWhite() == blackBishops.get(1).isWhite();
        }
        return false;
    }

    /**
     * Checks if there are same colored bishop and king on both sides.
     */
    private boolean isKingAndBishopVsKingAndBishop() { // ugly
        boolean isKingAndBishopVsKingAndBishop = (aliveBlackPiecesCells.size() == 2 && (aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop)) &&
                aliveWhitePiecesCells.size() == 2 && (aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop)));

        if (!isKingAndBishopVsKingAndBishop) return false;

        Optional<Cell> whiteBishopCell = aliveWhitePiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).findFirst();
        Optional<Cell> blackBishopCell = aliveBlackPiecesCells.stream().filter(x -> x.getPiece() instanceof Bishop).findFirst();
        if (whiteBishopCell.isPresent() && blackBishopCell.isPresent()) {
            return whiteBishopCell.get().isWhite() == blackBishopCell.get().isWhite();
        }

        return false;
    }

    /**
     * Checks if there is knight and king vs king on a board.
     */
    private boolean isKnightAndKingVsKing() { // ugly
        return (aliveBlackPiecesCells.size() == 2 && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight)
                && aliveWhitePiecesCells.size() == 1 && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King))
                ||
                (aliveWhitePiecesCells.size() == 2 && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                        && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Knight) && aliveBlackPiecesCells.size() == 1 &&
                        aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King));
    }

    /**
     * Checks if there is bishop and king vs king on a board.
     */
    private boolean isBishopAndKingVsKing() {
        return (aliveBlackPiecesCells.size() == 2 && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                && aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop)
                && aliveWhitePiecesCells.size() == 1 && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King))
                ||
                (aliveWhitePiecesCells.size() == 2 && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King)
                        && aliveWhitePiecesCells.stream().anyMatch(x -> x.getPiece() instanceof Bishop) && aliveBlackPiecesCells.size() == 1 &&
                        aliveBlackPiecesCells.stream().anyMatch(x -> x.getPiece() instanceof King));
    }

    /**
     * Checks if there is king vs king on a board.
     */
    private boolean isKingVsKing() {
        return aliveBlackPiecesCells.size() == 1 && aliveBlackPiecesCells.get(0).getPiece() instanceof King
                && aliveWhitePiecesCells.size() == 1 && aliveWhitePiecesCells.get(0).getPiece() instanceof King;
    }
}