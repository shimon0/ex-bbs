package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.form.CommentForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/article")
public class ArticleController {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList =articleRepository.findAll();
		for (Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}
		model.addAttribute("articleList", articleList);
		return "bbs";
	}
	@RequestMapping("/insertArticle")
	public String insertArticle(ArticleForm articleForm) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		articleRepository.insert(article);
		return "redirect:/article";
	}
	
	@RequestMapping("/insertComment")
	public String insertComment(CommentForm commentForm) {
		Comment comment=new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(commentForm.getArticleId());
		commentRepository.insert(comment);
		return "redirect:/article";
	}
	@RequestMapping("/deleteArticle")
	public String deleteArticle(Integer id) {
		
		commentRepository.deleteByArticleId(id);
		articleRepository.deleteById(id);
		
		return "redirect:/article";
	}
}
