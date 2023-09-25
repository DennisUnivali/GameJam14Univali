package engine.core.game;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import engine.core.GamePanel;

public class Constantes {
	
	public static int PWIDTH = 1020;
	public static int PHEIGHT = 720;
	public static double PWIDTHZ = 1020;
	public static double PHEIGHTZ = 720;
	
	public static int FPS,SFPS;
	public static int fpscount;
	
	public static GameRunCanvas gameruncanvas = null;
	
	public static Random rnd = new Random();
	
	public static Personagem heroi;
	public static Font font10 = new Font("",Font.BOLD,10);
	
	public static BufferedImage slashImages[] = new BufferedImage[3];
	public static BufferedImage dashImage;
	public static BufferedImage xpImage;
	public static BufferedImage bussulaImage;
	public static BufferedImage altarGanaciaImage;
	public static BufferedImage sucubusImage;
	public static BufferedImage youdiedImage;
	public static BufferedImage sucubusOrbImage;
	public static BufferedImage greedgodImage;
	public static BufferedImage aberturaImage;
	public static BufferedImage teclasImage;
	public static BufferedImage itemLifeImage;
	public static BufferedImage historinhaImage;
	public static BufferedImage fimJogoImages[] = new BufferedImage[4];
	
	static {
		slashImages[0] = carregaImagem("slash00.png");
		slashImages[1] = carregaImagem("slash01.png");
		slashImages[2] = carregaImagem("slash02.png");
		
		dashImage = carregaImagem("dash00.png");
		xpImage = carregaImagem("xp01.png");
		bussulaImage = carregaImagem("bussula.png");
		altarGanaciaImage = carregaImagem("ganancia00.png");
		sucubusImage = carregaImagem("sucubus00.png");
		youdiedImage = carregaImagem("youdied00.png");
		sucubusOrbImage = carregaImagem("sucubusOrb00.png");
		greedgodImage = carregaImagem("greedgod00.png");
		aberturaImage = carregaImagem("abertura00.png");
		teclasImage = carregaImagem("teclas00.png");
		itemLifeImage = carregaImagem("life01.png");
		historinhaImage = carregaImagem("historinha00.png");
		fimJogoImages[0] = carregaImagem("fim00.png");
		fimJogoImages[1] = carregaImagem("fim01.png");
		fimJogoImages[2] = carregaImagem("fim02.png");
		fimJogoImages[3] = carregaImagem("fim03.png");
	}
	
	public static BufferedImage carregaImagem(String imageName) {
		
		try {
			BufferedImage imageTmp = ImageIO.read( GamePanel.class.getResource(imageName) );
			BufferedImage imageFinal = new BufferedImage(imageTmp.getWidth(), imageTmp.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
			imageFinal.getGraphics().drawImage(imageTmp, 0, 0, null);
			
			return imageFinal;
		}
		catch(IOException e) {
			System.out.println("Load Image error:");
		}
		return null;
	}
}
