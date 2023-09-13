package org.ostis.scmemory.websocketmemory.memory.message.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.ostis.scmemory.websocketmemory.message.response.FindStringBySubstringResponse;

import java.util.List;
import java.util.stream.Stream;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FindStringBySubstringResponseImpl extends AbstractScResponse implements FindStringBySubstringResponse {
    @JsonProperty("payload")
    private List<List<String>> matches;
    @Override
    public Stream<List<String>> getMatches() {
        return matches.stream();
    }
}
