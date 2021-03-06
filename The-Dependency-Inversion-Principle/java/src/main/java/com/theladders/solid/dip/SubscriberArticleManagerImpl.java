package com.theladders.solid.dip;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
 * This system depends on suggestedArticleDAO
 * and a repositoryManger.
 * 
 * Run Time Dependencies:
 * suggestedArticle
 * SuggestedArticleDAO
 * repositoryManager
 * contentNode
 * 
 * Compile Time Dependencies:
 * suggestedArticle
 * SuggestedArticleExample
 * Date
 * List
 * HashMap
 * Map
 * String
 * Integer
 */
public class SubscriberArticleManagerImpl implements SubscriberArticleManager
{
  private static final String              IMAGE_PREFIX       = "http://somecdnprodiver.com/static/images/careerAdvice/";
  private static final Map<String, String> CATEGORY_IMAGE_MAP = new HashMap<>();

  static
  {
    CATEGORY_IMAGE_MAP.put("Interviewing", "interviewing_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Job Search", "job_search_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Networking", "networking_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Personal Branding", "personalBranding_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Resume", "resume_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Salary", "salary_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("Assessment", "salary_thumb.jpg");
    CATEGORY_IMAGE_MAP.put("On the Job", "salary_thumb.jpg");
  }

  private SuggestedArticleDao              suggestedArticleDao;
  private RepositoryManager                repositoryManager;

  public SubscriberArticleManagerImpl(SuggestedArticleDao suggestedArticleDao,
                                      RepositoryManager repositoryManager)
  {
    this.suggestedArticleDao = suggestedArticleDao;
    this.repositoryManager = repositoryManager;
  }

  public List<SuggestedArticle> getSuggestedArticlesbySubscriber(Integer subscriberId)
  {
    List<SuggestedArticle> suggestions = this.suggestedArticleDao.getSuggestedArticles(subscriberId);

    // Fetch content associated with SuggestedArticle (based on externalArticleId)
    resolveArticles(suggestions);

    return suggestions;
  }

  public int addSuggestedArticle(SuggestedArticle suggestedArticle)
  {
    Integer STATUS_UNREAD = 1;
    Integer HTP_CONSULTANT = 1;
    suggestedArticle.setSuggestedArticleStatusId(STATUS_UNREAD);
    suggestedArticle.setSuggestedArticleSourceId(HTP_CONSULTANT);
    suggestedArticle.setCreateTime(new Date()); // current date
    suggestedArticle.setUpdateTime(new Date()); // current date
    int newId = suggestedArticleDao.addSuggestedArticle(suggestedArticle);
    return newId;
  }

  private void resolveArticles(List<SuggestedArticle> suggestedArticles)
  {
    for (SuggestedArticle article : suggestedArticles)
    {

      // Attempt to fetch the actual content;
      ContentNode content = this.repositoryManager.getNodeByUuid(article.getArticleExternalIdentifier());
      if (content != null && ContentUtils.isPublishedAndEnabled(content))
      {
        // Override miniImagePath
        overrideMiniImagePath(content);
        article.setContent(content);
      }
    }
  }

  private static void overrideMiniImagePath(ContentNode node)
  {
    String path = (String) node.getProperty("miniImagePath");

    if (path == null || path.length() == 0)
    {
      String category = (String) node.getProperty("primaryTopic");
      node.addProperty("miniImagePath", IMAGE_PREFIX + CATEGORY_IMAGE_MAP.get(category));
    }
  }

  public void updateNote(Integer id, String note)
  {
    SuggestedArticle article = new SuggestedArticle();
    article.setSuggestedArticleId(id);
    article.setNote(note);
    suggestedArticleDao.updateSuggestedArticleById(article);
  }

  public void markRecomDeleted(Integer id)
  {
    Integer STATUS_DELETED = 4;
    SuggestedArticle article = new SuggestedArticle();
    article.setSuggestedArticleId(id);
    article.setSuggestedArticleStatusId(STATUS_DELETED);
    suggestedArticleDao.updateSuggestedArticleById(article);
  }
}