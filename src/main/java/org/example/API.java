package org.example;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.functions.HttpFunction;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;

import java.io.BufferedWriter;
import java.net.HttpURLConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class API implements HttpFunction {
    private final Logger logger;
    private Connector conn;

    public API(){
        this.logger = LoggerFactory.getLogger(API.class);

        try {
            FirestoreOptions firestoreOptions =
                    FirestoreOptions.getDefaultInstance().toBuilder()
                            .setProjectId("glowing-service-407613")
                            .setCredentials(GoogleCredentials.getApplicationDefault())
                            .build();

            Firestore db = firestoreOptions.getService();

            logger.info("Firestore successfully created");

             conn = new Connector(db,logger);
        }catch(Exception e){
            logger.info("Something went wrong creating the firestore database connection");
        }
    }

    @Override
    public void service(HttpRequest request, HttpResponse response)
            throws Exception {
        logger.info("Endpoint hit");

        BufferedWriter writer = response.getWriter();



        switch (request.getMethod()) {
            case "GET":
                response.setStatusCode(HttpURLConnection.HTTP_OK);
              long value= conn.GetTicker();
                writer.write(value+"");
                break;
            default:
                response.setStatusCode(HttpURLConnection.HTTP_BAD_METHOD);
                writer.write("Only accept Get method");
                break;
        }
    }
}