package org.myproject.statisticservice.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {
    INCORRECT_BODY_STAT_ID("Incorrect body stat id"),
    INCORRECT_BODY_STAT_DATA("Incorrect body stat data"),
    INCORRECT_USER_ID("Incorrect user id");

    private String errorName;

    ErrorCode(String errorName) {
        this.errorName = errorName;
    }
}
