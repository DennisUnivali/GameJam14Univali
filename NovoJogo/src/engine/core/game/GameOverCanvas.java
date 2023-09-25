package engine.core.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import engine.core.CanvasMainMenu;
import engine.core.GamePanel;
import engine.core.MyCanvas;

public class GameOverCanvas extends MyCanvas {
	

	MyCanvas canvasToOverlay;
	Font f16 = new Font("",Font.BOLD,16);
	
	public GameOverCanvas(MyCanvas canvasToOverlay) {
		this.canvasToOverlay = canvasToOverlay;
	}
	
	@Override
	public void update(float diffTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D dbg) {
		canvasToOverlay.render(dbg);
		dbg.drawImage(Constantes.youdiedImage, 0, 150, null);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		CanvasMainMenu mainMenu = new CanvasMainMenu();
		GamePanel.telaAtiva = mainMenu;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
