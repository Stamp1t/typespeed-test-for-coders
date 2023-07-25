import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class WordsHandler {

    //classes
    Panel panel;

    //lists
    ArrayList<String> wordList = new ArrayList<>();  // the complete list of every possible word
    ArrayList<String> wordsForBox = new ArrayList<>(); // contains random words from wordlist and displays the first n of them depending on their length
    char[] legitChars;        //contains all chars which are legit to type

    //vars
    private String currentWord;
    private int indexForCurrentWord;
    private int counter=0; // counts the typed Words -> if there are as many words typed as there are words fitting into the box the box will be redone
    private int charCounter=0; //counts the chars of the String for the highlighter
    private int p1,p2; //positons of the highlighters
    private String wordToCompare="";
    private int correctWordCounter=0;
    private File file = new File("words.txt");





    public WordsHandler(Panel panel){
        this.panel=panel;

    }

    public void resetVars(){         //resets all the variables
        counter=0;
        charCounter=0;
        wordToCompare="";
        correctWordCounter=0;
    }

    public void fillWordsList(){      //fills the wordlist with all the words from the txt file which stores the words


        Scanner sc = null;

        try{
            sc = new Scanner(file);
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        for(int i = 0; i<countLines(file); i++){
            String s= sc.nextLine();
            wordList.add(" "+s);

        }
    }


    public void fillLegChars() {                                                                                                             //creates the list which stores all chars the user is able to use
        legitChars="aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQqrRsStTuUvVwWxXyYzZ{}[]();.,+-äÄüÜöÖ=1234567890?ß!§$%/<>´´´".toCharArray();
    }



    public void displayWords() {                                                                                                              //displays the new words which the user has to type
        createWordsForBox();                                                                                                                   //and does everything that needs to be done as a consequence -> redoes the highlighter,sets a new currentword
        panel.wordTextField.setText(createCompositeString());
        setFirstCurrentWord();
        charCounter=charCounter+currentWord.length();
        p1=p2;
        p2=charCounter;


    }


    public String createCompositeString(){   //creates a String from all the words in  wordsForBox
        String string="";                     // has a calculated length and thus fits into the wordbox
        int index = getCountWordsInBox(wordsForBox);
        for(int i=0; i<index; i++){
            string=string.concat(wordsForBox.get(i));
        }
        return string;
    }

    private void createWordsForBox(){         // creates a list of 8 random words from wordslist
        indexForCurrentWord=0;
        wordsForBox.clear();
        Random random = new Random();
        int[] ranNums = new int[20];

        for(int i=0; i<ranNums.length; i++){
            ranNums[i]=random.nextInt(wordList.size());
            wordsForBox.add(i,wordList.get(ranNums[i]));


        }
    }



    private void setFirstCurrentWord() {
        currentWord=wordsForBox.get(0);
        indexForCurrentWord++;
    }



    private int getCountWordsInBox(ArrayList<String> wordsForBox){             // takes in a list of words and decides how many of them can fit into the box
        int counter=0;
        int lineLength=0;
        for(int i= 0; i<wordsForBox.size(); i++ ){

            lineLength=lineLength+getStringLength(wordsForBox.get(i));
            if(lineLength<700){
                counter++;

            }
            else{
                return counter;
            }

        }
        return counter;
    }



    public int getStringLength(String word){         // calculates the length of a string

        AffineTransform affineTransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affineTransform,true,true);
        Font font = new Font("Monospaced",Font.PLAIN,20);
        int textwidth = (int)(font.getStringBounds(word,frc).getWidth());
        return  textwidth;
    }

    public void switchWords(KeyEvent e){

        if(checkIfWordMatchesWithCurrWord(e)){  //if the written words is equal to the current word, the current word gets updated
            panel.speedHandler.setCpm(panel.speedHandler.getCpm()+currentWord.length());
            refreshCurrWord();
            correctWordCounter++;
            panel.drawHighlighter(p1,p2);
            if(counter==getAmountofDisplayedWords()){  // see above (counter)
                redoBox();
            }

        }

    }

    public void handleWordColor(KeyEvent e){
        if(checkIfCharIsLegit(e) || e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {

            setWordColor();
        }
    }

    private void setWordColor() {
        int wordLength=wordToCompare.length();
        if(wordLength+1<=currentWord.length()){

            panel.setFontColor(currentWord.substring(1, wordLength + 1).equals(wordToCompare));
        }
        else{
            panel.setFontColor(false);
        }

    }

    private boolean checkIfWordMatchesWithCurrWord(KeyEvent e){
        System.out.println(" "+wordToCompare);
        System.out.println(currentWord);

        return e.getKeyCode() == KeyEvent.VK_SPACE && " ".concat(wordToCompare).substring(0, wordToCompare.length()).equals(currentWord);
    }

    private void refreshCurrWord(){
        changeCurrWord();
        wordToCompare="";
        panel.mainTextField.setText("");
        counter++;
        charCounter=charCounter+currentWord.length();
        p1=p2+1;
        p2=charCounter;

    }



    public void changeCurrWord(){
        currentWord=wordsForBox.get(indexForCurrentWord);

        indexForCurrentWord++;
    }

    public int getAmountofDisplayedWords(){
        return getCountWordsInBox(wordsForBox);
    }

    private void redoBox(){                                   //

        counter=0;
        charCounter=0;

        displayWords();
        p1=1;
        p2=currentWord.length();
        panel.drawHighlighter(p1,p2);
        panel.mainTextField.setText("");

    }

    public void createWordToCompare(KeyEvent e){

        if(checkIfCharIsLegit(e)){  //checks if the typed char is contained in the list of legit chars

            wordToCompare=wordToCompare.concat(String.valueOf(e.getKeyChar()));

        }
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            wordToCompare=wordToCompare+" ";
        }
        if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE && wordToCompare.length()!=0){
            wordToCompare=wordToCompare.substring(0,wordToCompare.length()-1);
        }

    }

    private boolean checkIfCharIsLegit(KeyEvent e){                //checks if a typed char is contained in the legitchars list which stores all possible chars

        for(int i=0; i<legitChars.length;i++){
            if(e.getKeyChar()==legitChars[i]){
                return true;
            }
        }
        return false;


    }

    public int countLines(File file){                                 // counts the lines of the txt file which contains all the possible words which can be displayed
        int counter=0;
        Scanner sc = null;
        try{
            sc = new Scanner(file);
        }
        catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        while(sc.hasNext()){
            sc.nextLine();
            counter++;
        }
        return counter;

    }



    public void addNewWord(String word){                                               //adds a new words to the txt file which can be displayed after restarting the program -> words wont be added if it already exists
        FileWriter writer;

        if(!checkIfWordsExists(word)){
            try{
                writer= new FileWriter(file,true);
                writer.write(word);
                writer.write(System.getProperty("line.separator"));

                writer.flush();
                writer.close();

                panel.wordExistsPanel.setVisible(false);
            }
            catch(IOException e){
                throw new RuntimeException(e);
            }

        }
        else{
            panel.wordExistsPanel.setVisible(true);
        }


    }

    private boolean checkIfWordsExists(String word) {                 //checks if a word already exists in a file
        Scanner sc= null;

        try{
            sc=new Scanner(file);
        }
        catch(FileNotFoundException e){
            throw new RuntimeException(e);
        }
        for (int i=0; i<countLines(file);i++){
            String s = sc.nextLine();

            if(word.equals(s)){
                return true;
            }

        }
        return false;
    }



    public String getCurrentWord() {
        return currentWord;
    }

    public int getCorrectWordCounter() {
        return correctWordCounter;
    }










}
