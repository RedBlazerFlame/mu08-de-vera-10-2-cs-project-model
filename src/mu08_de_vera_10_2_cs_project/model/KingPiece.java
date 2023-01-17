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
public class KingPiece extends Piece {
    // Private Fields
    private boolean moved;
    
    // Constructors
    public KingPiece(IVec2 pos, PieceColor pieceColor, boolean moved) {
        super(pos, pieceColor);
        this.moved = moved;
    }
    
    public KingPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
        this.moved = false;
    }
    
    // Public Methods
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        // Normal moves
        ArrayList<IVec2> possible = new ArrayList<IVec2>();
        for(int dy = -1; dy < 2; dy ++) {
            for(int dx = -1; dx < 2; dx ++) {
                IVec2 offset = new IVec2(dx, dy);
                IVec2 possibleMove = IVec2.add(pos, offset);
                if(offset == new IVec2(0, 0)) continue;
                try {
                    if(curBoardState.getPieceAt(possibleMove).getPieceColor() != pieceColor) {
                        possible.add(possibleMove);
                    }
                } catch(PositionOutOfBoundsException e) {
                    // Do nothing
                }
            }
        }
        
        // Castling
        // I will implement this later since Castling is quite difficult to implement
        // Resource to help in implementation: https://www.chess.com/article/view/how-to-castle-in-chess
        // Simplified rules for implementation:
        // 1. The king essentially makes two moves. At any point, the king should not be in check
        // 2. Both the king and the rook should not have moved
        throw new RuntimeException("Not Implemented");
        
        return possible;
    };
    
    @Override
    public void move(IVec2 newPos, BoardState curBoardState) throws PositionOutOfBoundsException {
        moved = true;
        
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
    public KingPiece clone() {
        return new KingPiece(pos, pieceColor, moved);
    };
}
