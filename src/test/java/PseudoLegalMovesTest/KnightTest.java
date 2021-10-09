package PseudoLegalMovesTest;

import core.Board;
import core.Game;
import core.move.Move;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class KnightTest {
    List<List<String>> validMoves = Arrays.asList(
            Arrays.asList("b1a3", "b1c3"),
            Arrays.asList("g1f3", "g1h3"),
            Arrays.asList("b8a6", "b8c6"),
            Arrays.asList("g8f6", "g8h6")
    );
    Game game;
    Board board;

    @Before
    public void initialize() {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        game = context.getBean(Game.class);
        board = game.getBoard();
    }

    @Test
    public void knightTest() {
        List<String> moves = board.getCellByName("b1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("b1")).stream().map(Move::toString).collect(Collectors.toList());
        Assert.assertTrue(moves.containsAll(validMoves.get(0)) && validMoves.get(0).containsAll(moves));

        moves = board.getCellByName("g1").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("g1")).stream().map(Move::toString).collect(Collectors.toList());
        Assert.assertTrue(moves.containsAll(validMoves.get(1)) && validMoves.get(1).containsAll(moves));

        moves = board.getCellByName("b8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("b8")).stream().map(Move::toString).collect(Collectors.toList());
        Assert.assertTrue(moves.containsAll(validMoves.get(2)) && validMoves.get(2).containsAll(moves));

        moves = board.getCellByName("g8").getPiece().calculatePseudoLegalMoves(board, board.getCellByName("g8")).stream().map(Move::toString).collect(Collectors.toList());
        Assert.assertTrue(moves.containsAll(validMoves.get(3)) && validMoves.get(3).containsAll(moves));

    }

    @After
    public void after() {
        game = null;
    }
}
