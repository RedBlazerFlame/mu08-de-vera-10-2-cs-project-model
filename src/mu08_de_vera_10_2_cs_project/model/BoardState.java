/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;

import java.util.ArrayList;
import java.util.HashMap;
import mu08_de_vera_10_2_cs_project.exceptions.InvalidFenStringException;
import mu08_de_vera_10_2_cs_project.exceptions.InvalidPieceColorException;
import mu08_de_vera_10_2_cs_project.exceptions.PieceNotFoundException;
import mu08_de_vera_10_2_cs_project.exceptions.PositionOutOfBoundsException;

/*
TODO ask sir if you can make the project scope smaller. In particular, you may have to remove:
- Saving
- Threefold repetition (and the posCounter)

Making the class hashable might be a pain .-.
(I already spent like 1 hour implementing this mess)

Ask sir what you're expected to do for the model classes in the project proposal 2 submission
Are you supposed to finish everything?
Is it okay if some classes have NotImplemented errors?
When should we finish the model classes?
*/

/**
 *
 * @author gabee
 */
public class BoardState {
    // Private Fields
    private ArrayList<ArrayList<Piece>> boardState;
    private ArrayList<KingPiece> kings;
    private HashMap<BoardState, Integer> posCounter;
    private int halfmovesSinceLastCapture;
    private int fullmoves;

    
    // Constructor
    public BoardState(ArrayList<ArrayList<Piece>> boardState) throws PositionOutOfBoundsException {
        this.boardState = boardState;
        this.kings = new ArrayList<KingPiece>();
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                IVec2 curPos = new IVec2(x, y);
                Piece cur = getPieceAt(curPos);
                if(cur instanceof KingPiece) {
                    this.kings.add((KingPiece)cur);
                }
            }
        }
        posCounter = new HashMap<BoardState, Integer>();
        countPos(this);
        halfmovesSinceLastCapture = 0;
        fullmoves = 0;
    }
    
    public BoardState(ArrayList<ArrayList<Piece>> boardState, HashMap<BoardState, Integer> posCounter, int halfmovesSinceLastCapture, int fullmoves) throws PositionOutOfBoundsException {
        this.boardState = boardState;
        this.kings = new ArrayList<KingPiece>();
        for(int y = 0; y < 8; y++) {
            for(int x = 0; x < 8; x++) {
                IVec2 curPos = new IVec2(x, y);
                Piece cur = getPieceAt(curPos);
                if(cur instanceof KingPiece) {
                    this.kings.add((KingPiece)cur);
                }
            }
        }
        this.posCounter = posCounter;
        halfmovesSinceLastCapture = this.halfmovesSinceLastCapture;
        fullmoves = this.fullmoves;
    }
    
    // Public Methods
    public String toFenString() {
        throw new RuntimeException("Not Implemented!");
    }
    
    public void loadFromFenString() throws InvalidFenStringException {
        throw new RuntimeException("Not Implemented!");
    }
    
    public void setBoardState(BoardState newBoardState) {
        throw new RuntimeException("Not Implemented!");
    }
    
    public void countPos(BoardState pos) {
        posCounter.put(pos, posCounter.getOrDefault(pos, 0) + 1);
    }
    
    public void setPieceAt(IVec2 pos, Piece newPiece) {
        this.boardState.get(pos.getY()).set(pos.getX(), newPiece);
        newPiece.setPos(pos);
    }
    
    public Piece getPieceAt(IVec2 pos) throws PositionOutOfBoundsException {
        if(!pos.inBounds(new IVec2(0, 0), new IVec2(7, 7))) {
            throw new PositionOutOfBoundsException(String.format("Position %s is out of bounds", pos.toString()));
        }
        return this.boardState.get(pos.getY()).get(pos.getX());
    }
    
    public void swapPieces(IVec2 p1, IVec2 p2) throws PositionOutOfBoundsException {
        Piece tmp = getPieceAt(p1);
        setPieceAt(p1, getPieceAt(p2));
        setPieceAt(p2, tmp);
        
        getPieceAt(p1).setPos(p1);
        getPieceAt(p2).setPos(p2);
    }
    
    public KingPiece getKing(PieceColor color) throws InvalidPieceColorException, PieceNotFoundException {
        // Not fully implemented: Introduce the InvalidPieceColorException and PieceNotFoundException
        if(color == PieceColor.NEUTRAL) {
            throw new InvalidPieceColorException("PieceColor.NEUTRAL is an invalid color for a King Piece.");
        }
        
        for(KingPiece p : this.kings) {
            if(p.getPieceColor() == color) {
                return p;
            }
        }
        
        throw new PieceNotFoundException("King Piece not Found!");
    }
    
    public boolean isKingInCheck(PieceColor color) throws InvalidPieceColorException, PieceNotFoundException {
        // For each enemy piece, we check if the piece can capture the king in one move
        // If the piece can capture the king in one move, then that means that the king is in check
        // Note that we count pseudo-legal moves (more precisely, we allow moves where the opponent can leave his/her own king in check)
        // Here's an example of such a scenario where the king is in check: rnbkqbn1/pppp1ppp/6r1/4p1B1/8/6K1/PPPPPPPP/RN1Q1BNR w - - 0 1
        // (Go to https://www.365chess.com/board_editor.php, click "Import FEN", and paste the FEN string above)
        // Note that, even if the white bishop is technically pinned in the position above, we still count it as check because the black king is
        // under attack by the white bishop
        
        // Time complexity: O(n) (or O(n^3) if the number of pieces varies)
        KingPiece kingPiece = getKing(color);
        PieceColor oppositeColor = PieceColorUtils.invertColor(color);
        
        for (ArrayList<Piece> row : this.boardState) {
            for(Piece p : row) {
                if(p.getPieceColor() == oppositeColor) {
                    for(IVec2 legalMove : p.getLegalMovesNoCheck(this)) {
                        if(legalMove == kingPiece.getPos()) {
                            return true;
                        }
                    }
                }
            }
        }
        
        return false;
    }
    
    public int getPosCount(BoardState refState) {
        return posCounter.getOrDefault(refState, 0);
    }
    
    public int getHalfmovesSinceLastCapture() {
        return halfmovesSinceLastCapture;
    }
    
    public BoardState clone() {
        throw new RuntimeException("Not Implemented!");
    }
    
    public void completeMove(Piece lastMoved, boolean incrementHalfmoveClock) {
        // Increments the clocks and removes en passant target squares from the player that moved last
        // Note that the halfmove clock is incremented when a capture occurs OR if a pawn moves
        // The fullmove counter increments if black moves
        throw new RuntimeException("Not Implemented!");
    }
    
    // Overriding __hash__ and __eq__ to make the BoardState class hashable
    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof BoardState)) return false;
        
        // The line below is just to make the red squiggly error go away
        // Apparently, intellisense can't detect the fact that I already
        // accounted for the case where the object isn't a BoardState object
        BoardState otherBoardState = (BoardState)other;
        return this.toFenString().equals(otherBoardState.toFenString());
    }
}
