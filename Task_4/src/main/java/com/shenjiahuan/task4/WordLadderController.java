package com.shenjiahuan.task4;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.FutureTask;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class WordLadderController {
    private final static Logger logger = Logger.getLogger(WordLadderController.class);

    @Value("${thread.nThreads}")
    private Integer nThreads;

    @Value("${thread.threshold}")
    private Integer threshold;

    @Value("${thread.upperBound}")
    private Integer upperBound;

    @Value("${thread.timeout}")
    private Integer timeout;

    private ThreadPool threadPool;
    private WordLadder wordLadder;

    @PostConstruct
    private void init() {
        try {
            this.wordLadder = new WordLadder("/static/EnglishWords.txt");
            threadPool = new ThreadPool(nThreads, threshold, upperBound, timeout);
        } catch (IOException | NullPointerException ex) {
            this.wordLadder = null;
        }
    }


    @RequestMapping(value = "/wordladders", method = GET)
    public WordLadderResult wordLadder(@RequestParam(value = "from", required = false) String from, @RequestParam(value = "to", required = false) String to) {
        WordLadderTask task = new WordLadderTask(wordLadder, from, to);
        FutureTask<Object> futureTask = new FutureTask<>(task);
        threadPool.execute(futureTask);
        try {
            return (WordLadderResult) futureTask.get();
        } catch (CancellationException ex) {
            return new WordLadderResult(null, -4);
        } catch (Exception ex) {
            logger.warn("Unexpected error happened");
            return new WordLadderResult(null, -5);
        }
    }
}
