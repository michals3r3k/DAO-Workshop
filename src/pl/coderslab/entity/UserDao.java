package pl.coderslab.entity;

import pl.coderslab.DbUtil;
import pl.coderslab.Input;
import pl.coderslab.Main;
import pl.coderslab.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {
    private static String ADD_USER="INSERT INTO users(email, username, password) VALUES(?, ?, ?)";
    private static String DELETE_USER_BY_ID="DELETE FROM users WHERE id = ?";
    private static String SELECT_USER_BY_ID="SELECT * FROM users WHERE id = ?";
    private static String SELECT_ALL_USERS="SELECT * FROM users";
    private static String UPDATE_BY_ID="UPDATE users SET email=?, username=?, password=? WHERE id=?";


    public User create(User user){
        if(!isExist(user)) {
            try (Connection conn = DbUtil.getConnection()) {
                PreparedStatement preStmt = conn.prepareStatement(ADD_USER, Statement.RETURN_GENERATED_KEYS);
                preStmt.setString(1, user.getEmail());
                preStmt.setString(2, user.getUsername());
                preStmt.setString(3, user.getPassword());
                preStmt.executeUpdate();
                ResultSet resultSet = preStmt.getGeneratedKeys();
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
                System.out.println("New User has been created");
                return user;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            System.out.println("User with this email already exists");
        }
        return null;
    }


    public User read(int userId){
        User user=null;
        try (Connection conn = DbUtil.getConnection()){
            PreparedStatement preStmt= conn.prepareStatement(SELECT_USER_BY_ID, Statement.RETURN_GENERATED_KEYS);
            preStmt.setInt(1, userId);
            ResultSet resultSet= preStmt.executeQuery();
            if(resultSet.next()){
                user=new User(resultSet.getString("email"), resultSet.getString("username"), resultSet.getString("password"));
                user.setId(userId);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void update(User user){
        if(!isExist(user)){
            if(Input.getInputString("User not exist, create new? [y/n]").equals("y")) {
                create(user);
            }
        }
        else{
            int userId=user.getId();
            String email;
            do {
                email = Input.getInputString("Set Email:");
                if(!Main.isEmailValid(email)){
                    System.out.println("Email not valid!");
                }
            }while(!Main.isEmailValid(email));
            String username= Input.getInputString("Type username:");
            String password= Input.getInputString("Type password:");
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            DbUtil.updateById(UPDATE_BY_ID, userId, user.getEmail(), user.getUsername(), user.getPassword());
        }
    }

    public void delete(int id){
        DbUtil.deleteById(DELETE_USER_BY_ID, id);
    }

    public User[] findAll(){
        User[] array=new User[0];
        try(Connection conn = DbUtil.getConnection()){
            PreparedStatement preStmt= conn.prepareStatement(SELECT_ALL_USERS);
            ResultSet resultSet = preStmt.executeQuery();
            while (resultSet.next()){
                array=append(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("email"),
                        resultSet.getString("username"),
                        resultSet.getString("password")
                ), array);
            }
            return array;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return array;
    }

    private User[] append(User user, User[] array){
        array= Arrays.copyOf(array, array.length+1);
        array[array.length-1]=user;
        return array;
    }

    public boolean isExist(User user){
        return read(user.getId())!=null;
    }
}
