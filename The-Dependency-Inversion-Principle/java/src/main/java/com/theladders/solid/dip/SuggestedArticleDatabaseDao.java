package com.theladders.solid.dip;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SuggestedArticleDatabaseDao implements SuggestedArticleDao
{
  public int addSuggestedArticle(@SuppressWarnings("unused") SuggestedArticle suggestedArticle)
  {
    return 0;
  }
  
  public void updateSuggestedArticleById(@SuppressWarnings("unused") SuggestedArticle article) {}

  public List<SuggestedArticle> getSuggestedArticles(Integer subscriberId)
  {
    SuggestedArticleExample criteria = new SuggestedArticleExample();
    criteria.createCriteria()
            .andSubscriberIdEqualTo(subscriberId)
            .andSuggestedArticleStatusIdIn(Arrays.asList(1, 2))  // must be New or Viewed
            .andSuggestedArticleSourceIdEqualTo(1);

    criteria.setOrderByClause("create_time desc");
    return Collections.singletonList(new SuggestedArticle());
  }
}
