package com.lbs.admin;

import com.lbs.common.constant.SpringConstants;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Eric Xue
 * @date 2021/06/10
 */
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(SpringConstants.TEST_PROFILE)
public class KafkaLBSApplicationTests {
}
