package chess.core;

import chess.core.move.Move;
import chess.core.move.MoveValidator;
import chess.core.move.executor.Executor;
import chess.core.move.executor.ExecutorCalculator;
import chess.core.pieces.*;
import chess.utilities.FEN;
import chess.utilities.GameUtilities;
import lombok.Getter;

import java.util.*;
import java.util.stream.Stream;

import static chess.utilities.Constants.GRID_SIZE;
import static chess.utilities.GameUtilities.MoveInfo;


@Getter
/**
 *  Class that is a representation of a chess board.
 */
public final class Board {
    public static final String STARTING_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1 ";
    private static final List<GameUtilities.MoveInfo> infosResetingHalfmovesClock = Arrays.asList(GameUtilities.MoveInfo.CAPTURE,
            MoveInfo.PAWN_MOVE, MoveInfo.EN_PASSANT, MoveInfo.TWO_FORWARD, MoveInfo.BISHOP_PROMOTION, MoveInfo.BISHOP_PROMOTION,
            MoveInfo.KNIGHT_PROMOTION, MoveInfo.ROOK_PROMOTION, MoveInfo.QUEEN_PROMOTION);

    private Cell[][] cells;
    private Move lastMove;
    private final Deque<Move> moves = new ArrayDeque<>();
    /**
     * Number of halfmoves. (number of moves from last pawn move, promotion or capture.)
     */
    private int halfmoves = 0;
    /**
     * Number of fullmoves made in this board. Counter is increased when black has made a move.
     */
    private int fullmoves = 1;
    private List<Cell> aliveWhitePiecesCells = new ArrayList<>();
    private List<Cell> aliveBlackPiecesCells = new ArrayList<>();
    /**
     * Map that stores occurred positions in a board. By position, we mean first four elements of FEN.
     */
    private Map<String, Integer> positionsOccurred = new HashMap<>();
    private boolean turn;
    private Cell whiteKingCell;
    private Cell blackKingCell;

    public Board() {
        this(STARTING_FEN);
    }

    public Board(String fen) {
        this.cells = FEN.calculatePiecePlacement(fen);
        this.turn = FEN.calculateTurn(fen);
        this.halfmoves = FEN.calculateHalfmoves(fen);
        this.fullmoves = FEN.calculateFullmoves(fen);
        updateAliveCells();
    }

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
            if (infosResetingHalfmovesClock.contains(info)) {
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

    public static boolean fitInBoard(int x, int y) {
        return x >= 0 && x <= GRID_SIZE - 1 && y >= 0 && y <= GRID_SIZE - 1;
    }

    public String getFEN() {
        return FEN.from(this);
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (int i = GRID_SIZE - 1; i >= 0; i--) {
            board.append("| ");
            for (int j = 0; j < GRID_SIZE; j++) {
                board.append((Board.convertPieceToSymbol(cells[j][i].getPiece())));
                board.append(" | ");
            }
            board.append(i + 1).append("\n");
        }
        board.append(("  A   B   C   D   E   F   G   H   "));

        return board.toString();
    }

    public static String convertPieceToSymbol(Piece piece) {
        if (piece == null) return " ";
        return piece.toSymbol();
    }

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
     * Get List of pseudo legal Moves for current state. (pseudo legal move = move that can leave King in check)
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
     * Checks if current player can claim a draw due to threefold repetition or 50 moves rule.
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
                if (infosResetingHalfmovesClock.contains(info)) return false;
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
        return isKingVsKing() || isBishopAndKingVsKing() || isKnightAndKingVsKing() || areThereSameColoredBishopAndKingBothSides()
                || isKingVsBishopsSameColor();
    }

    /**
     * Checks if there is king vs king and 2 same colored bishops on a board.
     */
    private boolean isKingVsBishopsSameColor() {
        int aliveBlackPieces = aliveBlackPiecesCells.size();
        int aliveWhitePieces = aliveWhitePiecesCells.size();

        boolean aliveBlackPiecesCellsAnyMatchKing = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveBlackPiecesCellsMatchesTwoSameColorBishops = aliveBlackPiecesCells.stream()
                .filter(this::isBishop).filter(Cell::isWhite).count() == 2;

        boolean aliveWhitePiecesCellsAnyMatchKing = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveWhitePiecesCellsMatchesTwoSameColorBishops = aliveWhitePiecesCells.stream()
                .filter(this::isBishop).filter(Cell::isWhite).count() == 2;


        if (isKingAndTwoPieces(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing, aliveBlackPiecesCellsMatchesTwoSameColorBishops) &&
                isAloneKing(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing)) {
            return true;
        }
        return isKingAndTwoPieces(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing, aliveWhitePiecesCellsMatchesTwoSameColorBishops) &&
                isAloneKing(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing);
    }

