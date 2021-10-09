package utilities;

import core.Board;
import core.Cell;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utilities.Constants.GRID_SIZE;
import static utilities.Display.convertPieceToSymbol;

public class FENParser {
    private String piecePlacement;

    public FENParser() {

    }

    private void calculatePiecePlacement(Board board) {
        Cell[][] cells = board.getCells();

        piecePlacement = "";
        int freeCount = 0;
        for(int i = GRID_SIZE - 1; i >= 0; i--) {
            for(int j = 0; j < GRID_SIZE; j++) {
                if (cells[j][i].getPiece() != null) {
                    if (freeCount != 0) {
                        piecePlacement += freeCount;
                        freeCount = 0;
                    }
                    piecePlacement += convertPieceToSymbol(cells[j][i].getPiece());
                } else {
                    freeCount++;
                }
            }
            if(freeCount != 0) {
                piecePlacement += freeCount;
                freeCount = 0;
            }
            piecePlacement += "/";
        }
        piecePlacement = piecePlacement.substring(0, piecePlacement.length() - 1);
    }

    public String getPiecePlacement(Board board) {
        calculatePiecePlacement(board);
        return piecePlacement;
    }

    public String calculateFEN(Board board) {



        return piecePlacement + (board.isTurn() ? "w" : "b");
    }
}

