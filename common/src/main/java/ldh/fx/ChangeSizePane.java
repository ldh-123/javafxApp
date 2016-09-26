/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.fx;

import javafx.scene.input.MouseEvent;

/**
 *
 * @author Puhui
 */
public interface ChangeSizePane {
    
    void startChangeSize(MouseEvent evt);
    
    void endChangeSize(MouseEvent evt);
}
