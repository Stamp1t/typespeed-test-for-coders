import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyBoardListener implements KeyListener {


    private Panel panel;
    private String function;

    public KeyBoardListener(Panel panel, String function){
        this.panel=panel;
        this.function= function;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (function){
            case "mainTextField":
                handlemainTextField(e);
                break;
            case "addWordsField":
                handleAddWordsField(e);
                break;
        }



    }

    private void handleAddWordsField(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            panel.getWordsHandler().addNewWord(panel.addWordsField.getText());  //adds the words if enter is pressed
            panel.addWordsField.setText("");

        }
    }




    private void handlemainTextField(KeyEvent e) {
        if(!panel.timer.isTimerOn()){
            panel.timer.startTimer();
        }

        panel.wordsHandler.createWordToCompare(e);  //creates the word which will be compared to the current word
        panel.wordsHandler.switchWords(e);          //switches the current word and redoes the box which displays the words in case they were all written
        panel.wordsHandler.handleWordColor(e);      //will decide if color will be red or green
    }


    @Override
    public void keyReleased(KeyEvent e) {


    }


}