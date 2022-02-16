package com.example.form;

public class CommentForm {
	private Integer articleId;
	private String name;
	private String content;
	private Integer id;
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getArticleId() {
		return articleId;
	}


	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}
	
	
}
