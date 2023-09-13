package org.ostis.scmemory.websocketmemory.message.request;


import java.util.List;

public interface FindByNameRequest extends ScRequest{
    void addComponent(List<String> component);

    void addComponent(String component);
}
