package gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.net.URL;

/**
 * author: Paul Keller, Luca Goettle, Katharina Will
 * date: 02.04.2018
 * version: 1.0
 */
public class Spieler implements Serializable{
	//Spieler-Klasse
	String name;
	Fraction score=new Fraction("0","1");
	//Score ist anfangs Null
	private int posX;
	private int posY;
	private String path;
	//Koordinaten
	Spieler(String name, int posX, int posY,String path) {
		this.path=path;

		setName(name);
		setPosX(posX);
		setPosY(posY);
	}
	public String getName() {
		return name;
	}
	public Image getIcon(){
		Image dimg;
		try {
			BufferedImage image = ImageIO.read(new File(path));
			dimg = image.getScaledInstance(80,60, Image.SCALE_SMOOTH);
		} catch (Exception e) {
			System.out.println("notfound");
			return null;
		}
		return dimg;
	}
	private void setName(String name) {
		this.name = name;
	}
	public Fraction getScore() {
		return score;
	}
	private void setScore(Fraction score) {
		this.score = score;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosY() {
		return posY;
	}
	public void setPosY(int posY) {
		this.posY = posY;
	}
	public void addiere(Fraction frac) {
		//Methode zum hinzuaddieren von Br?chen zum Spielstand
		setScore(score.add(frac));
	}
}
