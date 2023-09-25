package engine.core.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import engine.base.entities.GameObject;
import engine.base.entities.GameState;
import engine.core.CanvasSplash;
import engine.core.GamePanel;
import engine.core.MyCanvas;
import engine.core.game.particulas.Particula;
import engine.core.game.projetil.Projetil;
import engine.core.game.projetil.ProjetilExplosivo;
import engine.core.game.projetil.ProjetilSigmoide;
import engine.core.game.projetil.ProjetilSimples;
import engine.core.game.projetil.SucubusOrb;

public class GameRunCanvas extends MyCanvas implements GameState {


	public static Random rnd = new Random();

	BufferedImage imagemcharsets;
	BufferedImage fundo;
	BufferedImage tileset;
	BufferedImage charsetpersonagens;
	public static BufferedImage fumaca;
	public static BufferedImage fumaca2;


	boolean LEFT, RIGHT,UP,DOWN;
	boolean FIRE,DASH;
	int dashtimer = 3000;


	int MouseX,MouseY;


	//float sqX1 = 0;
	//float sqY1 = 100;

	Personagem heroi;
	int velocidadeBase = 200;

	//float sqX2 = 0;
	//float sqY2 = 200;
	float velRedBloc = 2000;


	BufferedImage chara1;
	int linhaAnim = 2;
	int collFrame = 0;
	int animationTimer = 0;

	int fireTimer = 0;
	int fireInterval = 50;


	int xTela = 0;
	int yTela = 0;

	TileMpaInfinito mapa = null;

	public Font font01 = new Font(null, Font.BOLD, 16);

	public static ArrayList<Personagem> listaDePersonagem;
	public static ArrayList<Projetil> listaProjeteis;
	public static LinkedList<Particula> listaParticulas;
	public static ArrayList<ItemMapa> listaItemMapa;

	public static double zoom = 1.0;
	public boolean antialias_on = false;

	int tipoDeTiro = 1;
	
	int gameCicleTrimer = 0;
	
	int gameEndX = 20000;
	public static int sucubusPower = 0;
	SucubusOrb oneorb = null;
	SucubusOrb oneorb1 = null;
	SucubusOrb oneorb2 = null;
	
	public GameRunCanvas() {
		Constantes.gameruncanvas = this;
		chara1 = Constantes.carregaImagem("heroiChara.png");
		System.out.println("chara1 -> "+chara1.getColorModel());
		
		charsetpersonagens = Constantes.carregaImagem("chara2.png");

		tileset = Constantes.carregaImagem("owlishmedia_pixel_tiles.png");
		
		mapa = new TileMpaInfinito(tileset);
		//mapa.carregaMapaJson("./res/engine/core/mapa01_b.json");
		
		
		MouseX = MouseY = 0;
		
		listaDePersonagem = new ArrayList<>();
		
		Constantes.heroi = new Personagem(200, 200, mapa, chara1);
		this.heroi = Constantes.heroi;
		listaDePersonagem.add(heroi);
		//TODO
		heroi.vida = 5;
		heroi.vidaMaxima = 500;
		heroi.damage = 50;
		velocidadeBase = 200;//1600;
		
		//TODO
		zoom = 1.5f;
		Constantes.PWIDTHZ = Constantes.PWIDTH/zoom;
		Constantes.PHEIGHTZ = Constantes.PHEIGHT/zoom;
		
		
		listaProjeteis = new ArrayList<>();
		listaParticulas = new LinkedList<>();
		listaItemMapa = new ArrayList<>();
		
		fumaca = Constantes.carregaImagem("fumaca.png");
		fumaca2 = Constantes.carregaImagem("fumaca2.png");
		
		
		//Adiciona Altar da Ganancia
		ItemMapa item = new ItemMapa(1,800,212);
		listaItemMapa.add(item);
	}
	
