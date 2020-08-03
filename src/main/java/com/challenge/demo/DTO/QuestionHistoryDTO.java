package com.challenge.demo.DTO;

import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.QuestionsHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionHistoryDTO {

	private Long id;

	private Long questionId;

	private Long answer_id;

	private Date createdAt;

	private Date updatedAt;

	public static QuestionsHistory transform(final QuestionHistoryDTO newQADto, final Question question) {
		final QuestionsHistory newQa = new QuestionsHistory();
		newQa.setAnswer(newQADto.getAnswerId());
		newQa.setQuestion(question);

		return newQa;
	}

	public static QuestionHistoryDTO build(final QuestionsHistory save) {
		final QuestionHistoryDTO newQaDto = new QuestionHistoryDTO();

		newQaDto.setId(save.getId());
		newQaDto.setAnswer(save.getAnswer());
		newQaDto.setCreatedAt(save.getCreatedAt());
		newQaDto.setUpdatedAt(save.getUpdatedAt());
		newQaDto.setQuestionId(save.getQuestionId().getQuestionId());

		return newQaDto;
	}

	public static List<QuestionHistoryDTO> build(final List<QuestionsHistory> historys) {
		final List<QuestionHistoryDTO> ret = new ArrayList<>();
		for (QuestionsHistory qa : historys) {
			ret.add(build(qa));
		}
		return ret;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(final Long questionId) {
		this.questionId = questionId;
	}

	public Long getAnswerId() {
		return answer_id;
	}

	public void setAnswer(final Long answer) {
		this.answer_id = answer;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(final Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(final Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
