package com.app.model.user;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {
    @Id
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private Set<String> blockedUsers;

    private List<Object> loginHistory;
}
