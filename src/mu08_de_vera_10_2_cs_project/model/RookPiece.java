/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;

import java.util.ArrayList;
import mu08_de_vera_10_2_cs_project.exceptions.PositionOutOfBoundsException;

/**
 *
 * @author gabee
 */
public class RookPiece extends Piece {
    protected boolean moved;
    // Constructors
    public RookPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
        moved = false;
    }
    
    public RookPiece(IVec2 pos, PieceColor pieceColor, boolean moved) {
        super(pos, pieceColor);
        this.moved = moved;
    }
    
    // Public Methods
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        // I use a bitmask to store information about the direction of the diagonal
        ArrayList<IVec2> possible = new ArrayList<IVec2>();
        
        IVec2[] offsetDirs = {new IVec2(0, 1), new IVec2(0, -1), new IVec2(1, 0), new IVec2(-1, 0)};
        
        directionLoop:
        for(IVec2 offsetDir : offsetDirs) {
            int maxLInftyDistance = Integer.max(offsetDir.getX(), 0) * 7 - offsetDir.getX() * pos.getX() + 
                    Integer.max(offsetDir.getY(), 0) * 7 - offsetDir.getY() * pos.getY();
            
            for(int offset = 1; offset <= maxLInftyDistance; offset += 1) {
                IVec2 possibleMove = IVec2.add(pos, offsetDir.multScalar(offset));
                
                try {
                    Piece curPieceAtPossibleMove = curBoardState.getPieceAt(possibleMove);
                    if(curPieceAtPossibleMove instanceof NullPiece){
                        possible.add(possibleMove);
                    }
                    else if(curPieceAtPossibleMove.getPieceColor() != pieceColor) {
                        possible.add(possibleMove);
                        continue directionLoop;
                    } else if(curPieceAtPossibleMove.getPieceColor() == pieceColor) {
                        continue directionLoop;
                    }
                } catch(PositionOutOfBoundsException e) {
                    // Do nothing
                }
            }
        }
        
        return possible;
    };
    
    @Override
    public void move(IVec2 newPos, BoardState curBoardState) throws PositionOutOfBoundsException {
        Piece pieceAtNewPos = curBoardState.getPieceAt(newPos);
        boolean captured = pieceAtNewPos.getPieceColor() == PieceColorUtils.invertColor(pieceColor);
        if(captured) {
            // It's an enemy piece
            curBoardState.setPieceAt(newPos, new NullPiece(newPos));  
        } 
        curBoardState.swapPieces(pos, newPos);
        curBoardState.completeMove(this, captured);
    };
    
    @Override
    public RookPiece clone() {
        return new RookPiece(pos, pieceColor, moved);
    };
}
