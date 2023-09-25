package engine.core.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import engine.core.GamePanel;
import engine.core.MyCanvas;

public class OverlayCanvas extends MyCanvas {
	

	MyCanvas canvasToOverlay;
	Font f16 = new Font("",Font.BOLD,16);
	
	public OverlayCanvas(MyCanvas canvasToOverlay) {
		this.canvasToOverlay = canvasToOverlay;
	}
	
	@Override
	public void update(float diffTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D dbg) {
		canvasToOverlay.render(dbg);
		dbg.setColor(Color.gray);
		dbg.fillRect(300, 200, 400, 300);
		dbg.setColor(Color.DARK_GRAY);
		dbg.fillRect(310, 210, 380, 280);
		
		Font f = dbg.getFont();
		dbg.setColor(Color.white);
		dbg.setFont(f16);
		dbg.drawString("Levrl UP", 440, 230);
		dbg.drawString("Escolha:(Pressione o Numero)", 350, 260);
		dbg.drawString("1 : +20 Dano", 350, 320);
		dbg.drawString("2 : +200 Vida Maxima", 350, 350);
		
		dbg.setFont(f);
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_1){
			Constantes.heroi.damage+=20;
			Constantes.heroi.vida=Constantes.heroi.vidaMaxima;
			Constantes.gameruncanvas.resetMovingKeys();
			GamePanel.telaAtiva = canvasToOverlay;
		}
		if(keyCode == KeyEvent.VK_2){
			Constantes.heroi.vidaMaxima+=100;
			Constantes.heroi.vida=Constantes.heroi.vidaMaxima;
			Constantes.gameruncanvas.resetMovingKeys();
			GamePanel.telaAtiva = canvasToOverlay;
		}
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
