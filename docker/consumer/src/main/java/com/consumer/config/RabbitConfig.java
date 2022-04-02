package com.consumer.config;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ErrorHandler;
import com.consumer.handler.RabbitHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {
    @Value("${ipr-rabbit.taskQueue}")
    private String taskQueue;
    @Value("${ipr-rabbit.taskExchange}")
    private String taskExchange;
    @Value("${ipr-rabbit.dlqQueue}")
    private String dlqQueue;
    @Value("${ipr-rabbit.deadLetterExchange}")
    private String deadLetterExchange;
    @Value("${ipr-rabbit.dlqRoutingKey}")
    private String dlqRoutingKey;

    @Value("${ipr-rabbit.rabbit.consumer.concurrentConsumers}")
    private Integer concurrentConsumers;
    @Value("${ipr-rabbit.rabbit.consumer.maxConcurrentConsumers}")
    private Integer maxConcurrentConsumers;
    @Value("${ipr-rabbit.rabbit.consumer.prefetchCount}")
    private Integer prefetchCount;
    @Value("${ipr-rabbit.rabbit.consumer.receiveTimeout}")
    private Long receiveTimeout;
    @Value("${ipr-rabbit.rabbit.consumer.defaultRequeueRejected}")
    private Boolean defaultRequeueRejected;
    @Value("${ipr-rabbit.rabbit.consumer.acknowledgeMode}")
    private String acknowledgeMode;

    private static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    private static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setErrorHandler(errorHandler());
        return factory;
    }

    @Bean(name = "iprRabbitListenerFactory")
    public RabbitListenerContainerFactory iprRabbitListenerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setConnectionFactory(connectionFactory);
        rabbitListenerContainerFactory.setConcurrentConsumers(concurrentConsumers);
        rabbitListenerContainerFactory.setMaxConcurrentConsumers(maxConcurrentConsumers);
        rabbitListenerContainerFactory.setPrefetchCount(prefetchCount);
        rabbitListenerContainerFactory.setReceiveTimeout(receiveTimeout);
        rabbitListenerContainerFactory.setDefaultRequeueRejected(defaultRequeueRejected);
        rabbitListenerContainerFactory.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.valueOf(acknowledgeMode));
        rabbitListenerContainerFactory.setErrorHandler(errorHandler());
        return rabbitListenerContainerFactory;
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(getMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter getMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public ErrorHandler errorHandler() {
        return new RabbitHandler();
    }

    @Bean
    Queue taskQueue() {
        return QueueBuilder.durable(taskQueue)
                .withArguments(getDlqArgs()).build();
    }

    @Bean
    TopicExchange taskExchange() {
        return new TopicExchange(taskExchange);
    }

    @Bean
    Binding bindTask() {
        return BindingBuilder.bind(taskQueue()).to(taskExchange()).with("#");
    }

    @Bean
    Queue dlqQueue() {
        return QueueBuilder.durable(dlqQueue).build();
    }

    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(deadLetterExchange);
    }

    @Bean
    Binding dlqBinding(Queue dlqQueue, DirectExchange deadLetterExchange) {
        return BindingBuilder.bind(dlqQueue).to(deadLetterExchange).with(dlqRoutingKey);
    }

    private Map<String, Object> getDlqArgs() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put(X_DEAD_LETTER_EXCHANGE, deadLetterExchange);
        arguments.put(X_DEAD_LETTER_ROUTING_KEY, dlqRoutingKey);
        return arguments;
    }

}
