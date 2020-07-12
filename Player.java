package game_caro;


public class Player {
int x,y;
String name;
String value;

public String getValue() {
	return value;
}


public void setValue(String value) {
	this.value = value;
}


public Player(int x, int y) {
	super();
	this.x = x;
	this.y = y;
}


public Player() {
}


public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public int getX() {
	return x;
}

public void setX(int x) {
	this.x = x;
}

public int getY() {
	return y;
}

public void setY(int y) {
	this.y = y;
}

}
