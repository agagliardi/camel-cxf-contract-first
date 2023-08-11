package com.example.camel;

import javax.enterprise.context.ApplicationScoped;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.customerservice.CustomerServiceService;
import com.example.customerservice.NoSuchCustomer;
import com.example.customerservice.NoSuchCustomerException;

@ApplicationScoped
public class Route extends RouteBuilder {
    Logger logger = LoggerFactory.getLogger(Route.class);

    @Override
    public void configure() throws Exception {
        from("cxf://cs?serviceClass=" + CustomerServiceService.class.getName() + "&dataFormat=PAYLOAD")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        NoSuchCustomer noSuchCustomer = new NoSuchCustomer();
                        noSuchCustomer.setCustomerName("Antonio");
                        NoSuchCustomerException exception = new NoSuchCustomerException("Foo bar",noSuchCustomer);
                        exchange.setException(exception);
                    }
                })
                .end();
    }
}
