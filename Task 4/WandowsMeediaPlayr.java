/*// 
 * WANDOWS MEEDIA PLAYR
 * Displays colour changing spirographs every second
 * Plays only one song
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WandowsMeediaPlayr extends JFrame {

	private BufferedImage buffer;
	double x = 400;
	double y = 400;
	int oldX = 400;
	int oldY = 400;
	int newX = 0;
	int newY = 0;

	double R = 90;
	double r = 50;
	double O = 75;

	Random rand = new Random();

	public static void main(String[] args) {

		// Starts timer by calling Begin class
		Timer timer = new Timer();
		timer.schedule(new Begin(), 0, 500);

	}

	// Plays a good song
	public void playSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(new File("DarudeSandstorm.wav").getAbsoluteFile());
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception ex) {
			System.out.println("Error with playing sound.");
			ex.printStackTrace();
		}
	}

	// sets up UI
	public WandowsMeediaPlayr() {
		playSound();
		JPanel panel = new JPanel();

		JButton submit = new JButton("Submit");
		getRootPane().setDefaultButton(submit);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createSpiro();
			}
		});
		panel.add(submit);

		add(panel);

		setTitle("WANDOWS MEEDIA PLAYR");
		setSize(800, 800);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	// changes the variables to random ones and calls repaint
	public void createSpiro() {
		R = rand.nextInt(100) + 1;
		r = rand.nextInt(100) + 1;
		O = rand.nextInt(100) + 1;
		repaint();
	}

	// Paints the spiro using graphics
	public void paint(Graphics g) {

		g.setColor(getColour());
		g.fillRect(0, 0, 800, 800);
		g.setColor(getColour());

		for (int t = 0; t < 150; t += 1) {
			x = (R + r) * Math.cos(t) - (r + O) * Math.cos(((R + r) / r) * t);
			y = (R + r) * Math.sin(t) - (r + O) * Math.sin(((R + r) / r) * t);

			oldX = newX;
			oldY = newY;

			newX = (int) x + 400;
			newY = (int) y + 400;
			g.drawLine(oldX, oldY, newX + 1, newY + 1);
		}
	}

	// gets new random colour
	public Color getColour() {
		Random r = new Random();
		return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
	}
}

class Begin extends TimerTask {
	WandowsMeediaPlayr app = new WandowsMeediaPlayr();

	// calls the recreation of spiro
	public void run() {
		app.createSpiro();
	}
}