	public void resetMovingKeys() {
		LEFT = false;
		RIGHT = false;
		UP = false;
		DOWN = false;
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		/*if(e.getWheelRotation()<0) {
			zoom = zoom*1.1;
			if(zoom>=8) {
				zoom = 8;
			}
			
			Constantes.PWIDTHZ = Constantes.PWIDTH/zoom;
			Constantes.PHEIGHTZ = Constantes.PHEIGHT/zoom;
		}else if(e.getWheelRotation()>0) {
			zoom = zoom*0.9;
			if(zoom<=0.5) {
				zoom = 0.5;
			}
			Constantes.PWIDTHZ = Constantes.PWIDTH/zoom;
			Constantes.PHEIGHTZ = Constantes.PHEIGHT/zoom;
		}*/
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==1) {
			FIRE = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton()==1) {
			FIRE = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
			LEFT = true;
		}
		if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
			RIGHT = true;
		}
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
			UP = true;
		}
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			DOWN = true;
		}	
		if(keyCode == KeyEvent.VK_SPACE) {
			if(dashtimer>3000) {
				DASH = true;
				dashtimer = 0;
			}
		}
		if(keyCode == KeyEvent.VK_0) {
			antialias_on = !antialias_on;
		}
		if(keyCode == KeyEvent.VK_1) {
			tipoDeTiro = 1;
		}
		if(keyCode == KeyEvent.VK_2) {
			tipoDeTiro = 2;
		}
		if(keyCode == KeyEvent.VK_3) {
			tipoDeTiro = 3;
		}
		if(keyCode == KeyEvent.VK_M) {
			//GamePanel.intace.switchCanvas(new CanvasGameMenu());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
			LEFT = false;
		}
		if(keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
			RIGHT = false;
		}
		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W){
			UP = false;
		}
		if(keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			DOWN = false;
		}
		if(keyCode == KeyEvent.VK_SPACE) {
			DASH = false;
		}
	}





	
	private void addTiro1() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);
		//System.out.println("MouseX "+MouseX+" xTela "+xTela+" MouseY "+MouseY+" yTela "+yTela);
		//System.out.println("mx "+mx+" my "+my);
		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilSimples(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20);
		listaProjeteis.add(proj);
	}

	private void addTiro2() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);

		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		 
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilSigmoide(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20);
		listaProjeteis.add(proj);
	}

	private void addTiro3() {
		float mx = (float)(MouseX/zoom+xTela);
		float my = (float)(MouseY/zoom+yTela);
		//System.out.println("MouseX "+MouseX+" xTela "+xTela+" MouseY "+MouseY+" yTela "+yTela);
		//System.out.println("mx "+mx+" my "+my);
		
		float dx = mx - (heroi.X+heroi.charW/2);
		float dy = my - (heroi.Y+heroi.charH/2);
		
		float ang = (float)Math.atan2(dy, dx);
		
		Projetil proj = new ProjetilExplosivo(mapa,heroi.X+heroi.charW/2, heroi.Y+heroi.charH/2, ang, 500,heroi,20,200);
		listaProjeteis.add(proj);
	}

	@Override
	public String getName() {

		return null;
	}

	
	
	int tempoEntreWaves = 7000;
	int wave = 0;
	int timerwave = 5000;

	float enemydamagemull = 1;
	float enemylifeemull = 1;
	
	int wavetype[][] = {
			{20,100,100},//0
			{10,100,150},//1
			{20,200,120},//2
			{20,200,130},//3
			{20,200,200},//4
			{30,300,130},//5
			{40,300,140},//6
			{10,300,230},//7
			{50,400,210},//8
			{50,400,210}//9
	};
	@Override
	public void update(float diffTime) {
		int DiffTime = (int)diffTime;
		//System.out.println(""+diffTime+" "+DiffTime);
		gameCicleTrimer += DiffTime;
		if(gameCicleTrimer>20000) {
			gameCicleTrimer = 0;
			
			ItemMapa item = new ItemMapa(1,(int)(heroi.X+800+rnd.nextInt(500)),(int)heroi.Y-300+rnd.nextInt(600));
			listaItemMapa.add(item);
			
			enemydamagemull+=enemydamagemull*0.15;
			enemylifeemull+=enemylifeemull*0.25;
		}
		int progresso = (int)((heroi.X/gameEndX)*100);
		if(heroi.X>=gameEndX) {
			CanvasFimDeJogo fimDeJogo = new CanvasFimDeJogo();
			GamePanel.telaAtiva = fimDeJogo;
			return;
		}
		
		if(RIGHT||LEFT||DOWN||UP) {
			animationTimer+=DiffTime;
		}

		dashtimer+=DiffTime;
		
			
		int velocidade = velocidadeBase;
		if(dashtimer<300) {
			velocidade = 1000;
			heroi.dash = true;
		}else {
			velocidade = velocidadeBase;
			heroi.dash = false;
		}
		if(RIGHT) {
			heroi.velX = velocidade;
		}else if(LEFT) {
			heroi.velX = -velocidade;
		}else {
			heroi.velX = 0;
		}
		
		if(DOWN) {
			heroi.velY = velocidade;
		}else if(UP) {
			heroi.velY = -velocidade;
		}else {
			heroi.velY = 0;
		}
		
		
		xTela = (int)(heroi.X+12-Constantes.PWIDTHZ/2);
		yTela = (int)(heroi.Y+24-Constantes.PHEIGHTZ/2);
		
		
		for(int i = 0; i < listaDePersonagem.size();i++) {
			Personagem p = listaDePersonagem.get(i);
			if(p.vivo==false) {
				if(p==heroi) {
					OverlayCanvasSukubus ovcanvas = new OverlayCanvasSukubus(this);
					p.vivo = true;
					GamePanel.telaAtiva = ovcanvas;
				}else {
					listaDePersonagem.remove(i);
					i--;
				}
			}else {
				p.atualizeSe(DiffTime);
			}
		}
		
		fireTimer+=DiffTime;
		
		
//		if(FIRE&&fireTimer>=fireInterval) {
//			//TODO
//			if(tipoDeTiro==1) {
//				addTiro1();
//			}else if(tipoDeTiro==2) {
//				addTiro2();
//			}else if(tipoDeTiro==3) {
//				addTiro3();
//			}
//			
//			fireTimer = 0;
//		}
		
		if(oneorb==null&&sucubusPower>0) {
			oneorb = new SucubusOrb(mapa,heroi,50,150);
			listaProjeteis.add(oneorb);
		}
		if(oneorb1==null&&sucubusPower>1) {
			oneorb1 = new SucubusOrb(mapa,heroi,30,250);
			listaProjeteis.add(oneorb1);
		}
		if(oneorb2==null&&sucubusPower>2) {
			oneorb2 = new SucubusOrb(mapa,heroi,110,100);
			listaProjeteis.add(oneorb2);
		}

		for(int i = 0; i < listaProjeteis.size();i++) {
			Projetil p = listaProjeteis.get(i);
			if(p.vivo==false) {
				listaProjeteis.remove(i);
				i--;
			}else {
				p.atualizeSe(DiffTime);
			}
		}
		
		for (Iterator iterator = listaParticulas.iterator(); iterator.hasNext();) {
			Particula particula = (Particula) iterator.next();
			if(particula.vivo==false) {
				iterator.remove();
			}else {
				particula.atualizeSe(DiffTime);
			}
		}
		
		
		timerwave+=(int)DiffTime;
		if(timerwave>=tempoEntreWaves) {
			int wavedata[] = wavetype[wave]; 
			
			for(int i = 0; i < wavedata[0];i++) {
				Inimigo p = null;
				
				boolean colidiu = false;
				
				int baseX = (int)heroi.X;
				int baseY = (int)heroi.Y;
				
				int xmul = rnd.nextInt(3)-1;
				int ymul = rnd.nextInt(3)-1;
				if(xmul==0&&ymul==0) {
					xmul=1;
				}
				
				baseX+=1000*xmul; 
				baseY+=1000*ymul; 
				
				do {
					p = new Inimigo(baseX+rnd.nextInt(500),baseY+rnd.nextInt(500), mapa, charsetpersonagens);	
					colidiu = false;
					for(int j = 0; j < listaDePersonagem.size();j++) {
						Personagem p2 = listaDePersonagem.get(j);
						if(p2.colideRet(p)) {
							colidiu = true;
							break;
						}
					}
				}while(colidiu);
				
				
				p.velX = wavedata[2];//rnd.nextInt(400)-200; 
			    p.velY = rnd.nextInt(400)-200; 
			    p.vel = wavedata[2];
			    
				p.charaX = rnd.nextInt(4)*72; 
			    p.charaY = rnd.nextInt(2)*128; 
			    
			    float promul = (progresso/10.0f)+1.0f;
			    
			    p.vida = 10*enemylifeemull*promul;
			    p.vidaMaxima = 10*enemylifeemull*promul;
			    
			    p.damage = 10*enemydamagemull*promul;
				
				listaDePersonagem.add(p);
			}
			wave++;
			if(wave>9) {
				wave=9;
			}
			timerwave = 0;
		}
	
	}

	Color corLife = new Color(0.0f, 0.5f, 0.0f, 0.5f);
	Color corDash = new Color(0.2f, 0.2f, 0.2f, 0.5f);
	Color corXp = new Color(0.0f, 0.0f, 0.5f, 0.5f);
	@Override
	public void render(Graphics2D dbg) {
		//Pinta O fundo de Branco
		dbg.setColor(Color.white);
		dbg.fillRect(0, 0, Constantes.PWIDTH, Constantes.PHEIGHT);
		
		AffineTransform trans = dbg.getTransform();
		
		if(antialias_on) {
		    dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON); 
		    dbg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY); 
		    dbg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC); 
		}else {
		    dbg.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF); 
		    dbg.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_SPEED);
		    dbg.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); 
		}
	    
		dbg.scale(zoom, zoom);
		
		
		
		//Desenha Layer 1 e 2 do mapa
		mapa.desenhaLayer(dbg, xTela, yTela, 0,33,33);
		mapa.desenhaLayer(dbg, xTela, yTela, 1,33,33);
		
		// Desenha ItemMapa
		for(int i = 0; i < listaItemMapa.size();i++) {
			listaItemMapa.get(i).desenhaSe(dbg, xTela, yTela);
		}
		
		// Desenha Personagens
		for(int i = 0; i < listaDePersonagem.size();i++) {
			listaDePersonagem.get(i).desenhaSe(dbg, xTela, yTela);
		}
		// Desenha Projeteis
		for(int i = 0; i < listaProjeteis.size();i++) {
			listaProjeteis.get(i).desenhaSe(dbg, xTela, yTela);
		}
		// Desenha Particulas
		for (Iterator iterator = listaParticulas.iterator(); iterator.hasNext();) {
			Particula particula = (Particula) iterator.next();
			particula.desenhaSe(dbg, xTela, yTela);
		}
		
		
		//Desenha Layer 3 do mapa
		mapa.desenhaLayer(dbg, xTela, yTela, 2,33,33);
		
		
		dbg.setTransform(trans);
		
		//Barra de Vida
		int szbarh = Constantes.PHEIGHT-60;
		int fillbar = (int)((heroi.vida/(float)heroi.vidaMaxima)*szbarh);
		dbg.setColor(corLife);
		dbg.fillRect(5, 30+(szbarh-fillbar), 20, fillbar);
		dbg.setColor(Color.black);
		dbg.drawRect(5, 30, 20, szbarh);
		
		
		//Barra de DASH
		int dashbarsz = 200;
		int dashpart = 0;
		if(dashtimer<3000) {
			dashpart = (int)((dashtimer/(float)5000)*dashbarsz);
		}else {
			dashpart = 200;
		}
		dbg.setColor(corDash);
		dbg.fillRect(30, Constantes.PHEIGHT-230+(dashbarsz-dashpart), 20, dashpart);
		dbg.setColor(Color.black);
		dbg.drawRect(30, Constantes.PHEIGHT-230, 20, dashbarsz);
		
		//Barra de XP
		int xpbarsize = Constantes.PHEIGHT-235-30;
		int xpbarfill = (int)((heroi.xp/(float)heroi.xpLevelUp)*xpbarsize);
		
		dbg.setColor(corXp);
		dbg.fillRect(30, 30+(xpbarsize-xpbarfill), 20, xpbarfill);
		dbg.setColor(Color.black);
		dbg.drawRect(30, 30, 20, xpbarsize);
		
		//Bussula
		dbg.drawImage(Constantes.bussulaImage, Constantes.PWIDTH-50, 0, null);
		
		dbg.setFont(font01);
		dbg.setColor(Color.DARK_GRAY);
		int progresso = (int)((heroi.X/gameEndX)*100);
		dbg.drawString("Progresso: "+progresso+"%", Constantes.PWIDTH-190, 30);
		
		dbg.setColor(Color.DARK_GRAY);
		dbg.drawString("LV: "+heroi.level+"", Constantes.PWIDTH-250, 30);
		
		//Desenha Infos de Interface
		dbg.setFont(font01);
		dbg.setColor(Color.BLUE);
		dbg.drawString("FPS: "+GamePanel.FPS, 20, 20);
		//dbg.drawString("LEFT "+LEFT+" RIGHT "+RIGHT+" UP "+UP+" DOWN "+DOWN, 20, 50);	
	}

	@Override
	public Map<String, GameObject> getEntities() {
		return null;
	}	

}
