package com.ms8.md.user.global.auth.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReIssueResponse {
    private String accessToken;

    // public ObjectNode toJson(){
    //     ObjectNode json = new ObjectMapper().createObjectNode();
    //     json.put("accessToken", String.valueOf(accessToken));
    //     return json;
    // }

    public JsonNode toJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(this);
    }
}
