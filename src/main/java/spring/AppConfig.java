package spring;

import core.Board;
import core.Game;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.util.Scanner;

@Configuration
@ComponentScan(basePackages = {"core", "core.pieces", "spring"})
public class AppConfig {
  //  ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

 /*   @Bean
    @Scope("prototype")
    public Board getStandardBoard() {
        return new Board();
    }

    @Bean
    @Scope("prototype")
    public Game getGame() {
        return new Game(getStandardBoard());
    }*/
}

