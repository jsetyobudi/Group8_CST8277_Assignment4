//import statements

import business.Sprite;
import business.SpriteSessionRemote;
import java.awt.Color;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is responsible for constructing the popup box that allows users to
 * edit the Sprite properties.
 *
 * @author Chris,Mouhamad
 */
public class MessageBox extends JPanel {

    //initialize variable for directional axis
    private int currentDx = 0;
    private int currentDy = 0;
    //initial values for panel dimensions
    private final static int FRAME_WIDTH = 300;
    private final static int FRAME_HEIGHT = 220;

    /**
     *
     * @param sprite sprite object
     * @param session current session for sprites
     * @throws RemoteException
     */
    public MessageBox(final Sprite sprite, final SpriteSessionRemote session) throws RemoteException {

        //set initial values for grid layout
        setLayout(new GridLayout(7, 2));
        //create a jlabel and assign it a string
        JLabel xLabel = new JLabel("X Position: ");
        //assign the text field to the sprties current x axis position
        JTextField xTextField = new JTextField(Integer.toString(sprite.getX()));
        //add label and textfeild to layout
        add(xLabel);
        add(xTextField);

        //create a jlabel and assign it a string
        JLabel yLabel = new JLabel("Y Position: ");
        //assign the text field to the sprties current y axis position
        JTextField yTextField = new JTextField(Integer.toString(sprite.getY()));
        //add label and textfeild to layout
        add(yLabel);
        add(yTextField);

        //create a jlabel and assign it a string
        JLabel dxLabel = new JLabel("DX Speed: ");
        //assign the text field to the sprties current dx axis position
        JTextField dxTextField = new JTextField(Integer.toString(sprite.getDx()));
        //add label and textfeild to layout
        add(dxLabel);
        add(dxTextField);

        //create a jlabel and assign it a string
        JLabel dyLabel = new JLabel("DY Speed: ");
        //assign the text field to the sprties current dy axis position
        JTextField dyTextField = new JTextField(Integer.toString(sprite.getDy()));
        //add label and textfeild to layout
        add(dyLabel);
        add(dyTextField);

        //create a jlabel and assign it a string
        JLabel redLabel = new JLabel("Red: ");
        //assign the text field to the sprite colour
        JTextField redText = new JTextField(Integer.toString(sprite.getColor().getRed()));
        //add label and textfeild to layout
        add(redLabel);
        add(redText);

        //create a jlabel and assign it a string
        JLabel greenLabel = new JLabel("Green: ");
        //assign the text field to the sprite colour
        JTextField chanText = new JTextField(Integer.toString(sprite.getColor().getGreen()));
        //add label and textfeild to layout
        add(greenLabel);
        add(chanText);

        //create a jlabel and assign it a string
        JLabel blueLabel = new JLabel("Blue: ");
        //assign the text field to the sprite colour
        JTextField blueText = new JTextField(Integer.toString(sprite.getColor().getBlue()));
        //add label and textfeild to layout
        add(blueLabel);
        add(blueText);

        //set size of panel 
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        //set to visible
        setVisible(true);
        validate();

        //set variable to directional axis
        currentDx = sprite.getDx();
        currentDy = sprite.getDy();
        sprite.setDx(0);
        sprite.setDy(0);

        session.updateSprite(sprite);

        //create option buttons for dialog
        int option = JOptionPane.showConfirmDialog(null, this, "Update Sprite",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        //if option chose is OK set the values the user inputs
        if (option == JOptionPane.OK_OPTION) {
            Color color = new Color(Integer.parseInt(redText.getText()),
                    Integer.parseInt(chanText.getText()),
                    Integer.parseInt(blueText.getText()));
            sprite.setColor(color);
            sprite.setDx(Integer.parseInt(dxTextField.getText()));
            sprite.setDy(Integer.parseInt(dyTextField.getText()));
            sprite.setX(Integer.parseInt(xTextField.getText()));
            sprite.setY(Integer.parseInt(yTextField.getText()));

            session.updateSprite(sprite);

        } //use current values
        else {
            sprite.setDx(currentDx);
            sprite.setDy(currentDy);
            session.updateSprite(sprite);
        }
    }//end of constructor
}//end of class 'MessageBox'