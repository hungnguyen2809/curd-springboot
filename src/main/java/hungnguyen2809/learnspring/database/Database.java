package hungnguyen2809.learnspring.database;

import hungnguyen2809.learnspring.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//init database in H2 Database (database save date on RAM) when process exits data clear
@Configuration
public class Database {
    //logger => write log in console
    private static final Logger logger = LoggerFactory.getLogger(Database.class);

    // init database
    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
//                Product iphone = new Product("IPhone 12 Pro Max", 2021, 2000D, "");
//                Product samsung = new Product("Samsung Galaxy S20 Ultra", 2021, 1500D, "");
//                Product xiaomi = new Product("Xiaomi Mix 4", 2021, 1300D, "");
//
//                // save data
//                logger.info("Insert Data: " + productRepository.save(iphone));
//                logger.info("Insert Data: " + productRepository.save(samsung));
//                logger.info("Insert Data: " + productRepository.save(xiaomi));
            }
        };
    }
}
