package `in`.ceeq.paginglib.data.entity

data class Comment(val id: Int, val postId: Int, val name: String, val email: String, val body: String) {
    val idStr: String
        get() = id.toString()

    val postIdStr: String
        get() = postId.toString()
}
