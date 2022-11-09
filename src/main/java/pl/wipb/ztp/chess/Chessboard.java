package pl.wipb.ztp.chess;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

public class Chessboard extends JPanel {
	public static final int ZEROX = 23;
	static final int ZEROY = 7;

	private HashMap<Point, Piece> board = new HashMap<Point, Piece>();

	public void drop(Piece p, int x, int y) {
		repaint();
		p.moveTo(x, y);
		board.put(new Point(x, y), p); // jeśli na tych współrzędnych
		// jest już jakaś figura, znika ona z planszy
		// (HashMap nie dopuszcza powtórzeń)
	}

	public Piece take(int x, int y) {
		repaint();
		return board.remove(new Point(x, y));
	}

	private Image image;
	private Piece dragged = null;
	private Point mouse = null;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		Graphics2D g2d = (Graphics2D) g;
		for (Map.Entry<Point, Piece> e : board.entrySet()) {
			Point pt = e.getKey();
			Piece pc = e.getValue();
			pc.draw(g2d);
		}
		if (mouse != null && dragged != null) {
			dragged.draw(g2d);
		}
	}

	Chessboard() {
		board.put(new Point(0, 2), new Piece(11, 0, 2));
		board.put(new Point(0, 6), new Piece(0, 0, 6));
		board.put(new Point(1, 4), new Piece(6, 1, 4));
		board.put(new Point(1, 5), new Piece(5, 1, 5));
		board.put(new Point(3, 7), new Piece(1, 3, 7));
		board.put(new Point(4, 3), new Piece(6, 4, 3));
		board.put(new Point(4, 4), new Piece(7, 4, 4));
		board.put(new Point(5, 4), new Piece(6, 5, 4));
		board.put(new Point(5, 6), new Piece(0, 5, 6));
		board.put(new Point(6, 5), new Piece(0, 6, 5));
		board.put(new Point(7, 4), new Piece(0, 7, 4));

		try {
			image = loadImage("/img/board3.png");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		setPreferredSize(new Dimension(image.getWidth(null), image.getHeight(null)));

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent ev) {
				dragged = take((ev.getX() - ZEROX) / Piece.TILESIZE, (ev.getY() - ZEROY) / Piece.TILESIZE);
				mouse = ev.getPoint();
			}

			public void mouseReleased(MouseEvent ev) {
				drop(dragged, (ev.getX() - ZEROX) / Piece.TILESIZE, (ev.getY() - ZEROY) / Piece.TILESIZE);
				dragged = null;
				undo.setEnabled(true);
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent ev) {
				mouse = ev.getPoint();
				repaint();
			}
		});
	}

	class UndoButton implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.out.println("UNDO");
			redo.setEnabled(true);
		}
	}

	class RedoButton implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			System.out.println("REDO");
		}
	}

	private JButton undo, redo;

	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Chess");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Chessboard board = new Chessboard();

		JToolBar bar = new JToolBar();
		try {
			board.undo = new JButton(new ImageIcon(loadImage("/img/undo.png")));
			board.redo = new JButton(new ImageIcon(loadImage("/img/redo.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		board.undo.addActionListener(board.new UndoButton());
		board.redo.addActionListener(board.new RedoButton());
		board.undo.setEnabled(false);
		board.redo.setEnabled(false);
		bar.add(board.undo);
		bar.add(board.redo);

		frame.add(bar, BorderLayout.PAGE_START);
		frame.add(board);

		frame.pack();
		frame.setVisible(true);
	}

	public static Image loadImage(String path) throws IOException {
		InputStream fileName = Chessboard.class.getResourceAsStream(path);
        return ImageIO.read(fileName);
	}
}