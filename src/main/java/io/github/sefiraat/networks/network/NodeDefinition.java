package io.github.sefiraat.networks.network;

public class NodeDefinition {

    private final NodeType type;
    private final long timeRegistered;
    private NetworkNode node;

    public NodeDefinition(NodeType type) {
        this.type = type;
        this.timeRegistered = System.currentTimeMillis();
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

    public boolean isExpired() {
        return System.currentTimeMillis() > this.timeRegistered + 3000L;
    }

}
