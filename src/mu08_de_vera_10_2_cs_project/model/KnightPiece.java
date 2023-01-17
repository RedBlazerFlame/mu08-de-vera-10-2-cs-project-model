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
public class KnightPiece extends Piece {
    // Constructors
    public KnightPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
    }
    
    // Public Methods
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        // I start off from the base permutations
        // I then use a bitmask to store information about the signs of the x- and y- coordinates
        ArrayList<IVec2> possible = new ArrayList<IVec2>();
        IVec2[] basePermutations = {new IVec2(1, 2), new IVec2(2, 1)};
        for(IVec2 basePermutation : basePermutations) {
            for(short bitmask = 0; bitmask < 4; bitmask ++) {
                IVec2 offset = new IVec2((((bitmask & 1) << 1) - 1) * basePermutation.getX(), ((bitmask & 2) - 1) * basePermutation.getY());
                IVec2 possibleMove = IVec2.add(pos, offset);
                
                try {
                    if(curBoardState.getPieceAt(possibleMove).getPieceColor() != pieceColor) {
                        possible.add(possibleMove);
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
    public KnightPiece clone() {
        return new KnightPiece(pos, pieceColor);
    };
}
