package game_caro;

import java.util.Scanner;

public class Func {
	private static String[][] chessboard;// = new String[16][16];
	static final String VALUE_X = "[x]";
	static final String VALUE_Y = "[o]";
	Player playerX;
	Player playerY;
	Player player;
	boolean isPlayerX = true;
	private Heuristic heu;

	public void DrawChessboard() {
		Scanner sc = new Scanner(System.in);
		System.out.println("nhap kich thuoc cua ban co: ");
		int size = sc.nextInt();
		chessboard = new String[size + 1][size + 1];
		heu = new Heuristic(chessboard);
		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard.length; j++) {
				if (i == 0 && j == 0) {
					chessboard[i][j] = "x/y";
				} else if (i == 0) {
					for (int j1 = 1; j1 < chessboard.length; j1++) {
						if (j1 < 10) {
							chessboard[i][j1] = " " + j1 + " ";
						} else {
							chessboard[i][j1] = "" + j1 + " ";
						}
					}
				} else if (j == 0) {
					for (int i1 = 1; i1 < chessboard.length; i1++) {

						if (i1 < 10) {
							chessboard[i1][j] = " " + i1 + " ";
						} else {
							chessboard[i1][j] = "" + i1 + " ";
						}
					}
				} else {
					chessboard[i][j] = "[ ]";
				}
			}
		}
	}

