package mdt;

import java.util.Vector;

import javax.swing.JOptionPane;

public class Esegui {
	
	public static String input;
	public static Vector<String> nastro;
	public static Vector<String> testa;
	public static int stato;
	public static String simbolo;
	public static int posizioneNastro;
	public static int numeroEx;
	public static long start; 
	public static int ritardo;
	public static boolean passaggi;
	
	public Esegui(String in) {
		System.err.format("TM started...\n");
		start = System.currentTimeMillis();
		
		passaggi = InterfacciaEsecuzione.passaggi.isSelected();
		
		InterfacciaEsecuzione.vel.selectAll();
		ritardo = (int)Double.parseDouble(InterfacciaEsecuzione.vel.getSelectedText());
		stato = 1;
		posizioneNastro = 0;
		numeroEx = 0;
		
		input = in;
		nastro = new Vector<String>();
		testa = new Vector<String>();
		
		nastro.addElement("$");
		testa.addElement("^");
		if (in == null) {
			
		} else {
			for (int i = 0; i < input.length(); i++) {
				try {
					carattereValido(input.charAt(i));
					nastro.addElement(input.charAt(i) + "");
					testa.addElement("-");
				} catch (WrongCharException e) {
					InterfacciaEsecuzione.mac.dispose();
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							e.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					System.err.format(e.getMessage());
					return;
				}
			}
		}
		nastro.addElement("$");
		testa.addElement("-");
		stampaStato();
		simbolo = nastro.elementAt(posizioneNastro);
		eseguiComando(stato, simbolo);
	}
	
	private void carattereValido(char charAt) throws WrongCharException {
		boolean presente = false;
		for (int i = 1; i < InterfacciaEsecuzione.listaSimboli.length; i++) {
			if (charAt == InterfacciaEsecuzione.listaSimboli[i].charAt(0)) {
				presente = true;
			}
		}
		if(!presente) {
			throw new WrongCharException("The symbol " + charAt + " is invalid");
		}
	}

