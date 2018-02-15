
package project1;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileAccess {
    
    private StringBuffer sb = new StringBuffer("");
    
    public void addToBuffer(String s) {
        sb.append(s);
        sb.append(System.getProperty( "line.separator" ));
    }
    
    public void saveandexit(File file) throws IOException {
        FileWriter fw = new FileWriter(file, true);
        fw.write(sb.toString());
        fw.write(System.getProperty( "line.separator" ));
        fw.close();
        System.exit(0);
    }
    
    public void exit() {
        System.exit(0);
    }
    
}
