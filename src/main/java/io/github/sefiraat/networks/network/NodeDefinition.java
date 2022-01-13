package io.github.sefiraat.networks.network;

public class NodeDefinition {

    private final NodeType type;
    private final long timeRegistered;
    private NetworkNode node;
    private final int charge;

    public NodeDefinition(NodeType type) {
        this(type, 0);
    }

    public NodeDefinition(NodeType type, int charge) {
        this.type = type;
        this.timeRegistered = System.currentTimeMillis();
        this.charge = charge;
    }

    public NodeType getType() {
        return type;
    }

    public NetworkNode getNode() {
        return node;
    }

    public int getCharge() {
        return charge;
    }

    public void setNode(NetworkNode node) {
        this.node = node;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.timeRegistered + 3000L;
    }

}
