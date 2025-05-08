package com.kevindai.base.camunda_trail.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Map;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProcessStarterRequest {
    @NotBlank(message = "Process definition key cannot be blank")
    private String businessKey;
    private Map<String, Object> variables;
}
