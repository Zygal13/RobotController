package Logic;

import InputOutput.Commands;
import InputOutput.Console;
import processing.core.PVector;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.util.ArrayList;

public class File {
    ArrayList<String> lines = new ArrayList<String>();

    public File() {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(java.io.File f) {
                return f.getName().toLowerCase().endsWith(".txt") || f.isDirectory();
            }

            public String getDescription() {
                return "Text Files (*.txt)";
            }
        });
        int r = fileChooser.showDialog(null, "Choose a file");
        if (r == JFileChooser.APPROVE_OPTION) {
            readFile(fileChooser.getSelectedFile().getAbsolutePath());
        } else if (r == JFileChooser.CANCEL_OPTION) {
            Console.log("User cancelled operation", Console.Type.WARNING);
        }
    }

    private void readFile(String path) {
        try {
            java.io.File file = new java.io.File(path);
            java.util.Scanner input = new java.util.Scanner(file);
            while (input.hasNext()) {
                lines.add(input.nextLine());
            }
            input.close();
        } catch (Exception ex) {
            Console.log("Error in reading file", Console.Type.ERROR);
        }
    }

    public PVector getLine() {
        try {
            String line = lines.get(0).toLowerCase();
            lines.remove(0);
            if ((!line.isEmpty())) {
                if (line.charAt(0) == 'p') {
                    String[] split = line.split(" ");
                    float x = Float.parseFloat(split[1]);
                    float y = Float.parseFloat(split[2]);
                    float z = Float.parseFloat(split[3]);
                    return new PVector(x, y, z);
                } else {
                    if ((line.charAt(0) == '#')) { // comment
                        Console.log(line.substring(1), Console.Type.INFO);
                    } else {
                        Commands.run(line);
                    }
                }
            }
            return getLine(); // recursive call so commands run until a position is found
        } catch (NumberFormatException e) {
            Console.log("Invalid command", Console.Type.ERROR); // incorrect formating of file
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
        return null;
    }
}
