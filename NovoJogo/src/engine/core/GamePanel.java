package engine.core;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import old.CanvasMAIN;
import old.Constantes;


public class GamePanel extends JPanel implements Runnable{

public static GamePanel instance = null;

private Thread animator;
private boolean running = false;
private boolean gameOver = false; 

private Graphics2D dbg;

Canvas gui = null;
public BufferStrategy strategy = null;


public static int FPS,SFPS;
int fpscount;

public static MyCanvas telaAtiva = null;

public static Random rnd = new Random();

public GamePanel()
{
	instance = this;

	setBackground(Color.white);
	setPreferredSize( new Dimension(Constantes.telaW, Constantes.telaH));

	// create game components
	setFocusable(true);

	requestFocus(); // JPanel now receives key events
	
	gui = new Canvas();
	gui.setSize(Constantes.telaW, Constantes.telaH);
	setLayout(new BorderLayout());
	add(gui, BorderLayout.CENTER);
	
	// Adiciona um Key Listner
	gui.addKeyListener( new KeyAdapter() {
		public void keyPressed(KeyEvent e)
			{ 
				if(telaAtiva!=null){
					telaAtiva.keyPressed(e);
				}
			}
		@Override
			public void keyReleased(KeyEvent e ) {
				if(telaAtiva!=null){
					telaAtiva.keyReleased(e);
				}
			}
	});
	
	gui.addMouseMotionListener(new MouseMotionListener() {
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseMoved(e);
			}
		}
		
		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseDragged(e);
			}
		}
	});
	
	
	
	gui.addMouseListener(new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseReleased(e);
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mousePressed(e);
			}
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseExited(e);
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseEntered(e);
			}
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseClicked(e);
			}
		}
	});
	
	gui.addMouseWheelListener(new MouseWheelListener() {
		
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if(telaAtiva!=null){
				telaAtiva.mouseWheelMoved(e);
			}
		}
	});
	
	Constantes.carrega_Imagens();
	
	telaAtiva = new CanvasSplash();//new CanvasGame();
	//telaAtiva = new CanvasSplash();
	
  
} // end of GamePanel()

public void addNotify()
{
	super.addNotify(); // creates the peer
	startGame(); // start the thread
}

private void startGame()
// initialise and start the thread
{
	if (animator == null || !running) {
		animator = new Thread(this);
		animator.start();
	}
} // end of startGame()

public void stopGame()
// called by the user to stop execution
{ running = false; }


public void run()
/* Repeatedly update, render, sleep */
{
	running = true;
	
	long DifTime,TempoAnterior;
	
	int segundo = 0;
	DifTime = 0;
	TempoAnterior = System.currentTimeMillis();
	
	gui.createBufferStrategy(2);
	strategy = gui.getBufferStrategy();
	
	while(running) {
		
		dbg = (Graphics2D) strategy.getDrawGraphics();
		dbg.setClip(0, 0, Constantes.telaW, Constantes.telaH);
		dbg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if(DifTime>100) {
			DifTime = 100;
		}
		gameUpdate(DifTime);
		gameRender();
		
		dbg.dispose();
		strategy.show();
	
		try {
			Thread.sleep(0); // sleep a bit
		}	
		catch(InterruptedException ex){}
		
		DifTime = System.currentTimeMillis() - TempoAnterior;
		TempoAnterior = System.currentTimeMillis();
				
		if(segundo!=((int)(TempoAnterior/1000))){
			FPS = SFPS;
			SFPS = 1;
			segundo = ((int)(TempoAnterior/1000));
		}else{
			SFPS++;
		}
	
	}
System.exit(0); // so enclosing JFrame/JApplet exits
} // end of run()

private void gameUpdate(long diftime)
{ 
	if(telaAtiva!=null){
		telaAtiva.update((int)diftime);
	}
}

private void gameRender()
// draw the current frame to an image buffer
{
	if(telaAtiva!=null){
		telaAtiva.render(dbg);
	}
}


public static void main(String args[])
{
  GamePanel ttPanel = new GamePanel();

  // create a JFrame to hold the timer test JPanel
  JFrame app = new JFrame("The Return");
  app.getContentPane().add(ttPanel, BorderLayout.CENTER);
  app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  
  app.setResizable(false);  
  app.setVisible(true);
  
  app.pack();
} // end of main()

public BufferedImage carregaImagem(String path){
	try {
		System.out.println(""+this.getClass().getResourceAsStream(path)+" "+path+" "+this.getClass()+" ");
		BufferedImage imgtmp = ImageIO.read(this.getClass().getResourceAsStream(path));
		BufferedImage imagereturn = new BufferedImage(imgtmp.getWidth(),imgtmp.getHeight(),BufferedImage.TYPE_INT_ARGB);
		imagereturn.getGraphics().drawImage(imgtmp, 0, 0, null);
		return imagereturn;
		
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	return null;
}

} // end of GamePanel class

