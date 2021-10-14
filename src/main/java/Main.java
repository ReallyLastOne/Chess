import core.Game;
import spring.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import utilities.FEN;

import java.util.Arrays;

public class Main {
    // static final String[] moves = new String[] {"E2 E4", "E7 E5", "D1 F3", "B8 A6", "F1 C4", "B7 B6", "F3 F7"};//, "A2 A4", "A7 A5", "B2 B3"};
    //static String[] moves = new String[] {"D2 D4", "C7 C5", "D4 C5", "D8 A5", "B1 C3", "H7 H5", "E1 D2", "A5 C5", "D2 D3", "C5 C4", "D3 E3"};//, "D3 D4"}; // illegal move, white king is in check after C5 C4
   /* static String[] moves = {"e2e4", "e7e5", "d1h5", "a7a5", "f1b5", "c7c6", "g1f3", "d7d6", "b1c3", "g8f6",
            "d2d3", "b8d7", "c1d2", "f8e7", "e1c1"}; // pozycja do roszady krotkiej i dlugiej bialych i krotkiej czarnych
*/
    //static String[] moves = {"e2e4", "a7a5", "e4e5", "a5a4","e5e6", "a4a3", "e6d7", "e8d7"}; ruchy pionkami i check krola
    //static String[] moves = {"d2d4", "a7a5", "d4d5", "a5a4","d5d6", "a4a3", "d6c7", "a3b2", "c7b8n", "b2c1q"}; // promocja
    /*
    static String[] moves = ("d2d4 g8f6 c2c4 c7c5 d4d5 e7e6 b1c3 e6d5 c4d5 d7d6 g1f3 g7g6 c1g5 f8g7 f3d2 h7h6 " +
           "g5h4 g6g5 h4g3 f6h5 d2c4 h5g3 h2g3 e8g8 e2e3 d8e7 f1e2 f8d8 e1g1 b8d7 a2a4 d7e5 " +
           "c4e5 e7e5 a4a5 a8b8 a1a2 c8d7 c3b5 d7b5 e2b5 b7b6 a5a6 b8c8 d1d3 c8c7 b2b3 e5c3 " +
           "d3c3 g7c3 a2c2 c3f6 g3g4 c7e7 c2c4 d8c8 g2g3 f6g7 f1d1 c8f8 d1d3 g8h7 g1g2 h7g6 " +
           "d3d1 h6h5 g4h5 g6h5 g3g4 h5g6 c4c2 f8h8 b5d3 g6f6 g2g3 e7e8 d3b5 e8e4 c2c4 e4c4 " +
            "b3c4 f6e7 b5a4 g7e5 g3f3 h8h4 d1g1 f7f5").split(" "); // valid game*/
    // todo: tests using python chess (analyze fen after some moves, legal moves etc)
    /*static String[] moves = {"d2d3", "h7h5", "g1f3", "c7c5", "f3d4", "g7g5", "f2f3", "f8h6", "h2h4", "d8a5", "b1c3",
            "a5b6", "c3b5", "f7f6", "e1d2", "b8a6", "d2e1", "c5d4", "h1g1", "b6c5", "e2e4", "e8f7"};*/
    //static String[] moves = {"e2e4", "d7d5", "e4e5", "f7f5", "e5f6", "d5d4", "c2c4"};
    static String[] moves = {"d2d3", "h7h5", "g1f3", "c7c5", "f3d4", "g7g5", "f2f3", "f8h6", "h2h4", "d8a5", "b1c3", "a5b6",
            "c3b5", "f7f6", "e1d2", "b8a6", "d2e1", "c5d4", "h1g1", "b6c5", "e2e4", "e8f7"};

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Game game = context.getBean(Game.class);
        System.out.println("Initial moves: " + Arrays.asList(moves));
        play(game, true);
        //System.out.println(new FENParser().getPiecePlacement(game.getBoard()));
        System.out.println(new FEN().of(game.getBoard()));
    }

    private static void play(Game game, boolean display) {
        for (String move : moves) {
            System.out.println("Move: " + move);
            game.makeMove(move);
            if (display) game.displayBoard();
            System.out.println(game.getBoard().getLegalMoves());
        }
    }
}
