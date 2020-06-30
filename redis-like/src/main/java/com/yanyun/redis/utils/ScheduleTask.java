package com.yanyun.redis.utils;

import com.yanyun.redis.model.Article;
import com.yanyun.redis.model.UserLikeArticle;
import com.yanyun.redis.service.ArticleService;
import com.yanyun.redis.service.UserLikeArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Set;

/**
 * 定时落库任务
 */
@Service
@Slf4j
public class ScheduleTask {

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 用户点赞文章key
     */
    @Value("${user.like.article.key}")
    private String USER_LIKE_ARTICLE_KEY;

    /**
     * 文章被点赞的key
     */
    @Value("${article.like.user.key}")
    private String ARTICLE_LIKED_USER_KEY;

    @Resource
    private ArticleService articleService;

    @Resource
    private UserLikeArticleService userLikeArticleService;

    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void redisDataToMySQL() {
        log.info("time:{}，开始执行Redis数据持久化到MySQL任务", LocalDateTime.now().format(formatter));
        //1.更新文章总的点赞数
        Map<String, String> articleCountMap = redisTemplate.opsForHash().entries(ARTICLE_LIKED_USER_KEY);
        for (Map.Entry<String, String> entry : articleCountMap.entrySet()) {
            String articleId = entry.getKey();
            Set<Long> userIdSet = FastjsonUtil.deserializeToSet(entry.getValue(), Long.class);
            //1.同步某篇文章总的点赞数到MySQL
            synchronizeTotalLikeCount(articleId, userIdSet);
            //2.同步用户喜欢的文章
            synchronizeUserLikeArticle(articleId, userIdSet);
        }
        log.info("time:{}，结束执行Redis数据持久化到MySQL任务", LocalDateTime.now().format(formatter));
    }

    /**
     * 同步某篇文章总的点赞数到MySQL
     */
    private void synchronizeTotalLikeCount(String articleId, Set<Long> userIdSet) {
        Long totalLikeCount = new Long(userIdSet.size());
        Article article = buildArticle(totalLikeCount, articleId);
        articleService.modifyById(article);
    }

    /**
     * 同步用户喜欢的文章
     * @param articleId
     * @param userIdSet
     */
    private void synchronizeUserLikeArticle(String articleId, Set<Long> userIdSet) {
        for (Long userId : userIdSet) {
            UserLikeArticle userLikeArticle = buildUserLikeArticle(articleId, userId);
            if (userLikeArticleService.selectOne(userLikeArticle) == null) {
                userLikeArticleService.insert(userLikeArticle);
            }
        }
    }

    /**
     * 构造UserLikeArticle对象
     * @param articleId
     * @param userId
     * @return
     */
    private UserLikeArticle buildUserLikeArticle(String articleId, Long userId) {
        UserLikeArticle userLikeArticle = new UserLikeArticle();
        userLikeArticle.setArticleId(Long.parseLong(articleId));
        userLikeArticle.setUserId(userId);

        return userLikeArticle;
    }

    /**
     * 构造Article对象
     * @param articleId
     * @param totalLikeCount
     * @return
     */
    private Article buildArticle(Long totalLikeCount, String articleId) {
        Article article = new Article();
        article.setId(Long.parseLong(articleId));
        article.setTotalLikeCount(totalLikeCount);

        return article;
    }
}
