package ge.mgl;

import com.sun.deploy.net.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import sun.net.www.http.HttpClient;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

public class MGLCronJobRunner {
    public static void main(String[] args){
        Timer t = new Timer();
        MGLTask mTask = new MGLTask();
        t.scheduleAtFixedRate(mTask, 0, TimeUnit.HOURS.toMillis(6));
    }

}


