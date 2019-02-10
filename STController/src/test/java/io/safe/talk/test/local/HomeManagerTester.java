package io.safe.talk.test.local;

import io.safe.talk.controller.local.HomeManager;
import org.junit.Test;

public class HomeManagerTester {

    @Test
    public void checkIfTheInfrastructuresExists(){
        HomeManager homeManager = new HomeManager();
        homeManager.checkPersonalConfiguration();
        homeManager.checkContactConfiguration();
    }


}
