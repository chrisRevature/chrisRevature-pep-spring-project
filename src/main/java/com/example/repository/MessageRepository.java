package com.example.repository;

import java.util.List;
import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Integer>  {

    public Message findByPostedBy(int postedById);
    public Message findByMessageId(int id);
    public List<Message> findAllByPostedBy(int id);
}
