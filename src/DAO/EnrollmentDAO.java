package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Enrollment;
import Model.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DAO.ServerConnection.conn;

public class EnrollmentDAO {
    public static List<Enrollment> selectAll() {
        var list = new ArrayList<Enrollment>();
//        var query = "select * from enrollments";
//        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(
//                        new Enrollment(
//                                resultSet.getLong("enrollment_id"),
//                                resultSet.getString("user_id"),
//                                resultSet.getLong("room_id"),
//                                resultSet.getDouble("score")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
        conn.write("GET /enrollment");
        conn.write("selectAll");
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String roomString = conn.read().trim();
                Enrollment tmp = mapFromString(roomString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static Enrollment selectByID(long enrollment_id) {
//        var enrollment = new Enrollment();
//        var query = "select * from enrollments where enrollment_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollment_id);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                enrollment.setEnrollment_id(resultSet.getLong("enrollment_id"));
//                enrollment.setUser_id(resultSet.getString("user_id"));
//                enrollment.setRoom_id(resultSet.getLong("room_id"));
//                enrollment.setScore(resultSet.getDouble("score"));
//                return enrollment;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
        conn.write("GET /enrollment");
        conn.write("selectByID");
        conn.write(String.valueOf(enrollment_id));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            Enrollment enroll_tmp = mapFromString(res);
            return enroll_tmp;
        }
        return null;
    }

    public static List<Enrollment> selectByUserID(String user_id) {
        var list = new ArrayList<Enrollment>();
//        var query = "select * from enrollments where user_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user_id);
//            var resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                list.add(
//                        new Enrollment(
//                                resultSet.getLong("enrollment_id"),
//                                resultSet.getString("user_id"),
//                                resultSet.getLong("room_id"),
//                                resultSet.getDouble("score")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
        conn.write("GET /enrollment");
        conn.write("selectByUserID");
        conn.write(user_id);
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String roomString = conn.read().trim();
                Enrollment tmp = mapFromString(roomString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static long selectIDByModel(Enrollment enrollment) {
//        var user_id = enrollment.getUser_id();
//        var room_id = enrollment.getRoom_id();
//        var score = enrollment.getScore();
//        var query = "select enrollment_id from enrollments where user_id= ? and room_id = ? and score = ? order by enrollment_id desc LIMIT 1";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user_id);
//            ps.setLong(2, room_id);
//            ps.setDouble(3, score);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                return resultSet.getLong("enrollment_id");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /enrollment");
        conn.write("selectIDByModel");
        conn.write(String.valueOf(enrollment));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            return Long.parseLong(res);
        }
        return -1;
    }

    public static boolean insert(Enrollment enrollment) {
//        var query = "insert into enrollments(user_id,room_id,score) values(?,?,?)";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, enrollment.getUser_id());
//            ps.setLong(2, enrollment.getRoom_id());
//            ps.setDouble(3, enrollment.getScore());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /enrollment");
        conn.write("insert");
        conn.write(String.valueOf(enrollment));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean update(Enrollment enrollment) {
//        var query = "update enrollments set user_id = ?, room_id = ?, score = ?  where enrollment_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, enrollment.getUser_id());
//            ps.setLong(2, enrollment.getRoom_id());
//            ps.setDouble(3, enrollment.getScore());
//            ps.setLong(4, enrollment.getEnrollment_id());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /enrollment");
        conn.write("update");
        conn.write(String.valueOf(enrollment));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean delete(long enrollment_id) {
//        var query = "delete from enrollments where enrollment_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollment_id);
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /enrollment");
        conn.write("delete");
        conn.write(String.valueOf(enrollment_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Enrollment> enrollments = EnrollmentDAO.selectAll();
        System.out.println(enrollments.get(0).getScore());
        Enrollment enrollment = EnrollmentDAO.selectByID(1);
        System.out.println(
                (enrollment != null ? enrollment.getRoom_id() : -1)
                        + " "
                        + (enrollment != null ? enrollment.getUser_id() : "empty")
        );
//        Enrollment enrollment1 = new Enrollment("19h1010020", 1, 8.8);
//        System.out.println(EnrollmentDAO.insert(enrollment1));
//        assert enrollment != null;
//        enrollment.setScore(9.5);
//        System.out.println(EnrollmentDAO.update(enrollment));
//        System.out.println(EnrollmentDAO.delete(2));
    }
    public static Enrollment mapFromString(String enrollmentString) {

        // Split the enrollmentString into different fields
        String[] fields = enrollmentString.split(",");

        // Extract the values from the fields
        long enrollmentId = Long.parseLong(getValueFromField(fields[0]));
        String userId = getValueFromField(fields[1]);
        long roomId = Long.parseLong(getValueFromField(fields[2]));
        double score = Double.parseDouble(getValueFromField(fields[3]));

        // Create and return a new Enrollment object
        return new Enrollment(enrollmentId, userId, roomId, score);
    }

    private static String getValueFromField(String field) {
        // Remove the field name and any leading/trailing white spaces
        return field.substring(field.indexOf('=') + 1).trim();
    }
}
