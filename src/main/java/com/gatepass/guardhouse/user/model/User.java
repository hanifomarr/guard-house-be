package com.gatepass.guardhouse.user.model;

import com.gatepass.guardhouse.common.model.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseDocument {

    private String name;

    @Indexed(unique = true)
    private String email;

    private String password;

    private String unitNumber;

    private UserRole userRole;

    private UserStatus status;


}
