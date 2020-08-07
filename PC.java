package game_caro;

public class PC {
	private int x;
	private int y;
	private long scores;
	public PC(int x, int y, long scores) {
		super();
		this.x = x;
		this.y = y;
		this.scores = scores;
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
	public long getScores() {
		return scores;
	}
	public void setScores(long scores) {
		this.scores = scores;
	}
	

}
