package pl.coderslab;

import pl.coderslab.entity.User;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY =
            "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY =
            "SELECT * FROM users where id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? where id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL_USERS_QUERY =
            "SELECT * FROM users";
    public static void main(String[] args) {
        User user =new User();

        String query =" CREATE TABLE IF NOT EXISTS users ( " +
                "id int(11) NOT NULL, " +
                "username varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
                "email varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL, " +
                "password varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;";
         String query1="ALTER TABLE users " +
                "ADD PRIMARY KEY (id), " +
                "ADD UNIQUE KEY email (email);";
         String query2="ALTER TABLE users MODIFY id int(11) NOT NULL AUTO_INCREMENT;";

        DbUtil.createTable(query);
        DbUtil.createTable(query1);
        DbUtil.createTable(query2);


    }public String hashPassword(String password){
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }


    public User create(User user){
        try(
            Connection connection = DbUtil.getConection())
        {
            PreparedStatement prst = connection.prepareStatement(CREATE_USER_QUERY,PreparedStatement.RETURN_GENERATED_KEYS);
            prst.setString(1, user.getUserName());
            prst.setString(2, user.getEmail());
            prst.setString(3,hashPassword(user.getPassword()));
            prst.executeUpdate();
            ResultSet rs = prst.getGeneratedKeys();
            if (rs.next()){
                long id = rs.getLong(1);
                System.out.println("Inserted id: " + id);
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        return null;
        }
    }
    public static User read(int userId){
        try(Connection connection= DbUtil.getConection())
        {
            PreparedStatement prst = connection.prepareStatement(READ_USER_QUERY);
            prst.setInt(1,userId);
            ResultSet rs = prst.executeQuery();
            if (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }return null;

    }
    public void update(User user){
        try (Connection con = DbUtil.getConection()){
            PreparedStatement preparedStatement = con.prepareStatement(UPDATE_USER_QUERY);
            preparedStatement.setString(1, user.getUserName());
            preparedStatement.setString(2,user.getEmail());
            preparedStatement.setString(3, this.hashPassword(user.getPassword()));
            preparedStatement.setInt(4,user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public User[] showAll(){
        try (Connection conn = DbUtil.getConection()){
           User [] users = new User[0];
            PreparedStatement preparedStatement = conn.prepareStatement(FIND_ALL_USERS_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setUserName(resultSet.getString("userName"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                addToArray(user,users);

            }
            return users;

        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }

    }public void addToArray(User user, User [] users){
        users = Arrays.copyOf(users, users.length+1);
        users[users.length-1]= user;

    }

    public void delete(int userId){
        try (Connection conn = DbUtil.getConection() ){
            PreparedStatement preparedStatement = conn.prepareStatement(DELETE_USER_QUERY);
            preparedStatement.setInt(1,userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }




}
