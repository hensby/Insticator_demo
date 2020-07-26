# Insticator_demo

![](https://img.shields.io/badge/Java-1.8-brightgreen)
![](https://img.shields.io/badge/SpringBoot-2.1.3.RELEASE-orange)
![](https://img.shields.io/badge/Plugin-JPA-red)
![](https://img.shields.io/badge/DB-H2-blue)

A backend system based on Spring-boots, H2 database.

Design a User class, QuestionHistory class and add a Type attribute in Question class.

## Install and Run
Install H2 database and set username = 'sa' and password = ''. 
The table and data structure will be created automatically.

Install Java 1.8 and import the project by using Maven.

Run "DemoApplication.java"

## Missing Requirements:
1) Add a Type attribute in Questions Class. Easy to front-end developers to figure out the question type. 
(E.g. For matrix questions in Appendix, add answers like "<18, Male","<18, Female",""18 to 35, Male" .etc. 
When front-end developers get type of question is 4, they can analysis the answer to matrix questions)
2) Built two APIs. 

User gets new questions what he never has. If a unique question does not exist, It will return an old question.
````
HTTPS://localhost:8080/{user_uuid}/{site_uuid}/new_questions
````

User returns the question answer to System and store the history.
````
HTTPS://localhost:8080/{user_uuid}/{question_id}/{question_answer_id}/answer"
````

## Test cases:
```
// add site
curl -H "Content-Type:application/json" -X POST -d '{"url": "www.Bob.com"}' http://localhost:8080/sites

// add user
curl -H "Content-Type:application/json" -X POST -d '{}' http://localhost:8080/users          

// add question (Trivia question)
curl -H "Content-Type:application/json" -X POST -d '{"siteId":1, "question":"whitch team won the 2013 superbowl?","type":1}' http://localhost:8080/questions

// add question (Matrix question)
curl -H "Content-Type:application/json" -X POST -d '{"siteId":1, "question":"Please tell us a bit about you?","type":4}' http://localhost:8080/questions

// add answer (Trivia question)
curl -H "Content-Type:application/json" -X POST -d '{"answer":"Falcons","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"Patriots","isCorrectAnswer": true}' http://localhost:8080/questions/{{question_id}}/answers

// add answer (Matrix question)
curl -H "Content-Type:application/json" -X POST -d '{"answer":"<18, Male","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"<18, Female","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"18 to 35, Male","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"18 to 35, Female","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"35 to 55, Male","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":"35 to 55, Female","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":">55, Male","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers
curl -H "Content-Type:application/json" -X POST -d '{"answer":">55, Female","isCorrectAnswer": false}' http://localhost:8080/questions/{{question_id}}/answers

// get new question for user
http://localhost:8080/users/{user_uuid}/{site_uuid}/new_questions
(e.g.http://localhost:8080/users/5952352e8752449aa5bbfb4f95d801d4/03a7384afcb14cf0a9a0df4cf82715b6/new_questions)

// return the answer from user and store it
curl -H "Content-Type:application/json" -X POST http://localhost:8080/users/{{user_uuid}}/{{question_id}}/{{question_answer_id}}/answer
(e.g. curl -H "Content-Type:application/json" -X POST http://localhost:8080/users/5952352e8752449aa5bbfb4f95d801d4/4/6/answer)
```


