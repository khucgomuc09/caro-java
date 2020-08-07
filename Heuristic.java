package game_caro;

public class Heuristic {
	long[] attackArray = new long[] { 0, 8, 56, 392, 2477, 19208, 118008 };
	long[] defenseArray = new long[] { 0, 4, 32, 325, 2048, 16384, 59049 };
	private String[][] chessBoard;
	private PC pc = null;
	static final String VALUE_X = "[x]";
	static final String VALUE_Y = "[o]";

	public Heuristic(String[][] chessBoard) {
		this.chessBoard = chessBoard;
	}

	public Heuristic() {
	}

	public PC getMaxNode(String[][] Board) {
		long maxScores = minimax(3, Board, true);
		for (int i = 1; i < Board.length; i++) {
			for (int j = 1; j < Board.length; j++) {
				if (Board[i][j] == "[ ]") {
					long score = sumAttackScore(i, j) > sumDefendScore(i, j) ? sumAttackScore(i, j)
							: sumDefendScore(i, j);
					if (score == maxScores) {
					pc = new PC(i, j, maxScores);
					System.out.println(
							"x " + pc.getX() + " y " + pc.getY() + " sc " + pc.getScores() + " max " + maxScores);
				}
			}
		}
		}
		return pc;
	}

	private long getHeuristic(String[][] board) {
		long heuScores = 0;
		for (int i = 1; i < board.length; i++) {
			for (int j = 1; j < board.length; j++) {
				if (board[i][j] == "[ ]") {
					long score = sumAttackScore(i, j) > sumDefendScore(i, j) ? sumAttackScore(i, j)
							: sumDefendScore(i, j);
					if (score >= heuScores) {
						heuScores = score;
//						pc = new PC(i, j, heuScores);
					}
				}
			}
		}
		return heuScores;
	}

	private long minimax(int depth, String[][] board, boolean minmax) {
		long minScores = 0;
		if (depth == 0)
			return getHeuristic(board);
		if (minmax == true) {
			long temp = -9999;
			for (int i = 1; i < board.length; i++) {
				for (int j = 1; j < board.length; j++) {
					if (board[i][j] == "[ ]") {
						String[][] newChessBoard = new String[board.length][board.length];
						for (int k = 1; k < newChessBoard.length; k++) {
							for (int k2 = 1; k2 < newChessBoard.length; k2++) {
								newChessBoard[k][k2] = board[k][k2];
							}
						}
						newChessBoard[i][j] = VALUE_X;
						long value = minimax(depth - 1, newChessBoard, false);
						if (temp < value) {
							
							temp = value;
						}
						minScores = temp;
					}
				}
			}
		}
		if (minmax == false) {
			long temp = 9999999;
			for (int i = 1; i < board.length; i++) {
				for (int j = 1; j < board.length; j++) {
					if (board[i][j] == "[ ]") {
						String[][] newChessBoard = new String[board.length][board.length];
						for (int k = 1; k < newChessBoard.length; k++) {
							for (int k2 = 1; k2 < newChessBoard.length; k2++) {
								newChessBoard[k][k2] = board[k][k2];
							}
						}
						newChessBoard[i][j] = VALUE_Y;
						long value = minimax(depth - 1, newChessBoard, true);
						if (temp > value) {
							
							temp = value;
						}
						minScores = temp;
					}

				}
			}
		}

		return minScores;
	}

