import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
public class Wordle {
    
    private static String hiddenWord = "word";
    private WordleGWindow gw;
    public static String secretWord = PopularFiveLetterWords.getWord();
    public static void main(String[] args){
        new Wordle().run();
    }
    public void run(){
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
         new PopularFiveLetterWords();
        System.out.println(secretWord);
    }
    private static final int WORD_LENGTH = 5;
     
    public void enterAction(String guess){
        if (!isValidWord(guess)) {
            gw.showMessage("Not in word list");
            return; 
        }else if(guess.equalsIgnoreCase(secretWord)) {
            handleCorrectGuess(guess);
        }else{
            handleIncorrectGuess(guess);
            gw.setCurrentRow(gw.getCurrentRow() + 1);
        }
    }
    private boolean isValidWord(String guess){
        String[] list = WordleDictionary.getList();
        for(String word : list){
            if(word.equalsIgnoreCase(guess)){
                return true;
                }
            }
        return false;
    }
    private void handleCorrectGuess(String guess) {
        for (int i = 0; i < WORD_LENGTH; i++) {
            gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
            gw.setKeyColor(guess.substring(i, i + 1), gw.CORRECT_COLOR);
            }   
        gw.showMessage("Congratulations!!!");
    }
    private void handleIncorrectGuess(String guess){
         boolean[] secretWordUsed = new boolean[5];
         boolean[] guessUsed = new boolean[5];
        for (int i = 0; i < 5; i++) {
            if (guess.substring(i, i + 1).equalsIgnoreCase(secretWord.substring(i, i + 1))){
                gw.setSquareColor(gw.getCurrentRow(), i, gw.CORRECT_COLOR);
                gw.setKeyColor(guess.substring(i, i + 1), gw.CORRECT_COLOR);
                secretWordUsed[i] = true;
                guessUsed[i] = true;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!guessUsed[i]){
                String letter = guess.substring(i, i + 1);
                boolean found = false;
            for (int j = 0; j < 5; j++) {
                if (!secretWordUsed[j] && secretWord.substring(j, j + 1).equalsIgnoreCase(letter)) {
                    gw.setSquareColor(gw.getCurrentRow(), i, gw.PRESENT_COLOR);
                    gw.setKeyColor(letter, gw.PRESENT_COLOR);
                    secretWordUsed[j] = true; // Mark this secret word letter as used
                    found = true;
                    break;
                }
            }
                if (!found) {
                    gw.setKeyColor(letter, gw.MISSING_COLOR);
                }
            }
        }
          gw.showMessage("Nice Guess!");
    }
}