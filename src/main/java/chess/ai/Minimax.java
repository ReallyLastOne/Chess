package chess.ai;

import chess.core.Board;
import chess.core.Game;
import chess.core.move.Move;
import lombok.Getter;

@Getter
public class Minimax {
    private Move bestMove;
    private int bestScore;

    public Minimax() {

    }

    public Move solve(Game game, int depth) {
        Board board = game.getBoard();

        if (board.isTurn()) {
            int highest = Integer.MIN_VALUE;
            for (Move x : game.getBoard().getLegalMoves()) {
                game.makeMove(x.toString());
                int score = min(game, depth - 1);
                if (score > highest) {
                    highest = score;
                    bestMove = x;
                }
                game.undoMove();
            }
            bestScore = highest;
        } else {
            int lowest = Integer.MAX_VALUE;
            for (Move x : game.getBoard().getLegalMoves()) {
                game.makeMove(x.toString());
                int score = max(game, depth - 1);
                if (score < lowest) {
                    lowest = score;
                    bestMove = x;
                }
                game.undoMove();
            }
            bestScore = lowest;
        }
        return bestMove;
    }

    private int max(Game game, int depth) {
        if (StateHandler.isTerminalNode(game) || depth == 0) {
            return StateHandler.getEvaluationOfState(game);
        }
        Board board = game.getBoard();
        int maxEval = Integer.MIN_VALUE;
        int evaluation;
        for (Move x : board.getLegalMoves()) {
            game.makeMove(x.toString());
            evaluation = min(game, depth - 1);
            game.undoMove();
            maxEval = Math.max(maxEval, evaluation);
        }
        return maxEval;
    }

    private int min(Game game, int depth) {
        if (StateHandler.isTerminalNode(game) || depth == 0) {
            return StateHandler.getEvaluationOfState(game);
        }
        Board board = game.getBoard();
        int minEval = Integer.MAX_VALUE;
        int evaluation;
        for (Move x : board.getLegalMoves()) {
            game.makeMove(x.toString());
            evaluation = max(game, depth - 1);
            game.undoMove();
            minEval = Math.min(minEval, evaluation);
        }
        return minEval;
    }
}
