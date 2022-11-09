package pl.wipb.ztp.chess;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;

class Piece {
	public static final int TILESIZE = 32;
	private static Image image;
	
	static {
		try {
			image = Chessboard.loadImage("/img/pieces4.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int index, x, y;

	public Piece(int idx, int xx, int yy) {
		index = idx;
		x = xx;
		y = yy;
	}

	public void draw(Graphics2D g) {
		g.drawImage(image, x, y, x + TILESIZE, y + TILESIZE, index * TILESIZE, 0, (index + 1) * TILESIZE, TILESIZE,
				null);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void moveTo(int xx, int yy) {
		x = xx;
		y = yy;
	}
}