/** *************************************************************
 *
 * file: Play.java
 * author: Yuqing Han
 *************************************************************** */
package myHangman;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

public class Play_1 extends JPanel implements ActionListener {
    //Declare all the variables
    private JButton[] alphabets;
    private JButton skip;
    private JLabel[] alphabetsUsed;
    private JLabel scoreDisplay;
    private JLabel clockDisplay;
    private Random random = new Random();
    private String[] words = {"abstract", "cemetery", "nurse", "pharmacy", "climbing"};
    private boolean[] guessed;
    private String word;
    private String choice;
    private int score = 100;
    private int error = 0;
    private Home homePanel;
    private Highscores scorePanel;
    private BufferedImage pic = null;
    
    //Constructor
    public Play_1(Home home, Highscores score) {
        this.scorePanel = score;
        this.homePanel = home;
        int randomNumber = random.nextInt(4 + 1);
        word = words[randomNumber];
        guessed = new boolean[word.length()];
        loadGUI();
        clock();

    }

    //Creates a new live object of current time and date
    public void clock() {
        Thread clock = new Thread() {
            public void run() {
                try {
                    for (;;) {
                        Calendar calendar = new GregorianCalendar();
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        int month = calendar.get(Calendar.MONTH) + 1;
                        int year = calendar.get(Calendar.YEAR);

                        int second = calendar.get(Calendar.SECOND);
                        int minute = calendar.get(Calendar.MINUTE);
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);

                        clockDisplay.setText("Time " + hour + ":" + minute + ":" + second
                                + "         Date " + month + "/" + day + "/" + year);
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        clock.start();
    }

    public void loadGUI() {
        int count = 65;
        alphabetsUsed = new JLabel[word.length()];
        alphabets = new JButton[26];
        clockDisplay = new JLabel("");
        scoreDisplay = new JLabel("Score: " + score);
        skip = new JButton("Skip");
        skip.addActionListener(this);
        
        Container container = new Container();

        container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
        container.add(clockDisplay);
        container.add(scoreDisplay);
        container.add(Box.createRigidArea(new Dimension(100, 240)));

        Container bContainer = new Container();
        Container wContainer = new Container();
        bContainer.setLayout(new GridLayout(2, 13, 0, 0));
        wContainer.setLayout(new GridLayout(1, word.length()));

        container.add(wContainer);
        container.add(Box.createRigidArea(new Dimension(50, 20)));
        container.add(bContainer);

        //Create enough empty spaces for the secret word
        for (int i = 0; i < alphabetsUsed.length; i++) {
            alphabetsUsed[i] = new JLabel("_");
            wContainer.add(alphabetsUsed[i]);
        }
        //
        for (int i = 0; i < alphabets.length; i++, count++) {
            alphabets[i] = new JButton(Character.toString((char) count));
            alphabets[i].setPreferredSize(new Dimension(30, 30));
            alphabets[i].setBorder(null);
            alphabets[i].setFont(new Font("Noteworthy", Font.BOLD, 12));
            alphabets[i].addActionListener(this);
            bContainer.add(alphabets[i]);

        }
        add(container);
        add(skip);     
    }

    //Creates the title and the hangman stand
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Font f = new Font("Ariel Black", Font.BOLD + Font.ITALIC, 24);
        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(f);
        g2.drawString("Hangman", 5, 23);    
        try {
            pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-0.png"));     
        } catch(IOException e){}
        g.drawImage(pic,150,75,200,200,this);
    }
    
    //Creates a graphics2D to draw the hangman
    @Override
    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        JButton button = null;
        Graphics2D g2 = (Graphics2D) this.getGraphics();
        g2.setStroke(new BasicStroke (3));
        
        if (object instanceof JButton) {
            button = (JButton) object;
        }

        if (button != null) {
            setChoice(button.getText().toLowerCase());
            if (choice.equals("skip")) {
                this.setVisible(false);
                homePanel.setVisible(true);
                scorePanel.setScore(0);
            }
            //Draw head
            if (error == 0 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-1.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

                //Draw body
            } else if (error == 1 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-2.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

                //Draw right arm
            } else if (error == 2 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-3.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

                //Draw left arm
            } else if (error == 3 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-4.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

                //Draw left leg
            } else if (error == 4 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-5.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

                //Draw right leg
            } else if (error == 5 && !word.contains(getChoice())) {
                try {
                    pic = ImageIO.read(new File("src\\main\\java\\Images\\Hangman-6.png"));     
                } catch(IOException f){}
                g2.drawImage(pic,150,75,200,200,this);
                error++;
                score -= 10;
                scoreDisplay.setText("Score: " + Integer.toString(score));
                button.setVisible(false);

            } else if (error < 6 && word.contains(getChoice())) {

                for (int i = 0; i < word.length(); i++) {
                    if (button.getText().toLowerCase().charAt(0) == word.charAt(i)) {
                        alphabetsUsed[i].setText(button.getText());
                        guessed[i] = true;
                    }
                }
                button.setVisible(false);

            }
            //Variable needed to know if the user won the game or not
            boolean win = true;
            for (int i = 0; i < guessed.length; i++) {
                if (!guessed[i]) {
                    win = false;
                }
            }
            if (win == true) {

                homePanel.setVisible(true);
                homePanel.setFrameToFalse();
                scorePanel.setScore(score);
            }

            if (error == 6) {
                homePanel.setVisible(true);
                homePanel.setFrameToFalse();
                scorePanel.setScore(score);
            }
        }
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String s) {
        choice = s;
    }

    public int getScore() {
        return score;
    }

}
