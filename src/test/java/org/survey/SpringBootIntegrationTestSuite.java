package org.survey;

import org.junit.platform.suite.api.SelectClasses;

@SelectClasses({ActuatorIT.class, LoginControllerIT.class, SpringBootWebIT.class,
    UserServiceIT.class})
public class SpringBootIntegrationTestSuite {
}
