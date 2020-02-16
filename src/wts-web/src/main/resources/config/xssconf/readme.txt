antisamy-slashdot.xml 
Slashdot (http://www.slashdot.org/)是一个提供技术新闻的网站，它允许用户用有限的HTML格式的内容匿名回帖。
Slashdot不仅仅是目前同类中最酷的网站之一，而且同时也曾是最容易被成功攻击的网站之一。更不幸的是，导致大部分用户遭受攻击的原由是臭名昭着的goatse.cx 图片(请你不要刻意去看)。
Slashdot的安全策略非常严格：用户只能提交下列的html标签：<b>, <u>, <i>, <a>, <blockquote>，并且还不支持CSS.
因此我们创建了这样的策略文件来实现类似的功能。它允许所有文本格式的标签来直接修饰字体、颜色或者强调作用。


antisamy-ebay.xml
众所周知，eBay (http://www.ebay.com/)是当下最流行的在线拍卖网站之一。它是一个面向公众的站点，因此它允许任何人发布一系列富HTML的内容。
我们对eBay成为一些复杂XSS攻击的目标，并对攻击者充满吸引力丝毫不感到奇怪。由于eBay允许输入的内容列表包含了比Slashdot更多的富文本内容，所以它的受攻击面也要大得多。下面的标签看起来是eBay允许的（eBay没有公开标签的验证规则）:<a>,...


antisamy-myspace.xml
MySpace (http://www.myspace.com/)是最流行的一个社交网站之一。用户允许提交几乎所有的他们想用的HTML和CSS，只要不包含JavaScript。
MySpace现在用一个黑名单来验证用户输入的HTML，这就是为什么它曾受到Samy蠕虫攻击(http://namb.la/)的原因。Samy蠕虫攻击利用了一个本应该列入黑名单的单词(eval)来进行组合碎片攻击的，其实这也是AntiSamy立项的原因。


antisamy-anythinggoes.xml
我也很难说出一个用这个策略文件的用例。如果你想允许所有有效的HTML和CSS元素输入（但能拒绝javascript或跟CSS相关的网络钓鱼攻击），你可以使用这个策略文件。其实即使MySpace也没有这么疯狂。然而，它确实提供了一个很好的参考，因为它包含了对于每个元素的基本规则，所以你在裁剪其它策略文件的时候可以把它作为一个知识库。