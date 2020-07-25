# Insticator_demo

![](https://img.shields.io/badge/Java-1.8-brightgreen)
![](https://img.shields.io/badge/SpringBoot-2.1.3.RELEASE-orange)
![](https://img.shields.io/badge/Plugin-JPA-red)
![](https://img.shields.io/badge/DB-H2-blue)

A backend system based on Spring-boots

Design a USER class, QuestionHistory class and add a Type attribute in Question class.

##Missing Requirements:
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

