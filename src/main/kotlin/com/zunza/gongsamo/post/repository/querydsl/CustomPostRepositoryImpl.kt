package com.zunza.gongsamo.post.repository.querydsl

import com.querydsl.core.types.Expression
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import com.zunza.gongsamo.participant.entity.QParticipant
import com.zunza.gongsamo.post.constant.SortType
import com.zunza.gongsamo.post.dto.LocationFilter
import com.zunza.gongsamo.post.dto.NextCursor
import com.zunza.gongsamo.post.dto.PostCursor
import com.zunza.gongsamo.post.dto.PostDetailsResponse
import com.zunza.gongsamo.post.dto.PostPageResponse
import com.zunza.gongsamo.post.dto.PostPreview
import com.zunza.gongsamo.post.entity.QPost
import com.zunza.gongsamo.user.entity.QUser
import org.springframework.stereotype.Repository


@Repository
class CustomPostRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
    private val post: QPost = QPost.post,
    private val user: QUser = QUser.user,
    private val participant: QParticipant = QParticipant.participant
) : CustomPostRepository {
    override fun findPageByCursor(
        locationFilter: LocationFilter,
        sortType: SortType,
        size: Int,
        postCursor: PostCursor
    ): PostPageResponse {
        val result = jpaQueryFactory.select(
            Projections.constructor(
                PostPreview::class.java,
                post.id,
                post.title,
                post.meetingLocation.placeName,
                post.productPrice,
                post.productImageUrl,
                post.maxParticipants,
                participant.id.countDistinct(),
                post.recruitmentDeadline,
                post.createdAt
            )
        )
            .from(post)
            .leftJoin(post.participants, participant)
            .where(
                locationCondition(locationFilter),
                cursorCondition(sortType, postCursor)
            )
            .orderBy(*buildOrderByExpressions(sortType))
            .groupBy(*buildGroupByExpressions(sortType))
            .limit(size + 1L)
            .fetch()
            .toMutableList()

        val nextCursor = getNextCursor(sortType, size, result)
        val hasMore = result.size > size
        if (result.size > size) result.removeLast()

        return PostPageResponse(result, nextCursor, hasMore)
    }

    private fun locationCondition(
        locationFilter: LocationFilter
    ): BooleanExpression? {
        if (locationFilter.sido == "전체") return null

        var condition: BooleanExpression =
            post.meetingLocation.sido
            .eq(locationFilter.sido)

        locationFilter.sigungu?.let { region2 ->
            condition = condition.and(
                post.meetingLocation.sigungu
                    .eq(locationFilter.sigungu)
            )
        }

        locationFilter.dong?.let { region3 ->
            condition = condition.and(
                post.meetingLocation.dong
                    .eq(locationFilter.dong)
            )
        }

        return condition
    }

    private fun cursorCondition(
        sortType: SortType,
        postCursor: PostCursor
    ): BooleanExpression? {
        if (postCursor.postId == null) return null

        return when (sortType) {
            SortType.LATEST -> post.createdAt.lt(postCursor.datetime)
                .or(post.createdAt.eq(postCursor.datetime)
                    .and(post.id.lt(postCursor.postId)))

            SortType.DEADLINE_ASC -> post.recruitmentDeadline.gt(postCursor.datetime)
                .or(post.recruitmentDeadline.eq(postCursor.datetime)
                    .and(post.id.gt(postCursor.postId)))

            SortType.DEADLINE_DESC -> post.recruitmentDeadline.lt(postCursor.datetime)
                .or(post.recruitmentDeadline.eq(postCursor.datetime)
                    .and(post.id.lt(postCursor.postId)))
        }
    }

    private fun buildOrderByExpressions(sortType: SortType): Array<OrderSpecifier<*>> {
        return when (sortType) {
            SortType.LATEST -> arrayOf(post.createdAt.desc(), post.id.desc())
            SortType.DEADLINE_ASC -> arrayOf(post.recruitmentDeadline.asc(), post.id.asc())
            SortType.DEADLINE_DESC -> arrayOf(post.recruitmentDeadline.desc(), post.id.desc())
        }
    }

    private fun buildGroupByExpressions(sortType: SortType): Array<Expression<*>> {
        return when (sortType) {
            SortType.LATEST -> arrayOf(post.createdAt, post.id)
            SortType.DEADLINE_ASC,
            SortType.DEADLINE_DESC -> arrayOf(post.recruitmentDeadline, post.id)
        }
    }

    private fun getNextCursor(
        sortType: SortType,
        size: Int,
        result: MutableList<PostPreview>
    ): NextCursor {
        return when {
            result.size < size -> NextCursor()
            else -> when (sortType) {
                SortType.LATEST -> NextCursor(result.last().id, result.last().createdAt)
                else -> NextCursor(result.last().id, result.last().recruitmentDeadline)
            }
        }
    }

    override fun findPostDetailsById(
        postId: Long,
        userId: Long?
    ): PostDetailsResponse? {
        val userParticipant = QParticipant("userParticipant")

        fun isHost(): Expression<Boolean> {
            return if (userId != null) {
                post.host.id.eq(userId)
            } else {
                Expressions.constant(false)
            }
        }

        fun isParticipant(): Expression<Boolean> {
            return if (userId != null) {
                userParticipant.user.id.eq(userId)
            } else {
                Expressions.constant(false)
            }
        }

        return jpaQueryFactory.select(
            Projections.constructor(
                PostDetailsResponse::class.java,
                post.id,
                post.host.nickname,
                post.title,
                post.description,
                post.settlementType,
                post.status,
                post.productImageUrl,
                post.productLink,
                post.productPrice,
                post.maxParticipants,
                participant.id.countDistinct(),
                post.recruitmentDeadline,
                post.meetingLocation.placeName,
                post.meetingLocation.x,
                post.meetingLocation.y,
                isHost(),
                isParticipant(),
                post.createdAt
            )
        )
            .from(post)
            .join(post.host, user)
            .leftJoin(post.participants, participant)
            .leftJoin(userParticipant).on(userParticipant.post.id.eq(post.id))
            .where(post.id.eq(postId))
            .groupBy(post.id,
                post.host.id,
                post.title,
                post.description,
                post.settlementType,
                post.status,
                post.productImageUrl,
                post.productLink,
                post.productPrice,
                post.maxParticipants,
                post.recruitmentDeadline,
                post.meetingLocation.placeName,
                post.meetingLocation.x,
                post.meetingLocation.y,
                post.createdAt,
                post.host.nickname,
            )
            .fetchOne()
    }
}
