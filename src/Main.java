import tob.service.TOB;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        TOB tob = new TOB();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        while (!input.equals("exit")){
            String response = tob.getBestPrice(input);
            System.out.println(response);
            input = scanner.next();
        }
    }
}