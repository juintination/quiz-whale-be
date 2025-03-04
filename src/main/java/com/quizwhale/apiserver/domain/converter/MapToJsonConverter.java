package com.quizwhale.apiserver.domain.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;
import java.util.Map;

@Converter
public class MapToJsonConverter implements AttributeConverter<Map<String, String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(Map<String, String> attribute) {
        if (attribute == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting map to JSON: {}", e);
        }
    }

    @Override
    public Map<String, String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return null;
        }
        try {
            // 유효한 JSON 객체인지 확인
            JsonNode jsonNode = objectMapper.readTree(dbData);
            if (!jsonNode.isObject()) {
                throw new IllegalArgumentException("Invalid JSON format for choices: Expected a JSON object.");
            }

            // JSON 객체를 Map<String, String>으로 변환
            return objectMapper.convertValue(jsonNode, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to map: {}", e);
        }
    }

}
