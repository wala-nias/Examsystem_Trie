package edu.kit.informatik;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the node class. It implement Comparable to sort the node in
 * alphabetical order.
 * 
 * @author Mohammed Anas Ejjalili
 * @version 1.0
 */
public class Node implements Comparable<Node> {

    private Node parent;
    private List<Node> children;
    private String value;
    private boolean isEndOfWord;
    private boolean isLeaf;

    /**
     * The constructor of the Node object. it takes the value as a parameter and
     * initialize an Arraylist of nodes as children of this node. It sets also
     * boolean variables as false by default.
     * 
     * @param value
     *            The value saved in this mode, it could be the mark (int) in a
     *            String form or a letter.
     */
    public Node(String value) {
        this.value = value;
        this.children = new ArrayList<Node>();
        this.isLeaf = false;
        this.isEndOfWord = false;
    }

    /**
     * @return true if a Node is the end of an account name.
     */
    public boolean isEndofWord() {
        return isEndOfWord;
    }

    /**
     * @param isEndofWord
     *            true if the node is containing the last letter of the account's
     *            name
     */
    public void setEndofWord(boolean isEndofWord) {
        this.isEndOfWord = isEndofWord;
    }

    /**
     * @return The parent of this node.
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Sets the parent of the node
     * 
     * @param parent
     *            The parent of this node
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

    /**
     * @return the children of this node.
     */
    public List<Node> getChildren() {
        return children;
    }

    /**
     * @return The value saved in this node(It could be a letter or an int in a
     *         String form)
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            The new value to be set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return true if this node is a leaf (contains the mark)
     */
    public boolean isLeaf() {
        return isLeaf;
    }

    /**
     * @param isLeaf
     *            true if this node is a leaf (contains the mark)
     */
    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /**
     * @param letter
     *            a Letter from the account name
     * @return a node object with the given letter if it belongs to children of this
     *         node
     */
    public Node getChild(String letter) {
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i).getValue().equals(letter))
                return this.children.get(i);
        }
        return null;
    }

    /**
     * @return the number of children
     */
    public int numChildren() {
        return this.children.size();
    }

    /**
     * removes a child if there is only one, it helps in the remove/delete method.
     */
    public void removeChild() {
        if (this.numChildren() == 1)
            this.getChildren().remove(0);
    }

    /**
     * @return a String with this node and its children in the form given in the
     *         assignment. It helps by print method.
     */
    public String getChilds() {
        String output = this.getValue() + "[";
        for (Node node : this.children) {
            if (node.isLeaf()) {
                output = output.substring(0, output.length() - 1) + "(" + node.getValue() + ")";
                return output;
            } else
                output += node.getChilds();
        }
        return output + "]";
    }

    @Override
    public int compareTo(Node n) {
        return this.value.compareTo(n.getValue());
    }

}
