package io.github.sefiraat.networks.network;

public class ObjectDefinition {

    private final ObjectType type;
    private NetworkNode node;

    public ObjectDefinition(ObjectType type) {
        this.type = type;
    }

    public ObjectType getType() {
        return type;
    }

    public NetworkNode getNode() {
        return node;
    }

    public void setNode(NetworkNode node) {
        this.node = node;
    }

}
