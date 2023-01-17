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
public class PawnPiece extends Piece {
    protected boolean isEnPassantTarget; // Specifies whether the Pawn could be captured via en passant
    
    // Constructors
    public PawnPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
        isEnPassantTarget = false;
    }
    
    public PawnPiece(IVec2 pos, PieceColor pieceColor, boolean isEnPassantTarget) {
        super(pos, pieceColor);
        this.isEnPassantTarget = isEnPassantTarget;
    }
    
    // Public Methods
    public boolean getIsEnPassantTarget() {
        return this.isEnPassantTarget;
    }
    
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        ArrayList<IVec2> possibleMoves = new ArrayList<IVec2>();
        boolean canMoveTwoSquares = (pieceColor == PieceColor.WHITE && pos.getY() == 1) || (pieceColor == PieceColor.BLACK && pos.getY() == 6);
        IVec2 forward = (pieceColor == PieceColor.WHITE ? new IVec2(0, 1) : new IVec2(0, -1));
        
        // Check moves
        /// Push forward moves
        try {
            IVec2 f1 = IVec2.add(pos, forward);
            IVec2 f2 = IVec2.add(pos, forward.multScalar(2));
            if(curBoardState.getPieceAt(f1) instanceof NullPiece) {
                // Legal move; append to ArrayList
                possibleMoves.add(f1);
                
                if(canMoveTwoSquares && (curBoardState.getPieceAt(f2) instanceof NullPiece)) {
                    possibleMoves.add(f2);
                }
            }
        } catch(PositionOutOfBoundsException e) {
            // Do nothing. If the position is out of bounds, that's okay. It just means that it won't be included in the list of possible moves
        }
        
        /// Push diagonal moves
        IVec2[] hOffsets = {new IVec2(1, 0), new IVec2(-1, 0)};
        for(IVec2 hOffset : hOffsets) {
            IVec2 potentialPos = IVec2.add(IVec2.add(pos, forward), hOffset);
            
            try {
                if(curBoardState.getPieceAt(potentialPos).getPieceColor() == PieceColorUtils.invertColor(pieceColor)) {
                    // There is a capturable enemy piece at the location, add it to the list of possible moves
                    possibleMoves.add(potentialPos);
                }
            } catch(PositionOutOfBoundsException e) {
                // Do nothing. If the position is out of bounds, that's okay. It just means that it won't be included in the list of possible moves
            }
        }
        
        return possibleMoves;
    };
    @Override
    public void move(IVec2 newPos, BoardState curBoardState) throws PositionOutOfBoundsException {
        IVec2 posDiff = IVec2.sub(newPos, pos);
        IVec2 forward = (pieceColor == PieceColor.WHITE ? new IVec2(0, 1) : new IVec2(0, -1));
        IVec2 behindCaptureSquare = IVec2.sub(newPos, forward);
        Piece pieceBehind = curBoardState.getPieceAt(behindCaptureSquare);
        
        if(posDiff.getX() == 0) {
            curBoardState.swapPieces(pos, newPos);
        }
        else if (pieceBehind instanceof PawnPiece && ((PawnPiece)pieceBehind).getIsEnPassantTarget()){
            curBoardState.setPieceAt(behindCaptureSquare, new NullPiece(behindCaptureSquare, PieceColor.NEUTRAL));
            curBoardState.swapPieces(pos, newPos);
        } else {
            curBoardState.setPieceAt(newPos, new NullPiece(newPos, PieceColor.NEUTRAL));
            curBoardState.swapPieces(pos, newPos);
        }
        
        curBoardState.completeMove(this, true);
    };
    
    @Override
    public PawnPiece clone() {
        return new PawnPiece(pos, pieceColor, isEnPassantTarget);
    };
}
