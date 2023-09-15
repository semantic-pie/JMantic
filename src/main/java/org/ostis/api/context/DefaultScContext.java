package org.ostis.api.context;

import org.ostis.scmemory.model.ScMemory;
import org.ostis.scmemory.model.element.ScElement;
import org.ostis.scmemory.model.element.edge.EdgeType;
import org.ostis.scmemory.model.element.edge.ScEdge;
import org.ostis.scmemory.model.element.link.LinkType;
import org.ostis.scmemory.model.element.link.ScLinkBinary;
import org.ostis.scmemory.model.element.link.ScLinkFloat;
import org.ostis.scmemory.model.element.link.ScLinkInteger;
import org.ostis.scmemory.model.element.link.ScLinkString;
import org.ostis.scmemory.model.element.node.NodeType;
import org.ostis.scmemory.model.element.node.ScNode;
import org.ostis.scmemory.model.event.ScEventConsumer;
import org.ostis.scmemory.model.exception.ScMemoryException;
import org.ostis.scmemory.model.pattern.ScPattern;
import org.ostis.scmemory.model.pattern.factory.DefaultScPattern3Factory;
import org.ostis.scmemory.model.pattern.pattern3.ScConstruction3;
import org.ostis.scmemory.model.pattern.pattern3.ScPattern3;
import org.ostis.scmemory.model.pattern.pattern3.ScPattern3Impl;
import org.ostis.scmemory.model.pattern.pattern5.ScConstruction5;
import org.ostis.scmemory.model.pattern.pattern5.ScPattern5;

import java.io.ByteArrayOutputStream;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Common sc-context implementation
 * This implementation provides most of the useful methods for working with sc-memory.
 * But if you use this implementation, you should catch all sc-exceptions.
 * If you don't like exceptions, try to use {@link UncheckedScContext}.
 *
 * @author artrayme
 * @since 0.2.0
 */

public record DefaultScContext(ScMemory memory) {

