package org.zen.zenmail.model.session;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zen.zenmail.model.response.OperationResponse;

@Data
@EqualsAndHashCode(callSuper = false)
public class SessionResponse extends OperationResponse {
    private SessionItem item;
}

