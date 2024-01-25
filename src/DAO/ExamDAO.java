package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Exam;
import Model.Room;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static DAO.ServerConnection.conn;

public class ExamDAO {
    public static List<Exam> selectAll() {
        var list = new ArrayList<Exam>();
//        var query = "select * from exams";
//        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(
//                        new Exam(
//                                resultSet.getLong("exam_id"),
//                                resultSet.getString("subject"),
//                                resultSet.getInt("total_question"),
//                                resultSet.getInt("total_score"),
//                                resultSet.getDouble("score_per_question")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /exam");
        conn.write("selectAll");
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String roomString = conn.read().trim();
                Exam tmp = mapFromString(roomString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static Exam selectByID(long examID) {
//        var exam = new Exam();
//        var query = "select * from exams where exam_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, examID);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                exam.setExam_id(resultSet.getLong("exam_id"));
//                exam.setSubject(resultSet.getString("subject"));
//                exam.setTotal_question(resultSet.getInt("total_question"));
//                exam.setTotal_score(resultSet.getInt("total_score"));
//                exam.setScore_per_question(resultSet.getDouble("score_per_question"));
//                return exam;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /exam");
        conn.write("selectByID");
        conn.write(String.valueOf(examID));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            Exam exam_tmp = mapFromString(res);
            return exam_tmp;
        }
        return null;
    }

    public static boolean insert(Exam exam) {
//        var query = "insert into exams(subject,total_question,total_score,score_per_question) values(?,?,?,?)";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, exam.getSubject());
//            ps.setInt(2, exam.getTotal_question());
//            ps.setInt(3, exam.getTotal_score());
//            ps.setDouble(4, exam.getScore_per_question());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /exam");
        conn.write("insert");
        conn.write(String.valueOf(exam));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean update(Exam exam) {
//        var query = "update exams set subject = ?, total_question = ?, total_score = ?, score_per_question = ? where exam_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, exam.getSubject());
//            ps.setInt(2, exam.getTotal_question());
//            ps.setInt(3, exam.getTotal_score());
//            ps.setDouble(4, exam.getScore_per_question());
//            ps.setLong(5, exam.getExam_id());
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /exam");
        conn.write("update");
        conn.write(String.valueOf(exam));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean delete(long exam_id) {
//        var query = "delete from exams where exam_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, exam_id);
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /exam");
        conn.write("delete");
        conn.write(String.valueOf(exam_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List<Exam> exams = ExamDAO.selectAll();
        System.out.println(exams.get(0).getExam_id());
        Exam exam = ExamDAO.selectByID(1);
        System.out.println(
                (exam != null ? exam.getScore_per_question() : -1)
                        + " "
                        + (exam != null ? exam.getSubject() : "empty")
        );
//        Exam exam1 = new Exam("Toán", 20 , 10, 10.0/20.0);
//        System.out.println("Insert: " + ExamDAO.insert(exam1));
//        exam.setTotal_question(40);
//        System.out.println("Update: " + ExamDAO.update(exam));
//        System.out.println("delete: " + ExamDAO.delete(1));
    }
    public static Exam mapFromString(String examString) {

        // Split the examString into different fields
        String[] fields = examString.split(",");

        // Extract the values from the fields
        long examId = Long.parseLong(getValueFromField(fields[0]));
        String subject = getValueFromField(fields[1]);
        int totalQuestion = Integer.parseInt(getValueFromField(fields[2]));
        int totalScore = Integer.parseInt(getValueFromField(fields[3]));
        double scorePerQuestion = Double.parseDouble(getValueFromField(fields[4]));

        // Create and return a new Exam object
        return new Exam(examId, subject, totalQuestion, totalScore, scorePerQuestion);
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
