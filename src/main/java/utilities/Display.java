package utilities;

import core.Board;
import core.Cell;
import core.pieces.*;

public class Display {

    public static void display(Cell[][] cells) {
        for(int i = 7; i >= 0; i--){
            System.out.print("| ");
            for(int j = 0; j < 8; j++){
                if(cells[j][i] == null) System.out.println("(i=" + i +",j="+j+")" );
                System.out.print(convertPieceToSymbol(cells[j][i].getPiece()));
                System.out.print(" | ");
            }
            System.out.println(i + 1);
        }
        System.out.println("  A   B   C   D   E   F   G   H  ");
        printBlankLines(5);
    }

    public static void printBlankLines(int n){
        for(int i = 0; i < n; i++){
            System.out.println("");
        }
    }

    /** Returns 1-char String representation of given Piece. */
    public static String convertPieceToSymbol(Piece piece){
        if(piece == null) return " ";
        return piece.toSymbol();
    }
}