	// hàm này đánh giá tổng điểm tấn công của vị trí x,y từ tất cả các hướng
	private long sumAttackScore(int x, int y) {
		int eHuman = 0;
		int ePC = 0;
		long scoreVertical = 0;
		long scoreD = 0;
		// toLeft
		int column = y;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			if (column < 0)
				break;
			if (chessBoard[x][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[x][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column - 1 > 0 && chessBoard[x][column - 1] == VALUE_X) {
					ePC++;
				}
				break;

			}
		}
		// toRight
		column = y;
		for (int i = 5; i > 0; i--) {
			column += 1;
			if (column > chessBoard.length - 1)
				break;

			if (chessBoard[x][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[x][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column + 1 < chessBoard.length - 1 && chessBoard[x][column + 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
		scoreD = defenseArray[eHuman];
		scoreVertical = attackArray[ePC] - scoreD;
		//
		long scoreHorizontal = 0;
		scoreD = 0;
		eHuman = 0;
		ePC = 0;

		// toTop
		int row = x;
		for (int i = 5; i > 0; i--) {
			row -= 1;
			if (row < 0)
				break;
			if (chessBoard[row][y] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][y] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (row - 1 > 0 && chessBoard[x][row - 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
		// toBot
		row = x;
		for (int i = 5; i > 0; i--) {
			row += 1;
			if (row > chessBoard.length - 1)
				break;
			if (chessBoard[row][y] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][y] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (row + 1 < chessBoard.length - 1 && chessBoard[x][row + 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
		scoreD = defenseArray[eHuman];
		scoreHorizontal = attackArray[ePC] - scoreD;
		//
		long scoreDiagonalmain = 0;
		eHuman = 0;
		scoreD = 0;
		ePC = 0;
//		toBotRight
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column += 1;
			row += 1;
			if (column > chessBoard.length - 1 || row > chessBoard.length - 1) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column + 1 < chessBoard.length - 1 && row + 1 < chessBoard.length - 1
						&& chessBoard[row + 1][column + 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
//		toTopLeft
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			row -= 1;
			if (column < 0 || row < 0) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column - 1 > 0 && row - 1 > 0 && chessBoard[row - 1][column - 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
		scoreD = defenseArray[eHuman];
		scoreDiagonalmain = attackArray[ePC] - scoreD;
		///
		long scoreDiagonalfiller = 0;
		scoreD = 0;
		eHuman = 0;
		ePC = 0;
//		toBotLeft
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			row += 1;
			if (column < 0 || row > chessBoard.length - 1) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column - 1 > 0 && row + 1 < chessBoard.length - 1 && chessBoard[row + 1][column - 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
//		toTopRight
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column += 1;
			row -= 1;
			if (column > chessBoard.length - 1 || row < 0) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
				break;
			} else {
				if (column + 1 < chessBoard.length - 1 && row - 1 > 0 && chessBoard[row - 1][column + 1] == VALUE_X) {
					ePC++;
				}
				break;
			}
		}
		scoreD = defenseArray[eHuman];
		scoreDiagonalfiller = attackArray[ePC] - scoreD;
		ePC = 0;
		scoreD = 0;
		eHuman = 0;
		return scoreDiagonalfiller + scoreDiagonalmain + scoreHorizontal + scoreVertical;
	}

	// hàm này đánh giá tổng điểm phòng thủ của vị trí x,y từ tất cả các hướng
	private long sumDefendScore(int x, int y) {
		int eHuman = 0;
		int ePC = 0;
		long scoreD = 0;
		long scoreVertical = 0;
		// toLeft
		int column = y;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			if (column < 0)
				break;
			if (chessBoard[x][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[x][column] == VALUE_Y) {
				eHuman++;
//				break;
			} else {
				break;
			}
		}
		// toRight
		column = y;
		for (int i = 5; i > 0; i--) {
			column += 1;
			if (column > chessBoard.length - 1)
				break;

			if (chessBoard[x][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[x][column] == VALUE_Y) {
				eHuman++;
//				break;
			} else {
				break;
			}
		}
		scoreD = defenseArray[ePC];
		scoreVertical = defenseArray[eHuman] - scoreD;
		long scoreHorizontal = 0;
		eHuman = 0;
		ePC = 0;
		scoreD = 0;

		// toTop
		int row = x;
		for (int i = 5; i > 0; i--) {
			row -= 1;
			if (row < 0)
				break;
			if (chessBoard[row][y] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][y] == VALUE_Y) {
				eHuman++;
//				break;
			} else {
				break;
			}
		}
		// toBot
		row = x;
		for (int i = 5; i > 0; i--) {
			row += 1;
			if (row > chessBoard.length - 1)
				break;
			if (chessBoard[row][y] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][y] == VALUE_Y) {
				eHuman++;
//				break;
			} else {
				break;
			}
		}
		scoreD = defenseArray[ePC];
		scoreHorizontal = defenseArray[eHuman] - scoreD;
		long scoreDiagonalmain = 0;
		eHuman = 0;
		ePC = 0;
		scoreD = 0;
//		toBotRight
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column += 1;
			row += 1;
			if (column > chessBoard.length - 1 || row > chessBoard.length - 1) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
			} else {
				break;
			}
		}
//		toTopLeft
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			row -= 1;
			if (column < 0 || row < 0) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
			} else {
				break;
			}
		}
		scoreD = defenseArray[ePC];
		scoreDiagonalmain = defenseArray[eHuman] - scoreD;
		scoreD = 0;
		eHuman = 0;
		ePC = 0;
		long scoreDiagonalfiller = 0;
//		toBotLeft
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column -= 1;
			row += 1;

			if (column < 0 || row > chessBoard.length - 1) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
			} else {
				break;
			}
		}
//		toTopRight
		column = y;
		row = x;
		for (int i = 5; i > 0; i--) {
			column += 1;
			row -= 1;
			if (column > chessBoard.length - 1 || row < 0) {
				break;
			}
			if (chessBoard[row][column] == VALUE_X) {
				ePC++;
				break;
			} else if (chessBoard[row][column] == VALUE_Y) {
				eHuman++;
			} else {
				break;
			}
		}
		scoreD = defenseArray[ePC];
		scoreDiagonalfiller = defenseArray[eHuman] - defenseArray[ePC];
		eHuman = 0;
		ePC = 0;
		return scoreDiagonalfiller + scoreDiagonalmain + scoreHorizontal + scoreVertical;
	}

}
