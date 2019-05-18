import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class PaperReviewDriver {
        private static final String USER = "user";
        private static final String PSWD = "user";
        private static final String DATABASE = "jdbc:mysql://localhost:3306/PaperReviews?serverTimezone=UTC";

        public static String genRandomAuthorId() {
                String email = "";
                String str = "123456789abcdefghijklmnopqrstuvwxyz";
                Random rndGen = new Random();
                for (int i = 0; i < 10; ++i) {
                        email += str.charAt(rndGen.nextInt(str.length()));
                }
                email += "@";
                for (int i = 0; i < 3; ++i) {
                        email += str.charAt(rndGen.nextInt(str.length()));
                }
                email += ".";
                for (int i = 0; i < 3; ++i) {
                        email += str.charAt(rndGen.nextInt(str.length()));
                }

                return email;
        }

        public static ArrayList<ArrayList<Object>> getPaperDetailByAuthor(Connection conn, String authorId) {

                ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();

                String queryStr = "SELECT Paper.Id, Paper.Title, Paper.Abstract, "
                                + "Author.EmailAddress, Author.FirstName, Author.LastName "
                                + "FROM Paper, Author WHERE Paper.authorId = Author.EmailAddress "
                                + "AND Author.EmailAddress = ?";
                // System.out.println("query sql: " + queryStr);

                ResultSet rs = null;
                PreparedStatement stmt = null;

                try {
                        stmt = conn.prepareStatement(queryStr);
                        stmt.setString(1, authorId);
                        rs = stmt.executeQuery();

                        while (rs.next()) {
                                ArrayList<Object> record = new ArrayList<Object>();

                                record.add(rs.getInt(1));
                                record.add(rs.getString(2));
                                record.add(rs.getString(3));
                                record.add(rs.getString(4));
                                record.add(rs.getString(5));
                                record.add(rs.getString(6));

                                results.add(record);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (rs != null) {
                                rs.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (stmt != null) {
                                stmt.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return results;
        }

        public static ArrayList<ArrayList<Object>> getReviewsByPaper(Connection conn, int paperId, String state) {

                ArrayList<ArrayList<Object>> results = new ArrayList<ArrayList<Object>>();

                String queryStr = "SELECT Review.* " + "FROM Review " + "WHERE Review.paperId = ? "
                                + "AND Review.recommendation = ?";

                ResultSet rs = null;
                PreparedStatement stmt = null;

                try {
                        stmt = conn.prepareStatement(queryStr);
                        stmt.setInt(1, paperId);
                        stmt.setString(2, state);
                        rs = stmt.executeQuery();

                        while (rs.next()) {
                                ArrayList<Object> record = new ArrayList<Object>();

                                record.add(rs.getInt(1));
                                record.add(rs.getString(2));
                                record.add(rs.getString(3));
                                record.add(rs.getString(4));
                                record.add(rs.getString(5));
                                record.add(rs.getString(6));
                                record.add(rs.getInt(7));
                                record.add(rs.getString(8));

                                results.add(record);
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (rs != null) {
                                rs.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (stmt != null) {
                                stmt.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return results;
        }

        public static int getCountSubmittedPaper(Connection conn) {
                String queryStr = "SELECT count(id) " + "FROM Paper";

                ResultSet rs = null;
                PreparedStatement stmt = null;
                int count = 0;
                try {
                        stmt = conn.prepareStatement(queryStr);
                        rs = stmt.executeQuery();
                        rs.next();

                        count = rs.getInt(1);
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (rs != null) {
                                rs.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (stmt != null) {
                                stmt.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return count;
        }

        public static boolean submitPaper(Connection conn, String authorId, String firstName, String lastName,
                        String title, String papeAbstract, String fileName) {

                String queryStr = "INSERT INTO Author(EmailAddress, firstName, lastName) VALUES(?,?,?) ";

                PreparedStatement stmt = null;
                boolean status = false;
                try {
                        stmt = conn.prepareStatement(queryStr);
                        stmt.setString(1, authorId);
                        stmt.setString(2, firstName);
                        stmt.setString(3, lastName);
                        int num = stmt.executeUpdate();
                        if (num != 1) {
                                status = false;
                        } else {
                                stmt.close();
                                queryStr = "INSERT INTO Paper(title, abstract, fileName, authorId) VALUES(?,?,?,?) ";
                                stmt = conn.prepareStatement(queryStr);
                                stmt.setString(1, title);
                                stmt.setString(2, papeAbstract);
                                stmt.setString(3, fileName);
                                stmt.setString(4, authorId);
                                num = stmt.executeUpdate();
                                if (num != 1) {
                                        status = false;
                                } else {
                                        status = true;
                                }
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (stmt != null) {
                                stmt.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return status;
        }

        public static boolean removeAuthor(Connection conn, String authorId) {

                String queryStr = "DELETE FROM Author WHERE EmailAddress=? ";

                PreparedStatement stmt = null;
                boolean status = false;
                try {
                        stmt = conn.prepareStatement(queryStr);
                        stmt.setString(1, authorId);
                        int num = stmt.executeUpdate();
                        if (num != 1) {
                                status = false;
                        } else {
                                status = true;
                        }

                } catch (SQLException e) {
                        e.printStackTrace();
                }

                try {
                        if (stmt != null) {
                                stmt.close();
                        }
                } catch (SQLException e) {
                        e.printStackTrace();
                }

                return status;
        }

        public static void main(String[] args) {

                try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection conn = DriverManager.getConnection(DATABASE, USER, PSWD);

                        ArrayList<ArrayList<Object>> results = getPaperDetailByAuthor(conn, "java@sql.com");
                        System.out.println("Query 1 result: ");
                        System.out.println(results);

                        System.out.println("Query 2 result: ");
                        results = getReviewsByPaper(conn, 1, "published");
                        System.out.println(results);

                        System.out.println("Query 3 result: ");
                        int count = getCountSubmittedPaper(conn);
                        System.out.println("submitted paper count: " + count);

                        String authorId = genRandomAuthorId();
                        boolean status = submitPaper(conn, authorId, "f1", "f2", "title-x", "abstract-x", "/submit/x.pdf");
                        System.out.println("Query 4 result: ");
                        System.out.println("author " + authorId + " submit : " + status);

                        System.out.println("Query 5 result: ");
                        String foreignKey = "java@sql.com";
                        System.out.println("Deleting author " + foreignKey + " who submitted paper, will fail");
                        status = removeAuthor(conn, foreignKey);
                        System.out.println("remove first author record=java@sql.com result : " + status);

                } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
        }

}
