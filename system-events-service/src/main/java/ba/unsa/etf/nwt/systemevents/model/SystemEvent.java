package ba.unsa.etf.nwt.systemevents.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class SystemEvent {

    private Instant timestamp;
    private String service;
    private String principal;
    private String method;
    private String action;
    private String resource;
    private String requestURL;
    private Integer status;
}
