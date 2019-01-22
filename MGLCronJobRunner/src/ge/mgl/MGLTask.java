package ge.mgl;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

public class MGLTask extends TimerTask {

    private String ip = "10.135.12.84";
    private String port = "3306";
    private String database = "imperio";
    private String user = "root";
    private String pass = "*Voidmain18*";
    private String path = "/opt/tomcat/backups/";

    private String[] recepients = new String[]
            {
                    "gvashakidze6@gmail.com",
                    "meskhishvililevan@gmail.com"
            };

    public MGLTask() {

    }

    public void export() {

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs();
        } else {
            folder.setReadable(true, false);
            folder.setExecutable(true, false);
            folder.setWritable(true, false);
        }

        Date dateNow = new Date();
        SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss");
        String date_to_string = dateformatyyyyMMdd.format(dateNow);
        String fullName = path + "Backup_file_" + date_to_string + ".sql";
        String dumpCommand = "mysqldump " + database + " -h " + ip + " -u " + user + " -p" + pass;
        Runtime rt = Runtime.getRuntime();
        File test = new File(fullName);
        PrintStream ps;

        try {
            Process child = rt.exec(dumpCommand);
            ps = new PrintStream(test);
            InputStream in = child.getInputStream();
            int ch;
            while ((ch = in.read()) != -1) {
                ps.write(ch);
            }

            InputStream err = child.getErrorStream();
            while ((ch = err.read()) != -1) {
                System.out.write(ch);
            }

            MGLMailSenderTextHtml.sendEmail(recepients,
                    "Backup Result " + date_to_string,
                    "ბაზის ბექაფი შესრულდა წარმატებით. \n <br/> Backup Name: " + "Backup_file_" + date_to_string + ".sql");

        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void run() {
        export();
    }

}
