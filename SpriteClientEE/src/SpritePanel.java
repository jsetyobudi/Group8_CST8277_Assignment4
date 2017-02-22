//import statements
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

import java.rmi.RemoteException;
import java.util.List;

import business.Sprite;
import business.SpriteSessionRemote;

/**
 * This class constructs the JPanel portion of the SpriteClientEE Application.  
 * Sprites are created when the mouse is clicked within the JPanel, if a sprite
 * is clicked then a MenuBox pops up allowing the user to update the Sprite.
 * @author Christopher Enos
 * @since 2016-11-27
 * @version 1.0a
*/
public class SpritePanel extends JPanel implements Runnable{

    /**
     *A List object containing reference to Sprite objects 
     */
    private List<Sprite> sprites;
    
    
    /**
     * A SpriteSessionRemote object used to access remote methods on remote server
     */
    private SpriteSessionRemote session;
	
        /**
         * Constructor to create JPanel and add necessary components
         * @param session SpriteSessionRemote Object
         */
        public SpritePanel(SpriteSessionRemote session){
            this.session = session;
            this.addMouseListener(new Mouse());
	}//end of constructor

        /**
         * Paints sprites in List on the JPanel
         * @param g 
         */
	@Override
	public void paintComponent(Graphics g){
            super.paintComponent(g);
		if (sprites != null){
                    for (Sprite s: sprites){
			s.draw(g);
                    }
		}
	}//end of method: 'paintComponent'
        
        
        /**
         * Overriden Run method for JPanel runnable gets the Sprites from Server
         * and repaints them on the JPanel.
         */
	@Override
	public void run(){
            System.out.println("now animating..."); 
                while (true){
                    try{
                        sprites = session.getSpriteList();
                    }catch(Exception e){
			e.printStackTrace();
                        System.out.println("Lost connection, exiting...");
			System.exit(1);
                    }
                    
                    repaint();
                    
                    try {
			Thread.sleep(200);                                      // wake up roughly 25 frames per second
                    }catch ( InterruptedException exception ) {
			exception.printStackTrace();
                    }
                }
        }//end of method: 'run'
        

    /**
     * Private Class that handles the action of when a mouse is pressed
     * in the JPanel.
     * @author Christopher Enos
     * @since 2016-11-27
     * @version 1.0a
     */
    private class Mouse extends MouseAdapter {
    
        /**
         * Static final int that is used for mouse click detection.
         */
        private static final int CLICK_DETECTION = 10;
    
       /**
        * Sprite reference to sprite object used for by class
        * for sprite detection.
        */
        private Sprite sprite = new Sprite();
    
            /**
             * Method loops through algorithm checking to see if a sprite 
             * is within the click radius and returns that sprite if found
             * @param event MouseEvent object
             * @return Sprite Sprite Object 
             */
            public Sprite clickSprite(final MouseEvent event){
                for(Sprite sprite : sprites){
                    if(sprite.getX() >= event.getX() - CLICK_DETECTION && 
                        sprite.getX() <= event.getX() + CLICK_DETECTION)
                           if(sprite.getY() >= event.getY() - CLICK_DETECTION && 
                                sprite.getY() <= event.getY() + CLICK_DETECTION)
                                    return sprite;
                }
                return null;
            }//end of method: 'clickSprite'
            
            /**
             * Overridden method that handles the mousePressed function of the 
             * new MouseAdapter. It decides the outcome of a mouse click between
             * creating a new sprite or opening the message box
             * @param event MouseEvent
             */
            @Override
            public void mousePressed( final MouseEvent event ){
                sprite = clickSprite(event);
                    if(sprite != null){
                        System.out.print("Sprite Has Been Clicked");
                        try{
                            MessageBox messageBox = 
                                    new MessageBox(sprite, session);
                        }catch(RemoteException re){
                            re.printStackTrace();
                        }
                    }else{
                        try{
                            System.out.println("Creating a new sprite");
                            session.newSprite(event);
                        } catch (RemoteException re) {
                            re.printStackTrace();
			}
                    }                                                              
            }//end of method: 'mousePressed'
    }//end of class: 'Mouse'
}//end of class: 'SpritePanel'