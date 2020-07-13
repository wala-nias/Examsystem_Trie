package edu.kit.informatik;

import java.util.ArrayList;

/**
 * The main implementation of the exam's system. It contains all the courses
 * created, the different commands and the function that starts the program.
 * 
 * @author Mohammed Anas Ejjalili
 * @version 1.0
 *
 */
public class ExamsSystem {
    private ArrayList<Course> courses = new ArrayList<Course>();

    /**
     * it creates a new course and add it to the list of courses if it's not already
     * created
     * 
     * @param name
     *            The name of the course.
     */
    public void create(String name) {
        Course course = new Course(name);
        if (getCourse(name) != null)
            Terminal.printError("Course already exists");
        else {
            courses.add(course);
            Terminal.printLine("OK");
        }
    }

    /**
     * gets the course object from it's name. It helps to check if the course with
     * the same is already created.
     * 
     * @param name
     *            The course's name.
     * @return the object course if it's already created.
     */
    private Course getCourse(String name) {
        for (Course course : courses) {
            if (course.getName().equals(name))
                return course;
        }
        return null;
    }

    /**
     * adds an account with its mark to a certain course, after checking if the
     * course is already created.
     * 
     * @param course
     *            The course's name.
     * @param account
     *            The account's name.
     * @param mark
     *            The mark of this account in this course.
     */
    public void add(String course, String account, String mark) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().insert(account, mark);
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * modifies the mark of a certain account in a certain course, after checking if
     * the course is already created.
     * 
     * @param course
     *            The course's name.
     * @param account
     *            The account's name.
     * @param mark
     *            The new mark.
     */
    public void modify(String course, String account, String mark) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().modify(account, mark);
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * deletes a certain account from an already created course, after checking if
     * the course is already created.
     * 
     * @param course
     *            The course's name.
     * @param account
     *            The account's name to delete.
     */
    public void delete(String course, String account) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().delete(account);
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * shows the mark/credits of a certain account in a certain course, after
     * checking if it's already created.
     * 
     * @param course
     *            The course's name.
     * @param account
     *            The account's name.
     */
    public void credits(String course, String account) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().credits(account);
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * print the tries of a certain course, after checking if it's already created.
     * 
     * @param course
     *            The course's name.
     */
    public void print(String course) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().print();
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * Gives the average mark of a certain course, after checking if it's already
     * created.
     * 
     * @param course
     *            The course's name
     */
    public void average(String course) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().average();
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * Gives the median of a selected course, after checking if it's already
     * created.
     * 
     * @param course
     *            The course's name.
     */
    public void median(String course) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().median();
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * Resets the trie of a selected course, after checking if it's already created.
     * 
     * @param course
     *            The course's name.
     */
    public void reset(String course) {
        if (getCourse(course) != null) {
            getCourse(course).getTrie().reset();
        } else
            Terminal.printError("No Course with this name");
    }

    /**
     * Tests if the course's name is in the right form.
     * 
     * @param name
     *            The course name.
     * @return true if the name is in the right form.
     */

    public boolean isAlpha(String name) {
        if (name.matches("[a-z]+"))
            return true;
        Terminal.printError("Please give a valid course name");
        return false;
    }

    /**
     * Tests if the account's name is in the right form (u****)
     * 
     * @param account
     *            The account's name.
     * @return true if the name is in the right form
     */
    public boolean isValidAccountName(String account) {
        if (account.matches("u[a-z]{4}"))
            return true;
        Terminal.printError("Please give a valid account");
        return false;
    }

    /**
     * Tests the input from the readLine and shows if it's in the right form. If the
     * right command name is available then if the right parameters are given in the
     * right form. it will be executed till the right form is given
     * 
     * @return an array with the command's name in the first index and the different
     *         parameters separated with ";" in the second index.
     */
    public String[] inputTest() {
        String[] input = new String[2];
        String[] params = null;
        boolean right = false;
        while (!right) {
            try {
                input = Terminal.readLine().split(" ", 2);
                if (input[0].matches("create|reset|print|average|median") && input.length == 2) {
                    if (isAlpha(input[1]))
                        right = true;
                } else if (input[0].matches("add|modify") && input.length == 2) {
                    params = input[1].split(";", 3);
                    if (isAlpha(params[0]) && isValidAccountName(params[1]) && Integer.parseInt(params[2]) >= 0)
                        right = true;
                } else if (input[0].matches("delete|credits") && input.length == 2) {
                    params = input[1].split(";", 2);
                    if (isAlpha(params[0]) && isValidAccountName(params[1]))
                        right = true;
                } else if (input[0].matches("quit") && input.length == 1)
                    right = true;
                else
                    Terminal.printError("Please give a valid command");
            } catch (NumberFormatException ex) {
                Terminal.printError("Please give a valid mark");
            } catch (ArrayIndexOutOfBoundsException ex) {
                Terminal.printError("Please give valid parameters");
            }
        }
        return input;
    }

    /**
     * This method executes the program. After testing the input and reacts to the
     * chosen command.
     */
    public void start() {
        boolean running = true;
        while (running) {
            String[] input = inputTest();
            String[] params = null;
            if (input.length != 1)
                params = input[1].split(";");
            switch (input[0]) {
                case "create":
                    create(params[0]);
                    break;
                case "reset":
                    reset(params[0]);
                    break;
                case "add":
                    add(params[0], params[1], params[2]);
                    break;
                case "modify":
                    modify(params[0], params[1], params[2]);
                    break;
                case "delete":
                    delete(params[0], params[1]);
                    break;
                case "credits":
                    credits(params[0], params[1]);
                    break;
                case "print":
                    print(params[0]);
                    break;
                case "average":
                    average(params[0]);
                    break;
                case "median":
                    median(params[0]);
                    break;
                case "quit":
                    running = false;
                    break;
                default:
                    break;

            }
        }

    }

}
