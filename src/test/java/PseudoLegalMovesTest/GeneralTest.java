package PseudoLegalMovesTest;

import chess.core.Board;
import chess.core.Game;
import chess.core.move.Move;
import chess.utilities.FEN;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralTest {
    Game game;
    Board board;

    @BeforeEach
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void gameTest() throws IOException {
        /* Testing legal moves in real games.
         * Format of file is as following:
         * Line 0: possible moves in uci format separated by space
         * Line 1: move made in uci format
         * Line 2: possible moves in uci format separated by space and so on...
         * games are separated by blank line */

        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/legalMoves.txt"));
        StringBuffer textBuffer = new StringBuffer();
        String line;
        int counter = 0;
        List<String> piecesPlacements = new ArrayList<>();
        List<String> movesMade = new ArrayList<>();
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isBlank()) {
                counter = 0;
                movesMade.clear();
                System.out.println("\n");
                game = new Game();
                board = game.getBoard();
            } else {
                // System.out.println("Line: " + line);
                if (counter % 2 == 0) {
                    /* get correct  legal moves from file */
                    List<String> legalMoves = Arrays.asList(line.split(" "));
                    /* get legal moves from board */
                    List<String> boardMoves = board.getLegalMoves().stream().map(Move::toString).collect(Collectors.toList());
                    /* check if they are same */
                    boolean condition = legalMoves.containsAll(boardMoves) && boardMoves.containsAll(legalMoves);
                    if (!condition) {
                        System.out.println("------------------------");
                        System.out.println("pseudoLegal = " + board.getPseudoLegalMoves().stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                        System.out.println("movesMade = " + movesMade.stream().map(x -> "\"" + x + "\"").
                                collect(Collectors.toList()));
                        System.out.println("actual_fen = " + "\"" + FEN.calculatePiecePlacement(board) + "\"");
                        System.out.println("actual = " + boardMoves.stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                        System.out.println("legal = " + legalMoves.stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                        System.out.println(board);
                    }
                    Assertions.assertTrue(condition);
                    counter += 1;
                } else if (counter % 2 == 1) {
                    /* make move (it is provided that it is legal) */
                    System.out.println("_____________________");
                    System.out.println(board);
                    System.out.println("Move: " + line.trim());
                    game.makeMove(line);
                    System.out.println(board);
                    piecesPlacements.add(FEN.calculatePiecePlacement(board));
                    movesMade.add(line);
                    counter += 1;
                }
            }
        }
    }
}
