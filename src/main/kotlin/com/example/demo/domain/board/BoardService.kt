package com.example.demo.domain.board

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BoardService(
    private val boardRepository: BoardRepository,
) {
    @Transactional
    fun create(board: Board): Board {
        return boardRepository.save(board)
    }

    fun getAll(): List<Board> {
        return boardRepository.findAll()
    }

    fun getAllByUserName(name: String): List<Board> {
        return boardRepository.findAllByUserName(name)
    }
}
