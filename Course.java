package edu.kit.informatik;

/**
 * The course class, it contains a course name and a trie to save the accounts
 * in each course.
 * 
 * @author Mohammed Anas Ejjalili
 * @version 1.0
 */
public class Course {
    private Trie trie;
    private String name;

    /**
     * The constructor of the course object, takes name as parameter and initializes a
     * trie for this object.
     * 
     * @param name
     *            The name of the course
     */
    public Course(String name) {
        this.name = name;
        this.trie = new Trie();
    }

    /**
     * @return The trie of this course.
     */
    public Trie getTrie() {
        return trie;
    }

    /**
     * @return The name of this course.
     */
    public String getName() {
        return name;
    }
}
