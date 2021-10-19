import core.Game;
import spring.AppConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import utilities.FEN;

import java.util.Arrays;

public class Main {
    /*static String[] moves = {"d2d3", "h7h5", "g1f3", "c7c5", "f3d4", "g7g5", "f2f3", "f8h6", "h2h4", "d8a5", "b1c3", "a5b6",
            "c3b5", "f7f6", "e1d2", "b8a6", "d2e1", "c5d4", "h1g1", "b6c5", "e2e4", "e8f7"};
*/

    static String[] moves = {"g1f3", "g8f6", "f3g1", "f6g8", "g1f3", "g8f6", "f3g1", "f6g8", "g1f3", "g8f6", "f3g1",
            "f6g8", "g1f3", "g8f6", "f3g1", "f6g8", "g1f3"};

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        Game game = context.getBean(Game.class);
        System.out.println("Initial moves: " + Arrays.asList(moves));
        play(game, true);
    }

    private static void play(Game game, boolean display) {
        for (String move : moves) {
            System.out.println("Move: " + move);
            game.makeMove(move);
            if (display) game.displayBoard();
        }
    }
}
