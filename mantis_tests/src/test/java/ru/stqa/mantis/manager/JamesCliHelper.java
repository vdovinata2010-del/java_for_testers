package ru.stqa.mantis.manager;


import org.openqa.selenium.io.CircularOutputStream;

import java.io.File;
import java.io.IOException;

public class JamesCliHelper extends HelperBase {

    public JamesCliHelper(ApplicationManager manager) {
        super(manager);
    }

    public void addUser(String email, String password) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(
                "java",
                "-cp",
                "james-server-jpa-app.lib/*",
                "org.apache.james.cli.ServerCmd",
                "AddUser",
                email,
                password
        );
        pb.directory(new File(manager.property("james.workingDir")));
        pb.inheritIO();
                Process process = pb.start();
        process.waitFor();
    }
}
