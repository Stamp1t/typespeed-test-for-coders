public class SpeedHandler {

    Panel panel;

    private int wpm=0;
    private int cpm=0;

    public SpeedHandler(Panel panel){
        this.panel=panel;
    }

    public void setCurrentTypeSpeed(){
        wpm= (int) ((double)panel.wordsHandler.getCorrectWordCounter()/(double)(60-panel.timer.getInitialTime())*60);
        wpm=Math.round(wpm);

        panel.statsLabel.setText("Your Current TypeSpeed is: " +wpm + " Statements/min");

    }

    public void resetVars(){
        wpm=0;
        cpm=0;
    }

    public int getWpm() {
        return wpm;
    }

    public int getCpm() {
        return cpm;
    }

    public void setCpm(int cpm) {
        this.cpm = cpm;
    }
}
