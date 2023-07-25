import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonHandler implements ActionListener {

    private Panel panel;

    public ButtonHandler(Panel panel){
        this.panel=panel;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        reset();
    }


    private void reset(){
        panel.hideResultPanels(false);
        panel.speedHandler.resetVars();
        panel.wordsHandler.resetVars();
        panel.setResButtonVisibility(false);
        panel.setTimerVisibility(true);
        panel.timer.setTime(60);
        panel.timerLabel.setText("60");
        panel.mainTextField.setEditable(true);
        panel.statsLabel.setText("Your Current TypeSpeed is: " + panel.speedHandler.getWpm() + " Statements/min");
        panel.statsLabel.setVisible(true);
        panel.wordsHandler.resetVars();
        panel.wordsHandler.displayWords();
        panel.drawHighlighter(1,panel.wordsHandler.getCurrentWord().length());

    }
}