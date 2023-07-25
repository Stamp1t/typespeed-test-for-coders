import javax.swing.*;

public class Frame extends JFrame  {

    Panel panel;


    public Frame(){

        innitClasses();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(panel.getScreenWidth(),panel.getScreenHeight());
        setResizable(false);
        setTitle("TypeSpeed");
        setLocationRelativeTo(null);

        add(panel);
        setVisible(true);

    }

    private void innitClasses() {
        panel = new Panel();

    }


}
