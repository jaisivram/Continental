package app.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Scanner;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpClient {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        try {
            configureSSL();
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
        while (true) {
            System.out.print("Request resource : ");
            String route = scan.nextLine();
            System.out.print("Request operation : ");
            String opt = scan.nextLine();
            System.out.print("Request body type (json/none): ");
            String body = scan.nextLine();
            StringBuilder json = new StringBuilder("{");
            if (body.equals("json")) {
                json = createJsonInput(scan);
            }
            try {
                URL url = new URL("https://localhost:8443/Continental/api/v1/" + route);
                HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", body);
                connection.setRequestMethod(opt.toUpperCase());
                connection.setRequestProperty("APIKEY", "g8pl234fe6yr34q14ab9e8c2");
                if (body.equals("json")) {
                    try (OutputStream outputStream = connection.getOutputStream()) {
                        byte[] input = json.toString().getBytes("utf-8");
                        outputStream.write(input, 0, input.length);
                    }
                }

                int responseCode = connection.getResponseCode();
                System.out.println("Response Code: " + responseCode);

                BufferedReader reader;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                }

                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println("Response Body: " + response.toString());

                // Close the connection
                connection.disconnect();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void configureSSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
			public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }}, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());

        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
			public boolean verify(String hostname, SSLSession session) {
                return "localhost".equalsIgnoreCase(hostname);
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    private static StringBuilder createJsonInput(Scanner scanner) {
        StringBuilder json = new StringBuilder("{");
        while (true) {
            System.out.print("KEY : ");
            String key = scanner.nextLine();
            if (key.equals("|")) {
                json.deleteCharAt(json.lastIndexOf(",")).append("}");
                break;
            }
            json.append("\"").append(key).append("\":");
            System.out.print("VALUE : ");
            String value = scanner.nextLine();
            json.append("\"").append(value).append("\",");
        }
        return json;
    }
}
