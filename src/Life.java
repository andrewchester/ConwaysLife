import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Main class for conway's life
 * Handles all of the user input and GUI elements
 */

public class Life implements MouseListener, Runnable{
	
	Cell[][] cells;
	JFrame frame;
	LifePanel panel;
	Button step;
	Button start;
	Button stop;
	ActionListener stepListener;
	ActionListener startListener;
	ActionListener resumeListener;
	ActionListener stopListener;
	JSlider slider;
	boolean running = false;
	final int MAX_SPEED = 500;
	final int MIN_SPEED = 0;
	final int DEFAULT_SPEED = 150;
	int speed = DEFAULT_SPEED;
	Thread thread;
	boolean threadPaused = false;
	
	Life(){
		//Initializing frame, the 2d cells array, panel, the speed controls and the thread 
		frame = new JFrame("Conway's Life Simulation");
		cells = new Cell[25][25];
		panel = new LifePanel(cells);
		slider = new JSlider(JSlider.HORIZONTAL, MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
		thread = new Thread(this);
		panel.addMouseListener(this);
		
		//All of the action listeners/change listeners for the buttons and speed controls
		
		//When you click the step button, runs the step method
		stepListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				step();
			}
		};
		//Starts the thread
		startListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				thread.start();
			}
		};
		//If the thread is paused, resume 
		resumeListener = new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(threadPaused){
					thread.resume();
					threadPaused = false;
				}
			}
		};
		//If the thread isn't paused, pause it
		stopListener = new ActionListener(){
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e){
				if(!threadPaused){
					thread.suspend();
					threadPaused = true;
				}
			}
		};
		//Changes the update interval based on the slider
		ChangeListener speedListener = new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				speed = slider.getValue();
			}
		};
		
		//Initializing buttons
		step = new Button("Step");
		start = new Button("Start");
		stop = new Button("Stop");
		
		//Adding the action listeners declared above
		step.addActionListener(stepListener);
		start.addActionListener(startListener);
		stop.addActionListener(stopListener);
		
		//Settings for the slider
		slider.setMajorTickSpacing(100);
		slider.setMinorTickSpacing(10);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.addChangeListener(speedListener);
		
		//Frame settings, with the border layout
		frame.setSize(584, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		
		//Creating all of the cells, also letting each cell know where it is on the array so it can find it's neighbors with help from the life panel class
		for(int x = 0; x < cells.length; x++){
			for(int y = 0; y < cells[0].length; y++){
				cells[x][y] = new Cell((x * (500 / 25)), (y * (500 / 25)), 20,20);
				cells[x][y].setPos(x, y);
			}
		}
		
		frame.add(panel, BorderLayout.CENTER);
		frame.add(step, BorderLayout.NORTH);
		frame.add(start, BorderLayout.EAST);
		frame.add(stop, BorderLayout.WEST);
		frame.add(slider, BorderLayout.SOUTH);
		frame.setVisible(true);
		
	}
	//The method that is called when you click the step button, I probably could've called panel.update() inside of the action listener
	public void step(){
		panel.update();
	}
	//The run method, sets some variables when the thread is started, has the loop that uses the speed variable
	@SuppressWarnings("static-access")
	public void run(){
		if(running)//Makes sure there's only one thread, just in case I accidentally try to start it twice
			return;
		
		running = true;
		threadPaused = false;
		start.removeActionListener(startListener);//These two lines switch up the listeners on the start button cause we don't want to do thread.start() when resuming even though there's a safety measure in place to make sure there aren't dulicate threads
		start.addActionListener(resumeListener);
		while(running){
			panel.update();
			try {
				thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		new Life();
	}
	//Mouse events that I haven't used
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	//Called when you click, asks the panel if you clicked on a cell
	public void mouseReleased(MouseEvent e) {
		int mousex = e.getX();
		int mousey = e.getY();
		panel.clicked(mousex, mousey);
	}
}
