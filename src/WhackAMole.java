import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;

public class WhackAMole {
    private JPanel panelWAM;
    private JButton btnStart, btnReset, btnQuit, btnEasy, btnDifficult, btnAverage, btnMole1, btnMole2, btnMole3, btnMole4, btnMole5,
            btnMole6, btnMole7, btnMole8, btnMole9, btnMole10, btnMole11, btnMole12, btnMole13, btnMole14, btnMole15, btnMole16, btnHowToPlay,
            btnGameDescription;
    private JTextField txtHitCount, txtMiss, txtTime;
    private JButton btnBackGroundTop;
    int hit;
    Random r = new Random();
    private Timer timer, countDown, startGameTime;
    private int timeLeft, hitCount = 0, missCount = 0, count = 5, gameTime = 30;
    private final JButton[] moles = new JButton[] {btnMole1, btnMole2, btnMole3, btnMole4, btnMole5, btnMole6, btnMole7, btnMole8, btnMole9,
            btnMole10, btnMole11, btnMole12, btnMole13, btnMole14, btnMole15, btnMole16};
    private ImageIcon moleIMG;

    public WhackAMole(){
        ImageIcon gameDes, howToPlay, easy, average, difficult, start, reset, quit;

        btnBackGroundTop.setEnabled(false);
        ImageIcon bgTop = new ImageIcon(new ImageIcon(getClass().getResource("res/WhackAMoleBackGroundTop.png"))
                .getImage().getScaledInstance(665, 50, Image.SCALE_SMOOTH));
        btnBackGroundTop.setIcon(bgTop);
        btnBackGroundTop.setBorderPainted(false);
        btnBackGroundTop.setDisabledIcon(bgTop);

        moleIMG = new ImageIcon(new ImageIcon(getClass().getResource("res/Hole.png"))
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));

