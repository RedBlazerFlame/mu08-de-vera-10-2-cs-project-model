/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;

/**
 * An immutable class that represents an ordered tuple of two integers
 *
 * @author gabee
 */
public class IVec2 {
    // Private Fields
    private int x,y;
    
    // Constructors
    public IVec2(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Public Methods
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public IVec2 add(IVec2 v) {
        return new IVec2(x + v.getX(), y + v.getY());
    }
    
    public IVec2 sub(IVec2 v) {
        return new IVec2(x - v.getX(), y - v.getY());
    }
    
    public int dot(IVec2 v) {
        return x * v.x + y * v.y;
    }
    
    public IVec2 multScalar(int scalar) {
        return new IVec2(scalar * x, scalar * y);
    }
    
    public IVec2 hadamardProd(IVec2 other) {
        return new IVec2(x * other.getX(), y * other.getY());
    }
    
    @Override
    public String toString() {
        return String.format("IVec2< %d, %d >", x, y);
    }
    
    // Checks to see if the vector is within the bounds of a rectangle defined by two vectors
    public boolean inBounds(IVec2 v1, IVec2 v2) {
        int minX = Integer.min(v1.x, v2.x);
        int maxX = Integer.max(v1.x, v2.x);
        int minY = Integer.min(v1.y, v2.y);
        int maxY = Integer.max(v1.y, v2.y);
        
        return v1.getX() >= minX && v1.getX() <= maxX && v2.getY() >= minY && v2.getY() <= maxY;
    }
    
    // Public Static Methods
    public static IVec2 add(IVec2 a, IVec2 b) {
        return a.add(b);
    }
    
    public static IVec2 sub(IVec2 a, IVec2 b) {
        return a.sub(b);
    }
    
    public static int dot(IVec2 a, IVec2 b) {
        return a.dot(b);
    }
    
    public static IVec2 multScalar(IVec2 a, int b) {
        return a.multScalar(b);
    }
    
    public static IVec2 hadamardProd(IVec2 a, IVec2 b) {
        return a.hadamardProd(b);
    }
    
    // Overriding Equality
    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof IVec2)) return false;
        
        // The line below is just to make the red squiggly error go away
        // Apparently, intellisense can't detect the fact that I already
        // accounted for the case where the object isn't a BoardState object
        IVec2 otherIVec2 = (IVec2)other;
        return this.x == otherIVec2.getX() && this.y == otherIVec2.getY();
    }
}
