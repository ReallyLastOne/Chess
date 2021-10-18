package utilities;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int GRID_SIZE = 8;

    /** Map that stores 0-based integer equivalence of chess board column. */
    public static final Map<Character, Integer> COLUMN_TO_INT = new HashMap<>() {{
        put('a', 0);
        put('b', 1);
        put('c', 2);
        put('d', 3);
        put('e', 4);
        put('f', 5);
        put('g', 6);
        put('h', 7);
    }};

    public static final Map<Integer, Character> INT_TO_COLUMN = new HashMap<>() {{
        put(0, 'a');
        put(1, 'b');
        put(2, 'c');
        put(3, 'd');
        put(4, 'e');
        put(5, 'f');
        put(6, 'g');
        put(7, 'h');
    }};
}
