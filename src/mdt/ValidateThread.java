package mdt;

import mdt.Esegui;

public class ValidateThread implements Runnable {

	@Override
	public void run() {
		InterfacciaEsecuzione.uInput.selectAll();
		@SuppressWarnings("unused")
		Esegui exe = new Esegui(InterfacciaEsecuzione.uInput.getSelectedText());
	}

}
