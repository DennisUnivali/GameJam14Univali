package engine.core.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import engine.core.game.particulas.Particula;
import engine.core.game.particulas.ParticulaTexto;

public class Personagem extends Sprite {
	
	float velX = 0;
	float velY = 0;
	float vel = 100;
	TileMap mapa = null;
	BufferedImage charset;
	
	int collFrame = 0;
	int linhaAnim = 0;
	int animationTimer = 0;
	int tempoEntreFrames = 100;
	
	int charW = 24;
	int charH = 32;
	
	//72x128
	int charaX = 0;
	int charaY = 0;
	
	int raio = 12;
	
	float vida = 10;
	float vidaMaxima = 10;
	
	
	float damage = 10;
	int timerDamage = 0;
	
	boolean invulneravel = false;
	int invulneravelTimer = 0;
	boolean dash = false;
	
	//Heroi
	int attacktime = 0;
	int xp = 0;
	
	public Personagem(int x,int y,TileMap mapa,BufferedImage charset) {
		this.mapa = mapa;
		X = x;
		Y = y;
		this.charset = charset;
		
		vida = 10;
		vidaMaxima = 10;
	}

	@Override
	public void desenhaSe(Graphics2D dbg, int xTela, int yTela) {
		dbg.drawImage(charset, (int)X-xTela,(int)Y-yTela,(int)X+charW-xTela,(int)Y+charH-yTela,charaX+ charW*collFrame,charaY+charH*linhaAnim,charaX+ charW*collFrame+charW,charaY+charH*linhaAnim+charH,null);
		
//		dbg.setColor(Color.yellow);
//		dbg.drawRect((int)X-xTela,(int)Y-yTela, charW, charH);
//		
//		dbg.setColor(Color.red);
//		//dbg.drawRect((int)X-xTela+charW/2,(int)Y-yTela+charH/2, charW, charH);
//		dbg.drawOval((int)X-xTela+charW/2-raio,(int)Y-yTela+charH/2-raio,2*raio,2*raio);
		
		
		dbg.setColor(Color.red);
		dbg.fillRect((int)X-xTela,(int)Y-yTela-10, 25, 5);
		
		int barlife = (int)((vida/(float)vidaMaxima)*25);
		
		dbg.setColor(Color.green);
		dbg.fillRect((int)X-xTela+25-barlife,(int)Y-yTela-10, barlife, 5);
		
		if(attacktime<200) {
			double ang = 0;
			if(linhaAnim==3) {
				ang = Math.PI;
			}
			if(linhaAnim==0) {
				ang = -Math.PI/2;
			}
			if(linhaAnim==2) {
				ang = Math.PI/2;
			}
			
			AffineTransform trans = dbg.getTransform();
			
			dbg.translate((X-xTela+12),(Y-yTela+16));
			dbg.rotate(ang);
			
			//dbg.translate((X-xTela+12),(Y-yTela+16));
			
			
			if(attacktime<50) {
				//dbg.drawImage(Constantes.slashImages[0],(int)X-xTela+24,(int)Y-yTela-16,null);
				dbg.drawImage(Constantes.slashImages[0],12,-32,null);
			}else if(attacktime<150) {
				//dbg.drawImage(Constantes.slashImages[1],(int)X-xTela+24,(int)Y-yTela-16,null);
				dbg.drawImage(Constantes.slashImages[1],12,-32,null);
			}else {
				//dbg.drawImage(Constantes.slashImages[2],(int)X-xTela+24,(int)Y-yTela-16,null);
				dbg.drawImage(Constantes.slashImages[2],12,-32,null);
			}
			
			
			dbg.setTransform(trans);
		}
		if(dash) {
			double ang = 0;
			if(linhaAnim==3) {
				ang = Math.PI;
			}
			if(linhaAnim==0) {
				ang = Math.PI/2;
			}
			if(linhaAnim==2) {
				ang = -Math.PI/2;
			}
			
			AffineTransform trans = dbg.getTransform();
			dbg.translate((X-xTela-4+16),(Y-yTela+16));
			dbg.rotate(ang);

			
			dbg.drawImage(Constantes.dashImage,-16,-16,null);
			
			
			
			dbg.setTransform(trans);

		}
	}

