package com.zunza.gongsamo.post.repository.querydsl

import com.zunza.gongsamo.post.constant.SortType
import com.zunza.gongsamo.post.dto.LocationFilter
import com.zunza.gongsamo.post.dto.PostCursor
import com.zunza.gongsamo.post.dto.PostDetailsResponse
import com.zunza.gongsamo.post.dto.PostPageResponse

interface CustomPostRepository {
    fun findPageByCursor(locationFilter: LocationFilter, sortType: SortType, size: Int, postCursor: PostCursor): PostPageResponse
    fun findPostDetailsById(postId: Long, userId: Long?): PostDetailsResponse?
}
