package com.failfeed.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.failfeed.service.dto.RetweetDto;
import com.failfeed.service.dto.AlertDto;
import com.failfeed.service.exception.UserNotFoundException;
import com.failfeed.service.exception.AlertNotFoundException;
import com.failfeed.service.model.Retweet;
import com.failfeed.service.model.User;
import com.failfeed.service.model.Post;
import com.failfeed.service.repository.UserRepository;
import com.failfeed.service.repository.PostRepository;
import com.failfeed.service.repository.RetweetRepository;
import com.failfeed.service.observer.AlertSubject;
import com.failfeed.service.observer.LoggingObserver;
import com.failfeed.service.state.AlertContext;

@Service
public class RetweetServiceImpl implements RetweetServiceInterface {

    private final UserRepository userRepo;
    private final PostRepository postRepo;
    private final RetweetRepository retweetRepo;
    private final AlertSubject alertSubject;

    public RetweetServiceImpl(UserRepository userRepo, PostRepository postRepo, 
                            RetweetRepository retweetRepo) {
        this.userRepo = userRepo;
        this.postRepo = postRepo;
        this.retweetRepo = retweetRepo;
        this.alertSubject = new AlertSubject();
        // Attach default observers
        this.alertSubject.attach(new LoggingObserver());
    }

    @Override
    public RetweetDto retweetPost(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new AlertNotFoundException(postId));

        // Check if user already retweeted this post
        if (retweetRepo.findByUserAndOriginalPost(user, post).isPresent()) {
            throw new IllegalArgumentException("User has already retweeted this post");
        }

        Retweet retweet = new Retweet(user, post);
        Retweet savedRetweet = retweetRepo.save(retweet);

        // Initialize alert state
        AlertContext alertContext = new AlertContext(savedRetweet.getId());
        alertContext.send();

        // Create retweet alert and notify observers
        String alertContent = user.getName() + " retweeted " + post.getUser().getName() + "'s post";
        AlertDto alertDto = new AlertDto(
            savedRetweet.getId(),
            alertContent,
            userId,
            user.getName(),
            savedRetweet.getRetweetedAt()
        );
        alertSubject.notifyObservers(alertDto);

        return new RetweetDto(savedRetweet);
    }

    @Override
    public List<RetweetDto> getRetweetsByUser(Long userId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        
        return retweetRepo.findByUserOrderByRetweetedAtDesc(user).stream()
            .map(RetweetDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public List<RetweetDto> getRetweetsForPost(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new AlertNotFoundException(postId));
        
        return retweetRepo.findByOriginalPost(post).stream()
            .map(RetweetDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public long getRetweetCount(Long postId) {
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new AlertNotFoundException(postId));
        
        return retweetRepo.countByOriginalPost(post);
    }

    @Override
    public void undoRetweet(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new AlertNotFoundException(postId));

        Retweet retweet = retweetRepo.findByUserAndOriginalPost(user, post)
            .orElseThrow(() -> new IllegalArgumentException("User has not retweeted this post"));

        retweetRepo.delete(retweet);
        System.out.println("User " + user.getName() + " removed retweet from post " + postId);
    }

    @Override
    public boolean hasUserRetweeted(Long userId, Long postId) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new UserNotFoundException(userId));
        Post post = postRepo.findById(postId)
            .orElseThrow(() -> new AlertNotFoundException(postId));

        return retweetRepo.findByUserAndOriginalPost(user, post).isPresent();
    }
}
