# Chess

Fully functional game of chess with checkmate detection, draw detection and promotion availability. 

## Requirements

- JDK 15

## Installation

1. Download JDK 15 from https://www.oracle.com/java/technologies/javase/jdk15-archive-downloads.html
2. Download Chess.jar from releases.
3. Open terminal in folder containing Chess.jar and run

   ```
   java --add-opens java.base/java.lang=ALL-UNNAMED -jar Chess.jar
   ```
or if you want to code 
1. Clone this repository using command ```git clone https://github.com/ReallyLastOne/Chess```
2. Run by your favorite IDE.
## Usage
- If you run application using command line, simply type moves alternately for white and black in UCI format. 
- If you want to code yourself, here is code snippet:
```
        Game game = new Game("r3qrk1/1bp2pb1/p2pn1pp/1p6/PP1PPn1B/4NN2/2B2PPP/R2QR1K1 b - - 0 18"); 
        System.out.println(game.getBoard());
        game.makeMove("a6a5");
        System.out.println(game.getBoard().getLegalMoves());
```
 that creates game from FEN, prints board, makes move and print all possible legal moves for current position.

## License

[MIT](LICENSE) © 
