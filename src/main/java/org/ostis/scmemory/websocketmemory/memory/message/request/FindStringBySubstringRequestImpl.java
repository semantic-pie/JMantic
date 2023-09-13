package org.ostis.scmemory.websocketmemory.memory.message.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.ostis.scmemory.model.pattern.ScPatternTriplet;
import org.ostis.scmemory.websocketmemory.memory.structures.AliasElement;
import org.ostis.scmemory.websocketmemory.message.request.FindStringBySubstringRequest;
import org.ostis.scmemory.websocketmemory.message.request.RequestType;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FindStringBySubstringRequestImpl extends AbstractScRequest implements FindStringBySubstringRequest {
    @JsonProperty("payload")
    private final List<Wrapper> payload = new ArrayList<>();

    public FindStringBySubstringRequestImpl() {
        super(RequestType.CONTENT);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    private class Wrapper{
        @JsonProperty("command")
        private final String command = "find_strings_by_substr";

        @JsonProperty("data")
        private String data;

        @JsonIgnore
        public Wrapper(String data) {
            this.data = data;
        }

    }

    @Override
    public void setRequest(String data) {
        this.payload.add(new Wrapper(data));
    }
}
