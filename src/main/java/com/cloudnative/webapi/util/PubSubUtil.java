package com.cloudnative.webapi.util;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PubSubUtil {
    private static final Logger logger = LogManager.getLogger(PubSubUtil.class);

    public static void publishMessage(String projectId, String topicId, String message)
            throws IOException, ExecutionException, InterruptedException {

        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;

        try {
            publisher = Publisher.newBuilder(topicName).build();

            ByteString data = ByteString.copyFromUtf8(message);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();

            ApiFuture<String> messageFuture = publisher.publish(pubsubMessage);
            ApiFutures.addCallback(messageFuture, new ApiFutureCallback<>() {
                public void onSuccess(String messageId) {
                    logger.info("Published a message with message id: {}", messageId);
                }

                public void onFailure(Throwable t) {
                    logger.error("Failed to publish message: " + t);
                }
            }, MoreExecutors.directExecutor());

        } finally {
            if (publisher != null) {
                publisher.shutdown();
                publisher.awaitTermination(1, TimeUnit.MINUTES);
            }
        }
    }
}
