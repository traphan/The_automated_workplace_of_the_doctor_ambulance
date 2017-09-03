package traphan.ren95.the_automated_workplace_of_the_doctor_ambulance;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;

public class UpgradeActivity extends AppCompatActivity {
//ссылка должна выглядить примерно так

private final String URL="https://rocld.com/y33j3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        downloadFile(URL);
    }
    // распаковка файла с помощью GZIP
    private static void decompressGzipFile(File gzipFile, File newFile) {
        try {
            FileInputStream fis = new FileInputStream(gzipFile);
            GZIPInputStream gis = new GZIPInputStream(fis);
            FileOutputStream fos = new FileOutputStream(newFile);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            gis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void downloadFile(String url) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        new AsyncTask<String, Integer, File>() {
            private Exception m_error = null;

            @Override
            protected void onPreExecute() {
                progressDialog.setMessage("Downloading ...");
                progressDialog.setCancelable(false);
                progressDialog.setMax(100);
                progressDialog
                        .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                progressDialog.show();
            }

            @Override
            protected File doInBackground(String... params) {
                URL url;
                HttpURLConnection urlConnection;
                InputStream inputStream;
                int totalSize;
                int downloadedSize;
                byte[] buffer;
                int bufferLength;

                File file = null;
                FileOutputStream fos = null;

                try {
                    url = new URL(params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setDoOutput(true);
                    urlConnection.connect();
                    file = new File(DatabaseHelper.DB_PATH+"bd.db");
                    fos = new FileOutputStream(file);
                    inputStream = urlConnection.getInputStream();
                    totalSize = urlConnection.getContentLength();
                    downloadedSize = 0;
                    buffer = new byte[1024];
                    bufferLength = 0;
                    // читаем со входа и пишем в выход,
                    // с каждой итерацией публикуем прогресс
                    while ((bufferLength = inputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, bufferLength);
                        downloadedSize += bufferLength;
                        publishProgress(downloadedSize, totalSize);
                    }
                    fos.close();
                    inputStream.close();
                    return file;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    m_error = e;
                } catch (IOException e) {
                    e.printStackTrace();
                    m_error = e;
                }
                return null;
            }

            // обновляем progressDialog
            protected void onProgressUpdate(Integer... values) {
                progressDialog.setProgress((int) ((values[0] / (float) values[1]) * 100));
            };

            @Override
            protected void onPostExecute(File file) {
                // отображаем сообщение, если возникла ошибка
                if (m_error != null) {
                    m_error.printStackTrace();
                    return;
                }
                // закрываем прогресс и удаляем временный файл
                DatabaseHelper databaseHelper=new DatabaseHelper(getApplicationContext());
                databaseHelper.upgradeBd(file);
                progressDialog.hide();
                file.delete();
            }
        }.execute(url);
    }
}


