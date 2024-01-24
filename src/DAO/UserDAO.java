package DAO;

import JDBCHelper.DatabaseConnection;
import Model.User;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import static DAO.ServerConnection.conn;

public class UserDAO {
    private static final String ALGORITHM = "AES";
    private static final String default_key = "group01";
    private static SecretKeySpec secretKey;

    private static void prepareSecreteKey() {
        try {
            var key = default_key.getBytes(StandardCharsets.UTF_8);
            var sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptPassword(String password) {
        try {
            prepareSecreteKey();
            var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decryptPassword(String encrypted_password) {
        try {
            prepareSecreteKey();
            var cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(encrypted_password)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<User> selectAll() {
        var list = new ArrayList<User>();
//        var query = "select * from users";
//        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(
//                        new User(
//                                resultSet.getString("user_id"),
//                                resultSet.getString("full_name"),
//                                resultSet.getString("password_hash"),
//                                resultSet.getBoolean("is_host")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("selectAll");
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String userString = conn.read().trim();
                User tmp = mapFromString(userString);
                list.add(tmp);
            }
            return list;

        }
        return list;
    }

    public static User selectByAccount(String username, String password) {
        var user = new User();
//        var query = "select * from users where user_id=? and password_hash=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, username);
//            ps.setString(2, password);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                user.setUser_id(resultSet.getString("user_id"));
//                user.setFull_name(resultSet.getString("full_name"));
//                user.setPassword(resultSet.getString("password_hash"));
//                user.setHost(resultSet.getBoolean("is_host"));
//                return user;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("selectByAccount");
        conn.write(username);
        conn.write(password);
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            User user_tmp = mapFromString(res);
            return user_tmp;
        }
        return null;
    }

    public static User selectByID(String userID) {
        var user = new User();
//        var query = "select * from users where user_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, userID);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                user.setUser_id(resultSet.getString("user_id"));
//                user.setFull_name(resultSet.getString("full_name"));
//                user.setPassword(resultSet.getString("password_hash"));
//                user.setHost(resultSet.getBoolean("is_host"));
//                return user;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("selectByID");
        conn.write(userID);
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            User user_tmp = mapFromString(res);
            return user_tmp;
        }
        return null;
    }

    public static boolean insert(User user) {
//        var query = "insert into users values(?,?,?,?)";
//        var password_encrypted = encryptPassword(user.getPassword());
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user.getUser_id());
//            ps.setString(2, user.getFull_name());
//            ps.setString(3, password_encrypted);
//            ps.setBoolean(4, user.isHost());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("insert");
        conn.write(String.valueOf(user));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean update(User user) {
//        var query = "update users set full_name = ?, password_hash = ?, is_host = ? where user_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user.getFull_name());
//            ps.setString(2, user.getPassword());
//            ps.setBoolean(3, user.isHost());
//            ps.setString(4, user.getUser_id());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("update");
        conn.write(String.valueOf(user));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean delete(String user_id) {
//        var query = "delete from users where user_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user_id);
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /user");
        conn.write("delete");
        conn.write(String.valueOf(user_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String test = "test";
        String encrypt = encryptPassword(test);
        System.out.println(encrypt);
        String decrypt = decryptPassword(encrypt);
        System.out.println(decrypt);

//        List<User> user = UserDAO.selectAll();
//        System.out.println(user.get(0).getFull_name());
//        User us = UserDAO.selectByAccount("19h1010020","1231242145sxvsg232");
//        System.out.println(us != null ? us.getFull_name() : "Not found");
//        us = UserDAO.selectByUsername("19h1010020");
//        System.out.println(us != null ? us.getPassword() : "Not found");
//        User user2 = new User("123456","test1","testpass",true);
//        System.out.println(UserDAO.insert(user2));
//        user2.setFull_name("Nguyen Van A");
//        System.out.println(UserDAO.update(user2));
//        System.out.println(UserDAO.delete("19h1010020") ? "Thanh cong" : "That bai");

    }

    public static User mapFromString(String userString) {
        // Split the userString into different fields
        String[] fields = userString.split(",");

        // Extract the values from the fields
        String userId = getValueFromField(fields[0]);
        String fullName = getValueFromField(fields[1]);
        String passwordHash = getValueFromField(fields[2]);
        boolean isHost = Boolean.parseBoolean(getValueFromField(fields[3]));

        // Create and return a new User object
        return new User(userId, fullName, passwordHash, isHost);
    }

    private static String getValueFromField(String field) {
        // Remove the field name and any leading/trailing white spaces
        String value = field.substring(field.indexOf('=') + 1).trim();

        if (value.endsWith("}")) {
            value = value.substring(0, value.length()-1);
        }
        // Remove single quotes ('') if present
        if (value.startsWith("'") && value.endsWith("'")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }

    public static List<User> mapFromListString(String input) {
        List<User> userList = new ArrayList<>();

        String trimmedInput = input.trim();
        String usersString = trimmedInput.substring(1, trimmedInput.length() - 1);
        String[] userStrings = usersString.split(",\\s*(?=User\\{)");

        for (String userString : userStrings) {
            String[] userAttributes = userString.split(",\\s*");

            User user = new User();
            for (String attribute : userAttributes) {
                String[] parts = attribute.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim().replaceAll("'","");
                switch (key) {
                    case "user_id":
                        user.setUserId(value);
                        break;
                    case "full_name":
                        user.setFullName(value);
                        break;
                    case "password_hash":
                        user.setPasswordHash(value);
                        break;
                    case "is_host":
                        user.setHost(Boolean.parseBoolean(value));
                        break;
                }
            }
            userList.add(user);
        }
        return userList;
    }


}
