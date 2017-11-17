/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sockets.server.controller;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Character.isLetter;
import java.net.Socket;
import sockets.server.model.Hangman;
import sockets.server.model.RandomWord;

/**
 *
 * @author darkferi
 */
public class ServerController {
    
    private boolean isStarted = false;
    private boolean isMiddle = false;
    private Hangman hangmanGame;
    private StringBuilder wordScreen;
    private static final char SHOW_SCREEN = '_';
    private char guessChar;
    
    
    public void disconnect(Socket playerSocket) throws IOException{
        playerSocket.close();
    }
    
    public void intro (PrintWriter outToPlayer){
        new Hangman().showIntro(outToPlayer);
    }
    
    public void checkUserInput(String userInput, Socket playerSocket, PrintWriter outToPlayer){
            
         
                if(userInput.startsWith("start") && userInput.length()==5){
                   
                    if (!isStarted){
                        if(!isMiddle){
                            RandomWord findWord = new RandomWord();
                            String chosenWord = findWord.getTheWord();
                            wordScreen = new StringBuilder(chosenWord);

                            for (int i = 0; i < wordScreen.length(); i++){
                                wordScreen.setCharAt(i, SHOW_SCREEN);                                   
                            }
                            hangmanGame = new Hangman(chosenWord, chosenWord.length());
                            outToPlayer.println("\n---------------------------------------------------------------------------\n");
                            hangmanGame.showState(wordScreen,outToPlayer);          
                            isStarted = true;
                        }
                        else{
                            hangmanGame.showState(wordScreen,outToPlayer);
                            isMiddle = false;
                            isStarted = true;
                        }
                    }
                    
                                       
                    else{
                        if (!isMiddle){
                            outToPlayer.println("\nYou started the game already!!!\n ");
                            hangmanGame.showState(wordScreen,outToPlayer); 
                        }
                    }
                
                                   
                }
 
                else if((userInput.startsWith("rule")) && (userInput.length() == 4) && (!isMiddle)){
                    if(isStarted == true){
                        hangmanGame.showRules(outToPlayer);
                        hangmanGame.showState(wordScreen,outToPlayer);
                    }
                    else{
                        new Hangman().showRules(outToPlayer);
                    }
                } 
                
                else if(userInput.startsWith("word ") && (!isMiddle)){
                    if(isStarted == true){
                        userInput = userInput.substring(5).trim();
                        wordScreen = hangmanGame.hangmanCheckWord(userInput, wordScreen,outToPlayer);
                        //outToPlayer.println("\n");
                        if(!hangmanGame.gameWinLoseState){
                            outToPlayer.println("\n");
                            hangmanGame.showState(wordScreen,outToPlayer);
                        }
                        else{
                            outToPlayer.println("\nIf you want to continue write START command. Unless write EXIT...\n ");
                            isMiddle = true;
                            isStarted = false;
                            hangmanGame.gameWinLoseState = false;
                        }
                    }
                    else{
                        outToPlayer.println("\nYou have not started the game yet. First write START command.\n ");
                    }
                }
                else{
                    if(userInput.length()==0){
                        outToPlayer.println("\nYou didn't type anything or you are typing too fast!!!!\n");
                    }
                    else{
                        
                        guessChar = userInput.charAt(0);
                        if( (userInput.length()==1) && (isLetter(guessChar)) && (isStarted == true) && (!isMiddle) ){
                            wordScreen = hangmanGame.hangmanCheckChar(guessChar, wordScreen,outToPlayer);
                            //outToPlayer.println("\n");
                            if(!hangmanGame.gameWinLoseState){
                                hangmanGame.showState(wordScreen,outToPlayer);
                            }
                            else{
                                outToPlayer.println("\nIf you want to continue write START command. Unless write EXIT...\n ");
                                isMiddle = true;
                                isStarted = false;
                                hangmanGame.gameWinLoseState = false;
                            }
                        }
                       
                        else if ( (userInput.length()==1) && (!isLetter(guessChar))&& (isStarted == true) && (!isMiddle)){
                            outToPlayer.println("\nInvalid character! You should try a letter between a-z! Try agein...\n");
                            hangmanGame.showState(wordScreen,outToPlayer);
                        }
                        else{
                            outToPlayer.println("\nInvalid command! Try agein...\n");
                            if ((isStarted) && (!isMiddle)){
                                hangmanGame.showState(wordScreen,outToPlayer);
                            }
                        }
                    }
                }  
      
    }
    
}
