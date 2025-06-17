import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameSettings settings = new GameSettings();
        GameStatistics stats = new GameStatistics();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть символ для гравця 1: ");
        settings.player1Symbol = scanner.next().charAt(0);
        System.out.print("Введіть символ для гравця 2: ");
        settings.player2Symbol = scanner.next().charAt(0);
        System.out.print("Введіть розмір поля (напр. 3): ");
        settings.fieldSize = scanner.nextInt();

        boolean playAgain = true;

        while (playAgain) {
            GameField field = new GameField();
            field.field = new char[settings.fieldSize][settings.fieldSize];
            initializeField(field, settings);

            char winner = playGame(field, settings, scanner);

            stats.totalGames++;
            if (winner == settings.player1Symbol) {
                stats.player1Wins++;
            } else if (winner == settings.player2Symbol) {
                stats.player2Wins++;
            } else {
                stats.draws++;
            }

            printStatistics(stats);

            System.out.print("Грати ще раз? (y/n): ");
            playAgain = scanner.next().equalsIgnoreCase("y");
        }
    }

    public static void initializeField(GameField field, GameSettings settings) {
        for (int i = 0; i < settings.fieldSize; i++) {
            for (int j = 0; j < settings.fieldSize; j++) {
                field.field[i][j] = ' ';
            }
        }
    }

    public static void printField(GameField field, GameSettings settings) {
        for (int i = 0; i < settings.fieldSize; i++) {
            for (int j = 0; j < settings.fieldSize; j++) {
                System.out.print("[" + field.field[i][j] + "]");
            }
            System.out.println();
        }
    }

    public static char playGame(GameField field, GameSettings settings, Scanner scanner) {
        char currentPlayer = settings.player1Symbol;
        int moves = 0;
        int maxMoves = settings.fieldSize * settings.fieldSize;

        while (true) {
            printField(field, settings);
            System.out.println("Хід гравця " + currentPlayer);

            int row, col;
            do {
                System.out.print("Введіть рядок (0-" + (settings.fieldSize - 1) + "): ");
                row = scanner.nextInt();
                System.out.print("Введіть стовпець (0-" + (settings.fieldSize - 1) + "): ");
                col = scanner.nextInt();
            } while (row < 0 );
            field.field[row][col] = currentPlayer;
            moves++;

            if (checkWin(field, settings, currentPlayer)) {
                printField(field, settings);
                System.out.println("Гравець " + currentPlayer + " переміг!");
                return currentPlayer;
            } else if (moves == maxMoves) {
                printField(field, settings);
                System.out.println("Нічия!");
                return ' ';
            }

            currentPlayer = (currentPlayer == settings.player1Symbol) ? settings.player2Symbol : settings.player1Symbol;
        }
    }

    public static boolean checkWin(GameField field, GameSettings settings, char symbol) {
        for (int i = 0; i < settings.fieldSize; i++) {
            boolean win = true;
            for (int j = 0; j < settings.fieldSize; j++) {
                if (field.field[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        for (int j = 0; j < settings.fieldSize; j++) {
            boolean win = true;
            for (int i = 0; i < settings.fieldSize; i++) {
                if (field.field[i][j] != symbol) {
                    win = false;
                    break;
                }
            }
            if (win) return true;
        }
        boolean diag1 = true;
        for (int i = 0; i < settings.fieldSize; i++) {
            if (field.field[i][i] != symbol) {
                diag1 = false;
                break;
            }
        }
        if (diag1) return true;

        boolean diag2 = true;
        for (int i = 0; i < settings.fieldSize; i++) {
            if (field.field[i][settings.fieldSize - 1 - i] != symbol) {
                diag2 = false;
                break;
            }
        }
        return diag2;
    }

    public static void printStatistics(GameStatistics stats) {
        System.out.println(" Статистика:");
        System.out.println("Ігор зіграно: " + stats.totalGames);
        System.out.println("Перемог гравця 1: " + stats.player1Wins);
        System.out.println("Перемог гравця 2: " + stats.player2Wins);
        System.out.println("Нічиї: " + stats.draws);
    }
}
