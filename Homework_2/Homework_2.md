# Analysis on loading of [https://www.sjtu.edu.cn](https://www.sjtu.edu.cn)

## Introduction
This article compares [https://www.sjtu.edu.cn](https://www.sjtu.edu.cn) (hereinafter called 'SJTU') with other websites such as [https://www.tsinghua.edu.cn](https://www.tsinghua.edu.cn) (hereinafter called 'TsingHua', which has similar needs with SJTU.), [https://www.YouTube.com](https://www.YouTube.com) (hereinafter called 'YouTube', which is a big commercial site with good remark), analyzes the fore-end performances and carries out some optimization suggestions.

**Notes: This article is just on behalf of my personal views, and is only a piece of homework.**

## Loading
### Status & Problems
1. All resource files in SJTU came from the same hostname, www.sjtu.edu.cn. While in YouTube, images came from many other hostnames.
2. Scripts and style files in SJTU are not merged and minified, resulting in more HTTP requests and larger size of response. In TsingHua and YouTube, things get slightly better.
3. Both SJTU and TsingHua does not use gzip for compression, while YouTube does.

### Solutions
1. Move resource files to other hosts to reduce pressure of www.sjtu.edu.cn.
2. Merge and minify Scripts and style files as much as possible.
3. Turn on gzip compression option in web server.

## Rendering

### Status & Problems
1. Most of the contents are rendered by JavaScript in SJTU.
2. SJTU takes 1270.3 ms in scripting, 1111.4 ms in rendering and 335.4 ms in painting, while TsingHua takes 196.5 ms in scripting, 171.8 ms in rendering and 42.3 ms in painting.
3. SJTU costs high CPU usage after the web page is already loaded (About 30% CPU usage in my latest Chrome browser), while Tsinghua and YouTube costs almost none.

### Solutions
1. Adopt methods such as server-side caching to improve rendering efficiency if possible.
2. Optimize logic in script to improve rendering efficiency.