/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;
import java.util.ArrayList;
import mu08_de_vera_10_2_cs_project.exceptions.InvalidPieceColorException;
import mu08_de_vera_10_2_cs_project.exceptions.PieceNotFoundException;
import mu08_de_vera_10_2_cs_project.exceptions.PositionOutOfBoundsException;

/**
 *
 * @author gabee
 */
public abstract class Piece {
    // Protected Fields
    protected IVec2 pos;
    protected PieceColor pieceColor;
    
    // Constructors
    public Piece(IVec2 pos, PieceColor pieceColor) {
        this.pos = pos;
        this.pieceColor = pieceColor;
    }
    
    // Public Methods
    public abstract void move(IVec2 newPos, BoardState curBoardState) throws PositionOutOfBoundsException;
    public abstract ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState); // Returns the Legal Moves without performing a check to see if the king would be in check
    public ArrayList<IVec2> getLegalMoves(BoardState curBoardState) throws InvalidPieceColorException, PieceNotFoundException, PositionOutOfBoundsException {
        // TODO this function gets all the legal moves that don't leave the king in check
        // This algorithm is currently inefficient: it runs in O(n^3) time
        // For this class, this cubic-time algorithm will suffice since the focus is not on algorithm efficiency
        // Additionally, n is small (n = 8), so the algorithm should still run quickly
        ArrayList<IVec2> legalMovesNoCheck = getLegalMovesNoCheck(curBoardState);
        ArrayList<IVec2> legalMoves = new ArrayList<IVec2>();
        
        for (IVec2 potentialMove : legalMovesNoCheck) {
            // Create a virtual clone of the board and simulate the move
            BoardState virtualBoard = curBoardState.clone();
            virtualBoard.getPieceAt(pos).move(potentialMove, virtualBoard);
            
            // Now check to see if the move left the king in check
            if(!virtualBoard.isKingInCheck(pieceColor)) {
                // The king is not in check. Add the potential move to the list of moves
                legalMoves.add(potentialMove);
            }
        }
        
        return legalMoves;
    }
    public void setPos(IVec2 newPos) {
        pos = newPos;
    };
    public IVec2 getPos() {
        return pos;
    }
    public PieceColor getPieceColor() {
        return pieceColor;
    }
    public abstract Piece clone();
}
