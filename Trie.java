package edu.kit.informatik;

import java.util.*;

/**
 * This is the trie class. It has all methods to control a trie (add/delete/ an
 * element, calculate the average/median...)
 * 
 * @author Anas Ejjalili
 * @version 1.0
 */
public class Trie {

    private Node root;
    private List<String> accounts;

    /**
     * The constructor of the trie. It construct a trie with a root containing the
     * value "#" It also initializes a list of the accounts names in the trie.
     */
    public Trie() {
        this.accounts = new ArrayList<String>();
        this.root = new Node("#");
        this.root.setParent(null);
    }

    /**
     * It adds only the account's name to the trie and set the last element as
     * EndOfWord. It helps by the insert function that adds also the mark.
     * 
     * @param account
     *            The account's name.
     */
    private void addWord(String account) {
        Node current = root;
        for (int i = 0; i <= 4; i++) {
            String s = String.valueOf(account.charAt(i));
            if (current.getChild(s) == null) {
                Node node = new Node(s);
                node.setParent(current);
                current.getChildren().add(node);
                Collections.sort(current.getChildren());
            }
            current = current.getChild(s);
        }
        current.setEndofWord(true);
    }

    /**
     * Helps by insert method, by adding the mark.
     * 
     * @param account
     *            The account's name.
     * @return the node containing the last letter of the given account
     */
    private Node getEndofWord(String account) {
        Node current = root;
        for (int i = 0; i <= 4; i++) {
            String s = String.valueOf(account.charAt(i));
            current = current.getChild(s);
        }
        return current;
    }

    /**
     * It inserts am account's name and its mark to the trie. The node containing
     * the mark is the leaf of the trie.
     * 
     * @param account
     *            The account's name
     * @param mark
     *            The mark of this account
     */
    public void insert(String account, String mark) {
        if (search(account) == false) {
            addWord(account);
            Node current = getEndofWord(account);
            Node markNode = new Node(mark);
            markNode.setLeaf(true);
            markNode.setParent(current);
            current.getChildren().add(markNode);
            // add the account's name to the list after adding it to the trie
            accounts.add(account);
            Terminal.printLine("OK");
        } else {
            Terminal.printError("Account already created");
        }

    }

    /**
     * Searches if the given account already exists in the trie.
     * 
     * @param account
     *            The account's name.
     * @return true if the given already exists in the trie.
     */
    public boolean search(String account) {
        Node current = root;
        for (int i = 0; i <= 4; i++) {
            String s = String.valueOf(account.charAt(i));
            current = current.getChild(s);
            if (current == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Shows the credits of the given account, after checking if it exists.
     * 
     * @param account
     *            The account's name
     */
    public void credits(String account) {
        if (search(account) == true)
            Terminal.printLine(getMark(account));
        else
            Terminal.printError("Account not found ");
    }

    /**
     * modifies the mark of the given account, after checking if it exists.
     * 
     * @param account
     *            The account's name.
     * @param mark
     *            The new mark.
     */
    public void modify(String account, String mark) {
        if (search(account) == true) {
            getMarkNode(account).setValue(mark);
            Terminal.printLine("OK");
        } else
            Terminal.printError("Account not found");
    }

    /**
     * gets the node that contains the mark of the given account.
     * 
     * @param account
     *            The account's name.
     * @return the node object that contains the mark.
     */
    private Node getMarkNode(String account) {
        Node node = getEndofWord(account);
        return node.getChildren().get(0);
    }

    /**
     * Gets the mark of the given account. It helps by credits method..
     * 
     * @param account
     *            The account's name.
     * @return the mark (Integer) of the given account.
     */
    private int getMark(String account) {
        return Integer.parseInt(getMarkNode(account).getValue());
    }

    /**
     * removes an account and its mark from the trie. It helps by delete and reset
     * methods.
     * 
     * @param account
     *            The account's name.
     */
    private void remove(String account) {
        Node current = getMarkNode(account);
        Node parent = current.getParent();
        for (int i = 0; i <= 5; i++) {
            if (parent.numChildren() == 1) {
                parent.removeChild();
                current = parent;
                parent = current.getParent();
            } else {
                parent.getChildren().remove(current);
                break;
            }
        }
        // removes the account's from the list after removing it from the trie.
        accounts.remove(account);
    }

    /**
     * deletes the given account after checking if it exists.
     * 
     * @param account
     *            The account's name.
     */
    public void delete(String account) {
        if (search(account)) {
            remove(account);
            Terminal.printLine("OK");
        } else {
            Terminal.printError("Account not found");
        }
    }

    /**
     * calculates the average mark of the saved accounts in the trie.
     */
    public void average() {
        if (accounts.size() != 0) {
            int sum = 0;
            for (String account : accounts) {
                sum += getMark(account);
            }
            Terminal.printLine(sum / accounts.size());
        } else
            Terminal.printError("There is no account in this trie");

    }

    /**
     * resets the trie/deletes all the accounts in the trie.
     */
    public void reset() {
        List<String> copyAccounts = new ArrayList<String>();
        for (String account : accounts) {
            copyAccounts.add(account);
        }
        // gets all the available account's names from the list and remove it trie.
        for (String account : copyAccounts) {
            remove(account);
        }
        Terminal.printLine("OK");
    }

    /**
     * calculates the median of the available accounts. corresponding to the
     * convention in the assignment.
     */
    public void median() {
        List<Integer> marks = new ArrayList<Integer>();
        for (String account : accounts) {
            int mark = getMark(account);
            marks.add(mark);
            Collections.sort(marks);
        }
        if (marks.size() % 2 == 1) {
            Terminal.printLine(marks.get(((marks.size() + 1) / 2) - 1));
        } else if (marks.size() % 2 == 0 && marks.size() != 0) {
            Terminal.printLine((marks.get(marks.size() / 2 - 1) + marks.get(marks.size() / 2)) / 2);
        } else
            Terminal.printError("There is no account in this trie");
    }

    /**
     * print the element of the trie in the form described in the assignment.
     */
    public void print() {
        if (accounts.size() == 0)
            Terminal.printLine(root.getValue());
        else {
            String output = root.getValue() + "[";
            for (Node node : root.getChildren()) {
                if (node.isLeaf()) {
                    output = output.substring(0, output.length() - 1) + "(" + node.getValue() + ")";
                } else
                    output += node.getChilds();
            }
            output += "]";
            Terminal.printLine(output);
        }
    }

}
