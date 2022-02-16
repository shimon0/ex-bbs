package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

@Repository
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
		String sql="SELECT * FROM comments WHERE article_id= :articleId";
		SqlParameterSource param=new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList=template.query(sql, param,COMMENT_ROW_MAPPER);
		return commentList;
	}
	public void insert(Comment comment) {
		String insertSql="INSERT INTO comments(name,article_id,content) VALUES(:name,:articleId,:content) ";
		SqlParameterSource param=new MapSqlParameterSource().addValue("name",comment.getName())
				.addValue("articleId",comment.getArticleId()).addValue("content", comment.getContent()).addValue("id", comment.getId());
		template.update(insertSql, param);
	}
	public void deleteByArticleId(Integer articleId) {
		String deleteSql = "DELETE FROM comments WHERE article_id=:articleId;";
		SqlParameterSource param=new MapSqlParameterSource().addValue("articleId", articleId);
		template.update(deleteSql, param);
	}
}
