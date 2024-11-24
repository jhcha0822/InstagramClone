package com.project.instagramclone.service.chat;

import com.project.instagramclone.entity.chat.MessageEntity;
import com.project.instagramclone.repository.chat.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageEntity sendMessage(String roomId, String sender, String content) {
        MessageEntity message = new MessageEntity(roomId, sender, content, LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<MessageEntity> getMessagesByRoomId(String roomId) {
        return messageRepository.findByRoomId(roomId);
    }

//    private final SqsClient sqsClient;
//    private final MessageRepository messageRepository;
//
//    @Value("${aws.sqs.queue.url}")
//    private String queueUrl;
//
//    public void sendMessageToQueue(String messageBody) {
//        try {
//            System.out.println("Sending message to SQS: " + queueUrl);
//            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
//                    .queueUrl(queueUrl)
//                    .messageBody(messageBody)
//                    .build();
//            sqsClient.sendMessage(sendMessageRequest);
//            System.out.println("Message sent to SQS successfully!");
//        } catch (Exception e) {
//            System.err.println("Error while sending message to SQS: " + e.getMessage());
//            throw e;
//        }
//    }
//
//    // 특정 채팅방의 모든 메시지 가져오기
//    public List<MessageEntity> getMessagesByRoomId(String roomId) {
//        return messageRepository.findByRoomId(roomId);
//    }
//
//    // 메시지 수신
//    public List<Message> receiveMessages() {
//        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
//                .queueUrl(queueUrl)
//                .maxNumberOfMessages(10)
//                .waitTimeSeconds(10) // Long polling
//                .build();
//
//        return sqsClient.receiveMessage(receiveRequest).messages();
//    }
//
//    // 메시지 삭제 (처리 후)
//    public void deleteMessage(String receiptHandle) {
//        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
//                .queueUrl(queueUrl)
//                .receiptHandle(receiptHandle)
//                .build();
//        sqsClient.deleteMessage(deleteRequest);
//    }
}