/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package mu08_de_vera_10_2_cs_project.exceptions;

/**
 *
 * @author gabee
 */
public class PositionOutOfBoundsException extends Exception {

    /**
     * Creates a new instance of <code>PositionOutOfBoundsException</code>
     * without detail message.
     */
    public PositionOutOfBoundsException() {
    }

    /**
     * Constructs an instance of <code>PositionOutOfBoundsException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public PositionOutOfBoundsException(String msg) {
        super(msg);
    }
}
