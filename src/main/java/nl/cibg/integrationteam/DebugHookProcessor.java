package nl.cibg.integrationteam;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DebugHookProcessor implements Processor {

    private static final Logger logger = LoggerFactory.getLogger(DebugHookProcessor.class);

    @Override
    public void process(Exchange exchange) {
        String id = exchange.getExchangeId();
        logger.info(id + " -- Headers: " + exchange.getIn().getHeaders().toString());
        logger.info(id + " -- Properties: " + exchange.getProperties().toString());
        logger.info(id + " -- Body: " + exchange.getIn().getBody());
    }
}