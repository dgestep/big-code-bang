package ${topLevelDomain}.${companyName}.${productName}.restcontroller;

import ${topLevelDomain}.${companyName}.${productName}.model.log.LogFactory;
import ${topLevelDomain}.${companyName}.${productName}.model.log.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

/**
 * Error handler.
 *
 * @author ${codeAuthor}.
 */
@Controller
public class ErrorController {
    private Logger logger;

    /**
     * Initializes class and Logger
     */
    @PostConstruct
    public void init() {
        logger = LogFactory.getLogger();
        logger.info("==> 404 Handler Initialized! <==");
    }

    /**
     * Forwards 404 errors to the index page for Angular to pick and try and map
     * @return
     *  forward to index.html
     */
    @RequestMapping("/error")
    public String handler404() {
        final Logger logger = LogFactory.getLogger();
        logger.debug("==> Forwarding!!!! <==");
        return "forward:/index.html";
    }
}
