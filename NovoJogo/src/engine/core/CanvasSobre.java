package engine.core;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import old.CanvasMAIN;
import old.Constantes;

public class CanvasSobre extends MyCanvas {

	int timer = 0;

	@Override
	public void update(float diftime) {
		// TODO Auto-generated method stub
		timer += diftime;

		//if (timer > 8000) {
		//	GamePanel.telaAtiva = new CanvasGame();
		//}
	}
	String arrayOpcoes[] = {"  Novo Jogo ","  Teclas ","  Sobre "};
	int selected = 0;

	@Override
	public void render(Graphics2D dbg) {

		//dbg.setColor(Color.black);
		//dbg.fillRect(0, 0, Constantes.telaW, Constantes.telaH);

		dbg.drawImage(engine.core.game.Constantes.aberturaImage,0,0, null);
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_SPACE || keyCode == KeyEvent.VK_ENTER){
			GamePanel.telaAtiva = new  CanvasSobre();
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
		GamePanel.telaAtiva = new  CanvasSobre();

		
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
