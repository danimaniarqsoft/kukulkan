package mx.infotec.dads.archetype.cucumber.stepdefs;

import mx.infotec.dads.archetype.KukulkanApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = KukulkanApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
