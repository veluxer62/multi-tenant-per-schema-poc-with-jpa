package com.example.demo.domain.board

import com.example.demo.domain.board.QBoard.board
import com.example.demo.domain.user.QUser.user
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

interface BoardRepository : JpaRepository<Board, UUID>, QBoardRepository {
    fun findByUserId(userId: UUID): Board
}

interface QBoardRepository {
    fun findAllByUserName(name: String): List<Board>
}

class QBoardRepositoryImpl : QBoardRepository, QuerydslRepositorySupport(Board::class.java) {
    override fun findAllByUserName(name: String): List<Board> {
        return from(board)
            .join(user).on(board.user.id.eq(user.id))
            .fetchJoin()
            .where(user.name.eq(name))
            .fetch()
    }
}
