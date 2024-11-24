//package com.project.instagramclone.Component;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import software.amazon.awssdk.services.sqs.SqsClient;
//import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
//import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
//
//@Component
//@RequiredArgsConstructor
//public class MessageListener {
//
//    private final SqsClient sqsClient;
//
//    @Scheduled(fixedDelay = 5000)
//    public void pollMessages() {
//        String queueUrl = "your-queue-url"; // SQS 큐 URL
//        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
//                .queueUrl(queueUrl)
//                .maxNumberOfMessages(10)
//                .waitTimeSeconds(10)
//                .build();
//
//        sqsClient.receiveMessage(receiveMessageRequest).messages().forEach(message -> {
//            System.out.println("Received message: " + message.body());
//            processMessage(message.body());
//
//            // 메시지 삭제
//            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
//                    .queueUrl(queueUrl)
//                    .receiptHandle(message.receiptHandle())
//                    .build();
//            sqsClient.deleteMessage(deleteMessageRequest);
//        });
//    }
//
//    private void processMessage(String messageBody) {
//        // 메시지 처리 로직
//    }
//}
//
