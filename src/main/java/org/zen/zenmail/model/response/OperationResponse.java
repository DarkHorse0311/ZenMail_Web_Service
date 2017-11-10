package org.zen.zenmail.model.response;

import lombok.Data;

@Data //for getters and setters
public class OperationResponse {
    public enum ResponseStatusEnum {SUCCESS, ERROR, WARNING, NO_ACCESS};
    private ResponseStatusEnum operationStatus;
    private String operationMessage;
}
