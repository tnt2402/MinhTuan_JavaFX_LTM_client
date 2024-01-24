package DAO;

import JDBCHelper.DatabaseConnection;
import Model.Exam;
import Model.Question;
import Model.QuestionAnswer;
import Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static DAO.ServerConnection.conn;

public class TakeExamDAO {

    public static boolean verifyUserAlreadyTakenExam(String user_id, long room_id) {
//        var query = "select user_id from enrollments where user_id = ? and room_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setString(1, user_id);
//            ps.setLong(2, room_id);
//            var resultSet = ps.executeQuery();
//            return resultSet.next();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /takeexam");
        conn.write("verifyUserAlreadyTakenExam");
        conn.write(user_id);
        conn.write(String.valueOf(room_id));
        String res = conn.read().trim();
        if (!res.equals("true")) {
            return false;
        }
        return true;
    }

    public static Exam selectExamOfRoom(long room_id) {
//        var exam = new Exam();
//        var query = "select exams.* from rooms inner join exams on rooms.exam_id = exams.exam_id where room_id = ?";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, room_id);
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
        conn.write("GET /takeexam");
        conn.write("selectExamOfRoom");
        conn.write(String.valueOf(room_id));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
            Exam exam_tmp = mapFromString(res);
            return exam_tmp;
        }
        return null;
    }

    public static List<Question> selectQuestionOfExam(long exam_id) {
        var list = new ArrayList<Question>();
//        var query = "select questions.* from questions inner join exams on questions.exam_id = exams.exam_id where exams.exam_id= ? order by questions.level asc";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, exam_id);
//            var resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                list.add(
//                        new Question(
//                                resultSet.getLong("question_id"),
//                                resultSet.getLong("exam_id"),
//                                resultSet.getInt("level"),
//                                resultSet.getString("content")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /takeexam");
        conn.write("selectQuestionOfExam");
        conn.write(String.valueOf(exam_id));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String userString = conn.read().trim();
                Question tmp = QuestionDAO.mapFromString(userString);
                list.add(tmp);
            }
        }
        return list;
    }

    public static List<QuestionAnswer> selectQuestionAnswerOfQuestion(long question_id) {
        var list = new ArrayList<QuestionAnswer>();
//        var query = "select question_answers.* from question_answers inner join questions on question_answers.question_id = questions.question_id where question_answers.question_id = ? order by rand()";
//        try (var ps = DatabaseConnection.getConnectionInstance().prepareStatement(query)) {
//            ps.setLong(1, question_id);
//            var resultSet = ps.executeQuery();
//            while (resultSet.next()) {
//                list.add(
//                        new QuestionAnswer(
//                                resultSet.getLong("question_answer_id"),
//                                resultSet.getLong("question_id"),
//                                resultSet.getString("content"),
//                                resultSet.getBoolean("is_correct")
//                        )
//                );
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        conn.write("GET /takeexam");
        conn.write("selectQuestionAnswerOfQuestion");
        conn.write(String.valueOf(question_id));
        String res = conn.read().trim();
        if (!res.equals("INVALID")) {
//            list = (ArrayList<User>) mapFromListString(res);
            for (int i=0; i < Integer.parseInt(res); i++) {
                String userString = conn.read().trim();
                QuestionAnswer tmp = QuestionAnswerDAO.mapFromString(userString);
                list.add(tmp);
            }
        }
        return list;
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
        return field.substring(field.indexOf('=') + 1).trim();
    }
}
