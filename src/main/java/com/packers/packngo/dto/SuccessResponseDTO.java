package com.packers.packngo.dto;

import com.sun.net.httpserver.Authenticator;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SuccessResponseDTO {
    private HttpStatusCode status;
    private String data;

}
