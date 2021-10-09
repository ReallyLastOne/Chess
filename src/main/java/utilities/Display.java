package utilities;

import core.Board;
import core.Cell;
import core.pieces.*;

public class Display {

    public static void displayBoard(Board board){
        Cell[][] cells = board.getCells();
        for(int i = 7; i >= 0; i--){
            System.out.print("| ");
            for(int j = 0; j < 8; j++){
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

    public static String convertPieceToSymbol(Piece piece){
        if(piece instanceof Pawn) return piece.isWhite() ? "P" : "p";
        if(piece instanceof Rook) return piece.isWhite() ? "R" : "r";
        if(piece instanceof Queen) return piece.isWhite() ? "Q" : "q";
        if(piece instanceof Knight) return piece.isWhite() ? "N" : "n";
        if(piece instanceof Bishop) return piece.isWhite() ? "B" : "b";
        if(piece instanceof King) return piece.isWhite() ? "K" : "k";
        return " ";
    }
}
