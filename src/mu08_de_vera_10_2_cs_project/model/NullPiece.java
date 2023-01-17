/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;

import java.util.ArrayList;

/**
 *
 * @author gabee
 */
public class NullPiece extends Piece {
    // Constructors
    public NullPiece(IVec2 pos, PieceColor pieceColor) {
        super(pos, pieceColor);
    }

    public NullPiece(IVec2 newPos) {
        super(pos, PieceColor.NEUTRAL);
    }
    
    // Public Methods
    @Override
    public ArrayList<IVec2> getLegalMovesNoCheck(BoardState curBoardState) {
        return new ArrayList<>();
    };
    @Override
    public void move(IVec2 newPos, BoardState curBoardState) {
        // Null Pieces cannot be moved, so this does nothing
    };
    
    @Override
    public NullPiece clone() {
        return new NullPiece(pos, pieceColor);
    };
}
