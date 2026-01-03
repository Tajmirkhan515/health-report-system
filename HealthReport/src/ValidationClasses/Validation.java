/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ValidationClasses;

import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JTextField;

/**
 *
 * @author Tajmirkhan
 */
public class Validation{
    public static void validateInt(final JTextField txt){
            txt.addKeyListener(new KeyAdapter() {

                   @Override
                   public void keyTyped(KeyEvent e) {
                        char c = e.getKeyChar();
                        if ( ((c < '0') || (c > '9')) 
                             && (c != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();  // ignore event
                        Toolkit.getDefaultToolkit().beep();
                   }
               }
            });
    
    }
}
