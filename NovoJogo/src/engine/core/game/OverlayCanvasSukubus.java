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

public class OverlayCanvasSukubus extends MyCanvas {
	

	MyCanvas canvasToOverlay;
	Font f16 = new Font("",Font.BOLD,16);
	int scubusTimer = 0;
	
	public OverlayCanvasSukubus(MyCanvas canvasToOverlay) {
		this.canvasToOverlay = canvasToOverlay;
	}
	
	@Override
	public void update(float diffTime) {
		// TODO Auto-generated method stub
		scubusTimer+=diffTime;
	}

	@Override
	public void render(Graphics2D dbg) {
		canvasToOverlay.render(dbg);
		if(scubusTimer<1500) {
			dbg.drawImage(Constantes.youdiedImage, 0, 150, null);
		}else {
			dbg.drawImage(Constantes.youdiedImage, 0, 150, null);
			
			dbg.setColor(Color.gray);
			dbg.fillRect(300, 200, 400, 300);
			dbg.setColor(Color.DARK_GRAY);
			dbg.fillRect(310, 210, 380, 280);
			
			Font f = dbg.getFont();
			dbg.setColor(Color.white);
			dbg.setFont(f16);
			if(Constantes.gameruncanvas.sucubusPower<3) {
				dbg.drawString("Sucubus:", 440, 230);
				dbg.drawString("\" Você é um Homem Determinado seu retorno ", 320, 260);
				dbg.drawString("não precisa acabar aqui, posso lhe dar PODER.", 320, 280);
				dbg.drawString("Eu vou lhe tirar algo em troca.", 320, 320);
				dbg.drawString("A escolha é sua.\"", 320, 340);
				dbg.drawString("1 : Poder", 350, 380);
			}else {
				dbg.drawString("\" Ja tirei todos que você ama. ", 320, 260);
				dbg.drawString("Você não tem mais nada que me interesse.\"", 320, 280);
			}
			dbg.drawString("2 : Morte", 350, 410);
	
			
			dbg.drawImage(Constantes.sucubusImage, 650,0,384,645, null);
			
			dbg.setFont(f);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_1 && Constantes.gameruncanvas.sucubusPower<3){
			Constantes.gameruncanvas.sucubusPower++;
			Constantes.heroi.vida = Constantes.heroi.vidaMaxima;
			
			Constantes.gameruncanvas.resetMovingKeys();
			GamePanel.telaAtiva = canvasToOverlay;
		}
		if(keyCode == KeyEvent.VK_2){
			GameOverCanvas gameOverCanvas = new GameOverCanvas(canvasToOverlay);
			GamePanel.telaAtiva = gameOverCanvas;
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
