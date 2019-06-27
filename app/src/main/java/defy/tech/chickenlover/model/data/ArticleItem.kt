package defy.tech.chickenlover.model.data

data class ArticleItem(var _id : Int = 0,
                       var type : Int = 0,
                       var hashed_key : String? = null,
                       var writer : String? = null,
                       var title : String? = null,
                       var content : String? = null,
                       var img_url : ArrayList<String>? = null,
                       var write_date : String? = null,
                       var comment_amount : Int = 0,
                       var like_amount : Int = 0)