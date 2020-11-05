import java.util.Scanner;

public class GameIO {
    private static Game game = new Game();
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean closeApp = false;

    public static void start(){
        System.out.println("Welcome in Bowling Game");

        while (!closeApp) {
            System.out.println("Enter number of knocked pins to add roll (Enter 'e' to exit)");
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("e")){
                closeApp = true;
            }else{
                try{
                    if(!game.roll(Integer.parseInt(input))){
                        endGame();
                    }
                }catch(NumberFormatException e){
                    System.out.println("Wrong character. Please try again");
                }
            }
        }
    }

    private static void endGame(){
        System.out.println("Your score is: " + game.score());
        System.out.println("Enter 'n' to start new game or any other key to exit");
        String input = scanner.nextLine();
        if(input.equalsIgnoreCase("n")){
            game = new Game();
        }else{
            closeApp = true;
        }
    }
}
