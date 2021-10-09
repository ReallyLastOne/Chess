package core;

import core.move.Move;
import core.move.MoveConverter;
import core.move.MoveValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import utilities.Display;
import static core.GameUtilities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Class that controls all flow of the game.
 * */
@Component
@Scope("prototype")
@NoArgsConstructor
public final class Game {
    public static int nr = 0;

    @Getter
    private Board board;
    @Getter
    private GameStatus gameStatus = GameStatus.IN_PROGRESS;


    @Autowired
    public Game(Board board) {
        this.board = board;
        nr++;
    }

    public Game(String FEN) {
        board = new Board(FEN);
    }

    public void displayBoard() {
        System.out.println(board);
    }

    public void makeMove(String move) {
        if(gameStatus == GameStatus.IN_PROGRESS) {
           // board.displayBoard();
            Move currentMove = MoveConverter.linkMove(MoveConverter.convert(move), board);
            //System.out.println("in make move: " + currentMove.toString2());
            if (MoveValidator.isValid(currentMove, board)) {
                board.updatePawnsStatus();
                //System.out.println("Make move: " + currentMove.getInfo());
                board.executeMove(currentMove);
                updateGameStatus();
            } else {
                //System.out.println(MoveValidator.isValid(currentMove, board));
                throw new IllegalArgumentException("Wrong move");
            }
        }
    }

    private void updateGameStatus() {
        if (MoveValidator.isKingInCheckmate(board, board.isTurn())) {
            gameStatus = board.isTurn() ? GameStatus.BLACK_WIN : GameStatus.WHITE_WIN;
            System.out.println("CHECKMATED, status: " + gameStatus);
        }
    }

    public void printAllMoves() {
        board.printAllPossibleMoves();
    }

    public Cell getWhiteKingCell() {
        return board.getWhiteKingCell();
    }

    public Cell getBlackKingCell() {
        return board.getBlackKingCell();
    }

    public void undoMove() {
        board.undoMove();
    }
}
