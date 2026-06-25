package com.gatepass.guardhouse.user.model;

import com.gatepass.guardhouse.common.model.BaseDocument;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
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
