package tech.amereta.generator.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public final class AsciiArtProviderService {

    private static final Map<Character, String[]> CHARACTER = Map.<Character, String[]>ofEntries(
            Map.entry('A', new String[]{
                            "     _    ",
                            "    / \\   ",
                            "   / _ \\  ",
                            "  / ___ \\ ",
                            " /_/   \\_\\",
                            "          "
                    }
            ),
            Map.entry('B', new String[]{"  ____  ",
                            " | __ ) ",
                            " |  _ \\ ",
                            " | |_) |",
                            " |____/ ",
                            "        "
                    }
            ),
            Map.entry('C', new String[]{
                            "   ____ ",
                            "  / ___|",
                            " | |    ",
                            " | |___ ",
                            "  \\____|",
                            "        "
                    }
            ),
            Map.entry('D', new String[]{
                            "  ____  ",
                            " |  _ \\ ",
                            " | | | |",
                            " | |_| |",
                            " |____/ ",
                            "        "
                    }
            ),
            Map.entry('E', new String[]{
                            "  _____ ",
                            " | ____|",
                            " |  _|  ",
                            " | |___ ",
                            " |_____|",
                            "        "
                    }
            ),
            Map.entry('F', new String[]{
                            "  _____ ",
                            " |  ___|",
                            " | |_   ",
                            " |  _|  ",
                            " |_|    ",
                            "        "
                    }
            ),
            Map.entry('G', new String[]{
                            "   ____ ",
                            "  / ___|",
                            " | |  _ ",
                            " | |_| |",
                            "  \\____|",
                            "        "
                    }
            ),
            Map.entry('H', new String[]{
                            "  _   _ ",
                            " | | | |",
                            " | |_| |",
                            " |  _  |",
                            " |_| |_|",
                            "        "
                    }
            ),
            Map.entry('I', new String[]{
                            "  ___ ",
                            " |_ _|",
                            "  | | ",
                            "  | | ",
                            " |___|",
                            "      "
                    }
            ),
            Map.entry('J', new String[]{
                            "      _ ",
                            "     | |",
                            "  _  | |",
                            " | |_| |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('K', new String[]{
                            "  _  __",
                            " | |/ /",
                            " | ' / ",
                            " | . \\ ",
                            " |_|\\_\\",
                            "       "
                    }
            ),
            Map.entry('L', new String[]{
                            "  _     ",
                            " | |    ",
                            " | |    ",
                            " | |___ ",
                            " |_____|",
                            "        "
                    }
            ),
            Map.entry('M', new String[]{
                            "  __  __ ",
                            " |  \\/  |",
                            " | |\\/| |",
                            " | |  | |",
                            " |_|  |_|",
                            "         "
                    }
            ),
            Map.entry('N', new String[]{
                            "  _   _ ",
                            " | \\ | |",
                            " |  \\| |",
                            " | |\\  |",
                            " |_| \\_|",
                            "        "
                    }
            ),
            Map.entry('O', new String[]{
                            "   ___  ",
                            "  / _ \\ ",
                            " | | | |",
                            " | |_| |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('P', new String[]{
                            "  ____  ",
                            " |  _ \\ ",
                            " | |_) |",
                            " |  __/ ",
                            " |_|    ",
                            "        "
                    }
            ),
            Map.entry('Q', new String[]{
                            "   ___  ",
                            "  / _ \\ ",
                            " | | | |",
                            " | |_| |",
                            "  \\__\\_\\",
                            "        "
                    }
            ),
            Map.entry('R', new String[]{
                            "  ____  ",
                            " |  _ \\ ",
                            " | |_) |",
                            " |  _ < ",
                            " |_| \\_\\",
                            "        "
                    }
            ),
            Map.entry('S', new String[]{
                            "  ____  ",
                            " / ___| ",
                            " \\___ \\ ",
                            "  ___) |",
                            " |____/ ",
                            "        "
                    }
            ),
            Map.entry('T', new String[]{
                            "  _____ ",
                            " |_   _|",
                            "   | |  ",
                            "   | |  ",
                            "   |_|  ",
                            "        "
                    }
            ),
            Map.entry('U', new String[]{
                            "  _   _ ",
                            " | | | |",
                            " | | | |",
                            " | |_| |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('V', new String[]{
                            " __     __",
                            " \\ \\   / /",
                            "  \\ \\ / / ",
                            "   \\ V /  ",
                            "    \\_/   ",
                            "          "
                    }
            ),
            Map.entry('W', new String[]{
                            " __        __",
                            " \\ \\      / /",
                            "  \\ \\ /\\ / / ",
                            "   \\ V  V /  ",
                            "    \\_/\\_/   ",
                            "             "
                    }
            ),
            Map.entry('X', new String[]{
                            " __  __",
                            " \\ \\/ /",
                            "  \\  / ",
                            "  /  \\ ",
                            " /_/\\_\\",
                            "       "
                    }
            ),
            Map.entry('Y', new String[]{
                            " __   __",
                            " \\ \\ / /",
                            "  \\ V / ",
                            "   | |  ",
                            "   |_|  ",
                            "        "
                    }
            ),
            Map.entry('Z', new String[]{
                            "  _____",
                            " |__  /",
                            "   / / ",
                            "  / /_ ",
                            " /____|",
                            "       "
                    }
            ),
            Map.entry('a', new String[]{
                            "        ",
                            "   __ _ ",
                            "  / _` |",
                            " | (_| |",
                            "  \\__,_|",
                            "        "
                    }
            ),
            Map.entry('b', new String[]{
                            "  _     ",
                            " | |__  ",
                            " | '_ \\ ",
                            " | |_) |",
                            " |_.__/ ",
                            "        "
                    }
            ),
            Map.entry('c', new String[]{
                            "       ",
                            "   ___ ",
                            "  / __|",
                            " | (__ ",
                            "  \\___|",
                            "       "
                    }
            ),
            Map.entry('d', new String[]{
                            "      _ ",
                            "   __| |",
                            "  / _` |",
                            " | (_| |",
                            "  \\__,_|",
                            "        "
                    }
            ),
            Map.entry('e', new String[]{
                            "       ",
                            "   ___ ",
                            "  / _ \\",
                            " |  __/",
                            "  \\___|",
                            "       "
                    }
            ),
            Map.entry('f', new String[]{
                            "   __ ",
                            "  / _|",
                            " | |_ ",
                            " |  _|",
                            " |_|  ",
                            "      "
                    }
            ),
            Map.entry('g', new String[]{
                            "        ",
                            "   __ _ ",
                            "  / _` |",
                            " | (_| |",
                            "  \\__, |",
                            "  |___/ "
                    }
            ),
            Map.entry('h', new String[]{
                            "  _     ",
                            " | |__  ",
                            " | '_ \\ ",
                            " | | | |",
                            " |_| |_|",
                            "        "
                    }
            ),
            Map.entry('i', new String[]{
                            "  _ ",
                            " (_)",
                            " | |",
                            " | |",
                            " |_|",
                            "    "
                    }
            ),
            Map.entry('j', new String[]{
                            "    _ ",
                            "   (_)",
                            "   | |",
                            "   | |",
                            "  _/ |",
                            " |__/ "
                    }
            ),
            Map.entry('k', new String[]{
                            "  _    ",
                            " | | __",
                            " | |/ /",
                            " |   < ",
                            " |_|\\_\\",
                            "       "
                    }
            ),
            Map.entry('l', new String[]{
                            "  _ ",
                            " | |",
                            " | |",
                            " | |",
                            " |_|",
                            "    "
                    }
            ),
            Map.entry('m', new String[]{
                            "            ",
                            "  _ __ ___  ",
                            " | '_ ` _ \\ ",
                            " | | | | | |",
                            " |_| |_| |_|",
                            "            "
                    }
            ),
            Map.entry('n', new String[]{
                            "        ",
                            "  _ __  ",
                            " | '_ \\ ",
                            " | | | |",
                            " |_| |_|",
                            "        "
                    }
            ),
            Map.entry('o', new String[]{
                            "        ",
                            "   ___  ",
                            "  / _ \\ ",
                            " | (_) |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('p', new String[]{
                            "        ",
                            "  _ __  ",
                            " | '_ \\ ",
                            " | |_) |",
                            " | .__/ ",
                            " |_|    "
                    }
            ),
            Map.entry('q', new String[]{
                            "        ",
                            "   __ _ ",
                            "  / _` |",
                            " | (_| |",
                            "  \\__, |",
                            "     |_|"
                    }
            ),
            Map.entry('r', new String[]{
                            "       ",
                            "  _ __ ",
                            " | '__|",
                            " | |   ",
                            " |_|   ",
                            "       "
                    }
            ),
            Map.entry('s', new String[]{
                            "      ",
                            "  ___ ",
                            " / __|",
                            " \\__ \\",
                            " |___/",
                            "      "
                    }
            ),
            Map.entry('t', new String[]{
                            "  _   ",
                            " | |_ ",
                            " | __|",
                            " | |_ ",
                            "  \\__|",
                            "      "
                    }
            ),
            Map.entry('u', new String[]{
                            "        ",
                            "  _   _ ",
                            " | | | |",
                            " | |_| |",
                            "  \\__,_|",
                            "        "
                    }
            ),
            Map.entry('v', new String[]{
                            "        ",
                            " __   __",
                            " \\ \\ / /",
                            "  \\ V / ",
                            "   \\_/  ",
                            "        "
                    }
            ),
            Map.entry('w', new String[]{
                            "           ",
                            " __      __",
                            " \\ \\ /\\ / /",
                            "  \\ V  V / ",
                            "   \\_/\\_/  ",
                            "           "
                    }
            ),
            Map.entry('x', new String[]{
                            "       ",
                            " __  __",
                            " \\ \\/ /",
                            "  >  < ",
                            " /_/\\_\\",
                            "       "
                    }
            ),
            Map.entry('y', new String[]{
                            "        ",
                            "  _   _ ",
                            " | | | |",
                            " | |_| |",
                            "  \\__, |",
                            "  |___/ "
                    }
            ),
            Map.entry('z', new String[]{
                            "      ",
                            "  ____",
                            " |_  /",
                            "  / / ",
                            " /___|",
                            "      "
                    }
            ),
            Map.entry('0', new String[]{
                            "   ___  ",
                            "  / _ \\ ",
                            " | | | |",
                            " | |_| |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('1', new String[]{
                            "  _ ",
                            " / |",
                            " | |",
                            " | |",
                            " |_|",
                            "    "
                    }
            ),
            Map.entry('2', new String[]{
                            "  ____  ",
                            " |___ \\ ",
                            "   __) |",
                            "  / __/ ",
                            " |_____|",
                            "        "
                    }
            ),
            Map.entry('3', new String[]{
                            "  _____ ",
                            " |___ / ",
                            "   |_ \\ ",
                            "  ___) |",
                            " |____/ ",
                            "        "
                    }
            ),
            Map.entry('4', new String[]{
                            "  _  _   ",
                            " | || |  ",
                            " | || |_ ",
                            " |__   _|",
                            "    |_|  ",
                            "         "
                    }
            ),
            Map.entry('5', new String[]{
                            "  ____  ",
                            " | ___| ",
                            " |___ \\ ",
                            "  ___) |",
                            " |____/ ",
                            "        "
                    }
            ),
            Map.entry('6', new String[]{
                            "   __   ",
                            "  / /_  ",
                            " | '_ \\ ",
                            " | (_) |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('7', new String[]{
                            "  _____ ",
                            " |___  |",
                            "    / / ",
                            "   / /  ",
                            "  /_/   ",
                            "        "
                    }
            ),
            Map.entry('8', new String[]{
                            "   ___  ",
                            "  ( _ ) ",
                            "  / _ \\ ",
                            " | (_) |",
                            "  \\___/ ",
                            "        "
                    }
            ),
            Map.entry('9', new String[]{
                            "   ___  ",
                            "  / _ \\ ",
                            " | (_) |",
                            "  \\__, |",
                            "    /_/ ",
                            "        "
                    }
            )
    );

    private static final int HEIGHT = 6;

    public String writeAscii(String string) {
        final StringBuilder stringInAscii = new StringBuilder();
        final List<String[]> charsInAscii = string.chars().mapToObj(i -> (char) i).map(CHARACTER::get).toList();

        IntStream.range(0, HEIGHT).forEach(i -> {
            charsInAscii.forEach(
                    line -> stringInAscii.append(line[i])
            );
            stringInAscii.append("\n");
        });

        return stringInAscii.toString();
    }
}
