import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeKeeper {


    private int initialTime =60;
    private boolean timerOn=false;

    Panel panel;

    public TimeKeeper(Panel panel){
        this.panel = panel;
    }


    Timer timer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent e){
            reduceTimer();
            panel.speedHandler.setCurrentTypeSpeed();

        }
    });



    public void reduceTimer(){
        if(initialTime>0){

            initialTime--;
            panel.timerLabel.setText(Integer.toString(initialTime));
        }
        else{
            stopTimer();
            timerOn=false;
            panel.setTimerVisibility(false);
            panel.setResButtonVisibility(true);
            panel.cpmLabel.setText("You typed " + panel.speedHandler.getCpm() + " Characters/min");
            panel.averageWPMLabel.setText("Your TypeSpeed is: " + panel.speedHandler.getWpm() + " Statements/min");;

        }
    }

    public void startTimer(){
        timer.start();
        setTimerOn(true);
    }

    public void stopTimer(){
        timer.stop();
        panel.mainTextField.setText("");
        panel.mainTextField.setEditable(false);
        handlePanels();

    }


    private void handlePanels(){
        panel.statsLabel.setVisible(false);
        panel.hideResultPanels(true);
    }



    public void setTime(int time){
        initialTime=time;
    }



    public int getInitialTime() {
        return initialTime;
    }

    public boolean isTimerOn() {
        return timerOn;
    }

    public void setTimerOn(boolean timerOn) {
        this.timerOn = timerOn;
    }
}