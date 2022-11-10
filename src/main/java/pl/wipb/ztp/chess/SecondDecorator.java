package pl.wipb.ztp.chess;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

class SecondDecorator implements Pieces
{
    private AffineTransform affineTransform;
    protected Pieces pieceDecorator;

    public SecondDecorator(Pieces pieceDecorator, AffineTransform af) {
        this.pieceDecorator=pieceDecorator;
        affineTransform=af;
    }

    public void draw(Graphics2D g) {
        AffineTransform saveXform = g.getTransform();
        g.transform(affineTransform);
        pieceDecorator.draw(g);
        g.setTransform(saveXform);
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

    public Pieces Undecorate(){
        return pieceDecorator;
    }

}