	@Override
	public void atualizeSe(long DiffTime) {
		animationTimer+=DiffTime;
		collFrame = (animationTimer/tempoEntreFrames)%3;
		
		timerDamage+=DiffTime;
		attacktime+=DiffTime;
		invulneravelTimer+=DiffTime;
		if(invulneravelTimer<100) {
			invulneravel = true;
		}else {
			invulneravel = false;
		}
		
		float oldX = X;
		float oldY = Y;
		
		X = X + velX*DiffTime/1000.0f;
		Y = Y + velY*DiffTime/1000.0f;
		
		
		boolean colidiu = false;
		
		if(mapa.testColision(X+12, Y+32)) {
			colidiu = true;
		}
		

		
		if(colidiu) {
			X = oldX;
			Y = oldY;
			velX = -velX;
			velY = -velY;
		}
		
		
		if(this==Constantes.heroi) {
			if(attacktime>600) {
				attacktime = 0;
			}
			
			
			for(int i = 0; i < GameRunCanvas.listaItemMapa.size();i++) {
				ItemMapa item = GameRunCanvas.listaItemMapa.get(i);
				if(item.colideRet(this)) {
					//vida+=100;
					xp++;
					GameRunCanvas.listaItemMapa.remove(i);
					break;
				}
			}
			if(dash) {
				for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
					Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
					if(p2!=this) {
						if(colideRet(p2)) {
							p2.levaDano(damage);
						}
					}
				}
			}
			if(attacktime<200) {
				for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
					Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
					if(p2!=this) {
						if(linhaAnim==0) {
							if(colideRet(p2,X-16,Y+12-64,+X-16+32,Y+12)) {
								p2.levaDano(damage);
								p2.Y-=8;
							}
						}
						if(linhaAnim==1) {
							if(colideRet(p2,X+24,Y-16,+X+24+64,+Y-16+32)) {
								p2.levaDano(damage);
								p2.X+=8;
							}
						}
						if(linhaAnim==2) {
							if(colideRet(p2,X+16,Y+24,+X+16+32,+Y+24+64)) {
								p2.levaDano(damage);
								p2.Y+=8;
							}
						}
						if(linhaAnim==3) {
							if(colideRet(p2,X-64,Y+16,X,+Y+16+32)) {
								p2.levaDano(damage);
								p2.X-=8;
							}
						}
					}
				}
			}
		}else {
			if(timerDamage>1000&&colideRet(Constantes.heroi)) {
				timerDamage = 0;
				Constantes.heroi.levaDano(damage);
			}
		}
		

		if(Math.abs(velX)>Math.abs(velY)) {
			if(velX>0) {
				linhaAnim = 1;
			}else if(velX<0){
				linhaAnim = 3;
			}
		}else {
			if(velY<0) {
				linhaAnim = 0;
			}else if(velY>0){
				linhaAnim = 2;
			}
		}
		

	}

	public boolean colidePersonagem() {
		for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
			Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
			if(p2!=this) {
				if(colideRet(p2)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public Personagem colidePersonagemRP(int raio) {
		for(int i = 0; i < GameRunCanvas.listaDePersonagem.size();i++) {
			Personagem p2 = GameRunCanvas.listaDePersonagem.get(i);
			if(p2!=this) {
				if(colideCircle(p2,raio)) {
					return p2;
				}
			}
		}
		return null;
	}
	
	public void levaDano(float dano) {
		if(dash==true||invulneravel==true) {
			return;
		}
		vida-=dano;
		invulneravelTimer = 0;
		
		ParticulaTexto pt = new ParticulaTexto(mapa, X, Y,(float)-(Math.PI/2), 50, 400, "-"+dano);
		
		synchronized (GameRunCanvas.listaParticulas) {
			GameRunCanvas.listaParticulas.add((Particula)pt);
		}
		
		
		if(vida<=0) {
			vivo = false;
			ItemMapa item = new ItemMapa();
			item.X = X;
			item.Y = Y;
			GameRunCanvas.listaItemMapa.add(item);
		}
	}
	
	public boolean colideRet(Personagem p2) {
		if(!(p2.X > (X+charW)|| (p2.X+p2.charW) < X)&&!(p2.Y > (Y+charH)|| (p2.Y+p2.charH) < Y)) {
			return true;
		}
		
		return false;
	}
	
	public boolean colideRet(Personagem p2,float x1,float y1,float x2,float y2) {
		if(!(p2.X > x2 || (p2.X+p2.charW) < x1)&&!(p2.Y > y2 || (p2.Y+p2.charH) < y1)) {
			return true;
		}
		
		return false;
	}
	
	public boolean colideCircle(Personagem p2,int raio) {
		float dx = (p2.X+p2.charW/2)-(X+charW/2);
		float dy = (p2.Y+p2.charH/2)-(Y+charH/2);
		
		float d = dx*dx+dy*dy;
		float sraio = p2.raio+raio;//raio do personagem mais raio do  tiro
		
		if(sraio*sraio>d) {
			return true;
		}else {
			return false;
		}
	}

}
