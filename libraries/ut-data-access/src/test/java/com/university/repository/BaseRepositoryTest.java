package com.university.repository;

import com.university.PersistenceConfig;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceConfig.class)
public abstract class BaseRepositoryTest {

}