    /**
     * Checks if there is bishop and king vs bishop and king and both bishops stands on same colored cell.
     */
    private boolean areThereSameColoredBishopAndKingBothSides() {
        if (!isKingAndBishopVsKingAndBishop()) {
            return false;
        }
        Boolean isWhiteBishopCellWhite = isFirstBishopsCellWhite(aliveWhitePiecesCells);
        Boolean isBlackBishopCellWhite = isFirstBishopsCellWhite(aliveBlackPiecesCells);
        if (isWhiteBishopCellWhite == null || isBlackBishopCellWhite == null) {
            return false;
        }
        return isWhiteBishopCellWhite == isBlackBishopCellWhite;
    }

    private boolean isKingAndBishopVsKingAndBishop() {
        int aliveBlackPieces = aliveBlackPiecesCells.size();
        int aliveWhitePieces = aliveWhitePiecesCells.size();
        boolean aliveBlackPiecesCellsAnyMatchKing = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveBlackPiecesCellsAnyMatchBishop = aliveBlackPiecesCells.stream()
                .anyMatch(this::isBishop);

        boolean aliveWhitePiecesCellsAnyMatchKing = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveWhitePiecesCellsAnyMatchBishop = aliveWhitePiecesCells.stream()
                .anyMatch(this::isBishop);

        return isKingAndPiece(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing, aliveBlackPiecesCellsAnyMatchBishop) &&
                isKingAndPiece(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing, aliveWhitePiecesCellsAnyMatchBishop);
    }

    /**
     * Checks if there is a knight and king vs king on a board.
     */
    private boolean isKnightAndKingVsKing() { // very similar to isBishopAndKingVsKing()
        int aliveBlackPieces = aliveBlackPiecesCells.size();
        int aliveWhitePieces = aliveWhitePiecesCells.size();
        boolean aliveBlackPiecesCellsAnyMatchKing = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveBlackPiecesCellsAnyMatchKnight = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKnight);

        boolean aliveWhitePiecesCellsAnyMatchKing = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveWhitePiecesCellsAnyMatchKnight = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKnight);

        return (isKingAndPiece(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing, aliveWhitePiecesCellsAnyMatchKnight)
                && isAloneKing(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing)) ||
                (isKingAndPiece(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing, aliveBlackPiecesCellsAnyMatchKnight)
                        && isAloneKing(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing));
    }

    /**
     * Checks if there is a bishop and king vs king on a board.
     */
    private boolean isBishopAndKingVsKing() { // very similar to isKnightAndKingVsKing()
        int aliveBlackPieces = aliveBlackPiecesCells.size();
        int aliveWhitePieces = aliveWhitePiecesCells.size();
        boolean aliveBlackPiecesCellsAnyMatchKing = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveBlackPiecesCellsAnyMatchBishop = aliveBlackPiecesCells.stream()
                .anyMatch(this::isBishop);

        boolean aliveWhitePiecesCellsAnyMatchKing = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveWhitePiecesCellsAnyMatchBishop = aliveWhitePiecesCells.stream()
                .anyMatch(this::isBishop);

        return (isKingAndPiece(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing, aliveWhitePiecesCellsAnyMatchBishop)
                && isAloneKing(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing)) ||
                (isKingAndPiece(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing, aliveBlackPiecesCellsAnyMatchBishop)
                        && isAloneKing(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing));
    }

    /**
     * Checks if there is king vs king on a board.
     */
    private boolean isKingVsKing() {
        int aliveBlackPieces = aliveBlackPiecesCells.size();
        int aliveWhitePieces = aliveWhitePiecesCells.size();
        boolean aliveBlackPiecesCellsAnyMatchKing = aliveBlackPiecesCells.stream()
                .anyMatch(this::isKing);
        boolean aliveWhitePiecesCellsAnyMatchKing = aliveWhitePiecesCells.stream()
                .anyMatch(this::isKing);
        return isAloneKing(aliveBlackPieces, aliveBlackPiecesCellsAnyMatchKing) && isAloneKing(aliveWhitePieces, aliveWhitePiecesCellsAnyMatchKing);
    }

    private boolean isAloneKing(int alivePieces, boolean matchesKing) {
        return alivePieces == 1 && matchesKing;
    }

    private boolean isKingAndPiece(int alivePieces, boolean matchesKing, boolean matchesPiece) {
        return alivePieces == 2 && matchesKing && matchesPiece;
    }

    private boolean isKingAndTwoPieces(int alivePieces, boolean matchesKing, boolean matchesTwoPieces) {
        return alivePieces == 3 && matchesKing && matchesTwoPieces;
    }

    private Boolean isFirstBishopsCellWhite(List<Cell> alivePiecesCells) {
        return alivePiecesCells.stream()
                .filter(this::isBishop)
                .findFirst()
                .map(Cell::isWhite)
                .orElse(null);
    }

    private boolean isKing(Cell x) {
        return x.getPiece() instanceof King;
    }

    private boolean isBishop(Cell x) {
        return x.getPiece() instanceof Bishop;
    }

    private boolean isKnight(Cell x) {
        return x.getPiece() instanceof Knight;
    }
}
