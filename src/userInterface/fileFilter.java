package userInterface;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class fileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
            return true;
        }

        String extension = Utils.getExtension(f);
        if (extension != null) {
            if (extension.equals(Utils.mdt)) {
                    return true;
            } else {
                return false;
            }
        }

        return false;
	}

	@Override
	public String getDescription() {
		return "Turing Machine [.mdt]";
	}

}
