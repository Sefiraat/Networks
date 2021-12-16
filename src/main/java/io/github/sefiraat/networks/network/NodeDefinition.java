package io.github.sefiraat.networks.network;

public class NodeDefinition {

    private final NodeType type;
    private NetworkNode node;

    public NodeDefinition(NodeType type) {
        this.type = type;
    }

    public NodeType getType() {
        return type;
    }

    public NetworkNode getNode() {
        return node;
    }

    public void setNode(NetworkNode node) {
        this.node = node;
    }

}
