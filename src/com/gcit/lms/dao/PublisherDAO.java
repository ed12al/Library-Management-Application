package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO{

	public PublisherDAO(Connection conn) {
		super(conn);
	}
	
	public void addPublisher(Publisher publisher) throws SQLException {
		save("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)", 
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}
	
	public Integer addPublisherWithID(Publisher publisher) throws SQLException {
		return saveWithID("insert into tbl_publisher (publisherName, publisherAddress, publisherPhone) values (?, ?, ?)", 
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone()});
	}
	
	public void updatePublisher(Publisher publisher) throws SQLException {
		save("update tbl_publisher set publisherName = ?, publisherAddress = ?, publisherPhone = ? where publisherId = ?",
				new Object[] { publisher.getPublisherName(), publisher.getPublisherAddress(), publisher.getPublisherPhone(), publisher.getPublisherId() });
	}

	public void deletePublisher(Publisher publisher) throws SQLException {
		save("delete from tbl_publisher where publisherId = ?", 
				new Object[] { publisher.getPublisherId() });
	}

	public List<Publisher> readAllPublishers() throws SQLException {
		return readAll("select * from tbl_publisher", null);
	}
	
	public List<Publisher> readAllPublishersWithPageNo(Integer pageNo, Integer pageSize, String q) throws SQLException {
		if(q==null||q.trim().length()==0){
			return readAllWithPageNo("select * from tbl_publisher", null, pageNo, pageSize);
		}else{
			q = "%"+q+"%";
			return readAllWithPageNo("select * from tbl_publisher where publisherName like ?", new Object[]{q}, pageNo, pageSize);
		}
	}
	
	public Integer getPublishersCount(String q) throws SQLException{
		if(q==null||q.trim().length()==0){
			return getCount("select count(*) AS COUNT from tbl_publisher", null);
		}else{
			q = "%"+q+"%";
			return getCount("select count(*) AS COUNT from tbl_publisher where publisherName like ?", new Object[]{q});
		}
	}
	
	public Publisher readPublisherById(Publisher publisher) throws SQLException{
		List<Publisher> publishers =  readAll(
				"select * from tbl_publisher where publisherId = ?", 
				new Object[]{ publisher.getPublisherId() });
		if(publishers!=null){
			return publishers.get(0);
		}
		return null;
	}
	
	public Publisher readPublisherFirstLevelById(Publisher publisher) throws SQLException{
		List<Publisher> publishers =  readAllFirstLevel(
				"select * from tbl_publisher where publisherId = ?", 
				new Object[]{ publisher.getPublisherId() });
		if(publishers!=null){
			return publishers.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> extractData(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<>();
		BookDAO bdao = new BookDAO(conn);
		try {
			while (rs.next()) {
				Publisher p = new Publisher();
				p.setPublisherId(rs.getInt("publisherId"));
				p.setPublisherAddress(rs.getString("publisherAddress"));
				p.setPublisherName(rs.getString("publisherName"));
				p.setPublisherPhone(rs.getString("publisherPhone"));
				p.setBooks(bdao.readAllBooksFirstLevelByPublisher(p));
				publishers.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) {
		List<Publisher> publishers = new ArrayList<>();
		try {
			while (rs.next()) {
				Publisher p = new Publisher();
				p.setPublisherId(rs.getInt("publisherId"));
				p.setPublisherAddress(rs.getString("publisherAddress"));
				p.setPublisherName(rs.getString("publisherName"));
				p.setPublisherPhone(rs.getString("publisherPhone"));
				publishers.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}
}
