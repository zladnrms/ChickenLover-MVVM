package defy.tech.chickenlover.model.data

data class ArticleItem(var _id : Int = 0,
                       var title : String,
                       var writer : String,
                       var content : String,
                       var img_url : ArrayList<String>?,
                       var create_date : String,
                       var thumbs : String,
                       var comment_id : Int = 0)