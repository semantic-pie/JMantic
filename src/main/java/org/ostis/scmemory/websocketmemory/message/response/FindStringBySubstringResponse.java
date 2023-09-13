package org.ostis.scmemory.websocketmemory.message.response;

import java.util.List;
import java.util.stream.Stream;

public interface FindStringBySubstringResponse extends ScResponse{
    Stream<List<String>> getMatches();
}