        gameDes = new ImageIcon(new ImageIcon(getClass().getResource("res/GameDes1.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        howToPlay = new ImageIcon(new ImageIcon(getClass().getResource("res/HowToPlay.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        easy = new ImageIcon(new ImageIcon(getClass().getResource("res/easy.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        average = new ImageIcon(new ImageIcon(getClass().getResource("res/average.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        difficult = new ImageIcon(new ImageIcon(getClass().getResource("res/difficult.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        start = new ImageIcon(new ImageIcon(getClass().getResource("res/start1.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        reset = new ImageIcon(new ImageIcon(getClass().getResource("res/reset1.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));
        quit = new ImageIcon(new ImageIcon(getClass().getResource("res/quit.png"))
                .getImage().getScaledInstance(150, 30, Image.SCALE_SMOOTH));

        btnStart.setEnabled(false);
        btnStart.setBorderPainted(false);
        btnReset.setEnabled(false);
        btnReset.setBorderPainted(false);
        btnQuit.setEnabled(true);
        btnQuit.setBorderPainted(false);

        btnGameDescription.setIcon(gameDes);
        btnGameDescription.setBorderPainted(false);
        btnHowToPlay.setIcon(howToPlay);
        btnHowToPlay.setBorderPainted(false);
        btnEasy.setIcon(easy);
        btnEasy.setBorderPainted(false);
        btnAverage.setIcon(average);
        btnAverage.setBorderPainted(false);
        btnDifficult.setIcon(difficult);
        btnDifficult.setBorderPainted(false);
        btnStart.setIcon(start);
        btnStart.setBorderPainted(false);
        btnReset.setIcon(reset);
        btnReset.setBorderPainted(false);
        btnQuit.setIcon(quit);
        btnQuit.setBorderPainted(false);

        for (JButton i: moles){
            i.addActionListener(e -> moleListener(i));
            i.setIcon(moleIMG);
            i.setVisible(true);
            i.setDisabledIcon(moleIMG);
            i.setBorderPainted(false);
        }
        ActionListener buttonListener = e -> {
            Object action = e.getSource();
            if(action == btnEasy){
                ButtonPressed();
                disableButton();
                timeLeft = 800;
            }
            if(action == btnAverage){
                ButtonPressed();
                disableButton();
                timeLeft = 680;
            }
            if (action == btnDifficult){
                ButtonPressed();
                disableButton();
                timeLeft = 600;
            }
            if (action == btnStart){
                Start();
                txtHitCount.setText("0");
                txtMiss.setText("0");
                btnStart.setEnabled(false);
                for (JButton i: moles){
                    i.setBorderPainted(false);
                    i.setVisible(true);
                    i.setDisabledIcon(moleIMG);
                }
                ActionListener counter = c -> {
                    txtTime.setText(String.valueOf(count));
                    count -= 1;
                    if (count == 2 || count == 1 || count == 0){
                        CountDown();
                    }
                    else if(count <= 0){
                        countDown.stop();
                        time();
                        gameTime = 30;
                    }
                };
                countDown = new Timer (1000,counter);
                countDown.start();
            }
            if (action == btnReset){
                count =5;
                gameTime = 30;
                btnEasy.setEnabled(true);
                btnAverage.setEnabled(true);
                btnDifficult.setEnabled(true);
                txtTime.setText("0");
                txtHitCount.setText("0");
                txtMiss.setText("0");
                for (JButton i: moles){
                    i.setIcon(moleIMG);
                    hitCount = 0;
                    missCount = 0;
                    i.setEnabled(false);
                    i.setVisible(true);
                    i.setDisabledIcon(moleIMG);
                    i.setBorderPainted(false);
                    countDown.stop();
                    if (count == 5){
                        startGameTime.stop();
                        timer.stop();
                    }
                }
            }
            if (action == btnQuit){
                System.exit(0);
            }
            if (action == btnGameDescription){
                ButtonPressed();
                JOptionPane.showMessageDialog(null, "This game will test your hand-eye coordination response while under the time pressure. Think you can keep up?");
            }
            if (action == btnHowToPlay){
                ButtonPressed();
                JOptionPane.showMessageDialog(null, "1. Pick your desired game difficulty before playing.\n     EASY: 0.8 second mole appearance speed" +
                        "\n     AVERAGE: 0.68 second mole appearance speed\n     DIFFICULT: 0.60 second mole appearance speed\n2. A 5-second countdown will begin after hitting the \"Start\" button.\n" +
                        "3. The game timer will start from 30 seconds once the countdown timer hits 0.\n4. The buttons will appear randomly anywhere in the 4x4 grid and the moles appear for a certain amount of seconds " +
                        "based on the selected difficulty.\n4. The goal is to hit all the moles that are appearing one-by-one anywhere in the 4x4 grid.\n5. The game ends if the game timer hits 0.");
            }
        };
        btnEasy.addActionListener(buttonListener);
        btnAverage.addActionListener(buttonListener);
        btnDifficult.addActionListener(buttonListener);
        btnStart.addActionListener(buttonListener);
        btnReset.addActionListener(buttonListener);
        btnQuit.addActionListener(buttonListener);
        btnGameDescription.addActionListener(buttonListener);
        btnHowToPlay.addActionListener(buttonListener);
    }
    private void time(){
        ActionListener skip = a -> {
            txtTime.setText(String.valueOf(gameTime));
            gameTime -= 1;
            if (gameTime == 4 || gameTime == 3 || gameTime == 2 || gameTime == 1 || gameTime == 0){
                CountDown();
            }
            else if (gameTime <= 0) {
                TimeOut();
                for (JButton i: moles){
                    i.setIcon(moleIMG);
                    i.setEnabled(false);
                    i.setVisible(true);
                    startGameTime.stop();
                    countDown.stop();
                    timer.stop();
                }
                gameTime=30;
                txtTime.setText("0");
                int miss = missCount - 1;
                if (miss == -1){
                    miss = 0;
                }
                int input = JOptionPane.showOptionDialog(null,"Game Over\nHit count: "+ hitCount + "\nMiss count: " + (miss),"Message", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                if (input == JOptionPane.OK_OPTION) {
                    ButtonPressed();
                    for (JButton i : moles) {
                        i.setIcon(moleIMG);
                        hitCount = 0;
                        missCount = 0;
                        i.setEnabled(false);
                        i.setVisible(true);
                        startGameTime.stop();
                        countDown.stop();
                        timer.stop();
                    }
                    btnEasy.setEnabled(true);
                    btnAverage.setEnabled(true);
                    btnDifficult.setEnabled(true);
                    txtTime.setText("0");
                    txtHitCount.setText("0");
                    txtMiss.setText("0");
                }
            }
        };
        startGameTime = new Timer (1000, skip);
        startGameTime.start();
        moleSelector();
        count = 5;
    }
    private void moleListener(JButton btn) {
        ImageIcon moleIMG = new ImageIcon(new ImageIcon(getClass().getResource("res/Hole.png"))
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        btn.setIcon(moleIMG);
        btn.setDisabledIcon(moleIMG);
        btn.setEnabled(false);
        hit = 1;
        hitCount += 1;
        txtHitCount.setText(" "+hitCount);
        moleSelector();
        hits();
    }
    private void disableButton(){
        btnStart.setEnabled(true);
        btnReset.setEnabled(true);
        btnQuit.setEnabled(true);
        btnEasy.setEnabled(false);
        btnAverage.setEnabled(false);
        btnDifficult.setEnabled(false);
    }
    public void moleSelector(){
        int index = r.nextInt(16);
        ImageIcon moleImg = new ImageIcon(new ImageIcon(getClass().getResource("res/MoleAppear.png"))
                .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        moles[index].setEnabled(true);
        moles[index].setIcon(moleImg);
        ActionListener skip = e -> {
            timer.stop();
            moleIMG = new ImageIcon(new ImageIcon(getClass().getResource("res/Hole.png"))
                    .getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            for (JButton b: moles){
                b.setIcon(moleIMG);
            }
            hit = 0;
            moles[index].setEnabled(false);
            moleSelector();
        };
        if (hit == 1){
            timer.stop();
        }
        else {
            txtMiss.setText(""+missCount);
            missCount += 1;
            if(missCount > 1){
                miss();
            }
        }
        timer = new Timer (timeLeft, skip);
        timer.start();
    }
    public void Start()
    {
        String start  = "res/Start.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(start)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void miss()
    {
        String miss  = "res/Miss.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(miss)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void hits()
    {
        String hits  = "res/Hit.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(hits)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void ButtonPressed()
    {
        String pressed  = "res/ButtonPressed.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(pressed)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void TimeOut()
    {
        String timeOut  = "res/TimeOut.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(timeOut)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void BackGroundMusic()
    {
        String music  = "res/BGMusic.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(music)));
            clip.loop(clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-15.0f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public void CountDown()
    {
        String count  = "res/CountDown.wav";
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(count)));
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(+6.0206f);
            clip.start();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("Whack A Mole");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Exit the program after closing
        frame.setContentPane(new WhackAMole().panelWAM);
        frame.setResizable(false);
        frame.setTitle("Whack A Mole");
        frame.setVisible(true);
        frame.pack();
        frame.setLocationRelativeTo(null);
        WhackAMole obj = new WhackAMole();
        obj.BackGroundMusic();
        JOptionPane.showMessageDialog(null,"Pick your desired game difficulty.");
        try {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Image image = toolkit.getImage(obj.getClass().getResource("res/cursor.png"));
            Cursor c = toolkit.createCustomCursor(image , new Point(0,0), "img");
            frame.setCursor (c);
        }
        catch(Exception e){System.out.println("new");}
    }
}
