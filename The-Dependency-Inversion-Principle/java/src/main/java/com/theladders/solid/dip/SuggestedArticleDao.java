package com.theladders.solid.dip;

import java.util.List;

public interface SuggestedArticleDao
{
  public int addSuggestedArticle(SuggestedArticle suggestedArticle);
  public void updateSuggestedArticleById(SuggestedArticle article);
  List<SuggestedArticle> getSuggestedArticles(Integer subscriberId);
}
