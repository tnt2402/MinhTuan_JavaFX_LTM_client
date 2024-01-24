package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Enrollment;
import Model.EnrollmentAnswer;
import Model.QuestionAnswer;
import Model.Room;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import static DAO.ServerConnection.conn;

public class EnrollmentAnswerDAO {
    public static List<EnrollmentAnswer> selectAll() {
        var list = new ArrayList<EnrollmentAnswer>();
//        var query = "select * from enrollment_answers";
//        try (var statement = DatabaseConnection.getConnectionInstance().createStatement()) {
//            var resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                list.add(
//                        new EnrollmentAnswer(
//                                resultSet.getLong("enrollment_answer_id"),
//                                resultSet.getLong("enrollment_id"),
//                                resultSet.getLong("question_id"),
//                                resultSet.getLong("question_answer_id")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        conn.write("GET /ea");
        conn.write("selectAll");
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String roomString = conn.read().trim();
                EnrollmentAnswer tmp = mapFromString(roomString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static EnrollmentAnswer selectByID(long enrollment_answer_id) {
//        var enrollmentAnswer = new EnrollmentAnswer();
//        var query = "select * from enrollment_answers where enrollment_answer_id=?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollment_answer_id);
//            var resultSet = ps.executeQuery();
//            if (resultSet.next()) {
//                enrollmentAnswer.setEnrollment_answer_id(resultSet.getLong("enrollment_answer_id"));
//                enrollmentAnswer.setEnrollment_id(resultSet.getLong("enrollment_id"));
//                enrollmentAnswer.setQuestion_id(resultSet.getLong("question_id"));
//                enrollmentAnswer.setQuestion_answer_id(resultSet.getLong("question_answer_id"));
//                return enrollmentAnswer;
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return null;
        conn.write("GET /ea");
        conn.write("selectByID");
        conn.write(String.valueOf(enrollment_answer_id));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            EnrollmentAnswer room_tmp = mapFromString(res);
            return room_tmp;
        }
        return null;
    }

    public static boolean insert(EnrollmentAnswer enrollmentAnswer) {
//        var query = "insert into enrollment_answers(enrollment_id,question_id,question_answer_id) values(?,?,?)";
//        var question_id = enrollmentAnswer.getQuestion_id();
//        var question_answer_id = enrollmentAnswer.getQuestion_answer_id();
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollmentAnswer.getEnrollment_id());
//            if (question_id == null) {
//                ps.setNull(2, Types.BIGINT);
//            } else {
//                ps.setLong(2, question_id);
//            }
//            if (question_answer_id == null) {
//                ps.setNull(3, Types.BIGINT);
//            } else {
//                ps.setLong(3, question_answer_id);
//            }
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /ea");
        conn.write("insert");
        conn.write(String.valueOf(enrollmentAnswer));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean update(EnrollmentAnswer enrollmentAnswer) {
//        var query = "update enrollment_answers set enrollment_id = ?, question_id = ?, question_answer_id = ?  where enrollment_answer_id = ?";
//        var question_id = enrollmentAnswer.getQuestion_id();
//        var question_answer_id = enrollmentAnswer.getQuestion_answer_id();
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollmentAnswer.getEnrollment_id());
//            ps.setLong(2, enrollmentAnswer.getQuestion_id());
//            if (question_id == null) {
//                ps.setNull(2, Types.BIGINT);
//            } else {
//                ps.setLong(2, question_id);
//            }
//            if (question_answer_id == null) {
//                ps.setNull(3, Types.BIGINT);
//            } else {
//                ps.setLong(3, question_answer_id);
//            }
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /ea");
        conn.write("update");
        conn.write(String.valueOf(enrollmentAnswer));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static boolean delete(long enrollment_answer_id) {
//        var query = "delete from enrollment_answers where enrollment_answer_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, enrollment_answer_id);
//            var count = ps.executeUpdate();
//            return count != 0;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /ea");
        conn.write("delete");
        conn.write(String.valueOf(enrollment_answer_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
//        List<EnrollmentAnswer> enrollmentAnswers = EnrollmentAnswerDAO.selectAll();
//        System.out.println(enrollmentAnswers.get(0).getQuestion_answer_id());
//        EnrollmentAnswer enrollmentAnswer = EnrollmentAnswerDAO.selectByID(1);
//        System.out.println(
//                (enrollmentAnswer != null ? enrollmentAnswer.getEnrollment_answer_id() : -1)
//                        + " "
//                        + (enrollmentAnswer != null ? enrollmentAnswer.getEnrollment_id() : "empty")
//        );
//        EnrollmentAnswer enrollmentAnswer1 = new EnrollmentAnswer(1,2,4);
//        System.out.println(EnrollmentAnswerDAO.insert(enrollmentAnswer1));
//        enrollmentAnswer.setQuestion_answer_id(5);
//        System.out.println(EnrollmentAnswerDAO.update(enrollmentAnswer));
//        System.out.println(EnrollmentDAO.delete(1));
    }
    public static EnrollmentAnswer mapFromString(String enrollmentAnswerString) {
        // Split the enrollmentAnswerString into different fields
        String[] fields = enrollmentAnswerString.split(",");

        // Extract the values from the fields
        long enrollmentAnswerId = Long.parseLong(getValueFromField(fields[0]));
        long enrollmentId = Long.parseLong(getValueFromField(fields[1]));
        Long questionId = Long.parseLong(getValueFromField(fields[2]));
        Long questionAnswerId = Long.parseLong(getValueFromField(fields[3]));

        // Create and return a new EnrollmentAnswer object
        return new EnrollmentAnswer(enrollmentAnswerId, enrollmentId, questionId, questionAnswerId);
    }

    private static String getValueFromField(String field) {
        // Remove the field name and any leading/trailing white spaces
        return field.substring(field.indexOf('=') + 1).trim();
    }
}
