
public class Spieler {
	//Spieler-Klasse
	String name;
	Fraction score=new Fraction("0","1");
	//Score ist anfangs Null
	int posX;
	int posY;
	//Koordinaten
	public Spieler(String name, int posX, int posY) {
		setName(name);
		setPosX(posX);
		setPosY(posY);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Fraction getScore() {
		return score;
	}
	public void setScore(Fraction score) {
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