    /**
     * Node creating.
     * This method creates a node in sc-memory with the specified type.
     *
     * @param type type of the node.
     * @return Some implementation of ScNode, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScNode createNode(NodeType type) throws ScMemoryException {
        return memory.createNodes(Stream.of(type))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Nodes creating.
     * This method creates multiple nodes in sc-memory with the specified types.
     * If you want to create multiple nodes, this method will be more efficient than {@link #createNode(NodeType)}.
     *
     * @param types stream of node types.
     * @return Stream of some implementation of ScNode, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Stream<? extends ScNode> createNodes(Stream<NodeType> types) throws ScMemoryException {
        return memory.createNodes(types);
    }

    /**
     * Edge creating.
     * This method creates an edge in sc-memory with the specified type and between two non-null nodes
     *
     * @param type   type of the edge.
     * @param source edge source.
     * @param target edge target.
     * @return Some implementation of ScEdge, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScEdge createEdge(EdgeType type, ScElement source, ScElement target) throws ScMemoryException {
        return memory.createEdges(
                             Stream.of(type),
                             Stream.of(source),
                             Stream.of(target))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Edges creating.
     * This method creates multiple edges in sc-memory with the specified types, sources and targets.
     * If you want to create multiple edges, this method will be more efficient than {@link #createEdges(Stream, Stream, Stream)}.
     * <p>
     * Edge[n] will be created between sources[n] and targets[n] with types[n].
     *
     * @param types   type of the edge.
     * @param sources edge sources.
     * @param targets edge targets.
     * @return Stream of some implementation of ScEdge, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Stream<ScEdge> createEdges(Stream<EdgeType> types,
                                      Stream<? extends ScElement> sources,
                                      Stream<? extends ScElement> targets) throws ScMemoryException {
        return memory.createEdges(
                             types,
                             sources,
                             targets)
                     .map(e -> (ScEdge) e);
    }

    /**
     * Link with integer content creating.
     * This method creates a link in sc-memory with the specified type and integer content.
     *
     * @param type    type of the link.
     * @param content integer content of the link.
     * @return Some implementation of ScLinkInteger, that is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScLinkInteger createIntegerLink(LinkType type, Integer content) throws ScMemoryException {
        return memory.createIntegerLinks(
                             Stream.of(type),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link with float content creating.
     * This method creates a link in sc-memory with the specified type and float content.
     *
     * @param type    type of the link.
     * @param content float content of the link.
     * @return Some implementation of ScLinkFloat, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScLinkFloat createFloatLink(LinkType type, Float content) throws ScMemoryException {
        return memory.createFloatLinks(
                             Stream.of(type),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link with string content creating.
     * This method creates a link in sc-memory with the specified type and string content.
     *
     * @param type    type of the link.
     * @param content string content of the link.
     * @return Some implementation of ScLinkString, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScLinkString createStringLink(LinkType type, String content) throws ScMemoryException {
        return memory.createStringLinks(
                             Stream.of(type),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link with binary content creating.
     * This method creates a link in sc-memory with the specified type and ByteArrayOutputStream (binary) content.
     *
     * @param type    type of the link.
     * @param content binary content of the link.
     * @return Some implementation of ScLinkBinary, which is linked with the corresponding sc-memory.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScLinkBinary createBinaryLink(LinkType type, ByteArrayOutputStream content) throws ScMemoryException {
        return memory.createBinaryLinks(
                             Stream.of(type),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Element deleting
     * This method removes the sc-element from the sc-memory.
     *
     * @param element is the item you want to delete.
     * @return true when executed successfully
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean deleteElement(ScElement element) throws ScMemoryException {
        return memory.deleteElements(Stream.of(element));
    }

    /**
     * Elements deleting
     * This method remove the sc-elements from the sc-memory.
     *
     * @param elements stream of items to be deleted.
     * @return true when executed successfully.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean deleteElements(Stream<? extends ScElement> elements) throws ScMemoryException {
        return memory.deleteElements(elements);
    }

    /**
     * Construction search.
     * This method searches for all node-edge-node constructions with specified types relative to a fixed node.
     * This method is a special case of a searching pattern and exists only because it is most useful and popular.
     * Also, you can use {@link #find(ScPattern)} if more complex or not standard pattens needed.
     *
     * @param fixedNode the fixed node with respect to which the search will take place.
     * @param edge      type of edge you are looking for.
     * @param node      type of node you are looking for.
     * @return stream of found constructions.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Stream<? extends ScEdge> findAllConstructionsNodeEdgeNode(ScNode fixedNode,
                                                                     EdgeType edge,
                                                                     NodeType node) throws ScMemoryException {
        return memory.findByPattern3(DefaultScPattern3Factory.get(
                             fixedNode,
                             edge,
                             node))
                     .map(ScConstruction3::getEdge);
    }

    /**
     * Construction search.
     * This method searches for all 3-element constructions by pattern.
     * Also, you can use {@link #find(ScPattern)} if more complex or not standard pattens needed.
     *
     * @param pattern the pattern to be searched for
     * @return stream of found constructions.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public <t1 extends ScElement, t2, T3 extends ScElement> Stream<? extends ScConstruction3<t1, T3>> find(ScPattern3<t1, t2, T3> pattern) throws ScMemoryException {
        return memory.findByPattern3(pattern);
    }

    /**
     * Construction search.
     * This method searches for all 5-element constructions by pattern.
     * Also, you can use {@link #find(ScPattern)} if more complex or not standard pattens needed.
     *
     * @param pattern the pattern to be searched for
     * @return stream of found constructions.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public <t1 extends ScElement, t2, t3, T2 extends ScElement, T3 extends ScElement> Stream<? extends ScConstruction5<t1, T2, T3>> find(
            ScPattern5<t1, t2, t3, T2, T3> pattern) throws ScMemoryException {
        return memory.findByPattern5(pattern);
    }

    /**
     * Construction search.
     * This method searches for all constructions by pattern.
     * This method is universal and can be used for all types of constructions.
     * But, abstract patterns are hard to create.
     * If you want
     * to use some common pattern, these methods can help you:
     * {@link #findAllConstructionsNodeEdgeNode(ScNode, EdgeType, NodeType)},
     * {@link  #find(ScPattern3)}, {@link #find(ScPattern5)}
     *
     * @param pattern the pattern to be searched for
     * @return stream of found constructions. Each inner stream represents one found construction.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Stream<Stream<? extends ScElement>> find(ScPattern pattern) throws ScMemoryException {
        return memory.find(pattern);
    }

    /**
     * Link integer content setting.
     * This method sets the content to sc-link.
     *
     * @param link    target link.
     * @param content integer content.
     * @return true when executed successfully.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean setIntegerLinkContent(ScLinkInteger link, Integer content) throws ScMemoryException {
        return memory.setIntegerLinkContent(
                             Stream.of(link),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link float content setting.
     * This method sets the content to sc-link.
     *
     * @param link    target link.
     * @param content float content.
     * @return true when executed successfully.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean setFloatLinkContent(ScLinkFloat link, Float content) throws ScMemoryException {
        return memory.setFloatLinkContent(
                             Stream.of(link),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link string content setting.
     * This method sets the content to sc-link.
     *
     * @param link    target link.
     * @param content string content.
     * @return true when executed successfully.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean setStringLinkContent(ScLinkString link, String content) throws ScMemoryException {
        return memory.setStringLinkContent(
                             Stream.of(link),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Link binary content setting.
     * This method sets the content to sc-link.
     *
     * @param link    target link.
     * @param content binary (ByteArrayOutputStream) content.
     * @return true when executed successfully.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Boolean setBinaryLinkContent(ScLinkBinary link, ByteArrayOutputStream content) throws ScMemoryException {
        return memory.setBinaryLinkContent(
                             Stream.of(link),
                             Stream.of(content))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Integer link content getter.
     * This method gets the link content from sc-memory.
     *
     * @param link target link.
     * @return link content
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Integer getIntegerLinkContent(ScLinkInteger link) throws ScMemoryException {
        return memory.getIntegerLinkContent(Stream.of(link))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Float link content getter.
     * This method gets the link content from sc-memory.
     *
     * @param link target link.
     * @return link content
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Float getFloatLinkContent(ScLinkFloat link) throws ScMemoryException {
        return memory.getFloatLinkContent(Stream.of(link))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * String link content getter.
     * This method gets the link content from sc-memory.
     *
     * @param link - target link.
     * @return link content
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public String getStringLinkContent(ScLinkString link) throws ScMemoryException {
        return memory.getStringLinkContent(Stream.of(link))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Binary link content getter.
     * This method gets the link content from sc-memory.
     *
     * @param link - target link.
     * @return link content
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ByteArrayOutputStream getBinaryLinkContent(ScLinkBinary link) throws ScMemoryException {
        return memory.getBinaryLinkContent(Stream.of(link))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Keynodes finder.
     * This method finds keynode with a specific identifier.
     *
     * @param idtf identifier of node.
     * @return Optional of node. If node with a passed identifier exists - optional will be present.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Optional<? extends ScNode> findKeynode(String idtf) throws ScMemoryException {
        return memory.findKeynodes(Stream.of(idtf))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    public Optional<? extends ScElement> findByName(String name) throws ScMemoryException {
        return memory.findByName(Stream.of(name))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }

    /**
     * Keynodes resolver.
     * This method resolves keynode with a specific identifier.
     *
     * @param idtf identifier of node.
     * @param type type of node that will be created
     * @return resolved node. If node with identifier does not exist - a new node will be created.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScNode resolveKeynode(String idtf, NodeType type) throws ScMemoryException {
        return memory.resolveKeynodes(
                             Stream.of(idtf),
                             Stream.of(type))
                     .findFirst()
                     .orElseThrow(ScMemoryException::new);
    }
    /**
     * Edges resolver.
     * This method resolves keynode with a specific identifier.
     *
     * @param source object of source element.
     * @param type type of edge that will be created, must be only VAR types
     * @param target object of target element.
     * @return resolved edge. If edge with connection from source to target does not exist - a new edge will be created.
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public ScEdge resolveEdge(ScElement source, EdgeType type, ScElement target) throws ScMemoryException {
        Optional<? extends ScConstruction3<ScElement, ScElement>> pattern = find(new ScPattern3Impl<>(source, type, target)).findFirst();

        EdgeType constType = EnumSet.allOf(EdgeType.class)
                                    .stream()
                                    .filter(edgeType -> edgeType.getCode() == type.getCode() - 32)
                                    .findFirst()
                                    .orElseThrow();

        return pattern.isEmpty() ? createEdge(constType, source, target) : pattern.get().getEdge();
    }

    /**
     * Find all strings that include substring in SCLinks
     *
     * @param data substring for searching
     * @return List of strings that includes data
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public List<String> findStringBySubstring(String data) throws ScMemoryException {
        return this.memory.findStringBySubstring(data).flatMap(List::stream).toList();
    }

    /**
     * Method for subscribe on some event of sc-machine. See {@link ScEventConsumer} for types
     * @param element The element whose changes will be subscribed to.
     * @param consumer Handler of changes.
     * @return EventID
     * @throws ScMemoryException if an internal sc-memory error has occurred. You can find more information in cause exception
     */
    public Optional<Long> subscribeOnEvent(ScElement element, ScEventConsumer consumer) throws ScMemoryException {
        return this.memory.subscribeOnEvent(element, consumer);
    }

    /**
     * Unsubscribes from the event with the specified ID.
     *
     * @param  eventId ID of the event to unsubscribe from
     * @throws ScMemoryException if an error occurs while working with memory
     */
    public void unSubscribeOnEvent(Long eventId) throws ScMemoryException {
        this.memory.unsubscribeEvent(Stream.of(eventId));
    }

}
