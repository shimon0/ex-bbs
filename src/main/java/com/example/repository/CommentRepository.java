package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.example.domain.Comment;


public class CommentRepository {
	private final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setArticleId(rs.getInt("article_id"));
		comment.setContent(rs.getString("content"));
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		return comment;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Comment> findByArticleId(Integer articleId){
		String sql="SELECT * FROM comments WHERE article_id= :arteicleId";
		SqlParameterSource param=new MapSqlParameterSource().addValue("article_id", articleId);
		List<Comment> commentList=template.query(sql, param,COMMENT_ROW_MAPPER);
		return commentList;
	}
	public void insert(Comment comment) {
		SqlParameterSource param=new BeanPropertySqlParameterSource(comment);
		String insertSql="INSERT INTO comments(name,article_id,content,id) VALUES(:name,:arteicleId,content,id) ";
		KeyHolder keyHolder=new GeneratedKeyHolder();
		String[] keyColumnNames= {"id"};
		template.update(insertSql, param,keyHolder,keyColumnNames);
		comment.setId(keyHolder.getKey().intValue());
	}
	public void deleteByArticleId(Integer articleId) {
		String deleteSql = "DELETE FROM comments WHERE id=:id;";
		SqlParameterSource param=new MapSqlParameterSource().addValue("article_id", articleId);
		template.update(deleteSql, param);
	}
}
