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

public class OverlayCanvasGanancia extends MyCanvas {
	

	MyCanvas canvasToOverlay;
	Font f16 = new Font("",Font.BOLD,16);
	
	public OverlayCanvasGanancia(MyCanvas canvasToOverlay) {
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
		dbg.drawString("Altar ao Deus da Ganancia", 440, 230);
		dbg.drawString("Escolha Sua Aposta:(Pressione o Numero)", 350, 260);
		dbg.drawString("1 : [90%] +10% de Dano, [10%] -100 vida", 350, 320);
		dbg.drawString("2 : [50%] +50% de Dano, [50%] -500 vida", 350, 350);
		dbg.drawString("3 : NADA", 350, 380);
		
		dbg.setFont(f);
		
		dbg.drawImage(Constantes.greedgodImage, 675,0,384,645, null);
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_1){
			if(Constantes.rnd.nextInt(100)<90) {
				Constantes.heroi.damage+=Constantes.heroi.damage*0.1f;
			}else {
				Constantes.heroi.vida-=100;
			}
			Constantes.gameruncanvas.resetMovingKeys();
			GamePanel.telaAtiva = canvasToOverlay;
		}
		if(keyCode == KeyEvent.VK_2){
			if(Constantes.rnd.nextInt(100)<50) {
				Constantes.heroi.damage+=Constantes.heroi.damage*0.5f;
			}else {
				Constantes.heroi.vida-=500;
			}
			Constantes.gameruncanvas.resetMovingKeys();
			GamePanel.telaAtiva = canvasToOverlay;
		}
		if(keyCode == KeyEvent.VK_3){
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
