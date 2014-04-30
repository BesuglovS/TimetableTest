package ru.besuglovs.test.ttt;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.besuglovs.test.ttt.Timetable.Timetable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.zip.InflaterInputStream;

public class Test {
    public static void main(String[] args) {
        try {
            Long start = System.currentTimeMillis();

            int bufferLength = 10240000;

            URL url = new URL("http://wiki.nayanova.edu/api.php");
            String urlParameters = "{\"Parameters\":{\"action\":\"bundle\"}}";

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.setConnectTimeout(15000 /* milliseconds */);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(urlParameters.getBytes().length));
            connection.setUseCaches(false);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();


            connection.connect();

            InflaterInputStream iis = new InflaterInputStream(connection.getInputStream());

            byte[] resultArray = new byte[bufferLength];
            Integer offset = 0, bytesRead;
            while ((bytesRead = iis.read(resultArray, offset, bufferLength - offset)) != -1)
            {
                offset += bytesRead;
            }

            Long lag1 = System.currentTimeMillis();
            System.out.print("lag1 = ");
            System.out.println(lag1 - start);


            FileOutputStream fos = new FileOutputStream("json.txt");
            fos.write(Arrays.copyOf(resultArray, offset));
            fos.close();

            Long lag2 = System.currentTimeMillis();
            System.out.print("lag2 = ");
            System.out.println(lag2 - lag1);

            ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
            Timetable result = null;
            try {
                File file = new File("json.txt");

                result = mapper.readValue(file, Timetable.class);
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Long lag3 = System.currentTimeMillis();
            System.out.print("lag3 = ");
            System.out.println(lag3 - lag2);

            Integer eprst = 999;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
