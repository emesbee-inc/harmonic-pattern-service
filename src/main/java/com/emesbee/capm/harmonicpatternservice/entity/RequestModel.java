package com.emesbee.capm.harmonicpatternservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

@Data
public class RequestModel {
    @JsonProperty("msg_type")
    private String msgType;
    private List<PatternNotification> data;
}
