package mdt;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

public class winListner implements WindowListener {

	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		try {
			InterfacciaEsecuzione.thread.interrupt();
		} catch (NullPointerException e) {
			
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			InterfacciaEsecuzione.mac.dispose();
			InterfacciaEsecuzione.thread.interrupt();
		} catch (NullPointerException e) {
			
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		try {
			InterfacciaEsecuzione.mac.setState(JFrame.NORMAL);
		} catch (NullPointerException e) {
			
		}
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		try {
			InterfacciaEsecuzione.mac.setState(JFrame.ICONIFIED);
		} catch (NullPointerException e) {
			
		}
	}

	@Override
	public void windowOpened(WindowEvent arg0) {

	}

}
