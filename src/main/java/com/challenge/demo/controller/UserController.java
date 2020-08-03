package com.challenge.demo.controller;

import com.challenge.demo.DTO.QuestionDTO;
import com.challenge.demo.DTO.QuestionHistoryDTO;
import com.challenge.demo.entity.Question;
import com.challenge.demo.entity.QuestionsHistory;
import com.challenge.demo.entity.Site;
import com.challenge.demo.entity.User;
import com.challenge.demo.repository.QuestionRepository;
import com.challenge.demo.repository.QuestionsHistoryRepository;
import com.challenge.demo.repository.SiteRepository;
import com.challenge.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// create by Hengchao
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired  // get set
    UserRepository userRepository;

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    QuestionRepository questionsRepository;

    @Autowired
    QuestionsHistoryRepository questionHistoryRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public User createUser(@RequestBody User createUser) {
//        System.out.println(createUser.getUserId());
//        if (userRepository.findById(createUser.getUserId()) == null) {
        createUser.setUserUUID(UUID.randomUUID());
//            System.out.println(siteRepository.findById(createUser.getSiteId()));
//            createUser.setSiteId(createUser.getSiteId());
//        if (siteRepository.findById(createUser.getSiteId()).isPresent()) {
//            System.out.println(1111);
//                createUser.setSiteId((long)1);
        return userRepository.save(createUser);
//        }
//        System.out.println("there isn't valid site id");
//        return null;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUsers() {
        return Optional
                .ofNullable(userRepository.findAll())
                .map(users -> ResponseEntity.ok(users))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser, @PathVariable(value = "id") Long userId) {
        return userRepository
                .findById(userId)
                .map(user -> {
//                    site.setUrl(updatedSite.getUrl());
                    return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable(value = "id") Long UserId) {
        return userRepository
                .findById(UserId)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok(user);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long userId) {
        return userRepository
                .findById(userId)
                .map(user -> ResponseEntity.ok(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get history for user
    @GetMapping("/{id}/historys")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuestionHistoryDTO>> getQuestionHistorys(@PathVariable(value = "id") Long userId) {
        return userRepository
                .findById(userId)
                .map(user -> ResponseEntity.ok(QuestionHistoryDTO.build(user.getHistory())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // get new questions for user
    @GetMapping("/{user_uuid}/{site_uuid}/new_questions")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable(value = "user_uuid") String userUuid, @PathVariable(value = "site_uuid") String siteUuid) {
        String s1 = userUuid.replace("-", "");
        UUID user_uuid = new UUID(
                new BigInteger(s1.substring(0, 16), 16).longValue(),
                new BigInteger(s1.substring(16), 16).longValue());
        User user1 = new User();
        if(userRepository.findByUuid(user_uuid) != null) {
            user1 = userRepository.findByUuid(user_uuid);
        }
        else {
            System.out.println("invalid user uuid");
            return null;
        }
        Long userId = user1.getUserId();
        String s2 = siteUuid.replace("-", "");
        UUID site_uuid = new UUID(
                new BigInteger(s2.substring(0, 16), 16).longValue(),
                new BigInteger(s2.substring(16), 16).longValue());
        Site site = new Site();
        if(userRepository.findByUuid(user_uuid) != null) {
            site = siteRepository.findByUuid(site_uuid);
        }
        else {
            System.out.println("invalid site uuid");
            return null;
        }
        Long siteId = site.getSiteId();
//        List<QuestionsHistory> histories = user1.getHistory();
        List<Question> questions = questionsRepository.findSiteQuestions(siteId);
//        System.out.println(questions);
        List<Long> occurd_questions = user1.getOccurd();
//        System.out.println(occurd_questions);
        for(Long question_id: occurd_questions) {
            if(questionsRepository.findById(question_id).isPresent())
                questions.remove(questionsRepository.findById(question_id).orElse(new Question()));
        }
        Question newQu;
        if(questions.isEmpty()) {
            user1.getOccurd().clear();
            questions = questionsRepository.findSiteQuestions(siteId);
        }
        newQu = questions.get(0);
        user1.getOccurd().add(newQu.getQuestionId());

        userRepository.save(user1);
        Long newQuId = newQu.getQuestionId();
        return questionsRepository
                .findById(newQuId)
                .map(question -> ResponseEntity.ok(QuestionDTO.build(newQu)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // return and store the answer of each user
    @PostMapping("/{user_uuid}/{question_id}/{question_answer_id}/answer")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<QuestionHistoryDTO> setAnswer(
            @PathVariable(value = "user_uuid") String userUuid, @PathVariable(value = "question_id") Long questionId, @PathVariable Long question_answer_id) {

        String s1 = userUuid.replace("-", "");
        UUID user_uuid = new UUID(
                new BigInteger(s1.substring(0, 16), 16).longValue(),
                new BigInteger(s1.substring(16), 16).longValue());
        User user1 = new User();
        if(userRepository.findByUuid(user_uuid) != null) {
            user1 = userRepository.findByUuid(user_uuid);
        }else {
            System.out.println("invalid user uuid");
            return null;
        }
        Question question = questionsRepository.findById(questionId).orElse(null);
        QuestionsHistory qh = new QuestionsHistory();
        qh.setUserId(user1);
        qh.setAnswer(question_answer_id);
        qh.setQuestion(question);
        questionHistoryRepository.save(qh);
        return questionHistoryRepository
                .findById(question_answer_id)
                .map(questionHistory -> ResponseEntity.ok(QuestionHistoryDTO.build(qh)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}