import java.util.Random;
import java.util.Scanner;

public class GuessTheNumber {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int lowerBound = 1;
        int upperBound = 100;
        int numberOfRounds = 3;
        int totalScore = 0;

        System.out.println("Welcome to Guess the Number!");
        System.out.println("Total number of attempts for ");

        for (int round = 1; round <= numberOfRounds; round++) {
            int targetNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;
            int attempts = 0;
            int maxAttempts = 5; // You can adjust the maximum number of attempts

            System.out.println("\nRound " + round + ": Guess the number between " + lowerBound + " and " + upperBound);

            while (attempts <= maxAttempts) {
                System.out.print("Enter your guess: ");
                int userGuess = scanner.nextInt();
                attempts++;

                int difference = Math.abs(targetNumber - userGuess);
                int range = 10; // Range can be changed with the difficulty of the game

                if (userGuess == targetNumber) {
                    System.out.println("Congratulations! You guessed the number in " + attempts + " attempts.");
                    int roundScore = maxAttempts - attempts + 1;
                    totalScore += roundScore;
                    System.out.println("Round Score: " + roundScore + " | Total Score: " + totalScore);
                    break;
                } else if (difference <= range) {
                    if(userGuess<targetNumber){
                        System.out.println("Too low! Try again.");
                    }
                    else{
                        System.out.println("Too high! Try again.");
                    }
                    double closenessPercentage = (1 - (difference / (double) range)) * 100;
                    System.out.println("Close! You are " + String.format("%.2f", closenessPercentage) + "% close.");
                } else if (userGuess < targetNumber) {
                    System.out.println("Too low! Try again.");
                } else {
                    System.out.println("Too high! Try again.");
                }
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry, you've run out of attempts. The correct number was: " + targetNumber);
            }

            // Ask if the user wants to play another round
            if (round < numberOfRounds) {
                System.out.print("Do you want to play another round? (yes/no): ");
                String playAgain = scanner.next().toLowerCase();
                if (!playAgain.equals("yes")) {
                    break;
                }
            }
        }

        System.out.println("Thanks for playing! Your total score is: " + totalScore);
        scanner.close();
    }
}
