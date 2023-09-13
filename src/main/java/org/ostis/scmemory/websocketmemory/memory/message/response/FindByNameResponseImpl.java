package org.ostis.scmemory.websocketmemory.memory.message.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.ostis.scmemory.websocketmemory.message.response.FindByNameResponce;
import org.ostis.scmemory.websocketmemory.message.response.ScResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FindByNameResponseImpl extends AbstractScResponse implements FindByNameResponce {
    @JsonProperty("payload")
    private final List<List<Long>> addresses = new ArrayList<>();

    @Override
    public Stream<List<Long>> getFoundAddresses() {
        return addresses.stream();
    }
}
