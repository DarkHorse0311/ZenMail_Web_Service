package org.zen.zenmail.model.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zen.zenmail.model.response.OperationResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends OperationResponse {
    private User data = new User();
}
