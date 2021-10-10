package PseudoLegalMovesTest;

import core.Board;
import core.Game;
import core.move.Move;
import core.pieces.Pawn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;
import utilities.FENParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GeneralTest {
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    @Test
    public void gameTest() {
        /* Testing legal moves in real games.
         * Format of file is as following:
         * Line 0: possible moves in uci format separated by space
         * Line 1: move made in uci format
         * Line 2: possible moves in uci format separated by space and so on...
         * games are separated by blank line */

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/legalMoves.txt"))) {
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
                    game = context.getBean(Game.class);
                    board = game.getBoard();
                } else {
                    // System.out.println("Line: " + line);
                    if (counter % 2 == 0) {
                        /* get correct  legal moves from file */
                        List<String> legalMoves = Arrays.asList(line.split(" "));
                        /* get legal moves from board */
                        List<String> boardMoves = board.getLegalMoves().stream().map(Move::toString).collect(Collectors.toList());
                        /* check if they are same */
                        System.out.println("Legal: " + legalMoves);
                        System.out.println("Actual: " + boardMoves);

                        boolean condition = legalMoves.containsAll(boardMoves) && boardMoves.containsAll(legalMoves);
                        if (!condition) {
                            System.out.println("------------------------");
                            System.out.println("Actual pseudo legal moves: " + board.getPseudoLegalMoves().stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                            System.out.println("Moves made: " + movesMade.stream().map(x -> "\"" + x + "\"").
                                    collect(Collectors.toList()));
                            System.out.println("Pieces: " + new FENParser().getPiecePlacement(board));
                            System.out.println("Actual: " + boardMoves.stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                            System.out.println("Legal: " + legalMoves.stream().map(x -> "\"" + x + "\"").collect(Collectors.toList()));
                        }
                        Assert.assertTrue(condition);
                        counter++;
                    } else if (counter % 2 == 1) {
                        /* make move (it is provided that it is legal) */
                        System.out.println(board);
                        System.out.println("Move: " + line.trim());
                        game.makeMove(line);
                        System.out.println(board);
                        piecesPlacements.add(new FENParser().getPiecePlacement(board));
                        movesMade.add(line);
                        counter++;
                    }
                }
            }
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }


    }
}
