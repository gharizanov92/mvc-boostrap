package bg.foosoft.project.dao;

import bg.foosoft.project.model.User;
import bg.foosoft.project.util.PasswordHash;
import bg.foosoft.project.util.UserInfo;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongodb.Mongo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Kayne on 14-1-13.
 */
public class UserDAO extends BasicDAO<User, String> {

    @Autowired
    private UserInfo userInfo;

    @Autowired
    public UserDAO(Mongo mongo, Morphia morphia, String dbName) {
        super(mongo, morphia, dbName);
    }

    public List<User> findAll(){
        return ds.find(User.class).asList();
    }

    public User findUserByUsername(String username){
        return ds.find(User.class).field("username").equal(username).get();
    }

    public User findUserByID(String id){
        return ds.get(User.class, id);
    }

    public boolean registerUser(User aUser){

        if(findUserByUsername(aUser.getUsername()) != null){
            return false;
        }

        try {
            aUser.setPassword(PasswordHash.generateStorngPasswordHash(aUser.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        aUser.setId(new ObjectId().toString());
        ds.save(aUser);
        return true;
    }

    public void changePassword(String userID, String password) {
        try {
            password = PasswordHash.generateStorngPasswordHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        Query query = ds.createQuery(User.class).field("_id").equal(userID);
        UpdateOperations<User> operations = ds.createUpdateOperations(User.class).set("password", password);
        ds.update(query, operations);
    }


    public void removeUser(String id) {
        ds.delete(User.class, id);
    }

    public byte[] LoadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }
}
