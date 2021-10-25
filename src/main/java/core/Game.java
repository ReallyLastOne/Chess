package core;

import core.move.Move;
import core.move.MoveConverter;
import core.move.MoveValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static core.GameUtilities.GameStatus;

/**
 * Class that controls all flow of the game.
 */
@Component
@Scope("prototype")
@NoArgsConstructor
public final class Game {

    @Getter
    private Board board;
    @Getter
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;

    /**
     * Constructor when we don't want specific board.
     */
    @Autowired
    public Game(Board board) {
        this.board = board;
    }

    /**
     * Constructor for game when providing FEN.r
     */
    public Game(String FEN) {
        board = new Board(FEN);
    }

    public void displayBoard() {
        System.out.println(board);
    }

    /**
     * @param move to make in UCI format
     */
    public void makeMove(String move) {
        if (gameStatus == GameStatus.IN_PROGRESS) {
            Move currentMove = MoveConverter.convert(move, board);
            if (MoveValidator.isValid(currentMove, board)) {
                board.executeMove(currentMove);
                updateGameStatus();
            } else {
                throw new IllegalArgumentException("Wrong move");
            }
        }
    }

    /**
     * Updates game status.
     */
    private void updateGameStatus() {
        if (MoveValidator.isKingInCheckmate(board, board.isTurn())) {
            gameStatus = board.isTurn() ? GameStatus.BLACK_WIN : GameStatus.WHITE_WIN;
            System.out.println("CHECKMATED, status: " + gameStatus);
        } else if (board.isDraw()) {
            gameStatus = GameStatus.DRAW;
            System.out.println("Game drawn.");
        }
    }
}
