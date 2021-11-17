package chess.core;

import chess.core.move.Move;
import chess.core.move.MoveConverter;
import chess.core.move.MoveValidator;
import lombok.Getter;

import java.util.Scanner;

import static chess.utilities.GameUtilities.GameStatus;

/**
 * Class that controls all flow of the game.
 */

public final class Game {
    private final Scanner scanner = new Scanner(System.in);

    @Getter
    private Board board;
    @Getter
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;


    public Game() {
        board = new Board();
    }

    public Game(Board board) {
        this.board = board;
    }

    public Game(String FEN) {
        board = new Board(FEN);
    }

    /**
     * @param move to make in UCI format
     */
    public void makeMove(String move) {
        try {
            if (gameStatus == GameStatus.IN_PROGRESS) {
                Move currentMove = MoveConverter.convert(move, board);
                if (MoveValidator.isValid(currentMove, board)) {
                    board.executeMove(currentMove);
                    updateGameStatus();
                } else {
                    throw new IllegalArgumentException("Wrong move");
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Wrong move");
        }

    }

    public void undoMove() {
        board.undoMove();
        updateGameStatus();
    }

    private void updateGameStatus() {
        if (MoveValidator.isKingInCheckmate(board, board.isTurn())) {
            gameStatus = board.isTurn() ? GameStatus.BLACK_WIN : GameStatus.WHITE_WIN;
            //System.out.println("CHECKMATED, status: " + gameStatus);
        } else if (board.isDraw()) {
            gameStatus = GameStatus.DRAW;
            //System.out.println("Game drawn.");
        } else {
            gameStatus = GameStatus.IN_PROGRESS;
        }
    }

    /**
     * Method to start the game against yourself.
     */
    public void run() {
        System.out.println(board);
        while (gameStatus == GameStatus.IN_PROGRESS) {
            System.out.println(board.isTurn() ? "White to move. " : "Black to move.");
            String move = scanner.nextLine();
            this.makeMove(move);
            System.out.println(board);
        }
    }
}
