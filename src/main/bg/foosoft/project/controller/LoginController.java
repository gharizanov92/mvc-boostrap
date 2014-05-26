package bg.foosoft.project.controller;

import bg.foosoft.project.dao.UserDAO;
import bg.foosoft.project.model.User;
import bg.foosoft.project.util.PasswordHash;
import bg.foosoft.project.util.Urls;
import bg.foosoft.project.util.UserInfo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by Kayne on 25.5.2014 г..
 */
@Controller
@Scope("request")
@RequestMapping(value = Urls.BASE_PATH)
public class LoginController {

    @Autowired
    private UserInfo mUserInfo;

    @Autowired
    public UserDAO mDao;

    @RequestMapping(value = Urls.LOGIN_PATH, method = RequestMethod.GET)
    public String test(){
        if(mUserInfo.getId() != null){
            return "home";
        } else {
            //this.testAddUser();
            return "index";
        }
    }

    @RequestMapping(value = Urls.LOGIN_PATH, method = RequestMethod.POST)
    public String authenticateUser(HttpSession aSession, ModelMap aMap, @ModelAttribute("SpringWeb")User aUser){
        try{
            for(User user : mDao.findAll()){
                if(user.getUsername().equals(aUser.getUsername()) &&
                        PasswordHash.validatePassword(aUser.getPassword(), user.getPassword())){
                    aSession.setAttribute("uid", user.getUsername());
                    aMap.addAttribute("user", user.getUsername());
                    mUserInfo.setId(user.getUsername());
                    mUserInfo.setUserEntity(user);
                    return Urls.HOME_PAGE;
                }
            }
            return Urls.LOGIN_PAGE;
        } catch(NoSuchAlgorithmException ex){
            ex.printStackTrace();
        } catch (InvalidKeySpecException ex){
            ex.printStackTrace();
        }
        return null;
    }


    public UserDAO getmDao() {
        return mDao;
    }

    public void setmDao(UserDAO mDao) {
        this.mDao = mDao;
    }


}