//	public void copyChessboard(String[][] arr) {
//		chessboard = new String[16][16];
//		for (int i = 0; i < chessboard.length; i++) {
//			for (int j = 0; j < chessboard.length; j++) {
//				chessboard[i][j]=arr[i][j];
//			}
//			
//		}
//	}

	public void initChessBoard() {

		for (int i = 0; i < chessboard.length; i++) {
			for (int j = 0; j < chessboard.length; j++) {
				System.out.print(chessboard[i][j]);
			}
			System.out.println();
		}
	}

	public void initPlayer() {
		Scanner sc = new Scanner(System.in);
		System.out.println("nhap ten nguoi choi X:");
		playerX = new Player();
		playerX.setName(sc.nextLine());
		playerX.setValue(VALUE_X);
		System.out.println("nhap ten nguoi choi O:");
		playerY = new Player();
		playerY.setName(sc.nextLine());
		playerY.setValue(VALUE_Y);
		player = playerX;

	}

	public boolean isValid() {
		if (chessboard[player.getX()][player.getY()].equals("[ ]")) {
			isPlayerX = !isPlayerX;
			return true;
		} else
			return false;
	}

	public void playWithPC() {
		playerX = new Player();
		playerX.setName("yourPC");
		playerX.setValue(VALUE_X);
		playerY = new Player();
		playerY.setValue(VALUE_Y);
		player = playerX;
		System.out.println("nhap ten cua ban: ");
		Scanner sc = new Scanner(System.in);
		playerY.setName(sc.nextLine());
//		String yourname=sc.nextLine();
		boolean firstTime = true;
		while (true) {
			if (isPlayerX) {
				player = playerX;
			} else {
				player = playerY;
				System.out.println("luot choi cua " + player.getName() + " " + player.getValue());
			}
			if (!isPlayerX) {
				try {
					System.out.println("nhap x: ");
					player.setX(Integer.parseInt(sc.nextLine()));
					System.out.println("nhap y: ");
					player.setY(Integer.parseInt(sc.nextLine()));
					if (isValid()) {
						chessboard[player.getX()][player.getY()] = player.getValue();
					}
					initChessBoard();
					if (checkWin(player)) {
						System.out.println("Player " + player.getName() + " win");
						sc.close();
						break;
					}
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("---> input must is number and not out of zise chessboard <---");
				}
			} else {
				int x = 0;
				int y = 0;
				if (firstTime) {
					x = y = chessboard.length / 2 - 1;
//					y = x;
					firstTime = false;
				} else {
					PC field = heu.getMaxNode(chessboard);
					x = field.getX();
					y = field.getY();
				}
				player.setX(x);
				player.setY(y);
				if (isValid()) {
					chessboard[player.getX()][player.getY()] = player.getValue();
					initChessBoard();
					System.out.println(
							"==>> " + player.name + " vua danh vao vi tri [" + player.x + "][" + player.y + "]");
				}
				if (checkWin(player)) {
					System.out.println("Player " + player.getName() + " win");
					sc.close();
					break;
				}

			}

		}
	}

	public void playWithHuman() {
		initPlayer();
		Scanner sc = new Scanner(System.in);
		while (true) {
			if (isPlayerX) {
				player = playerX;
			} else {
				player = playerY;
			}
			System.out.println("luot choi cua " + player.getName() + " " + player.getValue());
			try {
				System.out.println("nhap x: ");
				player.setX(Integer.parseInt(sc.nextLine()));
				System.out.println("nhap y: ");
				player.setY(Integer.parseInt(sc.nextLine()));
				if (isValid()) {
					chessboard[player.getX()][player.getY()] = player.getValue();
				}
				initChessBoard();
				if (checkWin(player)) {
					System.out.println("Player " + player.getName() + " win");
					sc.close();
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
				System.out.println("---> input must is number and not out of zise chessboard <---");
			}
		}
	}

	public boolean checkWin(Player player) {
		boolean checkwin = false;
		// ngang\doc
		int count = 0, count1 = 0, count2 = 0;
		int X = player.getX();
		int Y = player.getY();
		for (int i = 1; i < chessboard.length; i++) {

			boolean checkrow = chessboard[X][i].equals(player.getValue());// hang

			if (checkrow) {
				count++;
				if (count > 4) {
					checkwin = true;
					break;
				}
			} else {
				count = 0;
			}
		}

		for (int i = 1; i < chessboard.length; i++) {
			boolean checkcol = chessboard[i][Y].equals(player.getValue());// cot
			if (checkcol) {
				count++;
				if (count > 4) {
					checkwin = true;
					break;
				}
			} else {
				count = 0;
			}
		}

		// duong cheo chinh
		for (int i = 1; i <= chessboard.length - player.getX(); i++) {
			if (X + i >= chessboard.length || Y + i >= chessboard.length)
				break;
			if (chessboard[X + i][Y + i].equals(player.getValue())) {
				count1++;
			} else
				break;
		}
		for (int i = 1; i < player.getX(); i++) {
			if (X - i <= 0 || Y - i <= 0)
				break;
			if (chessboard[X - i][Y - i].equals(player.getValue())) {
				count2++;
			} else
				break;
		}
		count = count1 + count2;
		if (count >= 4) {
			checkwin = true;
		} else {
			count1 = 0;
			count2 = 0;
			count = 0;
		}
		// duong cheo phu
		for (int i = 1; i <= player.getX(); i++) {
			if (X + i >= chessboard.length || Y - i <= 0)
				break;
			if (chessboard[X + i][Y - i].equals(player.getValue())) {
				count1++;
			} else
				break;
		}
		for (int i = 1; i < chessboard.length - player.getX(); i++) {
			if (X - i <= 0 || Y + i >= chessboard.length)
				break;
			if (chessboard[X - i][Y + i].equals(player.getValue())) {
				count2++;
			} else
				break;
		}
		count = count1 + count2;
		if (count >= 4) {
			checkwin = true;
		} else {

			count = 0;
		}
		return checkwin;
	}

	public static void main(String[] args) {
		System.out.println("type of play:\n1. Play with friend\n2. play with pc");
		Func f = new Func();
		Scanner sc = new Scanner(System.in);
		int typeOfPlay = sc.nextInt();
		if (typeOfPlay == 1) {
			f.DrawChessboard();
			f.initChessBoard();
			f.playWithHuman();
		} else if (typeOfPlay == 2) {
			f.DrawChessboard();
			f.initChessBoard();
			f.playWithPC();
		}

	}
}
