package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Room;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DAO.ServerConnection.conn;

public class RoomDAO {
    public static List<Room> selectAll() {
        var list = new ArrayList<Room>();
//        var query = "select * from rooms";
//        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(
//                        new Room(
//                                resultSet.getLong("room_id"),
//                                resultSet.getLong("exam_id"),
//                                resultSet.getString("title"),
//                                resultSet.getInt("time_limit"),
//                                resultSet.getString("password"),
//                                resultSet.getBoolean("is_available")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return list;
        conn.write("GET /room");
        conn.write("selectAll");
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String roomString = conn.read().trim();
                Room tmp = mapFromString(roomString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static Room selectVerifiedRoom(String roomID, String password) {
        var room = new Room();
//        var query = "select * from rooms where room_id=? and password=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, roomID);
//            ps.setString(2, password);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                room.setRoom_id(resultSet.getLong("room_id"));
//                room.setExam_id(resultSet.getLong("exam_id"));
//                room.setTitle(resultSet.getString("title"));
//                room.setTime_limit(resultSet.getInt("time_limit"));
//                room.setPassword(resultSet.getString("password"));
//                room.setAvailable(resultSet.getBoolean("is_available"));
//                return room;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /room");
        conn.write("selectVerifiedRoom");
        conn.write(roomID);
        conn.write(password);
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            Room room_tmp = mapFromString(res);
            return room_tmp;
        }
        return null;
    }

    public static Room selectByID(long roomID) {
        var room = new Room();
//        var query = "select * from rooms where room_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, roomID);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                room.setRoom_id(resultSet.getLong("room_id"));
//                room.setExam_id(resultSet.getLong("exam_id"));
//                room.setTitle(resultSet.getString("title"));
//                room.setTime_limit(resultSet.getInt("time_limit"));
//                room.setPassword(resultSet.getString("password"));
//                room.setAvailable(resultSet.getBoolean("is_available"));
//                return room;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        conn.write("GET /room");
        conn.write("selectByID");
        conn.write(String.valueOf(roomID));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            Room room_tmp = mapFromString(res);
            return room_tmp;
        }
        return null;
    }

    public static boolean insert(Room room) {
//        var query = "insert into rooms(exam_id,title,time_limit,password,is_available) values(?,?,?,?,?)";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, room.getExam_id());
//            ps.setString(2, room.getTitle());
//            ps.setInt(3, room.getTime_limit());
//            ps.setString(4, room.getPassword());
//            ps.setBoolean(5, room.isAvailable());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /room");
        conn.write("insert");
        conn.write(String.valueOf(room));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean update(Room room) {
//        var query = "update rooms set exam_id = ?, title = ?, time_limit = ?, password = ?, is_available = ? where room_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, room.getExam_id());
//            ps.setString(2, room.getTitle());
//            ps.setInt(3, room.getTime_limit());
//            ps.setString(4, room.getPassword());
//            ps.setBoolean(5, room.isAvailable());
//            ps.setLong(6, room.getRoom_id());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /room");
        conn.write("update");
        conn.write(String.valueOf(room));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean delete(long room_id) {
//        var query = "delete from rooms where room_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, room_id);
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /room");
        conn.write("delete");
        conn.write(String.valueOf(room_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Room> rooms = RoomDAO.selectAll();
        System.out.println(rooms.get(0).getTitle());
        Room room = RoomDAO.selectByID(1);
        System.out.println(
                (room != null ? room.getTime_limit() : -1)
                        + " "
                        + (room != null ? room.getTitle() : "empty")
        );
        assert room != null;
        room.setPassword("123456");
        System.out.println(RoomDAO.update(room));
    }

    public static Room mapFromString(String roomString) {
        // Split the roomString into different fields
        String[] fields = roomString.split(",");

        // Extract the values from the fields
        long roomId = Long.parseLong(getValueFromField(fields[0]));
        Long examId = Long.parseLong(getValueFromField(fields[1]));
        String title = getValueFromField(fields[2]);
        int timeLimit = Integer.parseInt(getValueFromField(fields[3]));
        String password = getValueFromField(fields[4]);
        boolean isAvailable = Boolean.parseBoolean(getValueFromField(fields[5]));

        // Create and return a new Room object
        return new Room(roomId, examId, title, timeLimit, password, isAvailable);
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
}