	void eseguiComando(int st, String si) {
		int count = 1;
		while (InterfacciaEsecuzione.listaSimboli[count].charAt(0) != si.charAt(0)) {
			count ++;
		}
		InterfacciaEsecuzione.celle.elementAt(st)[count].selectAll();
		String cmd = InterfacciaEsecuzione.celle.elementAt(st)[count].getSelectedText();
		try {
			if (cmd != null) /*&& numeroEx <= 1200)*/ {
				numeroEx ++;
				System.out.println("Execution: " + numeroEx);
				cmd = cmd.toUpperCase();
				int aux = 0;
				while (cmd.charAt(aux) != 'Q') {
					aux ++;
				}
				try {
					stato = (int) Double.parseDouble(cmd.substring(0, aux)) + 1;
				} catch (NumberFormatException e) {
					InterfacciaEsecuzione.mac.dispose();
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							(stato - 1) + "q:" + nastro.elementAt(posizioneNastro) + " next status not inserted.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				if (stato >= InterfacciaEsecuzione.celle.size()) {
					InterfacciaEsecuzione.mac.dispose();
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							"Status " + (stato - 1) + "q does not exist.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				aux ++;
				int sub = aux;
				try {
					while (cmd.charAt(aux) != 'L' && cmd.charAt(aux) != 'R') {
						aux ++;
					}
				} catch (StringIndexOutOfBoundsException e) {
					InterfacciaEsecuzione.mac.dispose();
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							"Invalid direction.",
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					return;
				}
				simbolo = cmd.substring(sub, aux);
				try {
					carattereValido(simbolo.charAt(0));
				} catch (WrongCharException e) {
					InterfacciaEsecuzione.mac.dispose();
					JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
							e.getMessage(),
						    "Error",
						    JOptionPane.ERROR_MESSAGE);
					System.err.format(e.getMessage());
					return;
				}
				String to = cmd.charAt(aux) + "";
				
				nastro.remove(posizioneNastro);
				nastro.add(posizioneNastro, simbolo);
				testa.remove(posizioneNastro);
				testa.add(posizioneNastro, "-");
				if (to.charAt(0) == 'R') {
					int ver = posizioneNastro + 1;
					if (ver >= testa.size()) {
						testa.addElement("-");
						nastro.addElement("$");
						posizioneNastro ++;
					} else {
						posizioneNastro ++;
					}
				} else if (to.charAt(0) == 'L') {
					if (posizioneNastro > 0) {
						posizioneNastro = posizioneNastro - 1;
					} else {
						trasla();
					}
				}
				testa.remove(posizioneNastro);
				testa.add(posizioneNastro, "^");
				
				stampaStato();
				//InterfacciaEsecuzione.esecuzione.update(InterfacciaEsecuzione.esecuzione.getGraphics());
				for (int i = 0; i < Math.pow(ritardo, 5); i++) {
					
				}
				eseguiComando(stato, nastro.elementAt(posizioneNastro));
			} else {
				String elapsedTimeString = "";
				long end = System.currentTimeMillis(); 
				long elapsedTime = end - start;
				if (elapsedTime / 1000 >= 1) {
					double elapsedTimeSec = elapsedTime / 1000;
					elapsedTimeString = elapsedTimeSec + "s";
				} else {
					elapsedTimeString = elapsedTime + "ms";
				}
				/*if (numeroEx > 1200) {
					InterfacciaEsecuzione.esecuzione.append("Execution terminated because the bound of\n" + numeroEx + " iterations was reached\n");
					InterfacciaEsecuzione.thread.interrupt();
					InterfacciaEsecuzione.avvia.setEnabled(true);
					InterfacciaEsecuzione.stop.setEnabled(false);
				}*/
				InterfacciaEsecuzione.esecuzione.append("Execution finished: " + elapsedTimeString + " (" + numeroEx + " iterations)");
				InterfacciaEsecuzione.esecuzione.setCaretPosition(InterfacciaEsecuzione.esecuzione.getText().length() - 1);
				InterfacciaEsecuzione.thread.interrupt();
				InterfacciaEsecuzione.avvia.setEnabled(true);
				InterfacciaEsecuzione.stop.setEnabled(false);
				return;
			}
		} catch (StackOverflowError err) {
			InterfacciaEsecuzione.mac.dispose();
			InterfacciaEsecuzione.thread.interrupt();
			InterfacciaEsecuzione.avvia.setEnabled(true);
			InterfacciaEsecuzione.stop.setEnabled(false);
			JOptionPane.showMessageDialog(InterfacciaEsecuzione.mdtFrame,
					"Something went wrong, execution terminated after " + numeroEx + " iterations.",
				    "Warning",
				    JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	private void trasla() {
		nastro.add(0, "$");
		
		testa.add(0, "^");
		testa.removeElementAt(1);
		testa.add(1, "-");
	}

	void stampaStato() {
		if (passaggi) {
			InterfacciaEsecuzione.esecuzione.append(nastro.elementAt(0) + " ");
		} else {
			InterfacciaEsecuzione.esecuzione.setText(nastro.elementAt(0) + " ");
		}
		for (int i = 1; i < nastro.size(); i++ ) {
			InterfacciaEsecuzione.esecuzione.append(nastro.elementAt(i) + " ");
		}
		if (passaggi) {
			InterfacciaEsecuzione.esecuzione.append(" <- " + (stato - 1) + "q\n");
		} else {
			InterfacciaEsecuzione.esecuzione.append("\n");
		}
		for (int i = 0; i < testa.size(); i++ ) {
			InterfacciaEsecuzione.esecuzione.append(testa.elementAt(i) + " ");
		}
		InterfacciaEsecuzione.esecuzione.append("\n\n");
		InterfacciaEsecuzione.esecuzione.setCaretPosition(InterfacciaEsecuzione.esecuzione.getText().length() - 1);
		//InterfacciaEsecuzione.esecuzione.update(InterfacciaEsecuzione.esecuzione.getGraphics());
	}
	
}
