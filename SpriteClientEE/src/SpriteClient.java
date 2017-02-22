//import statements
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JFrame;

import business.SpriteSessionRemote;

import java.rmi.RemoteException;

/** 
 * This class constructs the remote client connection and session SpriteClientEE 
 * Application.
 * Class contains main method for execution of the client
 * @author tgk, Stan Peida
 */
public class SpriteClient {
	
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Bouncing Sprites");
        SpriteSessionRemote session = null;
        
        System.setProperty("org.omg.CORBA.ORBInitialHost","localhost");// "127.0.0.1");
	System.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
	
            try {
		System.out.println("about to try for a session...");
                InitialContext ic = new InitialContext();
		session = (SpriteSessionRemote) 
                   ic.lookup("java:global/SpriteEE/SpriteEE-ejb/SpriteSession");

                System.out.println("I got a session");
                System.out.println("This is the height: " 
                                            + session.getHeight());
		} catch (NamingException nm) {
			nm.printStackTrace();
		} catch (RemoteException re) {
                    re.printStackTrace();
		}
		
           if (session != null){
		System.out.println("I got game");
            }else{
		System.err.println("Could not contact game server");
		System.exit(1);
            }
            
            try {
		frame.setSize(session.getHeight(), session.getWidth());
            } catch (RemoteException re) {
		System.err.println("Could not get one or both of "
                        + "HEIGHT, WIDTH for this game");
		re.printStackTrace();
            }
            
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
	SpritePanel panel = new SpritePanel(session);
        frame.add(panel);
        frame.validate();
        new Thread(panel).start();
    }
}
