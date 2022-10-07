package br.com.api.mockito.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Error {

    private LocalDateTime timeStamp;
    private Integer status;
    private String error;
    private String path;
}
