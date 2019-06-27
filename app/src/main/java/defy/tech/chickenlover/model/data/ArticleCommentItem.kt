package defy.tech.chickenlover.model.data

data class ArticleCommentItem(val _id : Int = 0,
                              val writer : String? = null,
                              val content : String? = null,
                              val write_date : String? = null,
                              var like_amount : Int = 0)