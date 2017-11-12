package org.zen.zenmail.model.messages;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.zen.zenmail.model.response.OperationResponse;

import javax.mail.Message;
import java.lang.reflect.Array;
import java.util.Vector;

@Data
@EqualsAndHashCode(callSuper = false)
public class MessagesResponse extends OperationResponse {
    private Vector<String> msgs;
}
