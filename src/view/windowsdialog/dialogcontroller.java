package view.windowsdialog;

import javafx.stage.FileChooser;

import java.io.File;

public class dialogcontroller {

    public File file1;
    public File file2;

    public dialogcontroller(){

    }

    public void getCSV(){

        System.out.println("start learning");
        FileChooser fc=new FileChooser();
        fc.setTitle("open CSV file");
        fc.setInitialDirectory(new File("./resources"));

    }

    public void uploadFileLearn(){

        System.out.println("start learning");
        FileChooser fc=new FileChooser();
        fc.setTitle("open CSV file");
        fc.setInitialDirectory(new File("./resources"));
        file1 = fc.showOpenDialog(null);
        if(file1 == null){
            System.out.println("please upload CSV of learn");
        }

    }

    public void uploadFileDetect(){

        System.out.println("start learning");
        FileChooser fc=new FileChooser();
        fc.setTitle("open CSV file");
        fc.setInitialDirectory(new File("./resources"));
        file2 = fc.showOpenDialog(null);
        if(file2 == null){
            System.out.println("please upload CSV of learn");
        }

    }

    public File getFile1() {
        return file1;
    }

    public File getFile2() {
        return file2;
    }
}
