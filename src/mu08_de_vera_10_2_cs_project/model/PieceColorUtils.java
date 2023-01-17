/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.model;

import mu08_de_vera_10_2_cs_project.exceptions.InvalidPieceColorException;

/**
 *
 * @author gabee
 */
public class PieceColorUtils {
    public static PieceColor invertColor(PieceColor color) {
        if(color == PieceColor.NEUTRAL) {
            return PieceColor.NEUTRAL;
        }
        return (color == PieceColor.WHITE ? PieceColor.BLACK : PieceColor.WHITE);
    }
}
