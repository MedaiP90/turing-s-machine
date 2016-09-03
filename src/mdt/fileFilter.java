package mdt;

import java.io.File;
import javax.swing.filechooser.*;

public class fileFilter extends FileFilter {

    //Accept mdt files
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

    //The description of this filter
    public String getDescription() {
        return "Turing's Machine [.mdt]";
    }
}
