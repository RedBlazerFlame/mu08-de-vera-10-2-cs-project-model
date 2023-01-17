package mu08_de_vera_10_2_cs_project.model;


import java.util.ArrayList;
import mu08_de_vera_10_2_cs_project.exceptions.PositionOutOfBoundsException;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gabee
 */
public class QueenPiece extends Piece {
    // Constructors
    public QueenPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
    }
    
    // Public Methods
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        // Legal moves of Bishop
        ArrayList<IVec2> possible = new ArrayList<IVec2>();
        
        diagonalDirectionLoop:
        for(int bitmask = 0; bitmask < 4; bitmask ++) {
            IVec2 offsetDir = new IVec2(((bitmask & 1) << 1) - 1, (bitmask & 2) - 1);
            int maxLInftyOffset = Integer.min(
                (bitmask & 1) * 7 - (((bitmask & 1) << 1) - 1) * pos.getX(),
                ((bitmask & 2) >> 1) * 7 - ((bitmask & 2) - 1) * pos.getY()
            ); // The L-infinity distance between the furthest point along this diagonal and the current position

            for(int offset = 1; offset <= maxLInftyOffset; offset ++) {
                IVec2 possibleMove = IVec2.add(pos, offsetDir.multScalar(offset));
                
                try {
                    Piece curPieceAtPossibleMove = curBoardState.getPieceAt(possibleMove);
                    if(curPieceAtPossibleMove instanceof NullPiece){
                        possible.add(possibleMove);
                    }
                    else if(curPieceAtPossibleMove.getPieceColor() != pieceColor) {
                        possible.add(possibleMove);
                        continue diagonalDirectionLoop;
                    } else if(curPieceAtPossibleMove.getPieceColor() == pieceColor) {
                        continue diagonalDirectionLoop;
                    }
                } catch(PositionOutOfBoundsException e) {
                    // Do nothing
                }
            }
        }
        
        // Legal moves of Rook
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
    public QueenPiece clone() {
        return new QueenPiece(pos, pieceColor);
    };
}
