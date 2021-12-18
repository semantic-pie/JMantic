package org.jmantic.scmemory.websocketmemory.sync;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jmantic.scmemory.websocketmemory.message.response.FindByPatternResponse;

import java.util.Collection;
import java.util.stream.Stream;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class FindByPatternResponseImpl extends AbstractScResponse implements FindByPatternResponse {

    @JsonProperty("payload")
    private PayloadFoundByTemplateStruct payloadFoundByTemplateStruct;

    @Override
    public Stream<Stream<Long>> getFoundAddresses() {
        return payloadFoundByTemplateStruct.getFoundAddresses().stream().map(Collection::stream);
    }

}
