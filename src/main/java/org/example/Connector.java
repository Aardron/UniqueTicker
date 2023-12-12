package org.example;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.functions.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
interface Iconnector {
    long GetTicker() throws Exception;
}


public class Connector implements Iconnector {
    private final Firestore db;
    private final Logger logger;

    public Connector(Firestore db,Logger logger) {
        this.db = db;
        this.logger = LoggerFactory.getLogger(API.class);
    }

    public long GetTicker() throws Exception {
        DocumentReference ref = db.collection("tickers").document("ticker");
        ApiFuture<Long> val =
                db.runTransaction(
                        transaction -> {
                            DocumentSnapshot snapshot = transaction.get(ref).get();
                            Long tickval = snapshot.getLong("ticker") ;

                            final ApiFuture<WriteResult> updateFuture =
                                    ref.update("ticker", FieldValue.increment(1));

                            logger.info("updated");

                            return tickval;
                        });

        return val.get();
    }
}
