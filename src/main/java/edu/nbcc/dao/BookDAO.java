/**
 * 
 */
package edu.nbcc.dao;

import java.util.List;

import edu.nbcc.model.Book;

/**
 * @author Liam McManus
 *
 */
public interface BookDAO {
	
	/**
	 * Delete book by id
	 * @param id
	 * @return
	 */
	public int delete(int id);
	
	/**
	 * Insert book
	 * @param book
	 * @return
	 */
	public int insert(Book book);
	
	/**
	 * Update book
	 * @param book
	 * @return
	 */
	public int update(Book book);
	
	/**
	 * Find all books
	 * @return
	 */
	public List<Book> findAll();
	
	/**
	 * Get the book by name
	 * @param name
	 * @return
	 */
	public Book findByName(String name);
	
	/**
	 * Find book by id
	 * @param id
	 * @return
	 */
	public Book findById(int id);
}
