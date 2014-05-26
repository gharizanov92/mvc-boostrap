package bg.foosoft.project.model;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

import java.util.List;

/**
 * Created by Kayne on 14-1-13.
 */
@Entity("users")
public class User implements Comparable<User> {

    @Id
    private String mId;

    @Property("username")
    private String mUsername;

    @Property("password")
    private String mPassword;

    public String getId() {
        return mId;
    }

    public void setId(String aId) {
        mId = aId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String aUsername) {
        mUsername = aUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String aPassword) {
        mPassword = aPassword;
    }

    @Override
    public int compareTo(User other) {
        //TODO: implement
        return 0;
    }
}
