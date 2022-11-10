package pl.wipb.ztp.chess;


import java.awt.geom.*;
import java.awt.Graphics2D;

class Decorator implements Pieces {

    protected Pieces pieceDecorator;

    public Decorator(Pieces pieceDecorator) {
        this.pieceDecorator=pieceDecorator;
    }
    

    public int getX() {
        return pieceDecorator.getX();
    }

    public int getY() {
        return pieceDecorator.getY();
    }

    public void moveTo(int xx, int yy) {
        pieceDecorator.moveTo(xx, yy);
    }

    public void draw(Graphics2D g) {

        AffineTransform saveXform = g.getTransform();
        AffineTransform tr = new AffineTransform();
        tr.translate(23, 7);
        tr.scale(Piece.TILESIZE, Piece.TILESIZE);
        g.transform(tr);
        pieceDecorator.draw(g);
        g.setTransform(saveXform);
    }

    public Pieces Undecorate(){
        return this.pieceDecorator;
    }
    
}