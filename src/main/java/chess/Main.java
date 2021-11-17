package chess;

import chess.ai.Minimax;
import chess.core.Game;

public class Main {
    public static void main(String[] args) {
        Game game = new Game("8/8/8/8/8/3k4/8/2rK4 w - - 0 1");
        System.out.println(game.getBoard());
        Minimax minimax = new Minimax();
        System.out.println(minimax.solve(game, 2));
        System.out.println(minimax.getBestScore());

        Game game2 = new Game("8/8/8/8/8/r2k4/8/3K4 b - - 0 1");
        System.out.println(game2.getBoard());
        Minimax minimax2 = new Minimax();
        System.out.println(minimax2.solve(game2, 2));
        System.out.println(minimax2.getBestScore());
    }
}
