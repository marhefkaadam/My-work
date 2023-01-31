# tweet-sentiment-analysis
Project fetches Elon Musk's tweets and decides according to sentiment analysis if we should buy or sell fictional Tesla shares.

### Task  
Write a simple Python 3 application which is supposed to trade Tesla stock based on Elon Musk's latest tweets.
Details:
- Once executed, application should wait for a new tweet by Elon
- Once retrieved, sentiment analysis should be performed.
- When sentiment is positive, we buy and vice versa. If the sentiment is neutral, do nothing.
- Instead of actual trading, application should just print "I'm buying" or "I'm selling"
- Once done, wait for another tweet.
- The approach to fetch tweets and how the sentiment analysis is done is up to you.
- Any library can be used.
- The code should be production ready. Include tests in pytest.