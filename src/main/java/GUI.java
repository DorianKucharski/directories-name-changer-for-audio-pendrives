import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI extends JFrame{


    private JList<File> foldersList;
    private JPanel panel1;
    private JButton upButton;
    private JButton downButton;
    private JButton saveButton;
    private DefaultListModel<File> foldersListModel;

    GUI(){
        setVisible(true);
        setSize(900, 600);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        add(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        foldersListModel = new DefaultListModel<File>();
        foldersList.setModel(foldersListModel);

        ArrayList<File> folders = getListOfFolders();
        foldersListModel.addAll(folders);

        downButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveFolderDown();
            }
        });

        upButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveFolderUp();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                renameFolders();
            }
        });



    }

    void renameFolders(){
        for(int i=0; i < foldersListModel.getSize(); i++){
            File file =  foldersListModel.getElementAt(i);
            String name = file.getName();
            String path = file.getPath().replace(name, "");


            name = name.replaceAll("CD[0-9]+[0-9]", "");
            name = name.replaceAll("CD[0-9]+[0-9]", "");
            name = name.replaceAll("CD.[0-9][0-9]", "");
            name = name.trim();


            if (i < 10){

                file.renameTo(new File(path + "CD0" + i + " " + name));
            } else {
                file.renameTo(new File(path + "CD" + i +  " " + name));
            }

            System.out.println(path);
            System.out.println(name);



        }

    }

    void moveFolderDown(){
        File selectedFolder = foldersList.getSelectedValue();
        int folderIndex = foldersList.getSelectedIndex();
        if (folderIndex < foldersListModel.size() - 1){
            foldersListModel.remove(folderIndex);
            foldersListModel.add(folderIndex + 1, selectedFolder);
            foldersList.setSelectedIndex(folderIndex + 1);
        }
    }

    void moveFolderUp(){
        File selectedFolder = foldersList.getSelectedValue();
        int folderIndex = foldersList.getSelectedIndex();
        if (folderIndex > 0){
            foldersListModel.remove(folderIndex);
            foldersListModel.add(folderIndex - 1, selectedFolder);
            foldersList.setSelectedIndex(folderIndex - 1);
        }
    }

    public ArrayList<File> getListOfFolders() {
        ArrayList<File> folders = new ArrayList<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            String[] directories = selectedFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    //return new File(current, name).isDirectory();
                    return true;
                }
            });
            Arrays.sort(directories);
            for (String directory : directories) {
                folders.add(new File(selectedFile.getAbsolutePath() + File.separator + directory));
                System.out.println(directory);
            }

        }
        return folders;
    }
}
