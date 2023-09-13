package org.ostis.scmemory.websocketmemory.message.response;

import java.util.List;
import java.util.stream.Stream;

public interface FindByNameResponce extends ScResponse{
   Stream<List<Long>> getFoundAddresses();
}
