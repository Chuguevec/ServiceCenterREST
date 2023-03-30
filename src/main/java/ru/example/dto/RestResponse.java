package ru.example.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

@AllArgsConstructor
@NoArgsConstructor
@Jacksonized
public class RestResponse {
    private String result;
}
