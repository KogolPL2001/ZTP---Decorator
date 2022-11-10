package pl.wipb.ztp.chess;


import java.awt.geom.*;
import java.awt.Graphics2D;

class Decorator extends DecoratorInterface {

    public Decorator(Pieces pieceDecorator) {
        super(pieceDecorator);
    }

    public void draw(Graphics2D g) {

        AffineTransform saveXform = g.getTransform();
        AffineTransform tr = new AffineTransform();
        tr.translate(23, 7);
        tr.scale(Piece.TILESIZE, Piece.TILESIZE);
        g.transform(tr);
        super.draw(g);
        g.setTransform(saveXform);
    }

    
}