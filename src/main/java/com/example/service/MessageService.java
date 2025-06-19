package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

    MessageRepository messageRepository; 

    MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message message) {
        if(!messageValidationUsingPostedBy(message)) {
            return null;
        }
        return messageRepository.save(message);
    }

    public boolean messageValidationUsingPostedBy(Message message) {
        Message existCheck = messageRepository.findByPostedBy(message.getPostedBy());
        if(!message.getMessageText().isEmpty() && message.getMessageText().length() <= 255 && existCheck != null)  {
            return true;
        }
        return false;
    }

    public Message findByMessageId(int id) {
        return messageRepository.findByMessageId(id);
    }

    public int deleteMessageById(int id) {
        int currentSize = messageRepository.findAll().size();
        Message message = messageRepository.findByMessageId(id);
        if(message != null) {
            messageRepository.deleteById(id);
            int newSize = messageRepository.findAll().size();
            return currentSize - newSize;
        }
        return 0;
    }

    public int updateMessageById(int id, String text) {
        Message message = messageRepository.findByMessageId(id);

        if(message != null && !text.isEmpty() && text.length() <= 255) {
            message.setMessageText(text);
            messageRepository.save(message);
            return 1;
        }
        return 0;
    }

    public List<Message> getAllMessagesById(int id) {
        return messageRepository.findAllByPostedBy(id);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}
