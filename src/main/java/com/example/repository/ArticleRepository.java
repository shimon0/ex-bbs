package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;


/**
 * @author smone
 *
 */
@Repository
public class ArticleRepository {
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Article> findAll() {
		
		String sql = "select * from articles order by id desc";
		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
		
		return articleList;
	//	String sql="SELECT a.id,a.name,a.content,c.id,c.name,c.content,c.article_id "
	//			+ "FROM articles AS a LEFT OUTER JOIN comments AS c ON a.id = c.article_id ORDER BY a.id DESC;";
	//	
	//	List<Article> articleList = template.query(sql.toString(), ARTICLE_ROW_MAPPER);
	//	return articleList;
		
		
		
	}
	public void insert(Article article) {
		String sql = "INSERT INTO articles (name,content) VALUES (:name,:content);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName())
				.addValue("content", article.getContent());
		template.update(sql, param);
	}
	
	public void deleteById(Integer id) {
		String deleteSql = "DELETE FROM articles WHERE id=:id;";
		SqlParameterSource param=new MapSqlParameterSource().addValue("id", id);
		template.update(deleteSql, param);
	}
}
