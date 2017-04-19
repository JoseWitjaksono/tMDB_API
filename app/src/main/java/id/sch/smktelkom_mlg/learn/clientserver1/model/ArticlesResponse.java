package id.sch.smktelkom_mlg.learn.clientserver1.model;

import java.util.List;

/**
 * Created by hyuam on 19/04/2017.
 */

public class ArticlesResponse
{
    //    "status": "ok",
    //    "source": "the-next-web",
    //    "sortBy": "latest",
    //    "articles": [{
    //            "author": "Matthew Hughes",
    //            "title": "Tecla’s revolutionary tech helps differently-abled people use touchscreens",
    //            "description": "There’s a quiet, yet fundamental dignity in being able to do things for yourself. It’s something you don’t really notice until it’s gone. Take something as simple as using a ...",
    //            "url": "https://thenextweb.com/gadgets/2017/04/18/teclas-revolutionary-tech-helps-differently-abled-people-use-touchscreens-easily/",
    //            "urlToImage": "https://cdn2.tnwcdn.com/wp-content/blogs.dir/1/files/2017/04/image5.jpg",
    //            "publishedAt": "2017-04-18T18:01:48Z"
    //            },]
    
    public String status;
    public String source;
    public String sortBy;
    public List<Article> articles;
}
