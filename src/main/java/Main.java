import core.Game;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.AppConfig;

import java.util.Arrays;

public class Main {
    static String[] moves = {"a2a4", "a7a5", "b2b4", "a8a6", "b4a5", "a6b6", "a5a6", "b6c6", "a6a7", "c6d6", "a7a8r"};


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
