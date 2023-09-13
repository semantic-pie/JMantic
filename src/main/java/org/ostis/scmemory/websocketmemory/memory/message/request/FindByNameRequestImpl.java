package org.ostis.scmemory.websocketmemory.memory.message.request;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ostis.scmemory.websocketmemory.message.request.FindByNameRequest;
import org.ostis.scmemory.websocketmemory.message.request.RequestType;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FindByNameRequestImpl extends AbstractScRequest implements FindByNameRequest {
    @JsonProperty("payload")
    private final List<Wrapper> payload = new ArrayList<>();

    @Override
    public void addComponent(List<String> component) {
        component.forEach(data -> this.payload.add(new Wrapper(data)));
    }

    @Override
    public void addComponent(String component) {
        this.payload.add(new Wrapper(component));
    }

    public FindByNameRequestImpl() {
        super(RequestType.CONTENT);
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    private class Wrapper {
        @JsonProperty("command")
        private final String command = "find";

        @JsonProperty("data")
        private String data;

        @JsonIgnore
        public Wrapper(String data) {
            this.data = data;
        }

    }
}
