package com.app.repository.message;

import com.app.model.message.Message;
import com.app.model.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findAllByFrom(String from);
}
