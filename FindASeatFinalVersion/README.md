Order of blackbox tests to run:

NOTE: On MakeAccountTest, EmailAlreadyExistsTest, IDAlreadyExistsTest, 
and any time it prompts you out of the app 
(like picking a photo) there is a 6 second thread sleep so you can do so!!

1. UserDNELoginTest
2. UserExistsLoginTest
2. MakeResTest
3. CantMakeAnotherResTest
4. ModifyTest
5. CancelTest
6. AllErrorsShowUPTest
7. BasicTests (2 tests)
8. EmailAlreadyExistsTest
9. EmailAndPassDontMatchTest
10. IDAlreadyExistsTest
11. InvalidEmailTest
12. InvalidPasswordTest
13. InvalidResTest
14. MakeAccountTest

Order of whitebox tests to run:

1. DatabaseTests (3 tests)
2. ExampleTest3 (13 tests)