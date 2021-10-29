package chess.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"chess/core", "core.pieces", "chess/spring"})
public class AppConfig {
}

