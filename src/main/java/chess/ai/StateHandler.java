package chess.ai;

import chess.core.Board;
import chess.core.Game;
import chess.utilities.GameUtilities;

import java.util.Arrays;
import java.util.List;

public class StateHandler {
    private static final List<GameUtilities.GameStatus> endStatuses = Arrays.asList(GameUtilities.GameStatus.DRAW,
            GameUtilities.GameStatus.BLACK_WIN, GameUtilities.GameStatus.WHITE_WIN);

    private StateHandler() {
        throw new AssertionError();
    }

    public static int getEvaluationOfState(Game game) { // for now - simple evaluation, just piece values
        Board board = game.getBoard();
        if (isTerminalNode(game)) {
            return game.getGameStatus().getEvaluation();
        }

        int evaluation = board.getAliveBlackPiecesCells().stream().mapToInt(x -> x.getPiece().getEvaluation()).sum();
        evaluation += board.getAliveWhitePiecesCells().stream().mapToInt(x -> x.getPiece().getEvaluation()).sum();

        return evaluation;
    }

    public static boolean isTerminalNode(Game game) {
        return endStatuses.contains(game.getGameStatus());
    }
}
