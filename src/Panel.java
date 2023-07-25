import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class Panel extends JPanel {

    //screen
    final static int screenWidth=1000;
    final static int screenHeight=800;


    //components
    JTextField mainTextField; // the textfield in which the user will write the words
    JTextField wordTextField; // the textfield which shows the words the user has to type
    JLabel averageWPMLabel;
    JPanel averageWPMPanel;
    JPanel cpmPanel;
    JLabel cpmLabel;
    JLabel statsLabel;
    JLabel addWordsLabel;
    JPanel addWordsPanel;
    JTextField addWordsField;
    JPanel wordExistsPanel;
    JLabel wordExistsLabel;
    JButton resButton;
    JLabel timerLabel;
    Highlighter.HighlightPainter painter;


    //classes
    WordsHandler wordsHandler;
    Highlighter highlighter;
    TimeKeeper timer;
    ButtonHandler buttonHandler;
    SpeedHandler speedHandler;




    public Panel(){

        innitClasses();
        wordsHandler.fillWordsList(); // fills the wordlist
        setPanel();  // everything that has to do with panel size etc
        wordsHandler.fillLegChars();  // fills the array which contians legit chars which the user can write
        createMainTypeBox(); // creates the box in which the user will type
        createMainWordBox(); // creates the box which displays the words the user is supposed to type
        createCurrentStats();
        createResults();
        createWordAlreadyAddedBox(); // creates the text which says the added word already exists
        createTimer();
        createResButton();
        createAddWordsBox();
        addComponents(); //adds components to panel
        wordsHandler.displayWords(); //displays the words the user is supposed to type on the wordbox
        addKeyListener();
        drawHighlighter(1,wordsHandler.getCurrentWord().length()); //draws the inital highlighter



    }

    private void setPanel(){

        setLayout(null);
        this.setBackground(new Color(198,217,255));

    }



    private void innitClasses(){
        wordsHandler = new WordsHandler(this);
        timer = new TimeKeeper(this);
        buttonHandler = new ButtonHandler(this);
        speedHandler = new SpeedHandler(this);
    }



    private void createWordAlreadyAddedBox() {
        wordExistsLabel = new JLabel();
        wordExistsLabel.setForeground(new Color(190,6,6));
        wordExistsLabel.setText("This word already exists");
        wordExistsLabel.setFont(new Font("Monospaced",Font.PLAIN,16));

        wordExistsPanel = new JPanel();
        wordExistsPanel.setBounds(600,723,300,30);
        wordExistsPanel.setBackground(getBackground());
        wordExistsPanel.add(wordExistsLabel);

        wordExistsPanel.setVisible(false);
    }



    private void createAddWordsBox() {
        addWordsLabel = new JLabel();
        addWordsLabel.setForeground(new Color(190, 6, 6));
        addWordsLabel.setText("Add your own words here:");
        addWordsLabel.setFont(new Font("Monospaced",Font.PLAIN,20));

        addWordsPanel = new JPanel();
        addWordsPanel.setBounds(600,630,300,30);
        addWordsPanel.setBackground(getBackground());
        addWordsPanel.add(addWordsLabel);


        addWordsField = new JTextField();
        addWordsField.setBackground(new Color(255, 255, 255));
        addWordsField.setBounds(600,680,300,40);
        addWordsField.setBorder(new LineBorder(Color.black));
        addWordsField.setHorizontalAlignment(SwingConstants.CENTER);
        addWordsField.setFont(new Font("Monospaced",Font.PLAIN,20));
    }

    private void createTimer(){

        timerLabel = new JLabel();
        timerLabel.setForeground(Color.red);
        timerLabel.setBounds(720,200,125,50);
        timerLabel.setFont(new Font("Monospaced",Font.PLAIN,20));
        timerLabel.setText(Integer.toString(timer.getInitialTime()));
        timerLabel.setBorder(new LineBorder(Color.BLACK));
        timerLabel.setHorizontalAlignment(SwingConstants.CENTER);
    }


    private void createMainTypeBox() {
        int boxWidth=400;
        int boxHeight=50;
        mainTextField = new JTextField();
        mainTextField.setBackground(new Color(255, 255, 255));
        mainTextField.setBounds((screenWidth-boxWidth) /2,200,boxWidth,boxHeight);
        mainTextField.setFont(new Font("Monospaced",Font.BOLD,25));
        mainTextField.setHorizontalAlignment(JTextField.CENTER);
        mainTextField.setBorder(new LineBorder(Color.BLACK));



    }

    private void createMainWordBox(){
        int boxWidth=700;
        int boxHeight=110;
        wordTextField = new JTextField();
        wordTextField.setBackground(new Color(180, 199, 229));
        wordTextField.setHorizontalAlignment(SwingConstants.CENTER);
        wordTextField.setBounds((screenWidth-boxWidth)/2,20,boxWidth,boxHeight);
        wordTextField.setEditable(false);
        wordTextField.setBorder(new LineBorder(Color.BLACK));
        wordTextField.setFont(new Font("Monospaced",Font.PLAIN,20));
        highlighter = wordTextField.getHighlighter();
        painter = new DefaultHighlighter.DefaultHighlightPainter(new Color(130, 138, 164, 95));

    }

    private void createResButton(){
        ImageIcon resetIcon = new ImageIcon(getClass().getResource("/reset.png"));
        resButton = new JButton(resetIcon);
        resButton.setBounds(720,200,50,50);
        resButton.setBorder(new LineBorder(Color.black));
        resButton.setFocusable(false);
        resButton.setBackground(new Color(180, 199, 229));
        resButton.addActionListener(buttonHandler);
        resButton.setVisible(false);

    }


    public void createResults(){

        averageWPMLabel = new JLabel();
        cpmLabel = new JLabel();

        averageWPMPanel = new JPanel();
        cpmPanel = new JPanel();

        averageWPMLabel.setFont(new Font("Monospaced", Font.PLAIN,20));
        averageWPMLabel.setForeground(new Color(255,79,79));
        averageWPMLabel.setText("Your TypeSpeed is: " + speedHandler.getWpm() + " Statements/min");

        averageWPMPanel.setBounds(255,280,500,50);
        averageWPMPanel.setBackground(getBackground());
        averageWPMPanel.add(averageWPMLabel);
        averageWPMPanel.setVisible(false);

        cpmLabel.setFont(new Font("Monospaced", Font.PLAIN,20));
        cpmLabel.setForeground((new Color(255,79,79)));
        cpmLabel.setText("You typed " + speedHandler.getCpm() + " Characters in 60 Seconds");

        cpmPanel.setBounds(255,330,500,50);
        cpmPanel.setBackground(getBackground());
        cpmPanel.add(cpmLabel);
        cpmPanel.setVisible(false);



    }

    public void createCurrentStats(){

        statsLabel = new JLabel();

        statsLabel.setBounds(200,300,580,50);
        statsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statsLabel.setBorder(new LineBorder(Color.black));
        statsLabel.setFont(new Font("Monospaced", Font.PLAIN,20));
        statsLabel.setText("Your Current TypeSpeed is: " + speedHandler.getWpm() + " Statements/min");

    }




    public void drawHighlighter(int p1, int p2){

        highlighter.removeAllHighlights();

        try {
            highlighter.addHighlight(p1,p2,painter);

        } catch (BadLocationException e) {
            throw new RuntimeException(e);
        }

    }



    private void addKeyListener(){
        KeyBoardListener keyListenerMainTextField = new KeyBoardListener(this,"mainTextField");
        mainTextField.addKeyListener(keyListenerMainTextField);
        KeyBoardListener keyListenerAddWordsField = new KeyBoardListener(this,"addWordsField");
        addWordsField.addKeyListener(keyListenerAddWordsField);
    }

    private void addComponents() {
        add(mainTextField);
        add(timerLabel);
        add(wordTextField);
        add(statsLabel);
        add(addWordsPanel);
        add(addWordsField);
        add(wordExistsPanel);
        add(resButton);
        add(cpmPanel);
        add(averageWPMPanel);


    }




    public void setResButtonVisibility(boolean b){
        resButton.setVisible(b);
    }

    public void setTimerVisibility(boolean b){
        timerLabel.setVisible(b);
    }


    public void hideResultPanels(boolean b){

        averageWPMPanel.setVisible(b);
        cpmPanel.setVisible(b);

    }



    public void setFontColor(boolean b){
        if(b){
            mainTextField.setForeground(new Color(27, 137, 35));

        }
        else{
            mainTextField.setForeground(Color.red);
        }


    }





    public int getScreenWidth(){
        return screenWidth;
    }

    public int getScreenHeight(){
        return screenHeight;
    }


    public WordsHandler getWordsHandler() {
        return wordsHandler;
    }
}