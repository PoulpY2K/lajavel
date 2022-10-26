package lajavel;

import org.eclipse.jetty.util.log.Log;

public class Person {
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String firstName;
    public String lastName;

    public String getFirstName() {
        return this.firstName;
    }

    public void logFirstName() {
        Log.getLog().info(this.firstName);
    }
}
