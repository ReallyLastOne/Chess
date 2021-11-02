package MovesTest;

import chess.core.Board;
import chess.core.Game;
import chess.utilities.FEN;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnPassantTest {
    Game game;
    Board board;

    @Before
    public void initialize() {
        game = new Game();
        board = game.getBoard();
    }

    @Test
    public void shouldEnPassant() {
        String[] moves = {"e2e4", "d7d5", "e4e5", "f7f5"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("rnbqkbnr/ppp1p1pp/8/3pPp2/8/8/PPPP1PPP/RNBQKBNR", new FEN().calculatePiecePlacement(board));
        // en passant
        game.makeMove("e5f6");
        Assert.assertEquals("rnbqkbnr/ppp1p1pp/5P2/3p4/8/8/PPPP1PPP/RNBQKBNR", new FEN().calculatePiecePlacement(board));
    }

    @Test
    public void shouldEnPassant2() {
        String[] moves = {"e2e4", "d7d5", "e4e5", "f7f5", "e5f6", "d5d4", "c2c4"};
        for(String move : moves) {
            game.makeMove(move);
        }
        Assert.assertEquals("rnbqkbnr/ppp1p1pp/5P2/8/2Pp4/8/PP1P1PPP/RNBQKBNR", new FEN().calculatePiecePlacement(board));
        // en passant
        game.makeMove("d4c3");
        Assert.assertEquals("rnbqkbnr/ppp1p1pp/5P2/8/8/2p5/PP1P1PPP/RNBQKBNR", new FEN().calculatePiecePlacement(board));
    }

}
