package `in`.ceeq.paginglib.data.entity

data class Post(val id: Int, val userId: Int, val title: String, val body: String) {
    val idStr: String
        get() = id.toString()

    val userIdStr: String
        get() = userId.toString()
}
