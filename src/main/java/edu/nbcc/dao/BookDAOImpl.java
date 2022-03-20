package edu.nbcc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.nbcc.model.Book;

public class BookDAOImpl implements BookDAO {
	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/java_ee";
	private static final String USERNAME = "dev";
	private static final String PASSWORD = "dev1234";
	
	private static final String DELETE = "DELETE FROM java_ee.book WHERE id = ?";
	private static final String INSERT = "INSERT INTO java_ee.book (name, term, price) VALUES (?, ?, ?)";
	private static final String UPDATE = "UPDATE java_ee.book SET name = ?, price = ?, term = ? WHERE id = ?";
	private static final String FIND_ALL = "SELECT * FROM java_ee.book ORDER BY id";
	private static final String FIND_BY_NAME = "SELECT * FROM java_ee.book WHERE name = ?";
	private static final String FIND_BY_ID = "SELECT * FROM java_ee.book WHERE id = ?";
	
	/**
	 * Get connection
	 * @return
	 */
	private Connection getConnection() {
		try {
			Class.forName(DRIVER);
			return DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			return null;
		}
	}
	
	
	private static void close(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	private static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	@Override
	public int delete(int id) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			statement = conn.prepareStatement(DELETE);
			statement.setInt(1, id);
			return statement.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return -1;
		} finally {
			close(statement);
			close(conn);
		}
	}

	@Override
	public int insert(Book book) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			statement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, book.getName());
			statement.setInt(2, book.getTerm());
			statement.setDouble(3, book.getPrice());
			
			int result = statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			
			if (rs.next()) {
				book.setId(rs.getInt(1));
			}
			return result;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return -1;
		} finally {
			close(statement);
			close(conn);
		}
	}

	@Override
	public int update(Book book) {
		Connection conn = null;
		PreparedStatement statement = null;
		
		try {
			conn = getConnection();
			statement = conn.prepareStatement(UPDATE);
			
			statement.setString(1, book.getName());
			statement.setDouble(2, book.getPrice());
			statement.setInt(3, book.getTerm());
			statement.setInt(4,  book.getId());
			
			return statement.executeUpdate();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return -1;
		} finally {
			close(statement);
			close(conn);
		}
	}

	@Override
	public List<Book> findAll() {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			statement = conn.prepareStatement(FIND_ALL);
			
			ResultSet rs = statement.executeQuery();
			
			List<Book> books = new ArrayList<Book>();
			while (rs.next()) {
				books.add(createBook(rs));
			}
			return books;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(statement);
			close(conn);
		}
	}

	@Override
	public Book findByName(String name) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			statement = conn.prepareStatement(FIND_BY_NAME);
			
			statement.setString(1, name);
			
			ResultSet rs = statement.executeQuery();
			
			Book book = new Book();
			if (rs.next())
				book = createBook(rs);
			
			return book;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(statement);
			close(conn);
		}
	}

	@Override
	public Book findById(int id) {
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = getConnection();
			statement = conn.prepareStatement(FIND_BY_ID);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			Book book = new Book();
			if (rs.next())
				book = createBook(rs);
			
			return book;
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return null;
		} finally {
			close(statement);
			close(conn);
		}
	}
	
	private static Book createBook(ResultSet rs) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("id"));
        book.setName(rs.getString("name"));
        book.setPrice(rs.getDouble("price"));
        book.setTerm(rs.getInt("term"));
        return book;
    }
}
